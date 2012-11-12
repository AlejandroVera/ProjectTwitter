package interfacesComunes;


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TwitterInit extends Remote{
	
	public Twitter login(String user, String pass, AStream.IListen client) throws RemoteException;
	public int register(String user, String pass, String email) throws RemoteException;
}
