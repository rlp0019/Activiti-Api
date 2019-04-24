package lector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import metricas.CambioPorAutor;
import metricas.CommitPorDia;
import metricas.CommitPorMes;
import metricas.ContadorAutor;
import metricas.ContadorCambiosPico;
import metricas.ContadorTareas;
import metricas.DiasPrimerUltimoCommit;
import metricas.IssuesPorAutor;
import metricas.MediaDiasCambio;
import metricas.MediaDiasCierre;
import metricas.NumeroCambiosSinMensaje;
import metricas.NumeroFavoritos;
import metricas.NumeroIssues;
import metricas.NumeroIssuesCerradas;
import metricas.PorcentajeIssuesCerradas;
import metricas.RatioActividadCambio;
import metricas.RelacionMesPico;
import metricas.UltimaModificacion;
import motormetricas.Medida;
import motormetricas.Metrica;
import motormetricas.ResultadoMetrica;
import motormetricas.Valor;
import motormetricas.valores.Cadena;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;
import motormetricas.valores.Fecha;
import motormetricas.valores.Largo;

/**
 * Fachada encargada de calcular las metricas asignadas a GitHub.
 * 
 * @author David Blanco Alonso
 */
public class FachadaMetricasGitHub implements FachadaMetricas {
	/**
	 * Medidas resultantes del cálculo de las metricas.
	 */
	private ResultadoMetrica metricas;

	/**
	 * Metricas asignadas.
	 */
	private Metrica commitXAutor;
	private Metrica commitXDia;
	private Metrica commitXMes;
	private Metrica contadorAutor;
	private Metrica contadorCambiosPico;
	private Metrica contadorTareas;
	private Metrica diasPrimerUltimoCommit;
	private Metrica issueXAutor;
	private Metrica mediaDiasCambios;
	private Metrica mediaDiasCierre;
	private Metrica numCambiosSinMensaje;
	private Metrica numFavoritos;
	private Metrica numIssues;
	private Metrica numIssuesCerradas;
	private Metrica porcentajeIssuesCerradas;
	private Metrica ratioActividadCambio;
	private Metrica relacionMesPico;
	private Metrica ultimaModificacion;

	/**
	 * Formato para las salidas de tiopos Largo o double.
	 */
	private DecimalFormat formateador = new DecimalFormat("###0.00");

