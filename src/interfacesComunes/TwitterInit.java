package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Representa nuestro servidor de Twitter. Presenta ciertos metodos para conexion, desconexion y manejo de imagenes.
 */
public interface TwitterInit extends Remote, Serializable{
	
	/**
	 * El registro se ha realizado correctamente
	 */
	final int REG_OK = 0;
	
	/**
	 * Error en el registro ya que el usuario ya existe.
	 */
	final int REG_WRONG_USER = 1;
	
	/**
	 * Error en el registro ya que el email ya existe.
	 */
	final int REG_WRONG_EMAIL = 2;
	
	/**
	 * Error desconocido en el registro
	 */
	final int REG_WRONG_UNKNOWN = 3;
	
	/**
	 * Metodo para iniciar sesion en nuestro Twitter.
	 * @param user ScreenName del usuario al cual loguear.
	 * @param pass Contraseña del usuario.
	 * @return Objeto Twitter con el que poder realizar todas las acciones necesarias con Twitter.
	 * @throws RemoteException Excepcion de Java RMI.
	 */
	public Twitter login(String user, String pass) throws RemoteException;
	
	/**
	 * Metodo para cerrar sesion en nuestro Twitter.
	 * @param userId Id del usuario al cual cerrar la sesion.
	 * @throws RemoteException Excepcion de Java RMI.
	 */
	public void logout(Long userId) throws RemoteException;
	
	/**
	 * Metodo para registrar a un usuario en nuestro Twitter.
	 * @param user ScreenName que tendra el usuario.
	 * @param pass Contraseña que tendra el usuario.
	 * @param email Email del usuario.
	 * @return Una constante con estado del registro: REG_OK, REG_WRONG_USER, REG_WRONG_EMAIL o REG_WRONG_UNKNOWN.
	 * @throws RemoteException Excepcion de Java RMI.
	 */
	public int register(String user, String pass, String email) throws RemoteException;
	
	/**
	 * Obtiene una imagen guardada en nuestro servidor.
	 * @param url Es un identificador de 7 caracteres alfanumericos.
	 * @return Devuelve un array de bytes que representan la imagen.
	 * @throws RemoteException Excepcion de Java RMI.
	 */
	public byte[] getImage(String url) throws RemoteException;
	
	/**
	 * Guarda una imagen en nuestro servidor.
	 * @param img Array de bytes que representan la imagen.
	 * @return Un identificador de 7 caracteres alfanumericos.
	 * @throws RemoteException Excepcion de Java RMI.
	 */
	public String saveImage(byte[] img) throws RemoteException;
	
	/**
	 * Envia un evento para un usuario a traves del topic.
	 * @param event Evento a enviar, puede ser un ITweet, un TwitterEvent o un Object[]
	 * @param id_dest Usuario al cual enviar el evento.
	 */
	public void sendThroughTopic(Serializable event, Long id_dest) throws RemoteException;
}
