package interfacesComunes;


import java.io.File;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;


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
	
	public Twitter login(String user, String pass, AStream.IListen client) throws RemoteException;
	public void logout(Long userId, AStream.IListen client) throws RemoteException;
	public int register(String user, String pass, String email) throws RemoteException;
	public byte[] getImage(String url) throws RemoteException;
	public String saveImage(byte[] img) throws RemoteException;
}
