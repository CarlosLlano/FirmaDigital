package modelo;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * Clase que firma documentos 
 */

public class Firmador
{

	/**
	 * Metodo que firma un archivo. Para esto le computa un hash (SHA1), cuyo resultado es encriptado utilizando la clave privada
	 * @param ruta - ruta del archivo a firmar
	 * @param clavePrivada - clave privada con la cual se encriptara el hash
	 * @param rutaFirma - ruta donde se guardara la firma
	 * @throws Exception - lanza Excepcion si no es posible terminar exitosamente la firma
	 */
	public void firmarArchivo(String ruta, PrivateKey clavePrivada, String rutaFirma) throws Exception
	{
		//crear firma
		byte[] data = leerArchivo(ruta);
		Signature dsa = Signature.getInstance("SHA1withRSA"); 
		dsa.initSign(clavePrivada);
		dsa.update(data);
		byte[] firma = dsa.sign();
		
		//guardar firma
		guardarFirma(rutaFirma, firma);
			
	}
	
	
	
	/**
	 * Metodo que lee un archivo obteniendo una representacion en bytes del mismo
	 * @param ruta - ruta donde se encuentra el archivo
	 * @return byte[] - arreglo de bytes que representa el contenido del archivo
	 * @throws Exception - se lanza una excepcion si ocurre un error de lectura
	 */
	public byte[] leerArchivo(String ruta) throws Exception
	{
		return Files.readAllBytes(Paths.get(ruta));
	}
	
	/**
	 * Metodo que guarda una firma en un archivo
	 * @param ruta - ruta donde se quiere guardar la firma
	 * @param firma - arreglo de bytes que representa una firma de un archivo
	 * @throws Exception - se lanza una excepcion si ocurre un error de escritura
	 */
	public void guardarFirma(String ruta, byte[] firma) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(ruta);
		fos.write(firma);
		fos.close();
	}
	
}
