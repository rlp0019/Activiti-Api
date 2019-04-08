package motormetricas.valores;

import motormetricas.Valor;

/**
 * Clase para controlar los tipos enteros.
 * 
 * @author David Blanco Alonso.
 */
public class Entero implements Valor {
	/**
	 * Valor.
	 */
	private int valor;

	/**
	 * Constructor.
	 */
	public Entero() {
		valor = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param valor a asignar.
	 */
	public Entero(int valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el valor.
	 * 
	 * @return valor del entero.
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Modifica el valor.
	 * 
	 * @param valor a asignar.
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

	/**
	 * Método toString.
	 * 
	 * @return valor del entero en modo texto.
	 */
	@Override
	public String toString() {
		return Integer.toString(valor);
	}
}
