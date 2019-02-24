package motormetricas;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import motormetricas.valores.Cadena;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Entero;
import motormetricas.valores.Fecha;
import motormetricas.valores.Largo;

/**
 * Objeto donde se guardan las medidas obtenidas.
 * 
 * @author David Blanco Alonso
 */
public class ResultadoMetrica {
	/**
	 * fecha de creación.
	 */
	private static Date fecha = new Date();

	/**
	 * Colección de medidas.
	 */
	private Vector<Medida> coleccionMedidas;

	/**
	 * Formato salida tipos Largo o double.
	 */
	private DecimalFormat formateador = new DecimalFormat("###0.00");

	/**
	 * Devuelve la fecha de creación.
	 * 
	 * @return Date fecha de creación.
	 */
	public static Date getFecha() {
		return fecha;
	}

	/**
	 * Constructor.
	 */
	public ResultadoMetrica() {
		coleccionMedidas = new Vector<Medida>();
	}

	/**
	 * Añade una medida a la colección.
	 * 
	 * @param medida Medida objeto a añadir.
	 */
	public void addMeasure(Medida medida) {
		coleccionMedidas.add(medida);
	}

	/**
	 * Devuelve la medida en la psosición i.
	 * 
	 * @param i int psosición de la coleccion.
	 * @return Medida medida encontrada en la psosición i.
	 */
	public Medida getMedida(int i) {
		return coleccionMedidas.get(i);
	}

	/**
	 * Devuleve los resultados de todas las medidas en modo texto.
	 * 
	 * @return String texto con los resultados.
	 */
	public String getMetricas() {
		String cadena = "";

		cadena += "Metricas:";
		for (Medida x : coleccionMedidas) {
			switch (x.getValue().getClass().getName()) {
			case "motorMetricas.valores.Entero":
				cadena += "\n  " + x.getMetrica().getDescripcion().getNombre() + ": "
						+ ((Entero) x.getValue()).getValor();
				break;
			case "motorMetricas.valores.Largo":
				cadena += "\n  " + x.getMetrica().getDescripcion().getNombre() + ": "
						+ formateador.format(((Largo) x.getValue()).getValor());
				break;
			case "motorMetricas.valores.Fecha":
				cadena += "\n  " + x.getMetrica().getDescripcion().getNombre() + ": "
						+ ((Fecha) x.getValue()).getValor().toString();
				break;
			case "motorMetricas.valores.Cadena":
				cadena += "\n  " + x.getMetrica().getDescripcion().getNombre() + ": "
						+ ((Cadena) x.getValue()).getValor();
				break;
			case "motorMetricas.valores.Conjunto":
				cadena += "\n  " + x.getMetrica().getDescripcion().getNombre() + ": ";
				Map<String, Entero> aux = ((Conjunto) x.getValue()).getValor();
				for (String key : aux.keySet()) {
					cadena += "\n\t" + key + ": " + aux.get(key).getValor();
				}
				break;
			default:
				cadena += x.getMetrica().getDescripcion().getProposito() + ": " + x.getValue();
			}
		}

		return cadena;
	}

	/**
	 * Tamaño de la coleccion.
	 * 
	 * @return int tamaño.
	 */
	public int size() {
		return coleccionMedidas.size();
	}
}