	/**
	 * constructor desde archivo.
	 * 
	 * @param archivo BufferedReader archivo leido.
	 */
	public FachadaMetricasGitHub(BufferedReader archivo) {
		metricas = new ResultadoMetrica();

		String linea = "";
		int nLinea = 0;
		Medida medida = null;
		try {
			linea = archivo.readLine();

			while (linea != null) {
				if (!linea.startsWith("//")) {
					switch (nLinea) {
					case 0:
						this.numIssues = new NumeroIssues();
						medida = new Medida(this.numIssues, new Entero(stringToInt(linea)));
						break;
					case 1:
						this.contadorTareas = new ContadorTareas();
						medida = new Medida(this.contadorTareas, new Largo(stringToDouble(linea)));
						break;
					case 2:
						this.numIssuesCerradas = new NumeroIssuesCerradas();
						medida = new Medida(this.numIssuesCerradas, new Entero(stringToInt(linea)));
						break;
					case 3:
						this.porcentajeIssuesCerradas = new PorcentajeIssuesCerradas();
						medida = new Medida(this.porcentajeIssuesCerradas, new Largo(stringToDouble(linea)));
						break;
					case 4:
						this.mediaDiasCierre = new MediaDiasCierre();
						medida = new Medida(this.mediaDiasCierre, new Largo(stringToDouble(linea)));
						break;
					case 5:
						this.numCambiosSinMensaje = new NumeroCambiosSinMensaje();
						medida = new Medida(this.numCambiosSinMensaje, new Entero(stringToInt(linea)));
						break;
					case 6:
						this.mediaDiasCambios = new MediaDiasCambio();
						medida = new Medida(this.mediaDiasCambios, new Largo(stringToDouble(linea)));
						break;
					case 7:
						this.diasPrimerUltimoCommit = new DiasPrimerUltimoCommit();
						medida = new Medida(this.diasPrimerUltimoCommit, new Largo(stringToDouble(linea)));
						break;
					case 8:
						this.ultimaModificacion = new UltimaModificacion();
						try {
							SimpleDateFormat formatoFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",
									Locale.US);
							medida = new Medida(this.ultimaModificacion,
									new Fecha(formatoFecha.parse(linea.substring(linea.indexOf(":") + 2))));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						break;
					case 9:
						this.commitXMes = new CommitPorMes();

						medida = calculateValor(archivo, linea, commitXMes);
						break;
					case 10:
						this.relacionMesPico = new RelacionMesPico();
						medida = new Medida(this.relacionMesPico, new Cadena(linea.substring(linea.indexOf(":") + 2)));
						break;
					case 11:
						this.contadorCambiosPico = new ContadorCambiosPico();
						medida = new Medida(this.contadorCambiosPico, new Largo(stringToDouble(linea)));
						break;
					case 12:
						this.ratioActividadCambio = new RatioActividadCambio();
						medida = new Medida(this.ratioActividadCambio, new Largo(stringToDouble(linea)));
						break;
					case 13:
						this.commitXDia = new CommitPorDia();

						medida = calculateValor(archivo, linea, commitXDia);
						break;
					case 14:
						this.commitXAutor = new CambioPorAutor();

						medida = calculateValor(archivo, linea, commitXAutor);
						break;
					case 15:
						this.contadorAutor = new ContadorAutor();
						medida = new Medida(this.contadorAutor, new Largo(stringToDouble(linea)));
						break;
					case 16:
						this.issueXAutor = new IssuesPorAutor();

						medida = calculateValor(archivo, linea, issueXAutor);
						break;
					case 17:
						this.numFavoritos = new NumeroFavoritos();
						medida = new Medida(this.numFavoritos, new Entero(stringToInt(linea)));
						break;
					default:
						break;
					}
					metricas.addMeasure(medida);
					nLinea++;
				}
				linea = archivo.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor desde información obtenida de GitHub.
	 * 
	 * @param repositorio Repository información del repositorio
	 * @param issues      lista con información de las issues.
	 * @param commits     lista con información de los commits.
	 * @throws IOException excepción de entrada o salida
	 */
	public FachadaMetricasGitHub(Repository repositorio, List<Issue> issues, List<RepositoryCommit> commits)
			throws IOException {
		metricas = new ResultadoMetrica();

		this.numIssues = new NumeroIssues();
		this.numIssues.calculate(issues, metricas);

		this.contadorTareas = new ContadorTareas();
		this.contadorTareas.calculate(issues, commits, metricas);

		this.numIssuesCerradas = new NumeroIssuesCerradas();
		this.numIssuesCerradas.calculate(issues, metricas);

		this.porcentajeIssuesCerradas = new PorcentajeIssuesCerradas();
		this.porcentajeIssuesCerradas.calculate(issues, metricas);

		this.mediaDiasCierre = new MediaDiasCierre();
		this.mediaDiasCierre.calculate(issues, metricas);

		this.numCambiosSinMensaje = new NumeroCambiosSinMensaje();
		this.numCambiosSinMensaje.calculate(commits, metricas);

		this.mediaDiasCambios = new MediaDiasCambio();
		this.mediaDiasCambios.calculate(commits, metricas);

		this.diasPrimerUltimoCommit = new DiasPrimerUltimoCommit();
		this.diasPrimerUltimoCommit.calculate(commits, metricas);

		this.ultimaModificacion = new UltimaModificacion();
		this.ultimaModificacion.calculate(commits, metricas);

		this.commitXMes = new CommitPorMes();
		this.commitXMes.calculate(commits, metricas);

		this.relacionMesPico = new RelacionMesPico();
		this.relacionMesPico.calculate(commits, metricas);

		this.contadorCambiosPico = new ContadorCambiosPico();
		this.contadorCambiosPico.calculate(commits, metricas);

		this.ratioActividadCambio = new RatioActividadCambio();
		this.ratioActividadCambio.calculate(commits, metricas);

		this.commitXDia = new CommitPorDia();
		this.commitXDia.calculate(commits, metricas);

		this.commitXAutor = new CambioPorAutor();
		this.commitXAutor.calculate(commits, metricas);

		this.contadorAutor = new ContadorAutor();
		this.contadorAutor.calculate(commits, metricas);

		this.issueXAutor = new IssuesPorAutor();
		this.issueXAutor.calculate(issues, metricas);

		this.numFavoritos = new NumeroFavoritos();
		this.numFavoritos.calculate(repositorio, metricas);
	}

	/**
	 * Convierte un string en double.
	 * 
	 * @param linea string con el valor a convertir.
	 * @return valor del double.
	 */
	private double stringToDouble(String linea) {
		return Double.parseDouble(linea.substring(linea.indexOf(":") + 2));
	}

	/**
	 * Convierte un string en int.
	 * 
	 * @param linea string con el valor a convertir.
	 * @return valor del int.
	 */
	private int stringToInt(String linea) {
		return Integer.parseInt(linea.substring(linea.indexOf(":") + 2));
	}

	private Medida calculateValor(BufferedReader archivo, String linea, Metrica metrica) {
		Conjunto valores = new Conjunto();
		int tamano = stringToInt(linea);
		for (int i = 0; i < tamano; i++) {
			String nuevaLinea = null;
			try {
				nuevaLinea = archivo.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nuevaLinea != null) {
				valores.setValor(nuevaLinea.substring(0, nuevaLinea.indexOf(":")), new Entero(stringToInt(nuevaLinea)));
			}
		}

		return new Medida(metrica, valores);
	}

	/**
	 * Realiza la comparacion con otra conexion.
	 * 
	 * @param comparacion conexion con la que comparar.
	 * @return String texto resultante de la comparacion.
	 */
	@Override
	public String comparar(FachadaConexion comparacion) {
		String resultadoComparacion = "";

		for (int i = 0; i < metricas.size(); i++) {
			switch (metricas.getMedida(i).getMetrica().getDescripcion().getNombre()) {
			case "NumeroIssues":
			case "NumeroCambiosSinMensaje":
				resultadoComparacion += comparaValores(comparacion, i, true, 0);
				break;
			case "NumeroIssuesCerradas":
			case "NumeroFavoritos":
				resultadoComparacion += comparaValores(comparacion, i, false, 0);
				break;
			case "ContadorTareas":
			case "PorcentajeIssuesCerradas":
			case "DiasPrimerUltimoCommit":
			case "ContadorCambiosPico":
			case "RatioActividadCambio":
				resultadoComparacion += comparaValores(comparacion, i, false, 1);
				break;
			case "MediaDiasCierre":
			case "MediaDiasCambio":
			case "ContadorAutor":
				resultadoComparacion += comparaValores(comparacion, i, true, 1);
				break;
			case "UltimaModificacion":
				resultadoComparacion += comparaValores(comparacion, i, false, 2);
				break;
			default:
				break;
			}
		}

		return resultadoComparacion;
	}

	/**
	 * Compara los valores de los distintos resultados de métricas.
	 * 
	 * @param comparacion conexion con la que comparar.
	 * @param i           índice de la métrica a comparar.
	 * @param menor       indica si menor es mejor.
	 * @param valor       tipo de valor, 0 es Entero, 1 es Largo y 2 es Fecha.
	 * @return texto resultante de la comparación.
	 */
	private String comparaValores(FachadaConexion comparacion, int i, boolean menor, int valor) {
		String color1 = "";
		String color2 = "";
		String resultadoComparacion = "";

		Valor valorMetrica = metricas.getMedida(i).getValue();
		Valor valorComparacion = comparacion.getMetricas().getResultadoMetrica().getMedida(i).getValue();

		if (menor) {
			color1 = "<td class=\"verde\">";
			color2 = "<td class=\"rojo\">";
		} else {
			color1 = "<td class=\"rojo\">";
			color2 = "<td class=\"verde\">";
		}

		resultadoComparacion += "<tr>";
		resultadoComparacion += "<td>" + metricas.getMedida(i).getMetrica().getDescripcion().getNombre() + "</td>";

		switch (valor) {
		case 0:
			resultadoComparacion += comparaEnteros(valorMetrica, valorComparacion, color1, color2);
			break;
		case 1:
			resultadoComparacion += comparaLargos(valorMetrica, valorComparacion, color1, color2);
			break;
		case 2:
			resultadoComparacion += comparaFechas(valorMetrica, valorComparacion, color1, color2);
			break;
		default:
			break;
		}

		resultadoComparacion += "</tr>";

		return resultadoComparacion;
	}

	/**
	 * Comparala los valores de los enteros.
	 * 
	 * @param i                índice de la métrica a comparar.
	 * @param valorMetrica     valor del primer resultado a comparar.
	 * @param valorComparacion valor del segundo resultado a comparar.
	 * @param color1           texto con el color de la primera comprobación.
	 * @param color2           texto con el color de la segunda comprobación.
	 * @return texto resultante de la comparación.
	 */
	private String comparaEnteros(Valor valorMetrica, Valor valorComparacion, String color1, String color2) {
		String resultadoComparacion = "";

		if (((Entero) valorMetrica).getValor() < ((Entero) valorComparacion).getValor()) {
			resultadoComparacion += color1 + ((Entero) valorMetrica).getValor() + "</td>";
			resultadoComparacion += color2 + ((Entero) valorComparacion).getValor() + "</td>";
		} else if (((Entero) valorMetrica).getValor() > ((Entero) valorComparacion).getValor()) {
			resultadoComparacion += color2 + ((Entero) valorMetrica).getValor() + "</td>";
			resultadoComparacion += color1 + ((Entero) valorComparacion).getValor() + "</td>";
		} else {
			resultadoComparacion += "<td>" + ((Entero) valorMetrica).getValor() + "</td>";
			resultadoComparacion += "<td>" + ((Entero) valorComparacion).getValor() + "</td>";
		}

		return resultadoComparacion;
	}

	/**
	 * Comparala los valores de los largos.
	 * 
	 * @param i                índice de la métrica a comparar.
	 * @param valorMetrica     valor del primer resultado a comparar.
	 * @param valorComparacion valor del segundo resultado a comparar.
	 * @param color1           texto con el color de la primera comprobación.
	 * @param color2           texto con el color de la segunda comprobación.
	 * @return texto resultante de la comparación.
	 */
	private String comparaLargos(Valor valorMetrica, Valor valorComparacion, String color1, String color2) {
		String resultadoComparacion = "";

		if (((Largo) valorMetrica).getValor() < ((Largo) valorComparacion).getValor()) {
			resultadoComparacion += color1 + formateador.format(((Largo) valorMetrica).getValor()) + "</td>";
			resultadoComparacion += color2 + formateador.format(((Largo) valorComparacion).getValor()) + "</td>";
		} else if (((Largo) valorMetrica).getValor() > ((Largo) valorComparacion).getValor()) {
			resultadoComparacion += color2 + formateador.format(((Largo) valorMetrica).getValor()) + "</td>";
			resultadoComparacion += color1 + formateador.format(((Largo) valorComparacion).getValor()) + "</td>";
		} else {
			resultadoComparacion += "<td>" + formateador.format(((Largo) valorMetrica).getValor()) + "</td>";
			resultadoComparacion += "<td>" + formateador.format(((Largo) valorComparacion).getValor()) + "</td>";
		}

		return resultadoComparacion;
	}

	/**
	 * Comparala los valores de las fechas.
	 * 
	 * @param i                índice de la métrica a comparar.
	 * @param valorMetrica     valor del primer resultado a comparar.
	 * @param valorComparacion valor del segundo resultado a comparar.
	 * @param color1           texto con el color de la primera comprobación.
	 * @param color2           texto con el color de la segunda comprobación.
	 * @return texto resultante de la comparación.
	 */
	private String comparaFechas(Valor valorMetrica, Valor valorComparacion, String color1, String color2) {
		String resultadoComparacion = "";

		if (((Fecha) valorMetrica).getValor().getTime() < ((Fecha) valorComparacion).getValor().getTime()) {
			resultadoComparacion += color1 + ((Fecha) valorMetrica).getValor() + "</td>";
			resultadoComparacion += color2 + ((Fecha) valorComparacion).getValor() + "</td>";
		} else if (((Fecha) valorMetrica).getValor().getTime() > ((Fecha) valorComparacion).getValor().getTime()) {
			resultadoComparacion += color2 + ((Fecha) valorMetrica).getValor() + "</td>";
			resultadoComparacion += color1 + ((Fecha) valorComparacion).getValor() + "</td>";
		} else {
			resultadoComparacion += "<td>" + ((Fecha) valorMetrica).getValor() + "</td>";
			resultadoComparacion += "<td>" + ((Fecha) valorComparacion).getValor() + "</td>";
		}

		return resultadoComparacion;
	}

	/**
	 * Genera un texto con los resultados obtenidos en las metricas.
	 * 
	 * @return String texto del informe.
	 */
	@Override
	public String generarArchivo() {
		String texto = "";
		for (int i = 0; i < metricas.size(); i++) {
			texto += metricas.getMedida(i).getMetrica().getDescripcion().getNombre() + ": "
					+ metricas.getMedida(i).getValue().toString() + "\n";
		}
		return texto;
	}

	/**
	 * Devuelve los resutlados de l as metricas.
	 * 
	 * @return ResultadoMetrica objeto con todas las medidas resultantes del cálculo
	 *         de métricas.
	 */
	@Override
	public ResultadoMetrica getResultadoMetrica() {
		return metricas;
	}

	/**
	 * Metodo que devuelve los resultados de las metricas en formato texto y
	 * gráfico.
	 * 
	 * @return Object[] resultados en formato texto y ChartPanel.
	 */
	@Override
	public Object[] getResultados() {
		Object[] resultados = new Object[6];

		resultados[0] = metricas.getMetricas();

		ObservableList<PieChart.Data> datosPieChart = FXCollections.observableArrayList();

		for (int i = 0; i < metricas.size(); i++) {
			switch (metricas.getMedida(i).getMetrica().getDescripcion().getNombre()) {
			case "NumeroIssues":
				datosPieChart
						.add(new PieChart.Data("Abiertas", ((Entero) metricas.getMedida(i).getValue()).getValor()));
				break;
			case "NumeroIssuesCerradas":
				datosPieChart
						.add(new PieChart.Data("Cerradas", ((Entero) metricas.getMedida(i).getValue()).getValor()));
				datosPieChart.get(0)
						.setPieValue(datosPieChart.get(0).getPieValue() - datosPieChart.get(1).getPieValue());

				datosPieChart
						.forEach(data -> data.nameProperty().set(data.getName() + " (" + data.getPieValue() + ")"));
				PieChart tarta = new PieChart(datosPieChart);
				tarta.setTitle("Issues");
				resultados[1] = tarta;
				break;
			case "CommitPorMes":
				resultados[2] = creaGraficoBarras(i, "Meses", "Nº Commits", "Commits por mes");
				break;
			case "CommitPorDia":
				resultados[3] = creaGraficoBarras(i, "Días", "Nº Commits", "Commits por día");
				break;
			case "CambioPorAutor":
				resultados[4] = creaGraficoBarras(i, "Autor", "Nº Commits", "Cambio por autor");
				break;
			case "IssuesPorAutor":
				resultados[5] = creaGraficoBarras(i, "Autor", "Nº Issues", "Issues por autor");
				break;
			default:
				break;
			}
		}

		return resultados;
	}

	private BarChart<String, Number> creaGraficoBarras(int i, String x, String y, String titulo) {
		Map<String, Entero> valores = ((Conjunto) metricas.getMedida(i).getValue()).getValor();
		ObservableList<String> valoresXAxis = FXCollections
				.observableArrayList(new ArrayList<String>(valores.keySet()));

		CategoryAxis xAxis = new CategoryAxis(valoresXAxis);
		xAxis.setLabel(x);

		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(false);
		yAxis.setTickUnit(2);
		yAxis.setMinorTickLength(1);
		yAxis.setLabel(y);

		BarChart<String, Number> barras = new BarChart<String, Number>(xAxis, yAxis);
		barras.setTitle(titulo);

		XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
		serie.setName(titulo);
		for (String valor : valoresXAxis) {
			serie.getData().add(new XYChart.Data<String, Number>(valor, valores.get(valor).getValor()));
		}

		barras.getData().add(serie);
		barras.setLegendVisible(false);

		return barras;
	}
}