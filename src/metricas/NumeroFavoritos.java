package metricas;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Entero;

/**
 * Métrica NumeroFavoritos.
 * 
 * @author David Blanco Alonso.
 */
public class NumeroFavoritos extends Metrica {
	/**
	 * Constructor.
	 */
	public NumeroFavoritos() {
		descripcion = new Descripcion("Proceso de orientación", "NumeroFavoritos",
				"Muestra el número de usuarios que han marcado al proyecto como favorito.",
				"¿Cuántos usuarios han declarado como favorito el proyecto?", "NF Favoritos",
				"NF >= 0 mejor valores altos", "Absoluta", "NF contador", "Repositorio GitHub de un proyecto");
	}

	/**
	 * Método que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * 
	 * @param dato repositorio con información necesaria para calcular la métrica.
	 * @return valor obtenido en la métrica.
	 * @throws IOException excepción de entrada o salida.
	 */
	@Override
	public Valor run(Repository dato) throws IOException {
		Entero entero = new Entero(dato.getWatchers());
		return entero;
	}
}