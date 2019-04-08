package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;
import lector.csv.LectorCSV;
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
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";

		try {
			FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

			String[] repositorios = lector.getNombresRepositorio("pruebarlp");

			assertEquals(repositorios.length, 1);
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
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";

		FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

		lector.getNombresRepositorio("pruebarlp");

		lector.obtenerMetricas("pruebarlp", "ProyectoVacio");
	}

	/**
	 * Prueba sobre un repositorio de tamaño grande.
	 */
	@Test
	public void testRepositorioGrande() {
		String usuario;
		String password;
		usuario = "pruebarlp";
		password = "12qe34wr";
		FachadaConexion lector = null;

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

			assertEquals(cadena, resultadoMetricas[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba sobre el lector de archivos .csv.
	 */
	@Test
	public void testLectorCSV() {
		LectorCSV lectorCSV = LectorCSV.getInstance();
		assertFalse(lectorCSV.equals(null));
		assert (lectorCSV.getValores() instanceof List);
		lectorCSV = null;
	}

	/**
	 * Prueba de los valores de los cuartiles.
	 */
	@Test
	public void testQuartiles() {
		LectorCSV lectorCSV = LectorCSV.getInstance();
		CalculadoraPercentil calc = new CalculadoraPercentil();
		calc.calculaCuartiles(lectorCSV.getValores());

		assertEquals(calc.getQ1TotalIssues(), Double.valueOf(6.0));
		assertEquals(calc.getQ1IssuesPorCommit(), Double.valueOf(0.09));
		assertEquals(calc.getQ1PorcentajeIssuesCerrados(), Double.valueOf(88.5));
		assertEquals(calc.getQ1DiasPorIssue(), Double.valueOf(2.2199999999999998));
		assertEquals(calc.getQ1DiasEntreCommit(), Double.valueOf(1.0750000000000002));
		assertEquals(calc.getQ1TotalDias(), Double.valueOf(81.58000000000001));
		assertEquals(calc.getQ1CambioPico(), Double.valueOf(0.385));
		assertEquals(calc.getQ1ActividadPorMes(), Double.valueOf(6.0));

		assertEquals(calc.getQ3TotalIssues(), Double.valueOf(48.5));
		assertEquals(calc.getQ3IssuesPorCommit(), Double.valueOf(0.5900000000000001));
		assertEquals(calc.getQ3PorcentajeIssuesCerrados(), Double.valueOf(100.0));
		assertEquals(calc.getQ3DiasPorIssue(), Double.valueOf(20.66));
		assertEquals(calc.getQ3DiasEntreCommit(), Double.valueOf(4.77));
		assertEquals(calc.getQ3TotalDias(), Double.valueOf(201.54000000000002));
		assertEquals(calc.getQ3CambioPico(), Double.valueOf(0.64));
		assertEquals(calc.getQ3ActividadPorMes(), Double.valueOf(27.335));

	}
}