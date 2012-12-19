package interfacesComunes;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Para poder realizar busquedas lentas
 */
public interface ClienteCallback extends Remote, Serializable {
    public void notifyMessage(Message message)  throws RemoteException;
    public void notifyStatus(Status status)  throws RemoteException;
}