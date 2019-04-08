package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Issue;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Largo;

/**
 * Métrica MediaDiasCierre.
 * 
 * @author David Blanco Alonso.
 */
public class MediaDiasCierre extends Metrica {
	/**
	 * Constructor.
	 */
	public MediaDiasCierre() {
		descripcion = new Descripcion("Proceso de orientacion", "MediaDiasCierre",
				"Muestra los días que se tarda en cerrar una issue, normalizado por el numero de issues cerradas",
				"¿Cuanto se tarda de media en cerrar una issue?",
				"MDC = D (suma de los días) / NIC (numero de issues cerradas)", "MDC >= 0 mejor valores bajos", "Ratio",
				"D contador, NIC contador", "Repositorio GitHub de un proyecto");
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
		double mediaDias = 0;
		int cerradas = 0;
		for (Object x : lista) {
			if (((Issue) x).getState().equals("closed")) {
				cerradas++;
				mediaDias += (((Issue) x).getClosedAt().getTime() - ((Issue) x).getCreatedAt().getTime())
						/ (1000 * 60 * 60 * 24);
			}
		}
		if (cerradas == 0) {
			cerradas = 1;
		}
		mediaDias /= cerradas;

		return new Largo(mediaDias);
	}
}