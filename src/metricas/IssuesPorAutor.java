package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Issue;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;

/**
 * Métrica IssuesPorAutor.
 * 
 * @author David Blanco Alonso.
 */
public class IssuesPorAutor extends Metrica {
	/**
	 * Constructor.
	 */
	public IssuesPorAutor() {
		descripcion = new Descripcion("Equipo", "IssuesPorAutor",
				"Muestra el número de issues realizados por cada usuario participante en el proyecto",
				"¿Cuantos issues ha realizado cada usuario?", "IPA issues por usuario", "IPA >= 0 mejor valores altos",
				"Absoluta", "IPA contador", "Repositorio GitHub de un proyecto");
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
		Conjunto valores = new Conjunto();

		for (Object x : lista) {
			String autor = ((Issue) x).getUser().getLogin();
			Entero aux = new Entero(0);

			if (valores.getValor().containsKey(autor)) {
				aux = new Entero(valores.getValor(autor).getValor());
			}
			aux.setValor(aux.getValor() + 1);
			valores.setValor(autor, aux);
		}

		return valores;
	}
}