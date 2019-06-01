package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;
import lector.csv.LectorCSV;
import motormetricas.csv.ManagerCSV;
import motormetricas.csv.SeparadorMetricas;
import motormetricas.valores.Cadena;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;
import motormetricas.valores.Fecha;
import motormetricas.valores.Largo;
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
		// Es necesario para que se inicie JavaFX.
		new JFXPanel();

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
			List<String> repositorios = lector.getNombresRepositorio("pruebarlp");
			assertEquals("Comprobación de que únicamente existe un repositorio.", 1, repositorios.size());
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

			assertEquals("Comprobación del número de forks.", 2, lector.getNombresForks("dba0010").size());

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

		assertEquals("Comparación del primer cuartil del total de issues.", Double.valueOf(4.5),
				calc.getQ1TotalIssues());
		assertEquals("Comparación del primer cuartil del total de issues entre el total de commits.",
				Double.valueOf(0.08), calc.getQ1IssuesPorCommit());
		assertEquals(
				"Comparación del primer cuartil del número de issues cerrados dividido entre el total de issues por 100.",
				Double.valueOf(85.0), calc.getQ1PorcentajeIssuesCerrados());
		assertEquals("Comparación del primer cuartil de la media de días para cerrar un issue.", Double.valueOf(2.0),
				calc.getQ1DiasPorIssue());
		assertEquals("Comparación del primer cuartil de la media de días entre commits.", Double.valueOf(1.07),
				calc.getQ1DiasEntreCommit());
		assertEquals("Comparación del primer cuartil del total de días del proyecto.", Double.valueOf(79.98),
				calc.getQ1TotalDias());
		assertEquals(
				"Comparación del primer cuartil del número de commits del mes que más ha habido dividido entre el número total de commits.",
				Double.valueOf(0.38), calc.getQ1CambioPico());
		assertEquals(
				"Comparación del primer cuartil del total de commits dividido entre el total de meses del proyecto.",
				Double.valueOf(6.0), calc.getQ1ActividadPorMes());

		assertEquals("Comparación del tercer cuartil del total de issues.", Double.valueOf(48.5),
				calc.getQ3TotalIssues());
		assertEquals("Comparación del tercer cuartil del total de issues entre el total de commits.",
				Double.valueOf(0.59), calc.getQ3IssuesPorCommit());
		assertEquals(
				"Comparación del tercer cuartil del número de issues cerrados dividido entre el total de issues por 100.",
				Double.valueOf(100.0), calc.getQ3PorcentajeIssuesCerrados());
		assertEquals("Comparación del tercer cuartil de la media de días para cerrar un issue.", Double.valueOf(20.32),
				calc.getQ3DiasPorIssue());
		assertEquals("Comparación del tercer cuartil de la media de días entre commits.", Double.valueOf(4.68),
				calc.getQ3DiasEntreCommit());
		assertEquals("Comparación del tercer cuartil del total de días del proyecto.", Double.valueOf(199.96),
				calc.getQ3TotalDias());
		assertEquals(
				"Comparación del tercer cuartil del número de commits del mes que más ha habido dividido entre el número total de commits.",
				Double.valueOf(0.63), calc.getQ3CambioPico());
		assertEquals(
				"Comparación del tercer cuartil del total de commits dividido entre el total de meses del proyecto.",
				Double.valueOf(27.0), calc.getQ3ActividadPorMes());
	}

	/**
	 * Prueba del separador de los resultados de las métricas.
	 */
	@Test
	public void testSeparadorMetricasCSV() {
		Path path = Paths.get("rsc/datoscsv/PruebaCuartiles.csv");
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

			assertEquals("Comparación del total de issues.", 24.0, separador.getTotalIssues(), 0);
			assertEquals("Comparación del total de issues entre el total de commits.", 0.53,
					separador.getContadorCambios(), 0);
			assertEquals("Comparación del número de issues cerrados dividido entre el total de issues por 100.", 100.0,
					separador.getPorcentajeCerrados(), 0);
			assertEquals("Comparación de la media de días para cerrar un issue.", 91.17, separador.getMediaDiasCierre(),
					0);
			assertEquals("Comparación de la media de días entre commits.", 5.41, separador.getMediaDiasEntreCommit(),
					0);
			assertEquals("Comparación de los días entre el primer y último commit.", 243.29, separador.getTotalDias(),
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
		Path path = Paths.get("rsc/datoscsv/PruebaGuardar.csv");
		ManagerCSV manager;
		String usuario = "pruebarlp";
		String password = "12qe34wr";
		FachadaConexion lector = null;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);
			lector.obtenerMetricas("dba0010", "Activiti-Api");
			String proyecto = lector.getNombreRepositorio();

			manager = new ManagerCSV(path, lector.getResultados()[0]);

			assertEquals("Resultado comparación del total de issues.", 1, manager.comparaTotalIssues());
			assertEquals("Resultado comparación del total de issues entre el total de commits.", 1,
					manager.comparaIssuesPorCommit());
			assertEquals(
					"Resultado comparación del número de issues cerrados dividido entre el total de issues por 100.", 1,
					manager.comparaPorcentajeCerrados());
			assertEquals("Resultado comparación de la media de días para cerrar un issue.", -1,
					manager.comparaMediaDiasCierre());
			assertEquals("Resultado comparación de la media de días entre commits.", -1,
					manager.comparaMediaDiasEntreCommit());
			assertEquals("Resultado comparación de los días entre el primer y último commit.", -1,
					manager.comparaTotalDias());
			assertEquals(
					"Resultado comparación del número de commits del mes que más ha habido dividido entre el número total de commits.",
					1, manager.comparaCambioPico());
			assertEquals("Resultado comparación del total de commits dividido entre el total de meses del proyecto.",
					-1, manager.comparaActividadCambio());

			int valoresPreAdd = manager.getNumeroProyectosCSV();

			if (!manager.hasProyecto(proyecto)) {
				manager.addMetricasProyecto(proyecto, lector.getUrl());
				assertEquals("Comprobación de añadir un proyecto.", (valoresPreAdd + 1),
						manager.getNumeroProyectosCSV());

				int valoresAdd = manager.getNumeroProyectosCSV();
				manager.addMetricasProyecto(proyecto, lector.getUrl());
				assertEquals("Comprobación de no añadir un proyecto porque ya está en la base de datos.", valoresAdd,
						manager.getNumeroProyectosCSV());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testManagerCSVComparacion() {
		Path path = Paths.get("rsc/datoscsv/PruebaCuartiles.csv");
		ManagerCSV manager;
		String usuario = "pruebarlp";
		String password = "12qe34wr";
		FachadaConexion lector = null;

		try {
			lector = fabricaLector.crearFachadaConexion(usuario, password);
			lector.obtenerMetricas("dba0010", "Activiti-Api");

			manager = new ManagerCSV(path, lector.getResultados()[0]);

			assertFalse("Comprobación de que el CSV contiene datos.", null == manager.getCSV());

			String tablaEsperada = "<table>"
					+ "<tr><th style=\"background-color: #60b3dc; padding: 10px;\">Métrica</th><th style=\"background-color: #60b3dc; padding: 10px;\">Q1</th><th style=\"background-color: #60b3dc; padding: 10px;\">dba0010_Activiti-Api</th><th style=\"background-color: #60b3dc; padding: 10px;\">Q3</th><th style=\"background-color: #60b3dc; padding: 10px;\">Recomendado</th></tr>"
					+ "<html><head><style>table {border: 2px solid black; font-family: Sans-Serif; font-size: 16px; margin: 0 auto; text-align: center}table .titulo {background-color: #a3d3eb;  padding: 10px;}table td {background-color: #e9e9e9; padding: 10px;}table .rojo {background-color: #ffa0a0;}table .verde {background-color: #b2e5b2;}table .amarillo {background-color: #fffdbb;}</style></head>"
					+ "<body bgcolor='#e6f2ff'><td class=\"titulo\" colspan=\"5\">Proceso de orientación</td>"
					+ "<tr><td class=\"titulo\">NumeroIssues</td><td>4.5</td><td class=\"verde\">24.0</td><td>48.5</td><td>Mayor que Q1</td></tr>"
					+ "<tr><td class=\"titulo\">ContadorTareas</td><td>0.08</td><td class=\"verde\">0.53</td><td>0.59</td><td>Entre Q1 y Q3</td></tr>"
					+ "<tr><td class=\"titulo\">PorcentajeIssuesCerradas</td><td>85.0</td><td class=\"verde\">100.0</td><td>100.0</td><td>Mayor que Q1</td></tr>"
					+ "<tr><td class=\"titulo\">MediaDiasCierre</td><td>2.0</td><td class=\"rojo\">91.17</td><td>20.32</td><td>Entre Q1 y Q3</td></tr>"
					+ "<td class=\"titulo\" colspan=\"5\">Restricciones temporales</td>"
					+ "<tr><td class=\"titulo\">MediaDiasCambio</td><td>1.07</td><td class=\"rojo\">5.41</td><td>4.68</td><td>Entre Q1 y Q3</td></tr>"
					+ "<tr><td class=\"titulo\">DiasPrimerUltimoCommit</td><td>79.98</td><td class=\"rojo\">243.29</td><td>199.96</td><td>Entre Q1 y Q3</td></tr>"
					+ "<tr><td class=\"titulo\">ContadorCambiosPico</td><td>0.38</td><td class=\"verde\">0.4</td><td>0.63</td><td>Entre Q1 y Q3</td></tr>"
					+ "<tr><td class=\"titulo\">RatioActividadCambio</td><td>6.0</td><td class=\"rojo\">5.62</td><td>27.0</td><td>Entre Q1 y Q3</td></tr>"
					+ "</body></html>";

			assertEquals("Comprobación de la correcta creación de la tabla.", tablaEsperada,
					manager.creaTabla(lector.getNombreRepositorio()));

			assertEquals("Comprobación de cálculo de nota poco estricta correcto.", 4.0, manager.calculaNota(false), 0);
			assertEquals("Comprobación de cálculo de nota estricta correcto.", 4.0, manager.calculaNota(true), 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testComparacionRepositorios() {
		String archivo1 = "rsc/informes/Activiti-Roberto.txt";
		String archivo2 = "rsc/informes/Activiti-David.txt";

		try {
			FileReader lee1 = new FileReader(archivo1);
			FileReader lee2 = new FileReader(archivo2);

			BufferedReader contenido1 = new BufferedReader(lee1);
			BufferedReader contenido2 = new BufferedReader(lee2);

			String linea1 = "";
			String linea2 = "";

			if (((linea1 = contenido1.readLine()) != null) && ((linea2 = contenido2.readLine()) != null)
					&& "GitHub".equals(linea1) && "GitHub".equals(linea2)) {
				FabricaConexion fabrica2 = FabricaConexionGitHub.getInstance();
				FachadaConexion conexion1 = fabricaLector.crearFachadaConexion();
				FachadaConexion conexion2 = fabrica2.crearFachadaConexion();
				conexion1.leerArchivo(contenido1);
				conexion2.leerArchivo(contenido2);

				String esperado = "<td class=\"titulo\" colspan=\"3\">Proceso de orientación</td>"
						+ "<tr><td class=\"titulo\">NumeroIssues</td><td class=\"verde\">69</td><td class=\"rojo\">24</td></tr>"
						+ "<tr><td class=\"titulo\">ContadorTareas</td><td class=\"verde\">0,57</td><td class=\"rojo\">0,53</td></tr>"
						+ "<tr><td class=\"titulo\">NumeroIssuesCerradas</td><td class=\"verde\">69</td><td class=\"rojo\">24</td></tr>"
						+ "<tr><td class=\"titulo\">PorcentajeIssuesCerradas</td><td>100,00</td><td>100,00</td></tr>"
						+ "<tr><td class=\"titulo\">MediaDiasCierre</td><td class=\"verde\">3,48</td><td class=\"rojo\">91,17</td></tr>"
						+ "<tr><td class=\"titulo\">NumeroCambiosSinMensaje</td><td>0</td><td>0</td></tr>"
						+ "<td class=\"titulo\" colspan=\"3\">Restricciones temporales</td>"
						+ "<tr><td class=\"titulo\">MediaDiasCambio</td><td class=\"rojo\">12,13</td><td class=\"verde\">5,41</td></tr>"
						+ "<tr><td class=\"titulo\">DiasPrimerUltimoCommit</td><td class=\"verde\">1455,88</td><td class=\"rojo\">243,29</td></tr>"
						+ "<tr><td class=\"titulo\">UltimaModificacion</td><td class=\"verde\">Sat Jun 01 16:06:26 CEST 2019</td><td class=\"rojo\">Fri Feb 05 00:49:28 CET 2016</td></tr>"
						+ "<tr><td class=\"titulo\">ContadorCambiosPico</td><td class=\"verde\">0,21</td><td class=\"rojo\">0,40</td></tr>"
						+ "<tr><td class=\"titulo\">RatioActividadCambio</td><td class=\"rojo\">2,50</td><td class=\"verde\">5,62</td></tr>"
						+ "<td class=\"titulo\" colspan=\"3\">Equipo de desarrollo</td>"
						+ "<tr><td class=\"titulo\">ContadorAutor</td><td class=\"rojo\">0,05</td><td class=\"verde\">0,04</td></tr>"
						+ "<td class=\"titulo\" colspan=\"3\">Relevancia</td>"
						+ "<tr><td class=\"titulo\">NumeroFavoritos</td><td class=\"rojo\">0</td><td class=\"verde\">1</td></tr>";

				assertEquals("Comprobación de la correcta creación de la tabla de comparación.", esperado,
						conexion1.comparar(conexion2));
				assertTrue("Comprobación de comparación inversa distinta",
						conexion1.comparar(conexion2) != conexion2.comparar(conexion1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprobación de la generación del contenido del archivo de guardado.
	 */
	@Test
	public void testGeneracionArchivoGuardado() {
		String archivo = "rsc/informes/test.txt";
		String usuario = "pruebarlp";
		String password = "12qe34wr";

		FileReader lee = null;
		BufferedReader contenido = null;

		try {
			lee = new FileReader(archivo);
			contenido = new BufferedReader(lee);

			String linea = "";

			if (((linea = contenido.readLine()) != null) && "GitHub".equals(linea)) {

				FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);
				assertTrue("Comprobación de peticiones restantes.", lector.getPeticionesRestantes() >= 0);
				lector.leerArchivo(contenido);

				assertFalse("Comprobación de generación de informe.", null == lector.generarArchivo());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (lee != null) {
				try {
					lee.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (contenido != null) {
				try {
					contenido.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Comprobación de la creación de valores de métricas.
	 */
	@Test
	public void testValores() {
		Entero entero = new Entero(1);
		Entero entero2 = new Entero();

		assertEquals("Comprobación valor entero por parámetro.", 1, entero.getValor());
		assertEquals("Comprobación valor entero sin parámetro..", 0, entero2.getValor());

		Largo largo = new Largo(1.0);
		Largo largo2 = new Largo();

		assertEquals("Comprobación valor largo por parámetro.", 1.0, largo.getValor(), 0);
		assertEquals("Comprobación valor largo sin parámetro.", 0, largo2.getValor(), 0);

		Date date = new Date();
		Fecha fecha = new Fecha(date);
		Fecha fecha2 = new Fecha();

		assertEquals("Comprobación fecha por parámetro.", date, fecha.getValor());
		assertTrue("Comprobación fecha sin parámetro.", null != fecha2.getValor());

		String cadenaEjemplo = "ejemplo";
		Cadena cadena = new Cadena(cadenaEjemplo);
		Cadena cadena2 = new Cadena();

		assertEquals("Comprobación cadena por parámetro.", cadenaEjemplo, cadena.getValor());
		assertEquals("Comprobación cadena sin parámetro.", "", cadena2.getValor());

		String key = "clave";
		Conjunto conjunto = new Conjunto(key, entero);
		Conjunto conjunto2 = new Conjunto();

		assertEquals("Comprobación valor conjunto por parámetro.", entero, conjunto.getValor(key));
		assertTrue("Comprobación conjunto sin parámetro.", conjunto2.getValor().isEmpty());
	}
}