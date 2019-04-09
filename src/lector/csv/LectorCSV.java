package lector.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	 * Datos del .csv.
	 */
	private List<String> valores;

	/**
	 * Constructor del lector de .csv.
	 */
	public LectorCSV(Path path) {
		BufferedReader br = null;
		valores = new ArrayList<String>();
		try {
			br = new BufferedReader(Files.newBufferedReader(path));

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
	 * Devuelve una lista con los valores del .csv.
	 * 
	 * @return valores del .csv.
	 */
	public List<String> getValores() {
		return valores;
	}
}
