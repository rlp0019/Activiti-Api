package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Entero;

/**
 * Métrica NumeroCambiosSinMensaje.
 * 
 * @author David Blanco Alonso.
 */
public class NumeroCambiosSinMensaje extends Metrica {
	/**
	 * Constructor.
	 */
	public NumeroCambiosSinMensaje() {
		descripcion = new Descripcion("Proceso de orientación", "NumeroCambiosSinMensaje",
				"Número de cambios realizados que no tienen mensaje.", "¿Cuántos cambios se han realizado sin mensaje?",
				"NCSM número de cambios sin mensaje", "NCSM >= 0 mejor valores bajos", "Absoluta", "NCSM contador",
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
		Entero entero = new Entero(0);

		for (Object x : lista) {
			if ("".equals(((RepositoryCommit) x).getCommit().getMessage())) {
				entero.setValor(entero.getValor() + 1);
			}
		}

		return entero;
	}
}