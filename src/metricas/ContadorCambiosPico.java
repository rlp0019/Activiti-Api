package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Largo;

/**
 * Métrica ContadorCambiosPico.
 * 
 * @author David Blanco Alonso.
 */
public class ContadorCambiosPico extends Metrica {
	/**
	 * Constructor.
	 */
	public ContadorCambiosPico() {
		descripcion = new Descripcion("Restricciones temporales", "ContadorCambiosPico",
				"Muestra el número de cambios en el mes que mas se han realizado, normalizado sobre el número total de cambios.",
				"¿Cuál es la proporción de trabajo realizado en el mes con mayor número de cambios?",
				"CCP = NCMP (Número de cambios en el Mes Pico) / NTC (Número total de cambios)",
				"0 <= CCP <= 1 Mejor valores intermedios.", "Ratio", "NCMP contador, NTC contador",
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
		Conjunto commits = obtenerCambiosXMesGitHub(lista);

		Largo max = new Largo(0);

		for (String key : commits.getValor().keySet()) {
			if (max.getValor() == 0 || max.getValor() < commits.getValor(key).getValor()) {
				max.setValor(commits.getValor(key).getValor());
			}
		}

		if (lista.size() == 0) {
			return new Largo(0);
		} else {
			return new Largo(max.getValor() / lista.size());
		}
	}
}