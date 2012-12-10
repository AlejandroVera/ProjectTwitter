package servidor;

/**
 * Clase formada por métodos estáticos para servir de utilidades del servidor
 */
public class ServerCommon {

	/**
	 * Contante que indica si se está realizando debug o no.
	 */
	public static final boolean DEBUG = true;

	/**
	 * Muestra un error del servidor y termina la ejecución.
	 * @param e Excepción que ha provocado el error. Solo se muestra si el DEBUG está activado.
	 * @param error Mensaje de error a mostrar.
	 * @param numError Valor de terminación del programa.
	 */
	public static void TwitterError(Exception e, String error, int numError) {
		if(error != null)
			System.err.println("[ERROR] "+ error);
		if (DEBUG && e != null)
			e.printStackTrace();
		System.exit(numError);
	}
	
	/**
	 * Muestra un error del servidor y termina la ejecución.
	 * @param e Excepción que ha provocado el error. Solo se muestra si el DEBUG está activado.
	 * @param error Mensaje de error a mostrar.
	 */
	public static void TwitterError(Exception e, String error) {
		TwitterError(e, error, 1);
	}
	
	/**
	 * Muestra un error del servidor y termina la ejecución.
	 * @param error Mensaje de error a mostrar.
	 */
	public static void TwitterError(String error) {
		TwitterError(null, error, 1);
	}
	
	/**
	 * Muestra un error del servidor y termina la ejecución.
	 * @param error Mensaje de error a mostrar.
	 * @param numError Valor de terminación del programa.
	 */
	public static void TwitterError(String error, int numError) {
		TwitterError(null, error, numError);
	}
	
	/**
	 * Muestra un aviso del servidor.
	 * @param e Excepción que ha provocado el aviso. Solo se muestra si el DEBUG está activado.
	 * @param warning Mensaje de aviso a mostrar.
	 */
	public static void TwitterWarning(Exception e, String warning) {
		if(warning != null)
			System.err.println("[WARNING] "+ warning);
		if (DEBUG && e != null)
			e.printStackTrace();
	}
	
	/**
	 * Muestra un aviso del servidor.
	 * @param warning Mensaje de aviso a mostrar.
	 */
	public static void TwitterWarning(String warning) {
		TwitterWarning(null, warning);
	}

}
