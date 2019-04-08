package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Fecha;

/**
 * Métrica UltimaModificacion.
 * 
 * @author David Blanco Alonso.
 */
public class UltimaModificacion extends Metrica {
	/**
	 * Constructor.
	 */
	public UltimaModificacion() {
		descripcion = new Descripcion("Restricciones temporales", "UltimaModificacion",
				"Fecha en la que se realizo el último cambio en el repositorio",
				"¿Cuando se realizo el último cambio en el repositorio?", "UM fecha", "", "Absoluta", "UM fecha",
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
		Fecha valor = new Fecha(null);

		for (Object x : lista) {
			if (valor.getValor() == null
					|| ((RepositoryCommit) x).getCommit().getAuthor().getDate().after(valor.getValor())) {
				valor.setValor(((RepositoryCommit) x).getCommit().getAuthor().getDate());
			}
		}

		return valor;
	}
}