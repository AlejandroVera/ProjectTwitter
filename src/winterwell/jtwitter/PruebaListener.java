package winterwell.jtwitter;

import java.rmi.RemoteException;

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

public class PruebaListener implements AStream.IListen {

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		boolean sol=true;
		System.out.println(tweet.getUser().getScreenName());
		System.out.println(tweet.getCreatedAt());
		System.out.println(tweet.getText()+"\n");
		return sol;
	}

}
