package lector;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Fachada para trabajar con GitHub.
 * 
 * @author David Blanco Alonso
 */
public class FachadaConexionGitHub implements FachadaConexion {
	/**
	 * instancia de la fachada.
	 */
	private static FachadaConexionGitHub instancia;

	/**
	 * GitHubClient cliente autenticado o no autenticado.
	 */
	private GitHubClient cliente;

	/**
	 * Commits obtenidos de la plataforma.
	 */
	private List<RepositoryCommit> commits;

	/**
	 * Issues obtenidas de la plataforma.
	 */
	private List<Issue> issues;

	/**
	 * Fachada que contiene las métricas calculadas para el repositorio.
	 */
	private FachadaMetricas metricas;

	/**
	 * String nombre del repositorio del que se ha realizado el calculo de métricas.
	 */
	private String nombreRepositorio;

	/**
	 * Nombres de los repositorios pertenecientes a un usuario.
	 */
	private String[] nombresRepositorio;

	/**
	 * Repositorio de trabajo.
	 */
	private Repository repositorio;

	/**
	 * Lista de los repositorios pertenecientes a un usuario.
	 */
	private List<Repository> repositorios;

	/**
	 * Servicio para trabajar con los repositorios de GitHub.
	 */
	private RepositoryService servicioRepositorios;

	/**
	 * Creación de instancia.
	 * 
	 * @return instancia de la fachada sin argumentos.
	 */
	public static FachadaConexionGitHub getInstance() {
		instancia = new FachadaConexionGitHub();

		return instancia;
	}

	/**
	 * Creación de instancia.
	 * 
	 * @param usuario  usuario del repositorio.
	 * @param password contraseña del repositorio.
	 * @return instancia de la fachada con argumentos.
	 * @throws IOException excepción de entrada o salida.
	 */
	public static FachadaConexionGitHub getInstance(String usuario, String password) throws IOException {
		instancia = new FachadaConexionGitHub(usuario, password);

		return instancia;
	}

	/**
	 * Constructor privado.
	 */
	private FachadaConexionGitHub() {
		cliente = new GitHubClient();
		this.servicioRepositorios = new RepositoryService(this.cliente);
	}

	/**
	 * Constructor privado.
	 * 
	 * @param usuario  usuario del repositorio.
	 * @param password contraseña del repositorio.
	 * @throws IOException excepción de entrada o salida.
	 */
	private FachadaConexionGitHub(String usuario, String password) throws IOException {
		cliente = new GitHubClient();
		cliente.setCredentials(usuario, password);
		this.servicioRepositorios = new RepositoryService(this.cliente);
		this.repositorios = this.servicioRepositorios.getRepositories(usuario);
	}

	/**
	 * Realiza la comparación de esta conexión y sus resultados con los resultados
	 * de otra conexión.
	 * 
	 * @param comparacion FachadaConexion conexión con la que realizar la
	 *                    comparación.
	 * @return resultado de la comparación(contine filas de tabla HTML).
	 */
	@Override
	public String comparar(FachadaConexion comparacion) {
		return metricas.comparar(comparacion);
	}

	/**
	 * Genera un informe con los resultados obtenidos en las métricas.
	 * 
	 * @return texto del informe.
	 */
	@Override
	public char[] generarArchivo() {
		String resultado = "GitHub\n" + this.nombreRepositorio + "\n" + this.metricas.generarArchivo();
		return resultado.toCharArray();
	}

	/**
	 * Devuelve la Fachada que contiene los resultados de las métricas calculadas.
	 * 
	 * @return métricas calculadas.
	 */
	@Override
	public FachadaMetricas getMetricas() {
		return metricas;
	}

	/**
	 * Devuelve el nombre del repositorio del que se ha realizado el calculo de las
	 * métricas.
	 * 
	 * @return nombre del repositorio.
	 */
	@Override
	public String getNombreRepositorio() {
		return this.nombreRepositorio;
	}

