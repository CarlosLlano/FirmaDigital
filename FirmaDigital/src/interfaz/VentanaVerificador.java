package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Controlador;

/**
 * Interfaz con la funcion de verificar una firma digital de un archivo
 *
 */
@SuppressWarnings("serial")
public class VentanaVerificador extends JFrame implements ActionListener {

	public static final String SUBIR_ARCHIVO = "Subir archivo";
	public static final String SUBIR_FIRMA = "Subir firma";
	public static final String SUBIR_CLAVE_PUBLICA = "Subir clave publica";
	public static final String COMPROBAR = "COMPROBAR";
	
	private JTextField txtSubirArchivo;
	private JTextField txtSubirFirma;
	private JTextField txtSubirClavePublica;
	
	private Inicio inicio;
	private String rutaArchivo;
	private String rutaFirma;
	private String rutaClavePublica;

	public VentanaVerificador(Inicio inicio) 
	{
		this.inicio = inicio;
		rutaArchivo = "";
		rutaFirma = "";
		rutaClavePublica = "";
		
		
		setTitle("Comprobador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 299);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton butSubirArchivo = new JButton("Subir archivo");
		butSubirArchivo.setActionCommand(SUBIR_ARCHIVO);
		butSubirArchivo.addActionListener((ActionListener) this);	
		butSubirArchivo.setBounds(10, 56, 190, 23);
		contentPane.add(butSubirArchivo);
		
		JButton butSubFirma = new JButton("Subir firma");
		butSubFirma.setActionCommand(SUBIR_FIRMA);
		butSubFirma.addActionListener((ActionListener) this);
		butSubFirma.setBounds(10, 102, 190, 23);
		contentPane.add(butSubFirma);
		
		JButton butSubClavePublica = new JButton("Subir clave publica");
		butSubClavePublica.setActionCommand(SUBIR_CLAVE_PUBLICA);
		butSubClavePublica.addActionListener((ActionListener) this);
		butSubClavePublica.setBounds(10, 156, 190, 23);
		contentPane.add(butSubClavePublica);
		
		//-----------------------------------------------------
		JButton butComprobar = new JButton("Comprobar");
		butComprobar.setActionCommand(COMPROBAR);
		butComprobar.addActionListener((ActionListener) this);
		butComprobar.setBounds(35, 238, 133, 23);
		contentPane.add(butComprobar);
		//-----------------------------------------------------
		
		JButton butMenuPrincipal = new JButton("Volver al menu principal");
		butMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{				
				inicio.setVisible(true);
				dispose();	
			}
		});
		butMenuPrincipal.setBounds(221, 238, 284, 23);
		contentPane.add(butMenuPrincipal);
		
		txtSubirArchivo = new JTextField();
		txtSubirArchivo.setEditable(false);
		txtSubirArchivo.setBounds(294, 57, 140, 20);
		contentPane.add(txtSubirArchivo);
		txtSubirArchivo.setColumns(10);
		
		txtSubirFirma = new JTextField();
		txtSubirFirma.setEditable(false);
		txtSubirFirma.setColumns(10);
		txtSubirFirma.setBounds(294, 103, 140, 20);
		contentPane.add(txtSubirFirma);
		
		txtSubirClavePublica = new JTextField();
		txtSubirClavePublica.setEditable(false);
		txtSubirClavePublica.setColumns(10);
		txtSubirClavePublica.setBounds(294, 157, 140, 20);
		contentPane.add(txtSubirClavePublica);
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
		    txtSubirArchivo.setText(fichero.getName());	    
		}
	}
	
	public void subirFirma()
	{
		JFileChooser fc=new JFileChooser();
		fc.setDialogTitle("Seleccionar firma");  
		 
		int seleccion=fc.showOpenDialog(this);
		 
		if(seleccion==JFileChooser.APPROVE_OPTION)
		{ 
		    File fichero=fc.getSelectedFile();
		    rutaFirma = fichero.getAbsolutePath(); 
		    txtSubirFirma.setText(fichero.getName());	    
		}
	}
	
	public void subirClavePublica()
	{
		JFileChooser fc=new JFileChooser();
		fc.setDialogTitle("Seleccionar clave publica");  
		 
		int seleccion=fc.showOpenDialog(this);
		 
		if(seleccion==JFileChooser.APPROVE_OPTION)
		{ 
		    File fichero=fc.getSelectedFile();
		    rutaClavePublica = fichero.getAbsolutePath(); 
		    txtSubirClavePublica.setText(fichero.getName());	    
		}
	}
	
	public void comprobar()
	{
		if(rutaArchivo.equals("") || rutaFirma.equals("") || rutaClavePublica.equals(""))
		{
			JOptionPane.showMessageDialog(this, "Para verificar una firma debe especificar un archivo, la firma y la clave publica del remitente",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			Controlador controlador = inicio.getControlador();
			try 
			{
				boolean respuesta = controlador.validarFirma(rutaArchivo, rutaFirma, rutaClavePublica);
				if(respuesta)
				{
					
					JOptionPane.showMessageDialog(this, "La firma es CORRECTA","Respuesta",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					
					JOptionPane.showMessageDialog(this, "La firma es INCORRECTA","Respuesta",JOptionPane.INFORMATION_MESSAGE);
					
				}
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(this, "La firma es incorrecta",
						"Error",JOptionPane.ERROR_MESSAGE);	
			}
		}
	}
	
	public void limpiar()
	{
		rutaArchivo="";
		rutaFirma="";
		rutaClavePublica="";
		
		txtSubirArchivo.setText("");
		txtSubirFirma.setText("");
		txtSubirClavePublica.setText("");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		if(comando.equals(SUBIR_ARCHIVO))
		{
			subirArchivo();
		}
		else if(comando.equals(SUBIR_FIRMA))
		{
			subirFirma();
		}
		else if(comando.equals(SUBIR_CLAVE_PUBLICA))
		{
			
			subirClavePublica();
		}
		else if(comando.equals(COMPROBAR))
		{
			comprobar();
		}
		
	}

}
