package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import modelo.Controlador;

/**
 * Interfaz con las funciones de generar un par de claves RSA y exportar una clave publica
 *
 */
@SuppressWarnings("serial")
public class VentanaGeneradorClaves extends JFrame implements ActionListener{

	public static final String GENERAR_CLAVES = "Generar clave RSA";
	public static final String EXPORTAR_CLAVE_PUBLICA = "Exportar clave";
	
	private JPasswordField passFieldContrasena;
	private JPasswordField passFieldIngreseNuevamenteContrasena;
	
	private Inicio inicio;

	public VentanaGeneradorClaves(Inicio inicio) 
	{
		this.inicio = inicio;
		
		setTitle("Generador claves");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labContrasena = new JLabel("Password para clave privada");
		labContrasena.setBounds(30, 35, 261, 14);
		contentPane.add(labContrasena);
		
		JLabel labConfirmarContrasena = new JLabel("Ingrese nuevamente el password");
		labConfirmarContrasena.setBounds(30, 72, 261, 14);
		contentPane.add(labConfirmarContrasena);
		
		//----------------------------------------------------------------
		JButton butGenerarClaves = new JButton("Generar clave RSA");
		butGenerarClaves.setBounds(44, 113, 162, 23);
		contentPane.add(butGenerarClaves);
		butGenerarClaves.setActionCommand(GENERAR_CLAVES);
		butGenerarClaves.addActionListener((ActionListener) this);
		//----------------------------------------------------------------
		
		passFieldContrasena = new JPasswordField();
		passFieldContrasena.setBounds(301, 32, 153, 20);
		contentPane.add(passFieldContrasena);
		
		passFieldIngreseNuevamenteContrasena = new JPasswordField();
		passFieldIngreseNuevamenteContrasena.setBounds(301, 69, 153, 20);
		contentPane.add(passFieldIngreseNuevamenteContrasena);
		
		//----------------------------------------------------------------
		JButton butExportarClavePublica = new JButton("Exportar clave publica");
		butExportarClavePublica.setBounds(44, 161, 262, 23);
		contentPane.add(butExportarClavePublica);
		butExportarClavePublica.setActionCommand(EXPORTAR_CLAVE_PUBLICA);
		butExportarClavePublica.addActionListener((ActionListener) this);
		//----------------------------------------------------------------
		
		
		JButton butIrAFirma = new JButton("Volver a menu principal");
		butIrAFirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inicio.setVisible(true);
				dispose();
				
			}
		});
		butIrAFirma.setBounds(160, 228, 196, 23);
		contentPane.add(butIrAFirma);
	}


	public void generarClaves() 
	{
		Controlador controlador = inicio.getControlador();
		
		String pass1 = String.valueOf(passFieldContrasena.getPassword());
		String pass2 = String.valueOf(passFieldIngreseNuevamenteContrasena.getPassword());
		
		if(pass1.equals("") || pass2.equals(""))
		{
			JOptionPane.showMessageDialog(this, "El password no puede ser vacio","Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			if(pass1.equals(pass2)==false)
			{
				JOptionPane.showMessageDialog(this, "No coindice el password","Error",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				controlador.generarClave(pass1);
				JOptionPane.showMessageDialog(this, "Se generaron correctamente las claves","Respuesta",JOptionPane.INFORMATION_MESSAGE);
				
				passFieldContrasena.setText("");
				passFieldIngreseNuevamenteContrasena.setText("");
			}
		}
	}
	
	
	public void exportarClavePublica()
	{
		Controlador controlador = inicio.getControlador();
		
		//paso 1: obtener identificador (password) de la clave
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Digite el password:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancelar"};
		int option = JOptionPane.showOptionDialog(null, panel, "Clave",JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
		if(option == 0) 
		{
		    String password = new String(pass.getPassword());
		    
		    
		    JOptionPane.showMessageDialog(this, "Seleccione donde quiere guardar el archivo");
				
			//paso 2: seleccionar nombre de archivo para guardar
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Guardar archivo");   
			 
			int seleccion = fc.showSaveDialog(this);
			 
			if (seleccion == JFileChooser.APPROVE_OPTION) 
			{
			    File fichero = fc.getSelectedFile();
			    String rutaClavePublica = fichero.getAbsolutePath();
			   
			    try 
			    {
					controlador.exportarClavePublica(rutaClavePublica, password);
					JOptionPane.showMessageDialog(this, "Clave exportada exitosamente","Respuesta",JOptionPane.INFORMATION_MESSAGE);
				} 
			    catch (Exception e) 
			    {
					
			    		JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			    
			}    
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String comando = e.getActionCommand();
		
		if(comando.equals(GENERAR_CLAVES))
		{	
			generarClaves();	
		}
		else if(comando.equals(EXPORTAR_CLAVE_PUBLICA))
		{
			exportarClavePublica();
		}
		
	}
}
