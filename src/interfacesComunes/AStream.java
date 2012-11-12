package interfacesComunes;

import java.rmi.RemoteException;

public interface AStream {

	public interface IListen {

		public boolean processEvent(TwitterEvent event) throws RemoteException;
		public boolean processSystemEvent(java.lang.Object[] obj) throws RemoteException; 
		public boolean processTweet(Twitter.ITweet tweet) throws RemoteException;
		
	}

}
