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

	public void testConexion() {
		String usuario, password;
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
		String usuario, password;
		usuario = "pruebarlp";
		password = "12qe34wr";

		FachadaConexion lector = fabricaLector.crearFachadaConexion(usuario, password);

		lector.getNombresRepositorio("pruebarlp");

		lector.obtenerMetricas("pruebarlp", "ProyectoVacio");
	}
}