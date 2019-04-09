package percentiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Clase de la calculadora de los cuartiles.
 * 
 * @author Roberto Luquero Peñacoba
 *
 */
public class CalculadoraPercentil {
	/**
	 * Primer cuartil del total de issues.
	 */
	private Double q1_totalIssues;

	/**
	 * Tercer cuartil del total de issues.
	 */
	private Double q3_totalIssues;

	/**
	 * Primer cuartil de issues por commit.
	 */
	private Double q1_issuesPorCommit;

	/**
	 * Tercer cuartil de issues por commit.
	 */
	private Double q3_issuesPorCommit;

	/**
	 * Primer cuartil de porcentaje de issues cerrados.
	 */
	private Double q1_porcentajeIssuesCerrados;

	/**
	 * Tercer cuartil de porcentaje de issues cerrados.
	 */
	private Double q3_porcentajeIssuesCerrados;

	/**
	 * Primer cuartil de dias por issue.
	 */
	private Double q1_diasPorIssue;

	/**
	 * Tercer cuartil de dias por issue.
	 */
	private Double q3_diasPorIssue;

	/**
	 * Primer cuartil de dias entre commit.
	 */
	private Double q1_diasEntreCommit;

	/**
	 * Tercer cuartil de dias entre commit.
	 */
	private Double q3_diasEntreCommit;

	/**
	 * Primer cuartil del total de días.
	 */
	private Double q1_totalDias;

	/**
	 * Tercer cuartil del total de días.
	 */
	private Double q3_totalDias;

	/**
	 * Primer cuartil del cambio pico.
	 */
	private Double q1_cambioPico;

	/**
	 * Tercer cuartil del cambio pico.
	 */
	private Double q3_cambioPico;

	/**
	 * Primer cuartil de la actividad por mes.
	 */
	private Double q1_actividadPorMes;

	/**
	 * Tercer cuartil de la actividad por mes.
	 */
	private Double q3_actividadPorMes;

	/**
	 * Método que se apoya en otros para realizar el cálculo de los cuartiles.
	 * 
	 * @param datos lista de String con todos los datos.
	 */
	public void calculaCuartiles(List<String> datos) {
		List<Double> totalIssues = new ArrayList<Double>();
		List<Double> issuesPorCommit = new ArrayList<Double>();
		List<Double> porcentajeIssuesCerrados = new ArrayList<Double>();
		List<Double> diasPorIssue = new ArrayList<Double>();
		List<Double> diasEntreCommit = new ArrayList<Double>();
		List<Double> totalDias = new ArrayList<Double>();
		List<Double> cambioPico = new ArrayList<Double>();
		List<Double> actividadPorMes = new ArrayList<Double>();

		reparteDatos(datos, totalIssues, issuesPorCommit, porcentajeIssuesCerrados, diasPorIssue, diasEntreCommit,
				totalDias, cambioPico, actividadPorMes);

		asignaCuartiles(totalIssues, issuesPorCommit, porcentajeIssuesCerrados, diasPorIssue, diasEntreCommit,
				totalDias, cambioPico, actividadPorMes);
	}

