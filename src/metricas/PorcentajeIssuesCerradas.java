package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Largo;

/**
 * Métrica PorcentajeIssuesCerradas.
 * 
 * @author David Blanco Alonso.
 */
public class PorcentajeIssuesCerradas extends Metrica {
	/**
	 * Constructor.
	 */
	public PorcentajeIssuesCerradas() {
		descripcion = new Descripcion("Proceso de orientación", "PorcentajeIssuesCerradas",
				"Porcentaje de las issues cerradas en el repositorio",
				"¿Proporción de issues cerradas en el repositorio en función de las creadas?",
				"PIC = NIC (Número de issues cerradas) * 100 / NI (Número de issues)",
				"0 <= PIC <= 100 mejor valores altos", "Ratio", "NIC contador, NI contador",
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
		int cerradas = obternerIssuesCerradasGitHub(lista).getValor();

		if (lista.size() == 0) {
			return new Largo(0);
		} else {
			return new Largo(cerradas * 100 / lista.size());
		}
	}
}