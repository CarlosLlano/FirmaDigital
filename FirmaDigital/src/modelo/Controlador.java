package modelo;

import java.security.KeyPair;


/**
 * Clase encargada de coordinar todas las funciones del programa
 */
public class Controlador {

	private GeneradorDeClaves generadorDeClaves;
	private Firmador firmador;
	private Verificador verificador;
	
	public Controlador() 
	{
		try
		{
			generadorDeClaves = new GeneradorDeClaves();
			firmador = new Firmador();
			verificador = new Verificador();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void generarClave(String password)
	{
		generadorDeClaves.generarClave(password);
		
	}
	
	public void firmarArchivo(String rutaArchivo,String rutaFirma, String passwordKeyPair) throws Exception
	{
		KeyPair parDeClaves = generadorDeClaves.getClaves().get(passwordKeyPair);
		if(parDeClaves==null)
		{
			throw new Exception("No existe una clave privada guardada con el password especificado");
		}
		else
		{
			firmador.firmarArchivo(rutaArchivo, parDeClaves.getPrivate(),rutaFirma);
		}
	}
	
	public boolean validarFirma(String rutaArchivo, String rutaFirma, String rutaClavePublica) throws Exception
	{
		return verificador.validarFirma(rutaArchivo, rutaFirma, rutaClavePublica);
	}

	public void exportarClavePublica(String rutaClavePublica, String passwordKeyPair) throws Exception
	{
		generadorDeClaves.exportarClavePublica(rutaClavePublica, passwordKeyPair);
	}

}
