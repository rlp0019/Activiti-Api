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
	 * Lector y escritor de archivos .csv.
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

		return calc.comparaValor(valor, 0, 0);
	}

	/**
	 * Compara el número de issues por commit entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaIssuesPorCommit() {
		double valor = separador.getIssuesPorCommit();

		return calc.comparaValor(valor, 1, 1);
	}

	/**
	 * Compara el porcentaje de issues cerrados entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaPorcentajeCerrados() {
		double valor = separador.getPorcentajeCerrados();

		return calc.comparaValor(valor, 1, 2);
	}

	/**
	 * Compara la media de días de cierre de un issue entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 si es menor que Q1, 0 si está entre ambos o -1 si es mayor que Q3.
	 */
	public int comparaMediaDiasCierre() {
		double valor = separador.getMediaDiasCierre();

		return calc.comparaValor(valor, 0, 3);
	}

	/**
	 * Compara la media de días entre actualizaciones entre el proyecto y la base de
	 * datos.
	 * 
	 * @return 1 si es menor que Q1, 0 si está entre ambos o -1 si es mayor que Q3.
	 */
	public int comparaMediaDiasEntreCommit() {
		double valor = separador.getMediaDiasEntreCommit();

		return calc.comparaValor(valor, 0, 4);
	}

	/**
	 * Compara el total de días entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1, 0 si está entre ambos o 1 si es mayor que Q3.
	 */
	public int comparaTotalDias() {
		double valor = separador.getTotalDias();

		return calc.comparaValor(valor, 2, 5);
	}

	/**
	 * Compara la relacción entre el mes que más cambios se han realizado y el total
	 * de cambios entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaCambioPico() {
		double valor = separador.getCambioPico();

		return calc.comparaValor(valor, 1, 6);
	}

	/**
	 * Compara la relacción entre el mes que más cambios se han realizado y el total
	 * de cambios entre el proyecto y la base de datos.
	 * 
	 * @return -1 si es menor que Q1 o mayor que Q3, 1 si está entre ambos.
	 */
	public int comparaActividadCambio() {
		double valor = separador.getActividadCambio();

		return calc.comparaValor(valor, 1, 7);
	}

	/**
	 * Añade las métricas calculadas del proyecto al archivo .csv a través del
	 * lectorCSV.
	 * 
	 * @param nombre nombre del proyecto.
	 */
	public void addMetricasProyecto(String nombre) {
		String metricas = nombre + "," + Double.toString(separador.getTotalIssues()) + ","
				+ Double.toString(separador.getIssuesPorCommit()) + ","
				+ Double.toString(separador.getPorcentajeCerrados()) + ","
				+ Double.toString(separador.getMediaDiasCierre()) + ","
				+ Double.toString(separador.getMediaDiasEntreCommit()) + "," + Double.toString(separador.getTotalDias())
				+ "," + Double.toString(separador.getCambioPico()) + ","
				+ Double.toString(separador.getActividadCambio()) + "," + ",";
		lector.addMetricasProyecto(nombre, metricas);
	}

	/**
	 * Obtiene el el número de proyectos guardados en el .csv.
	 * 
	 * @return número de proyectos guardados.
	 */
	public int getNumeroProyectosCSV() {
		return lector.getValores().size();
	}

	/**
	 * Devuelve si el .csv contiene o no un proyecto dado un nombre.
	 * 
	 * @param nombre nombre del proyecto.
	 * @return true si lo contiene o false si no.
	 */
	public boolean hasProyecto(String nombre) {
		return lector.hasProyecto(nombre);
	}
}
