package cliente;


import interfacesComunes.AStream;
import interfacesComunes.Twitter;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.TwitterInit;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;




public class Cliente extends UnicastRemoteObject implements AStream.IListen, Remote{

	private Twitter twitter;
	private static final long serialVersionUID = 6865106167203455251L;
	
	protected Cliente() throws RemoteException {
		super();
	}

	public static void main(String[] args){
		
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
