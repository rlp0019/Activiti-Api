package motormetricas;

/**
 * Implementa todos los campos que componen la descripción detallada de una
 * métrica.
 * 
 * @author David Blanco Alonso
 */
public class Descripcion {
	/**
	 * Categoría de la métrica.
	 */
	private String categoria;

	/**
	 * Descripcion de la métrica.
	 */
	private String descripcion;

	/**
	 * Fórmula de la métrica.
	 */
	private String formula;

	/**
	 * Fuente de medición de la métrica.
	 */
	private String fuenteDeMedicion;

	/**
	 * Interpretación de la métrica.
	 */
	private String interpretacion;

	/**
	 * Nombre de la métrica.
	 */
	private String nombre;

	/**
	 * Propósito de la métrica.
	 */
	private String proposito;

	/**
	 * Tipo de escala de la métrica.
	 */
	private String tipoEscala;

	/**
	 * Tipo de medida de la métrica..
	 */
	private String tipoMedida;

	/**
	 * Constructor.
	 * 
	 * @param categoria        categoría de la métrica.
	 * @param nombre           nombre de la métrica.
	 * @param descripcion      descripción de la métrica.
	 * @param proposito        porpósito de la métrica.
	 * @param formula          fórmula de la métrica.
	 * @param interpretacion   interpretación de la métrica.
	 * @param tipoEscala       tipo de escala de la métrica.
	 * @param tipoMedida       tipo de medida de la métrica.
	 * @param fuenteDeMedicion fuente de medición de la métrica.
	 */
	public Descripcion(String categoria, String nombre, String descripcion, String proposito, String formula,
			String interpretacion, String tipoEscala, String tipoMedida, String fuenteDeMedicion) {
		this.categoria = categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.proposito = proposito;
		this.formula = formula;
		this.interpretacion = interpretacion;
		this.tipoEscala = tipoEscala;
		this.tipoMedida = tipoMedida;
		this.fuenteDeMedicion = fuenteDeMedicion;
	}

	/**
	 * Devuelve la categoria de la métrica.
	 * 
	 * @return categoria de la métrica.
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Devuelve la descripción de la métrica.
	 * 
	 * @return descripción de la métrica.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Devuelve la fórmula de la métrica.
	 * 
	 * @return fórmula de la métrica.
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * Devuelve la fuente de medición de la métrica.
	 * 
	 * @return fuente de medición de la métrica.
	 */
	public String getFuenteDeMedicion() {
		return fuenteDeMedicion;
	}

	/**
	 * Devuelve la interpretación de la métrica.
	 * 
	 * @return interpretación de la métrica.
	 */
	public String getInterpretacion() {
		return interpretacion;
	}

	/**
	 * Devuelve el nombre de la métrica.
	 * 
	 * @return nombre de la métrica.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el propósito de la métrica.
	 * 
	 * @return propósito de la métrica.
	 */
	public String getProposito() {
		return proposito;
	}

	/**
	 * Devuelve el tipo de escala de la métrica.
	 * 
	 * @return tipo de escala de la métrica.
	 */
	public String getTipoEscala() {
		return tipoEscala;
	}

	/**
	 * Devuelve el tipo de medida de la métrica.
	 * 
	 * @return tipo de medida de la métrica.
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}
}
