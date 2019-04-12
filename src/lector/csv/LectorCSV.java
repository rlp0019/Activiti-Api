package lector.csv;

import java.io.BufferedReader;
import java.io.FileWriter;
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
	 * Path del .csv.
	 */
	private Path path;

	/**
	 * Constructor del lector de .csv.
	 * 
	 * @param path path del archivo .csv.
	 */
	public LectorCSV(Path path) {
		this.path = path;
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

	/**
	 * Añade métricas al archivo .csv que contiene las métricas de todos los
	 * proyectos analizados si el proyecto no se encuentra incluido ya.
	 * 
	 * @param nombre   nombre del proyecto.
	 * @param metricas métricas a añadir al .csv.
	 */
	public void addMetricasProyecto(String nombre, String metricas) {
		FileWriter fw = null;

		if (!hasProyecto(nombre)) {
			try {
				fw = new FileWriter(path.toString(), true);
				fw.append(metricas);

				fw.flush();
				fw.close();

				reloadValores();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Vuelve a cargar los valores de las métricas de los proyectos en el archivo
	 * .csv.
	 */
	public void reloadValores() {
		valores.clear();
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
	 * Devuelve si el archivo .csv contiene un proyecto o no.
	 * 
	 * @param nombre nombre del proyecto.
	 * @return true si lo contiene o false si no lo contiene.
	 */
	public boolean hasProyecto(String nombre) {
		boolean contiene = false;

		for (String proyecto : valores) {
			String[] partes = proyecto.split(",");
			if (partes[0].contentEquals(nombre)) {
				contiene = true;
			}
		}

		return contiene;
	}
}
