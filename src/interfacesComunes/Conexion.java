package interfacesComunes;




import java.rmi.Remote;
import java.rmi.RemoteException;



public interface Conexion extends Remote{
	
	public User getData(String user, String pass, ClienteCallback client) throws RemoteException;
}
