package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AStream extends Remote, Serializable{

	public interface IListen extends Remote, Serializable{

		public boolean processEvent(TwitterEvent event) throws RemoteException;
		public boolean processSystemEvent(java.lang.Object[] obj) throws RemoteException; 
		public boolean processTweet(Twitter.ITweet tweet) throws RemoteException;
		
	}

}
