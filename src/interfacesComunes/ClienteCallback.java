package interfacesComunes;


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClienteCallback extends Remote {
    public void notifyMessage(String mess)  throws RemoteException;
}