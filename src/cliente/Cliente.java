package cliente;


import interfacesComunes.ClienteCallback;
import interfacesComunes.TwitterInit;
import interfacesComunes.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;




public class Cliente extends UnicastRemoteObject implements ClienteCallback{

	protected Cliente() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6865106167203455251L;

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		String rmiUrl = "rmi://localhost/Conectar";
		ClienteCallback self = new Cliente();
		
		try {
			TwitterInit stub = (TwitterInit) Naming.lookup(rmiUrl);
			User user1 = stub.login("Antonio", "pajarito", self);
			User user2 = stub.login("Perico", "cachalote", self);
			System.out.println("Usuarios:" + user1.getName() + " y "+user2.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void notifyMessage(String message){
		System.out.println(message);
	}

}
