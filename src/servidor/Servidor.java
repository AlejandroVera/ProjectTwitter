package servidor;

import interfacesComunes.AStream;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;


public class Servidor {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		
		LinkedList<AStream.IListen> clientes = new LinkedList<AStream.IListen>();
		
		try {
			Naming.rebind("Conectar", new TwitterInitImpl(clientes));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
			Iterator<AStream.IListen> it = clientes.iterator();
			while(it.hasNext()){
				AStream.IListen cli = (AStream.IListen) it.next();
				//cli.notifyMessage("Este es un mensaje desde el servidor");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
