package interfacesComunes;


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClienteCallback extends Remote {
    public void notifyMessage(Message message)  throws RemoteException;
    public void notifyStatus(Status status)  throws RemoteException;
}