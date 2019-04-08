package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;

/**
 * Métrica CommitPorMes.
 * 
 * @author David Blanco Alonso.
 */
public class CommitPorMes extends Metrica {
	/**
	 * Constructor.
	 */
	public CommitPorMes() {
		descripcion = new Descripcion("Restricciones temporales", "CommitPorMes",
				"Muestra el número de commits realizados cada mes", "¿Cuántos commits se han realizado cada mes?",
				"CPM commits por mes", "CPM >= 0 mejor valores altos", "Absoluta", "CPM contador",
				"Repositorio GitHub de un proyecto");
	}

	/**
	 * Método que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista lista con información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor run(List<?> lista) throws IOException {
		return obtenerCambiosXMesGitHub(lista);
	}
}