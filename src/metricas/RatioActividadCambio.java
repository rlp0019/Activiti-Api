package metricas;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Fecha;
import motormetricas.valores.Largo;

/**
 * Métrica RatioActividadCambio.
 * 
 * @author David Blanco Alonso.
 */
public class RatioActividadCambio extends Metrica {
	/**
	 * Constructor.
	 */
	public RatioActividadCambio() {
		descripcion = new Descripcion("Restricciones temporales", "RatioActividadCambio",
				"Muestra el número de cambios relativos al número de meses.",
				"¿Cuál es el número medio de cambios por mes?",
				"RAC = (NTC = Número total de cambios) / NM (Número de meses)", "RAC > 0 Mejor valores intermedios",
				"Ratio", "NTC contador, NM contador", "Repositorio GitHub de un proyecto");
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
		Fecha inicio = new Fecha(null);
		Fecha fin = new Fecha(null);

		for (Object x : lista) {
			if (inicio.getValor() == null
					|| inicio.getValor().after(((RepositoryCommit) x).getCommit().getAuthor().getDate())) {
				inicio.setValor(((RepositoryCommit) x).getCommit().getAuthor().getDate());
			}

			if (fin.getValor() == null
					|| fin.getValor().before(((RepositoryCommit) x).getCommit().getAuthor().getDate())) {
				fin.setValor(((RepositoryCommit) x).getCommit().getAuthor().getDate());
			}
		}

		Calendar calencadInicio = Calendar.getInstance();
		calencadInicio.setTime(inicio.getValor());

		Calendar calencadFin = Calendar.getInstance();
		calencadFin.setTime(fin.getValor());

		int mesesInicio = (calencadInicio.get(Calendar.YEAR) * 12) + calencadInicio.get(Calendar.MONTH);
		int mesesFin = (calencadFin.get(Calendar.YEAR) * 12) + calencadFin.get(Calendar.MONTH);

		int diffMeses = mesesFin - mesesInicio;

		if (diffMeses == 0) {
			diffMeses = 1;
		}

		return new Largo((double) lista.size() / diffMeses);
	}
}