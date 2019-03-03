package gui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Panel con los componentes para realizar la conexion o seleccionar el modo
 * desconectado.
 * 
 * @author David Blanco Alonso
 */
public class PanelConexion extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Label para el campo de texto del usuario.
	 */
	private JLabel lblUsuario;

	/**
	 * Campo de texto para introducir el usuario con el que realizar la conexion.
	 */
	private JTextField txtUsuario;

	/**
	 * Label para el JPasswordField de la contrseña.
	 */
	private JLabel lblContrasena;

	/**
	 * Campo de texto protegido para introducir la contraseña del usuario.
	 */
	private JPasswordField txtpContrasena;

	/**
	 * Boton para realizar la conexion y continuar.
	 */
	private JButton btnConectar;

	/**
	 * boton para continuar en modo desconectado.
	 */
	private JButton btnDesconectado;

	/**
	 * Creamos el panel.
	 */
	public PanelConexion(final Principal aplicacion) {
		setLayout(null);
		this.setBounds(0, 0, 700, 470);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(276, 144, 148, 14);
		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setToolTipText("Usuario usuado para autentificarse en la plataforma.");
		txtUsuario.setBounds(276, 169, 148, 20);
		add(txtUsuario);
		txtUsuario.setColumns(10);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(276, 200, 148, 14);
		add(lblContrasena);

		txtpContrasena = new JPasswordField();
		txtpContrasena.setToolTipText("Contraseña para autentificar el usuario en la plataforma.");
		txtpContrasena.setBounds(276, 225, 148, 20);
		add(txtpContrasena);
		txtpContrasena.setColumns(10);

		btnConectar = new JButton("Conectar");
		btnConectar.setToolTipText(
				"<html><p width=\"500\">Conectar con el usuario seleccionado y pasar a seleccionar un repositorio.<br>Realizar la autentificacion aumenta la tasa de peticiones a 5000/hora.</p></html>");
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					aplicacion.frmFormulario.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));

					aplicacion.conexion = aplicacion.fabricaConexion.crearFachadaConexion(txtUsuario.getText(),
							String.valueOf(txtpContrasena.getPassword()));

					aplicacion.cargarPanel(aplicacion.pnlRepositorio);

					aplicacion.frmFormulario.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(aplicacion.frmFormulario, "Fallo al conectar.");
					aplicacion.frmFormulario.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		btnConectar.setBounds(276, 256, 148, 23);
		add(btnConectar);

		btnDesconectado = new JButton("Modo desconectado");
		btnDesconectado.setToolTipText(
				"<html><p width=\"500\">Ignorar la autentificación y pasar directamente a seleccionar el repositorio.<br>Si no se realiza la autentificacion la tasa de peticiones es de solo 60/hora.</p></html>");
		btnDesconectado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(aplicacion.frmFormulario,
						"Si usas este modo puedes obtener errores si alcanzas el limite de peticiones",
						"¿Estas seguro de usar el modo desconectado?", JOptionPane.YES_NO_OPTION);

				if (respuesta == JOptionPane.YES_OPTION) {
					aplicacion.conexion = aplicacion.fabricaConexion.crearFachadaConexion();

					aplicacion.cargarPanel(aplicacion.pnlRepositorio);
				}
			}
		});
		btnDesconectado.setBounds(276, 290, 148, 23);
		add(btnDesconectado);
	}
}
