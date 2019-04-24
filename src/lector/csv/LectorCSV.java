package lector.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
		valores = new ArrayList<String>();
		reloadValores();
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
	 * @return true si el archivo se ha guardado, false de lo contrario.
	 */
	public boolean addMetricasProyecto(String nombre, String metricas) {
		boolean guardado = false;
		Writer fw = null;

		if (!hasProyecto(nombre)) {
			try {
				fw = new OutputStreamWriter(new FileOutputStream(path.toString(), true), StandardCharsets.UTF_8);
				fw.append(metricas);
				fw.append("\n");

				guardado = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.flush();
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			File copia = new File(path.toString());
			if (copia.exists()) {
				copia.delete();
			}

			try {
				fw = new OutputStreamWriter(new FileOutputStream(path.toString(), false), StandardCharsets.UTF_8);

				for (String valor : valores) {
					String[] partes = valor.split(",");

					if (partes[0].contentEquals(nombre)) {
						valor = metricas;
					}

					fw.append(valor);
					fw.append("\n");
				}

				guardado = true;

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.flush();
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		reloadValores();

		return guardado;
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
