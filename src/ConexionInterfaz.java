

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConexionInterfaz extends Remote{
	
	public User getData(String user, String pass, ClienteInterfaz client) throws RemoteException;
}
