package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import org.jfree.chart.ChartPanel;

/**
 * Panel que muestra los resultados del cálculo de las metricas de un
 * repositorio.
 * 
 * @author David Blanco Alonso.
 */
public class PanelResultados extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Creamos el panel.
	 * 
	 * @param aplicacion ventana principal.
	 */
	public PanelResultados(final Principal aplicacion) {
		setLayout(null);
		this.setBounds(0, 0, 700, 470);

		JTabbedPane tabpnlPrincipal = new JTabbedPane(JTabbedPane.TOP);
		tabpnlPrincipal.setBounds(0, 0, 695, 450);
		add(tabpnlPrincipal);

		JScrollPane scrpnlAuxiliar = new JScrollPane();

		JTextPane txtpnlResultados = new JTextPane();
		txtpnlResultados.setEditable(false);

		Object[] resultados = aplicacion.conexion.getResultados();
		for (Object x : resultados) {
			switch (x.getClass().getName()) {
			case "java.lang.String":
				txtpnlResultados.setText((String) x);
				scrpnlAuxiliar.setViewportView(txtpnlResultados);
				tabpnlPrincipal.addTab("Resultados", null, scrpnlAuxiliar, "Resultados obtenidos en formato texto.");
				break;
			case "org.jfree.chart.ChartPanel":
				JScrollPane scrPnlGRafico = new JScrollPane();
				scrPnlGRafico.setViewportView((ChartPanel) x);
				tabpnlPrincipal.addTab(((ChartPanel) x).getChart().getTitle().getText(), null, scrPnlGRafico,
						"Uno de los graficos de los resultados.");
				break;
			default:
				break;
			}
		}
	}
}
