package motormetricas.csv;

import java.nio.file.Path;

import lector.csv.LectorCSV;
import percentiles.CalculadoraPercentil;

/**
 * Clase manager que realiza las comparaciones de un proyecto con los guardados
 * en la base de datos.
 * 
 * @author Roberto Luquero Peñacoba.
 *
 */
public class ManagerCSV {
	/**
	 * Lector de archivos .csb.
	 */
	private LectorCSV lector;

	/**
	 * Calculadora de cuartiles.
	 */
	private CalculadoraPercentil calc;

	/**
	 * Separador de los resultados de las métricas.
	 */
	private SeparadorMetricas separador;

	/**
	 * Constructor del mánager.
	 * 
	 * @param path       path de la base de datos en formato .csv.
	 * @param resultados resultados del análisis de métricas.
	 */
	public ManagerCSV(Path path, Object resultados) {
		lector = new LectorCSV(path);
		calc = new CalculadoraPercentil();
		calc.calculaCuartiles(lector.getValores());
		separador = new SeparadorMetricas(resultados);
	}

	/**
	 * Compara el total de issues entre el proyecto y la base de datos.
	 * 
	 * @return 1 si es menor que Q1, 0 si está entre ambos o -1 si es mayor que Q3.
	 */
	public int comparaTotalIssues() {
		double valor = separador.getTotalIssues();
		double min = calc.getQ1TotalIssues();
		double max = calc.getQ3TotalIssues();

		return comparaMenorMejor(valor, min, max);
	}

	/**
	 * Compara el número de issues por commit entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaIssuesPorCommit() {
		double valor = separador.getIssuesPorCommit();
		double min = calc.getQ1IssuesPorCommit();
		double max = calc.getQ3IssuesPorCommit();

		return comparaIntermedioMejor(valor, min, max);
	}

	/**
	 * Compara el porcentaje de issues cerrados entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaPorcentajeCerrados() {
		double valor = separador.getPorcentajeCerrados();
		double min = calc.getQ1PorcentajeIssuesCerrados();
		double max = calc.getQ3PorcentajeIssuesCerrados();

		return comparaIntermedioMejor(valor, min, max);
	}

	/**
	 * Compara la media de días de cierre de un issue entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 si es menor que Q1, 0 si está entre ambos o -1 si es mayor que Q3.
	 */
	public int comparaMediaDiasCierre() {
		double valor = separador.getMediaDiasCierre();
		double min = calc.getQ1DiasPorIssue();
		double max = calc.getQ3DiasPorIssue();

		return comparaMenorMejor(valor, min, max);
	}

	/**
	 * Compara la media de días entre actualizaciones entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 si es menor que Q1, 0 si está entre ambos o -1 si es mayor que Q3.
	 */
	public int comparaMediaDiasEntreCommit() {
		double valor = separador.getMediaDiasEntreCommit();
		double min = calc.getQ1DiasEntreCommit();
		double max = calc.getQ3DiasEntreCommit();

		return comparaMenorMejor(valor, min, max);
	}

	/**
	 * Compara el total de días entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1, 0 si está entre ambos o 1 si es mayor que Q3.
	 */
	public int comparaTotalDias() {
		double valor = separador.getTotalDias();
		double min = calc.getQ1TotalDias();
		double max = calc.getQ3TotalDias();

		return comparaMayorMejor(valor, min, max);
	}

	/**
	 * Compara la relacción entre el mes que más cambios se han realizado y el total
	 * de cambios entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaCambioPico() {
		double valor = separador.getCambioPico();
		double min = calc.getQ1CambioPico();
		double max = calc.getQ3CambioPico();

		return comparaIntermedioMejor(valor, min, max);
	}

	/**
	 * Compara la relacción entre el mes que más cambios se han realizado y el total
	 * de cambios entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaActividadCambio() {
		double valor = separador.getActividadCambio();
		double min = calc.getQ1ActividadPorMes();
		double max = calc.getQ3ActividadPorMes();

		return comparaIntermedioMejor(valor, min, max);
	}

	/**
	 * Compara un valor entre un mínimo y un máximo. Mejor cuanto menor es el valor.
	 * 
	 * @return 1 si es menor que min, 0 si está entre ambos o -1 si es mayor que
	 *         max.
	 */
	private int comparaMenorMejor(double valor, double min, double max) {
		int resultado = 0;

		if (valor < min) {
			resultado = 1;
		} else if (valor >= min && valor <= max) {
			resultado = 0;
		} else if (valor > max) {
			resultado = -1;
		}

		return resultado;
	}

	/**
	 * Compara un valor entre un mínimo y un máximo. Mejor cuanto mayor es el valor.
	 * 
	 * @return -1 si es menor que min, 0 si está entre ambos o 1 si es mayor que
	 *         max.
	 */
	private int comparaMayorMejor(double valor, double min, double max) {
		int resultado = 0;

		if (valor < min) {
			resultado = -1;
		} else if (valor >= min && valor <= max) {
			resultado = 0;
		} else if (valor > max) {
			resultado = 1;
		}

		return resultado;
	}

	/**
	 * Compara un valor entre un mínimo y un máximo. Mejor si está entre ambos.
	 * 
	 * @return 1 si está entre ambos o -1 si es mayor que max o menor que min.
	 */
	private int comparaIntermedioMejor(double valor, double min, double max) {
		int resultado = 0;

		if (valor >= min && valor <= max) {
			resultado = 1;
		} else {
			resultado = -1;
		}

		return resultado;
	}
}
