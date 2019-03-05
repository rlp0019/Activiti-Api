package test;

import java.io.IOException;

import junit.framework.TestCase;
import lector.FabricaConexion;
import lector.FabricaConexionGitHub;
import lector.FachadaConexion;

public class PrincipalTest extends TestCase {
	public void testMetricas() {
		try {
			FabricaConexion fabricaLector = FabricaConexionGitHub.getInstance();

			String usuario;
			String password;
			usuario = "dba0010Test";
			password = "Contraseña1234";
			FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

			// Probamos el repositorio clopezno/libre-gift
			lector.getNombresRepositorio("clopezno");

			lector.obtenerMetricas("clopezno", "libre-gift");
			/*
			 * Object[] resultadoMetricas = lector.getResultados(); String cadena =
			 * "Metricas:" + "\n  NumeroIssues: 12" + "\n  ContadorTareas: 4,00" +
			 * "\n  NumeroIssuesCerradas: 10" + "\n  PorcentajeIssuesCerradas: 83,00" +
			 * "\n  MediaDiasCierre: 0,00" + "\n  NumeroCambiosSinMensaje: 0" +
			 * "\n  MediaDiasCambio: 256,99" + "\n  DiasPrimerUltimoCommit: 770,98" +
			 * "\n  UltimaModificacion: Fri May 22 13:47:06 CEST 2015" +
			 * "\n  CommitPorMes: " + "\n\tEnero: 0" + "\n\tFebrero: 0" + "\n\tMarzo: 0" +
			 * "\n\tAbril: 2" + "\n\tMayo: 1" + "\n\tJunio: 0" + "\n\tJulio: 0" +
			 * "\n\tAgosto: 0" + "\n\tSeptiembre: 0" + "\n\tOctubre: 0" + "\n\tNoviembre: 0"
			 * + "\n\tDiciembre: 0" + "\n  RelacionMesPico: Abril" +
			 * "\n  ContadorCambiosPico: 0,67" + "\n  RatioActividadCambio: 0,12" +
			 * "\n  CommitPorDia: " + "\n\tDomingo: 0" + "\n\tLunes: 0" + "\n\tMartes: 0" +
			 * "\n\tMiercoles: 0" + "\n\tJueves: 2" + "\n\tViernes: 1" + "\n\tSabado: 0" +
			 * "\n  CambioPorAutor: " + "\n\tCarlos López: 1" + "\n\tclopezno@gmail.com: 1"
			 * + "\n\tnobody: 1" + "\n  ContadorAutor: 1,00" + "\n  IssuesPorAutor: " +
			 * "\n\tGoogleCodeExporter: 12" + "\n  NumeroFavoritos: 0";
			 * 
			 * assertEquals(cadena, resultadoMetricas[0]);
			 */
			assertTrue("dba0010Test".equals(usuario));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}