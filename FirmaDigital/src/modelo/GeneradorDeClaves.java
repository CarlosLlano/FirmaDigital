package modelo;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Clase capaz de generar un par de claves RSA y de asegurar adecuadamente la clave privada (mediante un password)
 */
public class GeneradorDeClaves 
{

	//HashMap que almacena el par de claves generado con un password (o key) de identificacion.
	private HashMap<String, KeyPair> claves;

	//generador de claves
	private KeyPairGenerator generador;


	public GeneradorDeClaves() throws NoSuchAlgorithmException
	{
		generador = KeyPairGenerator.getInstance("RSA");
		generador.initialize(1024);
		claves = new HashMap<>(100);
	}

	/**
	 * Genera una par de claves (clave privada y clave publica)
	 * @param password - password con la cual se identificara el par de claves generados
	 */
	public void generarClave(String password)
	{
		claves.put(password, generador.generateKeyPair());
	}

	/**
	 * Exporta una clave publica
	 * @param rutaClavePublica - ruta del archivo en el cual se escribira la clave publica
	 * @param passwordKeyPair - identificador de la clave publica
	 * @throws Exception - Lanza excepcion si no se finaliza la exportacion correctamente
	 */
	public void exportarClavePublica(String rutaClavePublica, String passwordKeyPair) throws Exception
	{
		KeyPair parDeClaves = claves.get(passwordKeyPair);
		if(parDeClaves==null)
		{
			throw new Exception("No existe una clave publica guardada con el password especificado");
		}
		else
		{
			FileOutputStream fos = new FileOutputStream(rutaClavePublica);
			fos.write(parDeClaves.getPublic().getEncoded());
			fos.close();
		}

	}

	public HashMap<String, KeyPair> getClaves() {
		return claves;
	}

	public void setClaves(HashMap<String, KeyPair> claves) {
		this.claves = claves;
	}

	public KeyPairGenerator getGenerador() {
		return generador;
	}

	public void setGenerador(KeyPairGenerator generador) {
		this.generador = generador;
	}



}
