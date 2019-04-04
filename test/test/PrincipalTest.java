package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;

public class PrincipalTest {
	private FabricaConexion fabricaLector;

	@Before
	public void setUp() {
		fabricaLector = FabricaConexionGitHub.getInstance();
	}

	@After
	public void tearDown() {
		fabricaLector = null;
	}

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
}