	/**
	 * Reparte los datos en las respectivas listas.
	 * 
	 * @param datos                    lista de String con todos los datos.
	 * @param totalIssues              lista a rellenar con el total de issues.
	 * @param issuesPorCommit          lista a rellenar con los issues por commit.
	 * @param porcentajeIssuesCerrados lista a rellenar con el porcentaje de issues
	 *                                 cerrados.
	 * @param diasPorIssue             lista a rellenar con los dias por issue.
	 * @param diasEntreCommit          lista a rellenar con los dias entre commit.
	 * @param totalDias                lista a rellenar con el total de dias.
	 * @param cambioPico               lista a rellenar con el cambio pico máximo.
	 * @param actividadPorMes          lista a rellenar con la actividad máxima por
	 *                                 mes.
	 */
	private void reparteDatos(List<String> datos, List<Double> totalIssues, List<Double> issuesPorCommit,
			List<Double> porcentajeIssuesCerrados, List<Double> diasPorIssue, List<Double> diasEntreCommit,
			List<Double> totalDias, List<Double> cambioPico, List<Double> actividadPorMes) {
		boolean first = true;

		for (String valorFila : datos) {
			if (!first) {
				String[] partes = valorFila.split(",");

				anadeParte(partes[3], totalIssues);
				anadeParte(partes[4], issuesPorCommit);
				anadeParte(partes[5], porcentajeIssuesCerrados);
				anadeParte(partes[6], diasPorIssue);
				anadeParte(partes[7], diasEntreCommit);
				anadeParte(partes[8], totalDias);
				anadeParte(partes[9], cambioPico);
				anadeParte(partes[10], actividadPorMes);
			} else {
				first = false;
			}
		}
	}

	/**
	 * Añade un valor a una lista si contiene algún valor.
	 * 
	 * @param parte valor a añadir.
	 * @param lista lista donde se añade.
	 */
	private void anadeParte(String parte, List<Double> lista) {
		if (!("".contentEquals(parte))) {
			lista.add(Double.parseDouble(parte));
		}
	}

	/**
	 * Asigna el valor a cada cuartil.
	 * 
	 * @param totalIssues              lista a rellenar con el total de issues.
	 * @param issuesPorCommit          lista a rellenar con los issues por commit.
	 * @param porcentajeIssuesCerrados lista a rellenar con el porcentaje de issues
	 *                                 cerrados.
	 * @param diasPorIssue             lista a rellenar con los dias por issue.
	 * @param diasEntreCommit          lista a rellenar con los dias entre commit.
	 * @param totalDias                lista a rellenar con el total de dias.
	 * @param cambioPico               lista a rellenar con el cambio pico máximo.
	 * @param actividadPorMes          lista a rellenar con la actividad máxima por
	 *                                 mes.
	 */
	private void asignaCuartiles(List<Double> totalIssues, List<Double> issuesPorCommit,
			List<Double> porcentajeIssuesCerrados, List<Double> diasPorIssue, List<Double> diasEntreCommit,
			List<Double> totalDias, List<Double> cambioPico, List<Double> actividadPorMes) {
		q1_totalIssues = calculaPercentil(totalIssues, 25);
		q3_totalIssues = calculaPercentil(totalIssues, 75);
		q1_issuesPorCommit = calculaPercentil(issuesPorCommit, 25);
		q3_issuesPorCommit = calculaPercentil(issuesPorCommit, 75);
		q1_porcentajeIssuesCerrados = calculaPercentil(porcentajeIssuesCerrados, 25);
		q3_porcentajeIssuesCerrados = calculaPercentil(porcentajeIssuesCerrados, 75);
		q1_diasPorIssue = calculaPercentil(diasPorIssue, 25);
		q3_diasPorIssue = calculaPercentil(diasPorIssue, 75);
		q1_diasEntreCommit = calculaPercentil(diasEntreCommit, 25);
		q3_diasEntreCommit = calculaPercentil(diasEntreCommit, 75);
		q1_totalDias = calculaPercentil(totalDias, 25);
		q3_totalDias = calculaPercentil(totalDias, 75);
		q1_cambioPico = calculaPercentil(cambioPico, 25);
		q3_cambioPico = calculaPercentil(cambioPico, 75);
		q1_actividadPorMes = calculaPercentil(actividadPorMes, 25);
		q3_actividadPorMes = calculaPercentil(actividadPorMes, 75);
	}

	/**
	 * Calcula un percentil.
	 * 
	 * @param lista     lista con los datos.
	 * @param percentil percentil a calcular.
	 * @return valor del percentil.
	 */
	private Double calculaPercentil(List<Double> lista, double percentil) {
		DescriptiveStatistics estadisticas = new DescriptiveStatistics();

		for (Double val : lista) {
			estadisticas.addValue(val);
		}

		return estadisticas.getPercentile(percentil);
	}

