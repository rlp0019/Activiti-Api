package motormetricas.valores;

import motormetricas.Valor;

/**
 * Calse para controlar los tipos double.
 * 
 * @author David Blanco Alonso.
 */
public class Largo implements Valor {
	/**
	 * Valor.
	 */
	private double valor;

	/**
	 * Constructor.
	 */
	public Largo() {
		valor = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param valor valor a asignar.
	 */
	public Largo(double valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el valor.
	 * 
	 * @return valor del double.
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Modifica el valor.
	 * 
	 * @param valor valor del double a asignar.
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Método toString.
	 * 
	 * @return valor del double en modo texto.
	 */
	@Override
	public String toString() {
		return String.valueOf(valor);
	}
}
