package metricas;

import java.io.IOException;
import java.util.List;

import motormetricas.Descripcion;
import motormetricas.Metrica;
import motormetricas.Valor;
import motormetricas.valores.Cadena;
import motormetricas.valores.Conjunto;
import motormetricas.valores.Largo;

/**
 * Métrica RelacionMesPico
 * @author David Blanco Alonso
 */
public class RelacionMesPico extends Metrica
{
	/**
	 * Constructor.
	 */
	public RelacionMesPico()
	{
		descripcion = new Descripcion("Restricciones temporales", "RelacionMesPico", "Muestra el mes en que más cambios se han realizado.",
				"¿Cuál es el mes en que más cambios se han realizado?", "RMP mes en el que más cambios se han realizado", "",
				"Nominal", "RMP mes", "Repositorio GitHub de un proyecto");
	}
	
	/**
	 * Metodo que calcula la métrica y la guarda en el objeto ResultadoMetrica.
	 * @param lista List<?> información necesaria para calcular la métrica.
	 * @param metricResult ResultadoMetrica objeto donde guardar el resultado.
	 * @return Valor valor obtenido en la métrica.
	 * @throws IOException
	 */
	public Valor run(List<?> lista) throws IOException
	{
		Conjunto commits = obtenerCambiosXMesGitHub(lista);
		
		Largo max = new Largo(0);
		Cadena valor = new Cadena();
		
		for(String key : commits.getValor().keySet())
		{
			if(max.getValor() == 0 || max.getValor() < commits.getValor(key).getValor())
			{
				valor.setValor(key);
				max.setValor(commits.getValor(key).getValor());
			}
		}
		
		return valor;
	}
}