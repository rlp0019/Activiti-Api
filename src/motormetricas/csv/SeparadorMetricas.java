package motormetricas.csv;

/**
 * Clase que reparte los valores de los resultados de las métricas.
 * 
 * @author Roberto Luquero Peñacoba.
 *
 */
public class SeparadorMetricas {
	/**
	 * Número total de issues.
	 */
	private double totalIssues;

	/**
	 * Número de issues por cada commit.
	 */
	private double issuesPorCommit;

	/**
	 * Porcentaje de issues cerrados.
	 */
	private double porcentajeCerrados;

	/**
	 * Media de días en cerrar un issue.
	 */
	private double mediaDiasCierre;

	/**
	 * Media de días entre cada commit.
	 */
	private double mediaDiasEntreCommit;

	/**
	 * Total de días entre el primer commit y el último.
	 */
	private double totalDias;

	/**
	 * Relacción entre el mes que más cambios se han realizado y el total de
	 * cambios.
	 */
	private double cambioPico;

	/**
	 * Media de cambios por mes.
	 */
	private double actividadCambio;

	/**
	 * Constructor del separador.
	 * 
	 * @param resultadoMetricas métricas a separar.
	 */
	public SeparadorMetricas(Object resultadoMetricas) {
		String resultado = (String) resultadoMetricas;
		String[] partes = resultado.split("\\s+");

		totalIssues = stringToDouble(partes[2]);
		issuesPorCommit = stringToDouble(partes[4]);
		porcentajeCerrados = stringToDouble(partes[8]);
		mediaDiasCierre = stringToDouble(partes[10]);
		mediaDiasEntreCommit = stringToDouble(partes[14]);
		totalDias = stringToDouble(partes[16]);
		cambioPico = stringToDouble(partes[52]);
		actividadCambio = stringToDouble(partes[54]);
	}

	/**
	 * Obtiene un double a partir de un string.
	 * 
	 * @param parte string con el valor.
	 * @return valor del double.
	 */
	private Double stringToDouble(String parte) {
		String valor = parte.replaceAll(",", ".");
		return Double.parseDouble(valor);
	}

	/**
	 * Devuelve el total de issues.
	 * 
	 * @return total de issues.
	 */
	public double getTotalIssues() {
		return totalIssues;
	}

	/**
	 * Devuelve el número de issues por cada commit.
	 * 
	 * @return número de issues por cada commit.
	 */
	public double getIssuesPorCommit() {
		return issuesPorCommit;
	}

	/**
	 * Devuelve el porcentaje de issues cerrados.
	 * 
	 * @return porcentaje de issues cerrados.
	 */
	public double getPorcentajeCerrados() {
		return porcentajeCerrados;
	}

	/**
	 * Devuelve la media de días en cerrar un issue.
	 * 
	 * @return media de días en cerrar un issue.
	 */
	public double getMediaDiasCierre() {
		return mediaDiasCierre;
	}

	/**
	 * Devuelve la media de días entre cada commit.
	 * 
	 * @return media de días entre cada commit.
	 */
	public double getMediaDiasEntreCommit() {
		return mediaDiasEntreCommit;
	}

	/**
	 * Devuelve el total de días entre el primer y el último commit.
	 * 
	 * @return total de días entre el primer y el último commit.
	 */
	public double getTotalDias() {
		return totalDias;
	}

	/**
	 * Devuelve la relacción entre el mes que más cambios se han realizado y el
	 * total de cambios.
	 * 
	 * @return relacción entre el mes que más cambios se han realizado y el total de
	 *         cambios.
	 */
	public double getCambioPico() {
		return cambioPico;
	}

	/**
	 * Devuelve la media de cambios por mes.
	 * 
	 * @return media de cambios por mes.
	 */
	public double getActividadCambio() {
		return actividadCambio;
	}
}
