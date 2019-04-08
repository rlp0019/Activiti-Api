package motormetricas.valores;

import java.util.Date;

import motormetricas.Valor;

/**
 * Clase para controlar los tipos Date.
 * 
 * @author David Blanco Alonso.
 */
public class Fecha implements Valor {
	/**
	 * Valor
	 */
	private Date valor;

	/**
	 * Constructor.
	 */
	public Fecha() {
		valor = new Date();
	}

	/**
	 * Constructor.
	 * 
	 * @param valor a asignar.
	 */
	public Fecha(Date valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el valor.
	 * 
	 * @return valor de la fecha.
	 */
	public Date getValor() {
		return valor;
	}

	/**
	 * Modifica el valor.
	 * 
	 * @param valor valor de la fecha a asignar.
	 */
	public void setValor(Date valor) {
		this.valor = valor;
	}

	/**
	 * Método toString.
	 * 
	 * @return valor de la fecha en modo texto.
	 */
	@Override
	public String toString() {
		return valor.toString();
	}
}