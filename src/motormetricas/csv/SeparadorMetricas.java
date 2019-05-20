package motormetricas.csv;

import java.util.ArrayList;

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
	 * Contador de tareas normalizado.
	 */
	private double contadorTareas;

	/**
	 * Cambios sin mensaje.
	 */
	private double numeroCambiosSinMensaje;

	/**
	 * Fecha de la última modificación.
	 */
	private String ultimaModificacion;

	/**
	 * Commits por mes.
	 */
	private String commitPorMes;

	/**
	 * Mes con más commits.
	 */
	private String relacionMesPico;

	/**
	 * Commits por día.
	 */
	private String commitPorDia;

	/**
	 * Cambios por autor.
	 */
	private String cambioPorAutor;

	/**
	 * Número de autores normalizado sobre el número de cambios.
	 */
	private double contadorAutor;

	/**
	 * Número de issues por autor.
	 */
	private String issuesPorAutor;

	/**
	 * Número de favoritos.
	 */
	private double numeroFavoritos;

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
		contadorTareas = stringToDouble(partes[6]);
		porcentajeCerrados = stringToDouble(partes[8]);
		mediaDiasCierre = stringToDouble(partes[10]);
		numeroCambiosSinMensaje = stringToDouble(partes[12]);
		mediaDiasEntreCommit = stringToDouble(partes[14]);
		totalDias = stringToDouble(partes[16]);
		relacionMesPico = partes[50];
		cambioPico = stringToDouble(partes[52]);
		actividadCambio = stringToDouble(partes[54]);

		String[] partes2 = resultado.split("UltimaModificacion: ");
		partes2 = partes2[1].split("CommitPorMes: ");
		ultimaModificacion = partes2[0];

		partes2 = partes2[1].split("RelacionMesPico: ");
		commitPorMes = separaMultiplesHtml(partes2[0]);

		partes2 = partes2[1].split("CommitPorDia: ");
		partes2 = partes2[1].split("CambioPorAutor: ");
		commitPorDia = separaMultiplesHtml(partes2[0]);

		partes2 = partes2[1].split("ContadorAutor: ");
		cambioPorAutor = separaMultiplesEspacioHtml(partes2[0]);

		partes2 = partes2[1].split("IssuesPorAutor: ");
		contadorAutor = stringToDouble(partes2[0]);

		partes2 = partes2[1].split("NumeroFavoritos: ");
		issuesPorAutor = separaMultiplesEspacioHtml(partes2[0]);

		partes2 = partes2[1].split("\n");
		numeroFavoritos = stringToDouble(partes2[0]);
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
	 * Asigna los commits por mes o commits por dia en formato html.
	 * 
	 * @param valores String con el valor a asignar.
	 * @return resultado en formato html.
	 */
	private String separaMultiplesHtml(String valores) {
		String[] partes = valores.split("\\s+");
		String resultado = "";

		for (int i = 1; i < partes.length; i++) {
			if (i % 2 != 0) {
				if (i != 1) {
					resultado += "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";
				}
				resultado += partes[i];
				resultado += " ";
			} else {
				resultado += partes[i];

				if (i + 1 < partes.length) {
					resultado += "<br>";
				}
			}
		}

		return resultado;
	}

	/**
	 * Asigna los valores de los campos múltiples que contienen espacios en formato
	 * html.
	 * 
	 * @param valores String con el valor a asignar.
	 * @return resultado en formato html.
	 */
	private String separaMultiplesEspacioHtml(String valores) {
		String[] partes = valores.split(":");
		ArrayList<String> separados = new ArrayList<String>();

		for (int i = 0; i < partes.length; i++) {
			for (String s : partes[i].split("\n")) {
				separados.add(s);
			}
		}
		separados.remove(separados.size() - 1);

		String resultado = "";

		for (int i = 1; i < separados.size(); i++) {
			if (i % 2 != 0) {
				if (i != 1) {
					resultado += "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";
				}
				resultado += separados.get(i);
				resultado += ":";
			} else {
				resultado += separados.get(i);

				if (i + 1 < separados.size()) {
					resultado += "<br>";
				}
			}
		}

		return resultado;
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
	 * Devuelve el número de tareas normalizado.
	 * 
	 * @return número de tareas normalizado.
	 */
	public double getContadorTareas() {
		return contadorTareas;
	}

	/**
	 * Devuelve el número de cambios sin mensaje.
	 * 
	 * @return número de cambios sin mensaje.
	 */
	public double getNumeroCambiosSinMensaje() {
		return numeroCambiosSinMensaje;
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

	/**
	 * Devuelve la fecha de última modificación.
	 * 
	 * @return fecha de última modificación.
	 */
	public String getUltimaModificacion() {
		return ultimaModificacion;
	}

	/**
	 * Devuelve la cantidad de commits por cada mes en formato html.
	 * 
	 * @return commits por cada mes.
	 */
	public String getCommitPorMes() {
		return commitPorMes;
	}

	/**
	 * Devuelve el mes con más commits.
	 * 
	 * @return mes con más commits.
	 */
	public String getRelacionMesPico() {
		return relacionMesPico;
	}

	/**
	 * Devuelve la cantidad de commits por día en formato html.
	 * 
	 * @return commits por día.
	 */
	public String getCommitPorDia() {
		return commitPorDia;
	}

	/**
	 * Devuelve los cambios por autor en formato html.
	 * 
	 * @return cambios por autor.
	 */
	public String getCambioPorAutor() {
		return cambioPorAutor;
	}

	/**
	 * Devuelve el número de autores normalizado sobre el número de cambios.
	 * 
	 * @return número de autores normalizado.
	 */
	public double getContadorAutor() {
		return contadorAutor;
	}

	/**
	 * Devuelve el número de issues por autor en formato html.
	 * 
	 * @return número de issues por autor.
	 */
	public String getIssuesPorAutor() {
		return issuesPorAutor;
	}

	/**
	 * Devuelve el número de favoritos.
	 * 
	 * @return número de favoritos.
	 */
	public double getNumeroFavoritos() {
		return numeroFavoritos;
	}
}
