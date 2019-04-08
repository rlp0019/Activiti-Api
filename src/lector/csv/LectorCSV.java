package lector.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase del lector de .csv.
 * 
 * @author Roberto Luquero Peñacoba.
 *
 */
public final class LectorCSV {
	/**
	 * Instancia del lector.
	 */
	private static LectorCSV instancia;

	/**
	 * Datos del .csv.
	 */
	private List<String> valores;

	/**
	 * Constructor del lector de .csv.
	 */
	private LectorCSV() {
		BufferedReader br = null;
		valores = new ArrayList<String>();
		try {
			br = new BufferedReader(
					Files.newBufferedReader(Paths.get("rsc/datoscsv/DataSet_EvolutionSoftwareMetrics_FYP.csv")));

			String fila;
			while ((fila = br.readLine()) != null) {
				valores.add(fila);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Singleton para obtener la instancia del lector.
	 * 
	 * @return instancia del lector.
	 */
	public static LectorCSV getInstance() {
		if (instancia == null) {
			instancia = new LectorCSV();
		}
		return instancia;
	}

	/**
	 * Devuelve una lista con los valores del .csv.
	 * 
	 * @return valores del .csv.
	 */
	public List<String> getValores() {
		return valores;
	}
}
