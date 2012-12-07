package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import winterwell.jtwitter.AStreamImpl.IListen;

public interface AStream extends Remote, Serializable{

	public interface IListen extends Remote, Serializable{

		public boolean processEvent(TwitterEvent event) throws RemoteException;
		public boolean processSystemEvent(java.lang.Object[] obj) throws RemoteException; 
		public boolean processTweet(Twitter.ITweet tweet) throws RemoteException;
	}
	
	public java.util.List<TwitterEvent>	getEvents(); 
}
