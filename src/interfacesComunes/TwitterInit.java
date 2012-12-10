package interfacesComunes;

import interfacesComunes.AStream.IListen;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;


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
	 * Método para iniciar sesión en nuestro Twitter.
	 * @param user ScreenName del usuario al cual loguear.
	 * @param pass Contraseña del usuario.
	 * @param client CallBack por el que mandar eventos del servidor al cliente.
	 * @return Objeto Twitter con el que poder realizar todas las acciones necesarias con Twitter.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public Twitter login(String user, String pass, AStream.IListen client) throws RemoteException;
	
	/**
	 * Método para cerrar sesión en nuestro Twitter.
	 * @param userId Id del usuario al cual cerrar la sesión.
	 * @param client CallBack por el que mandar eventos del servidor al cliente.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public void logout(Long userId, AStream.IListen client) throws RemoteException;
	
	/**
	 * Método para registrar a un usuario en nuestro Twitter.
	 * @param user ScreenName que tendrá el usuario.
	 * @param pass Contraseña que tendrá el usuario.
	 * @param email Email del usuario.
	 * @return Una constante con estado del registro: REG_OK, REG_WRONG_USER, REG_WRONG_EMAIL ó REG_WRONG_UNKNOWN.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public int register(String user, String pass, String email) throws RemoteException;
	
	/**
	 * Obtiene una imagen guardada en nuestro servidor.
	 * @param url Es un identificador de 7 caracteres alfanuméricos.
	 * @return Devuelve un array de bytes que representan la imagen.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public byte[] getImage(String url) throws RemoteException;
	
	/**
	 * Guarda una imagen en nuestro servidor.
	 * @param img Array de bytes que representan la imagen.
	 * @return Un identificador de 7 caracteres alfanuméricos.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public String saveImage(byte[] img) throws RemoteException;
	
	/**
	 * Devuelve el array de callbacks que está utilizando el servidor.
	 * @return Array de callbacks que está utilizando el servidor.
	 * @throws RemoteException Excepción de Java RMI.
	 */
	public HashMap<Long, LinkedList<IListen>> getCallbackArray() throws RemoteException;
}
