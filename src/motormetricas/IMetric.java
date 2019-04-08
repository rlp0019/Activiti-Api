package motormetricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;

/**
 * Interface de las métricas.
 * 
 * @author David Blanco Alonso
 */
public interface IMetric {
	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista        lista con información necesaria para calcular la métrica.
	 * @param lista2       lista con información necesaria para calcular la métrica.
	 * @param metricResult objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor calculate(List<?> lista, List<?> lista2, ResultadoMetrica metricResult) throws IOException;

	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista        lista con información necesaria para calcular la métrica.
	 * @param metricResult objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor calculate(List<?> lista, ResultadoMetrica metricResult) throws IOException;

	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param dato         repositorio con información necesaria para calcular la
	 *                     métrica.
	 * @param metricResult objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor calculate(Repository dato, ResultadoMetrica metricResult) throws IOException;
}