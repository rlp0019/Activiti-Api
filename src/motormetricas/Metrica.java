package motormetricas;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;

import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;

/**
 * Clase que implementa la interface IMetric.
 * 
 * @author David Blanco Alonso
 */
public abstract class Metrica implements IMetric {
	/**
	 * Descripción de la métrica.
	 */
	protected Descripcion descripcion;

	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista        lista con información necesaria para calcular la métrica.
	 * @param lista2       lista con información necesaria para calcular la métrica.
	 * @param metricResult ResultadoMetrica objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor calculate(List<?> lista, List<?> lista2, ResultadoMetrica metricResult) throws IOException {
		Valor valor = run(lista, lista2);
		Medida measure = new Medida(this, valor);
		metricResult.addMeasure(measure);
		return valor;
	}

	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista        lista con información necesaria para calcular la métrica.
	 * @param metricResult ResultadoMetrica objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor calculate(List<?> lista, ResultadoMetrica metricResult) throws IOException {
		Valor valor = run(lista);
		Medida measure = new Medida(this, valor);
		metricResult.addMeasure(measure);
		return valor;
	}

	/**
	 * Método que calcula la métrica y la guarda en el objeto metricResult.
	 * 
	 * @param dato         repositorio con información necesaria para calcular la
	 *                     métrica.
	 * @param metricResult objeto donde guardar el resultado.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor calculate(Repository dato, ResultadoMetrica metricResult) throws IOException {
		Valor valor = run(dato);
		Medida measure = new Medida(this, valor);
		metricResult.addMeasure(measure);
		return valor;
	}

	/**
	 * Devulve la descripción de la métrica.
	 * 
	 * @return descripción de la métrica.
	 */
	public Descripcion getDescripcion() {
		return descripcion;
	}

	/**
	 * Obtiene el número de cambios por mes.
	 * 
	 * @param lista lista con información necesaria, en este caso los commits.
	 * @return Conjunto valores obtenidos.
	 */
	protected Conjunto obtenerCambiosXMesGitHub(List<?> lista) {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };

		Conjunto valores = new Conjunto();
		Calendar fecha = Calendar.getInstance();

		for (String key : meses) {
			valores.setValor(key, new Entero(0));
		}

		int i = 0;
		for (Object x : lista) {
			fecha.setTime(((RepositoryCommit) x).getCommit().getAuthor().getDate());
			i = fecha.get(Calendar.MONTH);
			valores.setValor(meses[i], new Entero(valores.getValor(meses[i]).getValor() + 1));
		}

		return valores;
	}

	/**
	 * Obtiene el número de issues cerradas en GitHub.
	 * 
	 * @param lista lista con información necesaria, en este caso los issues.
	 * @return valor obtenido.
	 */
	protected Entero obternerIssuesCerradasGitHub(List<?> lista) {
		Entero entero = new Entero(0);

		for (Object x : lista) {
			if (((Issue) x).getState().equals("closed")) {
				entero.setValor(entero.getValor() + 1);
			}
		}

		return entero;
	}

	/**
	 * Calcula el valor de la métrica.
	 * 
	 * @param lista lista con información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor run(List<?> lista) throws IOException {
		return null;
	}

	/**
	 * Calcula el valor de la métrica.
	 * 
	 * @param lista  lista con información necesaria para calcular la métrica.
	 * @param lista2 lista con información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor run(List<?> lista, List<?> lista2) throws IOException {
		return null;
	}

	/**
	 * Calcula el valor de la metrica
	 * 
	 * @param dato Repository información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	public Valor run(Repository dato) throws IOException {
		return null;
	}

	/**
	 * Metodo toString.
	 */
	@Override
	public String toString() {
		if (descripcion != null) {
			return descripcion.getNombre();
		} else {
			return "";
		}
	}
}