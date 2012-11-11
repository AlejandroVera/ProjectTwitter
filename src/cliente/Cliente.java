package cliente;


import interfacesComunes.ClienteCallback;
import interfacesComunes.Message;
import interfacesComunes.Status;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterInit;

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
			Twitter user1 = stub.login("Antonio", "pajarito", self);
			if(user1 != null)
				System.out.println("Conectado con usuario Antonio");
			Twitter user2 = stub.login("Perico", "cachalote", self);
			if(user2 != null)
				System.out.println("Conectado con usuario Perico");
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
		
	}
	
	public void notifyMessage(Message message){
		System.out.println(message.getText());
	}

	@Override
	public void notifyStatus(Status status) throws RemoteException {
		System.out.println(status.getText());

	}

}
