package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Entero;

/**
 * Métrica NumeroIssues.
 * 
 * @author David Blanco Alonso.
 */
public class NumeroIssues extends Metrica {
	/**
	 * Constructor.
	 */
	public NumeroIssues() {
		descripcion = new Descripcion("Proceso de orientación", "NumeroIssues",
				"Número de issues total creadas en el repositorio", "¿Cuántas issues se han creado en el repositorio?",
				"NI número de issues", "NI >= 0 mejor valores bajos", "Absoluta", "NI contador",
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
		Entero entero = new Entero(lista.size());
		return entero;
	}
}