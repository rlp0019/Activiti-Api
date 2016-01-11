package metricas;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;

import motorMetricas.Descripcion;
import motorMetricas.Metrica;
import motorMetricas.Valor;
import motorMetricas.valores.Conjunto;
import motorMetricas.valores.Double;

public class CommitPorAutor extends Metrica
{
	private Descripcion descripcion;
		
	public CommitPorAutor()
	{
		descripcion = new Descripcion("Estadistica", "Numero de commits realizados por cada usuario participante", "Muestra el numero de commits realizados por cada usuario participante en el proyecto",
				"¿Cuantos commits ha realizado cada usuario?", "Cu commits por usuaurio", "Cu >= 0 mejor valores altos",
				"Absoluta", "Cu contador", "Repositorio GitHub de un proyecto");
	}
	
	public Valor run(List<?> lista) throws IOException
	{
		Conjunto valores = new Conjunto();
				
		for(Object x : lista)
		{
			String autor = ((RepositoryCommit) x).getCommit().getAuthor().getName();
			Double aux = new Double(0);
			
			if(valores.getValor().containsKey(autor))
			{
				aux = new Double(valores.getValor(autor).getValor());
			}
			aux.setValor(aux.getValor() + 1);
			valores.setValor( autor, aux);
		}
		
		return valores;
	}
	
	public Descripcion getDescripcion()
	{
		return descripcion;
	}

	public void check() 
	{		
	}
}