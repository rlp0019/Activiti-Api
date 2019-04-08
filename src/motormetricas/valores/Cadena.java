package motormetricas.valores;

import motormetricas.Valor;

/**
 * Calse para manejar los tipos de valores String.
 * 
 * @author David Blanco Alonso.
 */
public class Cadena implements Valor {
	/**
	 * valor.
	 */
	private String valor;

	/**
	 * Constructor.
	 */
	public Cadena() {
		valor = "";
	}

	/**
	 * Constructor.
	 * 
	 * @param valor valor de la cadena.
	 */
	public Cadena(String valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el valor.
	 * 
	 * @return valor de la cadena.
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Modifica el valor.
	 * 
	 * @param valor valor a asignar.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Método toString.
	 * 
	 * @return valor de la cadena.
	 */
	@Override
	public String toString() {
		return valor;
	}
}
