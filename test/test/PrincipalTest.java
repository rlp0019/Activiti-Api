package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;
import lector.csv.LectorCSV;
import motormetricas.csv.ManagerCSV;
import motormetricas.csv.SeparadorMetricas;
import percentiles.CalculadoraPercentil;

/**
 * Clase con las pruebas del proyecto.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class PrincipalTest {
	/**
	 * Fábrica del lector.
	 */
	private FabricaConexion fabricaLector;

	/**
	 * Inicialización de la fábrica antes de cada prueba.
	 */
	@Before
	public void setUp() {
		fabricaLector = FabricaConexionGitHub.getInstance();
	}

	/**
	 * Eliminación de la instancia del lector después de cada prueba.
	 */
	@After
	public void tearDown() {
		fabricaLector = null;
	}

	/**
	 * Prueba de la creación de una conexión.
	 */
	@Test
	public void testConexion() {
		String usuario = "pruebarlp";
		String password = "12qe34wr";

		try {
			FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);
			String[] repositorios = lector.getNombresRepositorio("pruebarlp");
			assertEquals("Comprobación de que únicamente existe un repositorio.", repositorios.length, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba del lanzamiento de excepción cuando el repositorio está vacío.
	 * 
	 * @throws IOException excepción de repositorio vacío
	 */
	@Test(expected = IOException.class)
	public void testRepositorioVacio() throws IOException {
		String usuario = "pruebarlp";
		String password = "12qe34wr";

		FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);
		lector.getNombresRepositorio("pruebarlp");
		lector.obtenerMetricas("pruebarlp", "ProyectoVacio");
	}

	/**
	 * Prueba sobre un repositorio de tamaño grande.
	 */
	@Test
	public void testRepositorioGrande() {
		String usuario = "pruebarlp";
		String password = "12qe34wr";
		FachadaConexion lector;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);
			lector.getNombresRepositorio("dba0010");
			lector.obtenerMetricas("dba0010", "Activiti-Api");
			Object[] resultadoMetricas = lector.getResultados();
			String cadena = "Metricas:" + "\n  NumeroIssues: 24" + "\n  ContadorTareas: 0,53"
					+ "\n  NumeroIssuesCerradas: 24" + "\n  PorcentajeIssuesCerradas: 100,00"
					+ "\n  MediaDiasCierre: 91,17" + "\n  NumeroCambiosSinMensaje: 0" + "\n  MediaDiasCambio: 5,41"
					+ "\n  DiasPrimerUltimoCommit: 243,29" + "\n  UltimaModificacion: Fri Feb 05 00:49:28 CET 2016"
					+ "\n  CommitPorMes: " + "\n\tEnero: 18" + "\n\tFebrero: 13" + "\n\tMarzo: 0" + "\n\tAbril: 0"
					+ "\n\tMayo: 0" + "\n\tJunio: 6" + "\n\tJulio: 1" + "\n\tAgosto: 0" + "\n\tSeptiembre: 0"
					+ "\n\tOctubre: 0" + "\n\tNoviembre: 2" + "\n\tDiciembre: 5" + "\n  RelacionMesPico: Enero"
					+ "\n  ContadorCambiosPico: 0,40" + "\n  RatioActividadCambio: 5,62" + "\n  CommitPorDia: "
					+ "\n\tDomingo: 2" + "\n\tLunes: 14" + "\n\tMartes: 5" + "\n\tMiercoles: 7" + "\n\tJueves: 7"
					+ "\n\tViernes: 2" + "\n\tSabado: 8" + "\n  CambioPorAutor: " + "\n\tdba0010: 43" + "\n\tNazalik: 2"
					+ "\n  ContadorAutor: 0,04" + "\n  IssuesPorAutor: " + "\n\tdba0010: 21" + "\n\tclopezno: 3"
					+ "\n  NumeroFavoritos: 1";

			assertEquals("Comparación de los resultados de las métricas del repositorio.", cadena,
					resultadoMetricas[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba sobre el cargado de métricas.
	 */
	@Test
	public void testCargar() {
		try {
			FachadaConexion lector = fabricaLector.crearFachadaConexion();
			File archivo = new File("rsc/informes/test.txt");
			FileReader lee = new FileReader(archivo);
			BufferedReader contenido = new BufferedReader(lee);
			contenido.readLine();

			lector.leerArchivo(contenido);

			Object[] resultadoMetricas = lector.getResultados();
			String cadena = "Metricas:" + "\n  NumeroIssues: 24" + "\n  ContadorTareas: 0,53"
					+ "\n  NumeroIssuesCerradas: 24" + "\n  PorcentajeIssuesCerradas: 100,00"
					+ "\n  MediaDiasCierre: 91,17" + "\n  NumeroCambiosSinMensaje: 0" + "\n  MediaDiasCambio: 5,41"
					+ "\n  DiasPrimerUltimoCommit: 243,29" + "\n  UltimaModificacion: Fri Feb 05 00:49:28 CET 2016"
					+ "\n  CommitPorMes: " + "\n\t Enero: 18" + "\n\t Febrero: 13" + "\n\t Marzo: 0" + "\n\t Abril: 0"
					+ "\n\t Mayo: 0" + "\n\t Junio: 6" + "\n\t Julio: 1" + "\n\t Agosto: 0" + "\n\t Septiembre: 0"
					+ "\n\t Octubre: 0" + "\n\t Noviembre: 2" + "\n\t Diciembre: 5" + "\n  RelacionMesPico: Enero"
					+ "\n  ContadorCambiosPico: 0,40" + "\n  RatioActividadCambio: 5,62" + "\n  CommitPorDia: "
					+ "\n\t Domingo: 2" + "\n\t Lunes: 14" + "\n\t Martes: 5" + "\n\t Miercoles: 7" + "\n\t Jueves: 7"
					+ "\n\t Viernes: 2" + "\n\t Sabado: 8" + "\n  CambioPorAutor: " + "\n\t dba0010: 43"
					+ "\n\t Nazalik: 2" + "\n  ContadorAutor: 0,04" + "\n  IssuesPorAutor: " + "\n\t dba0010: 21"
					+ "\n\t clopezno: 3" + "\n  NumeroFavoritos: 1";

			assertEquals("Comparación de los resultados de las métricas del repositorio.", cadena,
					resultadoMetricas[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba sobre el lector de archivos .csv.
	 */
	@Test
	public void testLectorCSV() {
		Path path = Paths.get("rsc/datoscsv/DataSet_EvolutionSoftwareMetrics_FYP.csv");
		LectorCSV lectorCSV = new LectorCSV(path);
		assertTrue(lectorCSV.getValores() instanceof List);
	}

	/**
	 * Prueba de los valores de los cuartiles.
	 */
	@Test
	public void testQuartiles() {
		Path path = Paths.get("rsc/datoscsv/PruebaCuartiles.csv");
		LectorCSV lectorCSV = new LectorCSV(path);
		CalculadoraPercentil calc = new CalculadoraPercentil();
		calc.calculaCuartiles(lectorCSV.getValores());

		assertEquals("Comparación del primer cuartil del total de issues.", calc.getQ1TotalIssues(),
				Double.valueOf(4.5));
		assertEquals("Comparación del primer cuartil del total de issues entre el total de commits.",
				calc.getQ1IssuesPorCommit(), Double.valueOf(0.08));
		assertEquals(
				"Comparación del primer cuartil del número de issues cerrados dividido entre el total de issues por 100.",
				calc.getQ1PorcentajeIssuesCerrados(), Double.valueOf(85.0));
		assertEquals("Comparación del primer cuartil de la media de días para cerrar un issue.",
				calc.getQ1DiasPorIssue(), Double.valueOf(2.0));
		assertEquals("Comparación del primer cuartil de la media de días entre commits.", calc.getQ1DiasEntreCommit(),
				Double.valueOf(1.07));
		assertEquals("Comparación del primer cuartil del total de días del proyecto.", calc.getQ1TotalDias(),
				Double.valueOf(79.98));
		assertEquals(
				"Comparación del primer cuartil del número de commits del mes que más ha habido dividido entre el número total de commits.",
				calc.getQ1CambioPico(), Double.valueOf(0.38));
		assertEquals(
				"Comparación del primer cuartil del total de commits dividido entre el total de meses del proyecto.",
				calc.getQ1ActividadPorMes(), Double.valueOf(6.0));

		assertEquals("Comparación del tercer cuartil del total de issues.", calc.getQ3TotalIssues(),
				Double.valueOf(48.5));
		assertEquals("Comparación del tercer cuartil del total de issues entre el total de commits.",
				calc.getQ3IssuesPorCommit(), Double.valueOf(0.5900000000000001));
		assertEquals(
				"Comparación del tercer cuartil del número de issues cerrados dividido entre el total de issues por 100.",
				calc.getQ3PorcentajeIssuesCerrados(), Double.valueOf(100.0));
		assertEquals("Comparación del tercer cuartil de la media de días para cerrar un issue.",
				calc.getQ3DiasPorIssue(), Double.valueOf(20.32));
		assertEquals("Comparación del tercer cuartil de la media de días entre commits.", calc.getQ3DiasEntreCommit(),
				Double.valueOf(4.68));
		assertEquals("Comparación del tercer cuartil del total de días del proyecto.", calc.getQ3TotalDias(),
				Double.valueOf(199.96));
		assertEquals(
				"Comparación del tercer cuartil del número de commits del mes que más ha habido dividido entre el número total de commits.",
				calc.getQ3CambioPico(), Double.valueOf(0.63));
		assertEquals(
				"Comparación del tercer cuartil del total de commits dividido entre el total de meses del proyecto.",
				calc.getQ3ActividadPorMes(), Double.valueOf(27.0));
	}

	/**
	 * Prueba del separador de los resultados de las métricas.
	 */
	@Test
	public void testSeparadorMetricasCSV() {
		Path path = Paths.get("rsc/datoscsv/DataSet_EvolutionSoftwareMetrics_FYP.csv");
		LectorCSV lectorCSV = new LectorCSV(path);
		CalculadoraPercentil calc = new CalculadoraPercentil();
		calc.calculaCuartiles(lectorCSV.getValores());

		String usuario = "pruebarlp";
		String password = "12qe34wr";
		FachadaConexion lector;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);
			lector.getNombresRepositorio("dba0010");
			lector.obtenerMetricas("dba0010", "Activiti-Api");

			SeparadorMetricas separador = new SeparadorMetricas(lector.getResultados()[0]);

			assertEquals("Comparación del total de issues.", separador.getTotalIssues(), 24.0, 0);
			assertEquals("Comparación del total de issues entre el total de commits.", separador.getIssuesPorCommit(),
					0.53, 0);
			assertEquals("Comparación del número de issues cerrados dividido entre el total de issues por 100.",
					separador.getPorcentajeCerrados(), 100.0, 0);
			assertEquals("Comparación de la media de días para cerrar un issue.", separador.getMediaDiasCierre(), 91.17,
					0);
			assertEquals("Comparación de la media de días entre commits.", separador.getMediaDiasEntreCommit(), 5.41,
					0);
			assertEquals("Comparación de los días entre el primer y último commit.", separador.getTotalDias(), 243.29,
					0);
			assertEquals(
					"Comparación del número de commits del mes que más ha habido dividido entre el número total de commits.",
					separador.getCambioPico(), 0.40, 0);
			assertEquals("Comparación del total de commits dividido entre el total de meses del proyecto.",
					separador.getActividadCambio(), 5.62, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba de la comparación entre un repositorio y los de la base de datos y el
	 * método para añadir las métricas calculadas del proyecto.
	 */
	@Test
	public void testManagerCSV() {
		Path path = Paths.get("rsc/datoscsv/DataSet_EvolutionSoftwareMetrics_FYP.csv");
		ManagerCSV manager;
		String usuario = "pruebarlp";
		String password = "12qe34wr";
		FachadaConexion lector = null;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);
			lector.obtenerMetricas("dba0010", "Activiti-Api");
			String proyecto = lector.getNombreRepositorio();

			manager = new ManagerCSV(path, lector.getResultados()[0]);

			assertEquals("Resultado comparación del total de issues.", manager.comparaTotalIssues(), 0);
			assertEquals("Resultado comparación del total de issues entre el total de commits.",
					manager.comparaIssuesPorCommit(), 1);
			assertEquals(
					"Resultado comparación del número de issues cerrados dividido entre el total de issues por 100.",
					manager.comparaPorcentajeCerrados(), 1);
			assertEquals("Resultado comparación de la media de días para cerrar un issue.",
					manager.comparaMediaDiasCierre(), -1);
			assertEquals("Resultado comparación de la media de días entre commits.",
					manager.comparaMediaDiasEntreCommit(), -1);
			assertEquals("Resultado comparación de los días entre el primer y último commit.",
					manager.comparaTotalDias(), 1);
			assertEquals(
					"Resultado comparación del número de commits del mes que más ha habido dividido entre el número total de commits.",
					manager.comparaCambioPico(), 1);
			assertEquals("Resultado comparación del total de commits dividido entre el total de meses del proyecto.",
					manager.comparaActividadCambio(), -1);

			int valoresPreAdd = manager.getNumeroProyectosCSV();

			if (manager.hasProyecto(proyecto)) {
				manager.addMetricasProyecto(proyecto);
				assertEquals("Comprobación de no añadir un proyecto porque ya está en la base de datos.", valoresPreAdd,
						manager.getNumeroProyectosCSV());
			} else {
				manager.addMetricasProyecto(proyecto);
				assertEquals("Comprobación de añadir un proyecto.", valoresPreAdd + 1, manager.getNumeroProyectosCSV());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}