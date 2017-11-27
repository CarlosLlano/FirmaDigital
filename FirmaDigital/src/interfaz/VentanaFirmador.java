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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Controlador;

/**
 * Interfaz con la funcion de firmar documentos
 *
 */
@SuppressWarnings("serial")
public class VentanaFirmador extends JFrame implements ActionListener{

	
	public static final String SUBIR_ARCHIVO = "Subir archivo";
	public static final String FIRMAR_ARCHIVO = "Firmar archivo";
	
	private JPasswordField passFieldContrasena;
	private JTextField txtNombreDocumento;
	
	private Inicio inicio;
	private String rutaArchivo;

	public VentanaFirmador(Inicio inicio) 
	{
		
		this.inicio = inicio;
		rutaArchivo="";
		
		setTitle("Firmador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//--------------------------------------------------------
		JButton butSubirDocumento = new JButton("Subir documento");
		butSubirDocumento.setActionCommand(SUBIR_ARCHIVO);
		butSubirDocumento.addActionListener((ActionListener) this);
		butSubirDocumento.setBounds(0, 39, 242, 23);
		contentPane.add(butSubirDocumento);
		//--------------------------------------------------------
		
		passFieldContrasena = new JPasswordField();
		passFieldContrasena.setBounds(252, 89, 158, 20);
		contentPane.add(passFieldContrasena);
		
		JLabel labIngreseContrasena = new JLabel("Password clave privada",SwingConstants.CENTER);
		
		labIngreseContrasena.setBounds(0, 92, 242, 14);
		contentPane.add(labIngreseContrasena);
		
		//--------------------------------------------------------
		JButton butFirmarDocumento = new JButton("Firmar documento");
		butFirmarDocumento.setActionCommand(FIRMAR_ARCHIVO);
		butFirmarDocumento.addActionListener((ActionListener) this);
		butFirmarDocumento.setBounds(126, 142, 176, 23);
		contentPane.add(butFirmarDocumento);
		//--------------------------------------------------------
		
		txtNombreDocumento = new JTextField();
		txtNombreDocumento.setEditable(false);
		txtNombreDocumento.setBounds(252, 40, 158, 20);
		contentPane.add(txtNombreDocumento);
		txtNombreDocumento.setColumns(10);
		
		
		JButton butMenuPrincipal = new JButton("Volver al menu principal");
		butMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicio.setVisible(true);
				dispose();
			}
		});
		butMenuPrincipal.setBounds(113, 228, 266, 23);
		contentPane.add(butMenuPrincipal);
	}

	
	public void subirArchivo()
	{
		JFileChooser fc=new JFileChooser();
		fc.setDialogTitle("Seleccionar archivo");  
		 
		int seleccion=fc.showOpenDialog(this);
		 
		if(seleccion==JFileChooser.APPROVE_OPTION)
		{ 
		    File fichero=fc.getSelectedFile();
		    rutaArchivo = fichero.getAbsolutePath(); 
		    txtNombreDocumento.setText(fichero.getName());
		    
		}
	}
	
	public void firmarArchivo()
	{
		//verificar datos minimos
		String password = new String(passFieldContrasena.getPassword());
		if(rutaArchivo.equals("") || password.equals(""))
		{
			JOptionPane.showMessageDialog(this, "Para firmar debe seleccionar un archivo y especificar el password de la clave privada",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			Controlador controlador = inicio.getControlador();
			
			JOptionPane.showMessageDialog(this, "Seleccione donde quiere guardar la firma");
			
			//Seleccionar donde guardar la firma
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Guardar archivo");   
			 
			int seleccion = fc.showSaveDialog(this);
			 
			if (seleccion == JFileChooser.APPROVE_OPTION) 
			{
			    File fichero = fc.getSelectedFile();
			    String rutaFirma = fichero.getAbsolutePath();
			    
			    //proceder a firmar
			    try 
			    {
					controlador.firmarArchivo(rutaArchivo, rutaFirma, password);
					JOptionPane.showMessageDialog(this, "Firma generada exitosamente","Respuesta",JOptionPane.INFORMATION_MESSAGE);
					
					rutaArchivo = "";
					txtNombreDocumento.setText("");
					passFieldContrasena.setText(""); 
				} 
			    catch (Exception e) 
			    {
			    		JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		if(comando.equals(SUBIR_ARCHIVO))
		{
			
			subirArchivo();
		}
		else if(comando.equals(FIRMAR_ARCHIVO))
		{
			firmarArchivo();
		}
		
	}

}
