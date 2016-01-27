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
import org.eclipse.egit.github.core.service.*;


public class FachadaConexionGitHub implements FachadaConexion
{
	private static FachadaConexionGitHub instancia;
	
	private String nombreRepositorio;

	private GitHubClient cliente;
	
	private RepositoryService servicioRepositorios;
	
	private IssueService servicioIssues;
	
	private CommitService servicioCommits;
	
	private List<Repository> repositorios;
	
	private Repository repositorio;
	
	private List<Issue> issues;
	
	private List<RepositoryCommit> commits;
	
	private String[] nombresRepositorio;
	
	private FachadaMetricas metricas;
		
	/*Constructor privado*/
	private FachadaConexionGitHub(String usuario, String password) throws IOException
	{
		cliente = new GitHubClient();
        cliente.setCredentials(usuario, password);
        this.servicioRepositorios = new RepositoryService(this.cliente);
		this.repositorios = this.servicioRepositorios.getRepositories(usuario);
	}
	
	/*Creacion de instancia y return de la misma*/
	public static FachadaConexionGitHub getInstance(String usuario, String password) throws IOException
	{
		instancia = new FachadaConexionGitHub(usuario, password);
		
		return instancia;
	}
	
	/*Constructor privado*/
	private FachadaConexionGitHub()
	{
		cliente = new GitHubClient();
        this.servicioRepositorios = new RepositoryService(this.cliente);
	}
	
	/*Creacion de instancia y return de la misma*/
	public static FachadaConexionGitHub getInstance()
	{
		instancia = new FachadaConexionGitHub();
		
		return instancia;
	}
	
	private void obtenerRepositorios(String usuario) throws IOException, IllegalArgumentException
	{
		this.servicioRepositorios = new RepositoryService(this.cliente);
		
		this.repositorios = this.servicioRepositorios.getRepositories(usuario);
				
		this.nombresRepositorio = new String[this.repositorios.size()];
		int contador = 0;
		for(Repository x : this.repositorios)
		{
			this.nombresRepositorio[contador] = x.getName();
			contador++;
		}
	}

	private void obtenerRepositorio(String usuario, RepositoryId repositorio) throws IOException
	{
		this.repositorio = this.servicioRepositorios.getRepository(usuario, repositorio.getName());
	}
	
	private void obtenerIssues(RepositoryId repositorio) throws IOException
	{
		this.servicioIssues = new IssueService(this.cliente);
		
		Map<String,String> filtro = new HashMap<String,String>();
		filtro.put("state", "all");
       	
		this.issues = this.servicioIssues.getIssues(repositorio, filtro);
	}
	
	private void obtenerCommits(RepositoryId repositorio) throws IOException 
	{
		this.servicioCommits = new CommitService(this.cliente);
		
		this.commits = this.servicioCommits.getCommits(repositorio);
	}
		
	public void obtenerMetricas(String usuario, String repositorio) throws IOException
	{
		this.metricas = null;
		RepositoryId idRepositorio = new RepositoryId(usuario,repositorio); 
		
		this.obtenerRepositorio(usuario, idRepositorio);
		this.obtenerIssues(idRepositorio);
		this.obtenerCommits(idRepositorio);	
		
		this.nombreRepositorio = idRepositorio.getOwner() + "_" + idRepositorio.getName();
				
		metricas = new FachadaMetricasGitHub(this.repositorio, this.issues, this.commits);
	}
	
	public Object[] getResultados()
	{		
		return this.metricas.getResultados();
	}
	
	public char[] generarArchivo()
	{
		String resultado = "GitHub\n" + this.nombreRepositorio + "\n" + this.metricas.generarArchivo();
		return resultado.toCharArray();
	}
	
	public void leerArchivo(BufferedReader archivo)
	{
		String linea = "";
		try 
		{
			if((linea = archivo.readLine()) != null)
			{
				this.nombreRepositorio = linea;
				this.metricas = new FachadaMetricasGitHub(archivo);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String[] getNombresRepositorio(String usuario) throws IOException 
	{
		this.obtenerRepositorios(usuario);
		
		return this.nombresRepositorio;
	}
	
	public String getNombreRepositorio()
	{
		return this.nombreRepositorio;
	}
	
	public int getPeticionesRestantes()
	{
		return this.cliente.getRemainingRequests();
	}
	
	public String comparar(FachadaConexion comparacion)
	{
		return metricas.comparar(comparacion);
	}
	
	public FachadaMetricas getMetricas()
	{
		return metricas;
	}
}
