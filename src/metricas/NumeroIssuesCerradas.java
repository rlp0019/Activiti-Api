package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;

/**
 * Métrica NumeroIssuesCerradas.
 * 
 * @author David Blanco Alonso.
 */
public class NumeroIssuesCerradas extends Metrica {
	/**
	 * Constructor.
	 */
	public NumeroIssuesCerradas() {
		descripcion = new Descripcion("Proceso de orientación", "NumeroIssuesCerradas",
				"Número de issues cerradas total en el repositorio",
				"¿Cuántas issues se han cerrado en el repositorio?", "NIC número de issues cerradas",
				"NIC >= 0 mejor valores altos", "Absoluta", "NIC contador", "Repositorio GitHub de un proyecto");
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
		return obternerIssuesCerradasGitHub(lista);
	}
}