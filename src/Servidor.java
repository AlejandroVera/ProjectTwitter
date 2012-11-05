

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
		
		LinkedList<ClienteInterfaz> clientes = new LinkedList<ClienteInterfaz>();
		
		try {
			Naming.rebind("Conectar", new ConexionImpl(clientes));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
			Iterator<ClienteInterfaz> it = clientes.iterator();
			while(it.hasNext()){
				ClienteInterfaz cli = (ClienteInterfaz) it.next();
				cli.notifyMessage("Este es un mensaje desde el servidor");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