	/**
	 * Metodo que devuelve los repositorios que pertenecen al usuario indicado.
	 * 
	 * @param usuario String usuario del que obtner los repositorios.
	 * @return array que contiene todos los respositorios de ese usuario.
	 * @throws IOException excepción de entrada o salida
	 */
	@Override
	public String[] getNombresRepositorio(String usuario) throws IOException {
		this.obtenerRepositorios(usuario);

		return this.nombresRepositorio;
	}

	/**
	 * Devulve las peticiones restantes para el cliente existente.
	 * 
	 * @return int numero de peticiones.
	 */
	@Override
	public int getPeticionesRestantes() {
		return this.cliente.getRemainingRequests();
	}

	/**
	 * Metodo que devuelve los resultados de las metricas en formato texto y
	 * gráfico.
	 * 
	 * @return Object[] resultados en formato texto y ChartPanel.
	 */
	@Override
	public Object[] getResultados() {
		return this.metricas.getResultados();
	}

	/**
	 * Realiza la lectura de un informe.
	 * 
	 * @param archivo BufferedReader archivo del informe.
	 */
	@Override
	public void leerArchivo(BufferedReader archivo) {
		String linea = "";
		try {
			if ((linea = archivo.readLine()) != null) {
				this.nombreRepositorio = linea;
				this.metricas = new FachadaMetricasGitHub(archivo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la información de los commits para un repositorio dado.
	 * 
	 * @param repositorio RepositoryID repositorio del que obtener los commits.
	 * @throws IOException excepción de entrada o salida
	 */
	private void obtenerCommits(RepositoryId repositorio) throws IOException {
		CommitService servicioCommits = new CommitService(this.cliente);

		this.commits = servicioCommits.getCommits(repositorio);
	}

	/**
	 * Obtiene la información de las issues para un repositorio dado.
	 * 
	 * @param repositorio RepositoryID repositorio del que obtener las issues.
	 * @throws IOException excepción de entrada o salida
	 */
	private void obtenerIssues(RepositoryId repositorio) throws IOException {
		IssueService servicioIssues = new IssueService(this.cliente);

		Map<String, String> filtro = new HashMap<String, String>();
		filtro.put("state", "all");

		this.issues = servicioIssues.getIssues(repositorio, filtro);
	}

	/**
	 * Metodo por el que se realiza el calculo de las metricas, despues de solicitar
	 * la información del repositorio indicado.
	 * 
	 * @param usuario     String usuario propietario del repositorio.
	 * @param repositorio String repositorio seleccionado.
	 * @throws IOException excepción de entrada o salida
	 */
	@Override
	public void obtenerMetricas(String usuario, String repositorio) throws IOException {
		this.metricas = null;
		RepositoryId idRepositorio = new RepositoryId(usuario, repositorio);

		this.obtenerRepositorio(usuario, idRepositorio);
		this.obtenerIssues(idRepositorio);
		this.obtenerCommits(idRepositorio);

		this.nombreRepositorio = idRepositorio.getOwner() + "_" + idRepositorio.getName();

		metricas = new FachadaMetricasGitHub(this.repositorio, this.issues, this.commits);
	}

	/**
	 * Obtiene los datos de un repositorio dando su usuario y su id.
	 * 
	 * @param usuario     String Usuario al que pertenece el repositorio.
	 * @param repositorio RepositoryID id del repositorio buscado.
	 * @throws IOException excepción de entrada o salida
	 */
	private void obtenerRepositorio(String usuario, RepositoryId repositorio) throws IOException {
		this.repositorio = this.servicioRepositorios.getRepository(usuario, repositorio.getName());
	}

	/**
	 * Obtiene los repositorios pertenecientes a un usuario.
	 * 
	 * @param usuario String usuario del que buscar los repositorios.
	 * @throws IOException              excepción de entrada o salida
	 * @throws IllegalArgumentException excepción de parámetro erróneo
	 */
	private void obtenerRepositorios(String usuario) throws IOException, IllegalArgumentException {
		this.servicioRepositorios = new RepositoryService(this.cliente);

		this.repositorios = this.servicioRepositorios.getRepositories(usuario);

		this.nombresRepositorio = new String[this.repositorios.size()];
		int contador = 0;
		for (Repository x : this.repositorios) {
			this.nombresRepositorio[contador] = x.getName();
			contador++;
		}
	}
}
