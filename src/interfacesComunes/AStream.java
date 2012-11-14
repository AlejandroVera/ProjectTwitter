package interfacesComunes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AStream extends Remote{

	public interface IListen extends Remote{

		public boolean processEvent(TwitterEvent event) throws RemoteException;
		public boolean processSystemEvent(java.lang.Object[] obj) throws RemoteException; 
		public boolean processTweet(Twitter.ITweet tweet) throws RemoteException;
		
	}

}
