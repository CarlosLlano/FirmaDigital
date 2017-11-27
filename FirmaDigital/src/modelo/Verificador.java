package modelo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Clase capaz de validar una firma digital de un archivo
 */
public class Verificador 
{
	
	/**
	 * Metodo encargado de validar una firma
	 * @param rutaArchivo - ruta del archivo del cual se quiere verificar su firma
	 * @param rutaFirma - ruta de la firma digital del archivo
	 * @param rutaClavePublica - ruta de la clave publica del firmador
	 * @return boolean - True si la firma es correcta (veridica), False en caso contrario
	 * @throws Exception - lanza excepcion si no se termina la validacion correctamente
	 */
	public boolean validarFirma(String rutaArchivo, String rutaFirma, String rutaClavePublica) throws Exception
	{
		byte[] datos = leerArchivo(rutaArchivo);
		byte[] firma = leerArchivo(rutaFirma);
		PublicKey clavePublica = leerClavePublicaDesdeArchivo(rutaClavePublica);	
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(clavePublica);
		signature.update(datos);
		
		return signature.verify(firma);
		
	}
	
	/**
	 * Metodo capaz de leer un archivo y obtener su contenido en bytes
	 * @param ruta - ruta del archivo a leer
	 * @return byte[] -  retorno un arreglo de bytes que representa el contenido del archivo
	 * @throws Exception - se lanza una excepcion si ocurre un error de lectura
	 */
	public byte[] leerArchivo(String ruta) throws Exception
	{
		return Files.readAllBytes(Paths.get(ruta));
	}
	
	/**
	 * Metodo capaz de obtener la clave publica que esta almacenada en un archivo
	 * @param ruta - ruta del archivo cuyo contenido es una clave publica
	 * @return PublicKey - retorna una representacion de la clave publica
	 * @throws Exception - lanza excepcion si no se pudo leer la clave publica
	 */
	public PublicKey leerClavePublicaDesdeArchivo(String ruta) throws Exception
	{
		byte[] keyBytes = leerArchivo(ruta);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);	
	}

}
