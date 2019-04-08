package motormetricas.valores;

import java.util.LinkedHashMap;
import java.util.Map;

import motormetricas.Valor;

/**
 * Clase para controlar los tipos Map.
 * 
 * @author David Blanco Alonso.
 */
public class Conjunto implements Valor {
	/**
	 * Conjunto.
	 */
	private Map<String, Entero> conjunto = new LinkedHashMap<String, Entero>();

	/**
	 * Constructor.
	 */
	public Conjunto() {
		conjunto = new LinkedHashMap<String, Entero>();
	}

	/**
	 * Constructor.
	 * 
	 * @param key   clave del conjunto a asignar.
	 * @param valor valor del conjunto a asignar.
	 */
	public Conjunto(String key, Entero valor) {
		conjunto.put(key, valor);
	}

	/**
	 * Devuelve el valor.
	 * 
	 * @return el conjunto.
	 */
	public Map<String, Entero> getValor() {
		return conjunto;
	}

	/**
	 * Obtiene un valor para una clave.
	 * 
	 * @param key clave del conjunto.
	 * @return valor del conjunto.
	 */
	public Entero getValor(String key) {
		return conjunto.get(key);
	}

	/**
	 * Modifica el valor.
	 * 
	 * @param key   clave del conjunto.
	 * @param valor valor del conjunto.
	 */
	public void setValor(String key, Entero valor) {
		this.conjunto.put(key, valor);
	}

	/**
	 * Método toString.
	 * 
	 * @return valor del conjunto en modo texto.
	 */
	@Override
	public String toString() {
		String resultado = conjunto.size() + "\n";
		for (String key : conjunto.keySet()) {
			resultado += " " + key + ": " + conjunto.get(key).toString() + "\n";
		}
		resultado = resultado.substring(0, resultado.length() - 1);
		return resultado;
	}
}
