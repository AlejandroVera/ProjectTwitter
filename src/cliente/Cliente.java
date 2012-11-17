package cliente;


import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;




public class Cliente extends UnicastRemoteObject implements AStream.IListen, Remote{

	private static final long serialVersionUID = 6865106167203455251L;
	
	protected Cliente() throws RemoteException {
		super();
	}

	public static void main(String[] args){
		
	}
	
	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		System.out.println("Se ha recibido un evento:" + event.getType());
		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		//System.out.println(message.getText());
		return true;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		System.out.println("Se ha recibido un tweet:" + tweet.getText());
		return true;
	}

}
