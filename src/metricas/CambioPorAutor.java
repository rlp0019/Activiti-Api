package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;

/**
 * Métrica CambioPorAutor.
 * 
 * @author David Blanco Alonso.
 */
public class CambioPorAutor extends Metrica {
	/**
	 * constructor
	 */
	public CambioPorAutor() {
		descripcion = new Descripcion("Equipo", "CambioPorAutor",
				"Muestra el número de commits realizados por cada usuario participante en el proyecto",
				"¿Cuántos commits ha realizado cada usuario?", "CA cambio por autor", "CA > 0 mejor valores altos",
				"Absoluta", "CA contador", "Repositorio GitHub de un proyecto");
	}

	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param lista lista con información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor run(List<?> lista) throws IOException {
		Conjunto valores = new Conjunto();

		for (Object x : lista) {
			String autor = ((RepositoryCommit) x).getCommit().getAuthor().getName();
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