	/**
	 * Devuelve el primer cuartil de total issues.
	 * 
	 * @return primer cuartil de total issues.
	 */
	public Double getQ1TotalIssues() {
		return q1_totalIssues;
	}

	/**
	 * Devuelve el tercer cuartil de total issues.
	 * 
	 * @return tercer cuartil de total issues.
	 */
	public Double getQ3TotalIssues() {
		return q3_totalIssues;
	}

	/**
	 * Devuelve el primer cuartil de issues por commit.
	 * 
	 * @return primer cuartil de issues por commit.
	 */
	public Double getQ1IssuesPorCommit() {
		return q1_issuesPorCommit;
	}

	/**
	 * Devuelve el tercer cuartil de issues por commit.
	 * 
	 * @return tercer cuartil de issues por commit.
	 */
	public Double getQ3IssuesPorCommit() {
		return q3_issuesPorCommit;
	}

	/**
	 * Devuelve el primer cuartil de porcentaje issues cerrados.
	 * 
	 * @return primer cuartil de porcentaje issues cerrados.
	 */
	public Double getQ1PorcentajeIssuesCerrados() {
		return q1_porcentajeIssuesCerrados;
	}

	/**
	 * Devuelve el tercer cuartil de porcentaje issues cerrados.
	 * 
	 * @return tercer cuartil de porcentaje issues cerrados.
	 */
	public Double getQ3PorcentajeIssuesCerrados() {
		return q3_porcentajeIssuesCerrados;
	}

	/**
	 * Devuelve el primer cuartil de días por issue.
	 * 
	 * @return primer cuartil de días por issue.
	 */
	public Double getQ1DiasPorIssue() {
		return q1_diasPorIssue;
	}

	/**
	 * Devuelve el tercer cuartil de días por issue.
	 * 
	 * @return tercer cuartil de días por issue.
	 */
	public Double getQ3DiasPorIssue() {
		return q3_diasPorIssue;
	}

	/**
	 * Devuelve el primer cuartil de días entre commit.
	 * 
	 * @return primer cuartil de días entre commit.
	 */
	public Double getQ1DiasEntreCommit() {
		return q1_diasEntreCommit;
	}

	/**
	 * Devuelve el tercer cuartil de días entre commit.
	 * 
	 * @return tercer cuartil de días entre commit.
	 */
	public Double getQ3DiasEntreCommit() {
		return q3_diasEntreCommit;
	}

	/**
	 * Devuelve el primer cuartil de total de días.
	 * 
	 * @return primer cuartil de total de días.
	 */
	public Double getQ1TotalDias() {
		return q1_totalDias;
	}

	/**
	 * Devuelve el tercer cuartil de total de días.
	 * 
	 * @return tercer cuartil de total de días.
	 */
	public Double getQ3TotalDias() {
		return q3_totalDias;
	}

	/**
	 * Devuelve el primer cuartil de cambio pico.
	 * 
	 * @return primer cuartil de cambio pico.
	 */
	public Double getQ1CambioPico() {
		return q1_cambioPico;
	}

	/**
	 * Devuelve el tercer cuartil de cambio pico.
	 * 
	 * @return tercer cuartil de cambio pico.
	 */
	public Double getQ3CambioPico() {
		return q3_cambioPico;
	}

	/**
	 * Devuelve el primer cuartil de actividad por mes.
	 * 
	 * @return primer cuartil de actividad por mes.
	 */
	public Double getQ1ActividadPorMes() {
		return q1_actividadPorMes;
	}

	/**
	 * Devuelve el tercer cuartil de actividad por mes.
	 * 
	 * @return tercer cuartil de actividad por mes.
	 */
	public Double getQ3ActividadPorMes() {
		return q3_actividadPorMes;
	}
}