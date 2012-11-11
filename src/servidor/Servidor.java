package servidor;





import interfacesComunes.ClienteCallback;

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
		
		LinkedList<ClienteCallback> clientes = new LinkedList<ClienteCallback>();
		
		try {
			Naming.rebind("Conectar", new TwitterInitImpl(clientes));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
			Iterator<ClienteCallback> it = clientes.iterator();
			while(it.hasNext()){
				ClienteCallback cli = (ClienteCallback) it.next();
				//cli.notifyMessage("Este es un mensaje desde el servidor");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
