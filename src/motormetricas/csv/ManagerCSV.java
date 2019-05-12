package motormetricas.csv;

import java.nio.file.Path;
import java.util.List;

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
	 * @return 1 -1 si es menor que Q1, 1 si es mayor que Q3, 0 si está entre ambos.
	 */
	public int comparaPorcentajeCerrados() {
		double valor = separador.getPorcentajeCerrados();

		return calc.comparaValor(valor, 2, 2);
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
	 * Compara la actividad por mes del proyecto y la base de datos.
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
	 * @return true si se han añadido o false de lo contrario.
	 */
	public boolean addMetricasProyecto(String nombre) {
		String metricas = nombre + "," + Double.toString(separador.getTotalIssues()) + ","
				+ Double.toString(separador.getIssuesPorCommit()) + ","
				+ Double.toString(separador.getPorcentajeCerrados()) + ","
				+ Double.toString(separador.getMediaDiasCierre()) + ","
				+ Double.toString(separador.getMediaDiasEntreCommit()) + "," + Double.toString(separador.getTotalDias())
				+ "," + Double.toString(separador.getCambioPico()) + ","
				+ Double.toString(separador.getActividadCambio());
		return lector.addMetricasProyecto(nombre, metricas);
	}

	/**
	 * Obtiene el el número de proyectos guardados en el .csv.
	 * 
	 * @return número de proyectos guardados.
	 */
	public int getNumeroProyectosCSV() {
		return lector.getValores().size();
	}

	public List<String> getCSV() {
		return lector.getValores();
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

	/**
	 * Crea una tabla html con la comparación de un proyecto y la base de datos en
	 * formato .csv.
	 * 
	 * @param nombre nombre del proyecto.
	 * @return String con la tabla en html.
	 */
	public String creaTabla(String nombre) {
		String tabla = "<table>";
		tabla += "<tr>";
		tabla += "<th>Métrica</th>";
		tabla += "<th>Q1</th>";
		tabla += "<th>" + nombre + "</th>";
		tabla += "<th>Q3</th>";
		tabla += "<th>Mejor valor</th>";
		tabla += "</tr>";

		tabla += "<html>" + "<head><style>" + "table {font-weight: bold; margin: 0 auto; text-align: center}"
				+ "table td {background-color: #C0C0C0; font-family: Sans-Serif;}"
				+ "table .rojo {background-color: #ffa0a0;}" + "table .verde {background-color: #b2e5b2;}"
				+ "table .amarillo {background-color: #fffdbb}" + "</style></head>" + "<body bgcolor='#e6f2ff'>";

		tabla += creaDatosTabla();

		tabla += "</body></html>";

		return tabla;
	}

	/**
	 * Devuelve un String con los datos de la comparación de las métricas del
	 * proyecto y los cuartiles en formato html.
	 * 
	 * @return String con los datos de las métricas.
	 */
	private String creaDatosTabla() {
		String datos = "";

		int issuesT = comparaTotalIssues();
		datos += addCelda("NumeroIssues", calc.getQ1TotalIssues(), calc.getQ3TotalIssues(), issuesT,
				separador.getTotalIssues(), 0);

		int issuesC = comparaIssuesPorCommit();
		datos += addCelda("ContadorTareas", calc.getQ1IssuesPorCommit(), calc.getQ3IssuesPorCommit(), issuesC,
				separador.getIssuesPorCommit(), 2);

		int issuesCerr = comparaPorcentajeCerrados();
		datos += addCelda("PorcentajeIssuesCerradas", calc.getQ1PorcentajeIssuesCerrados(),
				calc.getQ3PorcentajeIssuesCerrados(), issuesCerr, separador.getPorcentajeCerrados(), 1);

		int mediaCerr = comparaMediaDiasCierre();
		datos += addCelda("MediaDiasCierre", calc.getQ1DiasPorIssue(), calc.getQ3DiasPorIssue(), mediaCerr,
				separador.getMediaDiasCierre(), 0);

		int mediaEntre = comparaMediaDiasEntreCommit();
		datos += addCelda("MediaDiasCambio", calc.getQ1DiasEntreCommit(), calc.getQ3DiasEntreCommit(), mediaEntre,
				separador.getMediaDiasEntreCommit(), 0);

		int diasT = comparaTotalDias();
		datos += addCelda("DiasPrimerUltimoCommit", calc.getQ1TotalDias(), calc.getQ3TotalDias(), diasT,
				separador.getTotalDias(), 1);

		int cambioP = comparaCambioPico();
		datos += addCelda("ContadorCambiosPico", calc.getQ1CambioPico(), calc.getQ3CambioPico(), cambioP,
				separador.getCambioPico(), 2);

		int actividadC = comparaActividadCambio();
		datos += addCelda("RatioActividadCambio", calc.getQ1ActividadPorMes(), calc.getQ3ActividadPorMes(), actividadC,
				separador.getActividadCambio(), 2);

		return datos;
	}

	/**
	 * Califica un proyecto dependiendo de su comparación con la base de datos .csv.
	 * 
	 * @param estricto booleano para indicar si el cálculo es estricto o no.
	 * @return nota del proyecto.
	 */
	public double calculaNota(boolean estricto) {
		double nota = 0.0;

		int issuesT = comparaTotalIssues();
		nota += sumaNota(issuesT, estricto);

		int issuesC = comparaIssuesPorCommit();
		nota += sumaNota(issuesC, estricto);

		int issuesCerr = comparaPorcentajeCerrados();
		nota += sumaNota(issuesCerr, estricto);

		int mediaCerr = comparaMediaDiasCierre();
		nota += sumaNota(mediaCerr, estricto);

		int mediaEntre = comparaMediaDiasEntreCommit();
		nota += sumaNota(mediaEntre, estricto);

		int diasT = comparaTotalDias();
		nota += sumaNota(diasT, estricto);

		int cambioP = comparaCambioPico();
		nota += sumaNota(cambioP, estricto);

		int actividadC = comparaActividadCambio();
		nota += sumaNota(actividadC, estricto);

		return nota;
	}

	/**
	 * Suma un valor a la nota dependiendo de la comparación del proyecto con la
	 * base de datos .csv.
	 * 
	 * @param valor valor de la comparación.
	 * @return nota a sumar.
	 */
	private double sumaNota(int valor, boolean estricto) {
		double suma = 0.0;

		if (valor == 1) {
			suma += 1;
		} else if (valor == 0) {
			if (estricto) {
				suma += 0.5;
			} else {
				suma += 1;
			}
		}

		return suma;
	}

	/**
	 * Devuelve un String con una fila de celdas que contiene los datos de una
	 * métrica en formato html.
	 * 
	 * @param metrica   nombre de la métrica.
	 * @param q1        primer cuartil del .csv.
	 * @param q3        tercer cuartil del .csv.
	 * @param valorComp valor del proyecto.
	 * @param valorMet  valor de la métrica del proyecto.
	 * @return String con la fila de celdas.
	 */
	private String addCelda(String metrica, Double q1, Double q3, int valorComp, double valorMet, int mejor) {
		String datos = "";

		datos += "<tr>";
		datos += "<td>" + metrica + "</td>";
		datos += "<td>" + q1 + "</td>";
		datos += addClase(valorComp, valorMet);
		datos += "<td>" + q3 + "</td>";

		datos += "<td>";
		if (mejor == 0) {
			datos += "Menor o igual que Q1";
		} else if (mejor == 1) {
			datos += "Mayor o igual que Q3";
		} else {
			datos += "Entre Q1 y Q3";
		}
		datos += "</td>";

		datos += "</tr>";

		return datos;
	}

	/**
	 * Devuelve un String con una clase de un determinado color debido a su
	 * resultado en la comparación en formato html.
	 * 
	 * @param resultado resultado de la comparación.
	 * @return string con la clase de un color.
	 */
	private String addClase(int resultado, double valor) {
		String datos = "";

		if (resultado == 1) {
			datos += "<td class=\"verde\">";
		} else if (resultado == -1) {
			datos += "<td class=\"rojo\">";
		} else {
			datos += "<td class=\"amarillo\">";
		}

		datos += valor + "</td>";

		return datos;
	}
}
