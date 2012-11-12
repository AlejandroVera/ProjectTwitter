package cliente;


import interfacesComunes.AStream;
import interfacesComunes.Twitter;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.TwitterInit;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;




public class Cliente extends UnicastRemoteObject implements AStream.IListen{

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
		AStream.IListen self = new Cliente();
		
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
	
	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		System.out.println(event.getType());
		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		//System.out.println(message.getText());
		return true;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		System.out.println(tweet.getText());
		return true;
	}

}
