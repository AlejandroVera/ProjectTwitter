package interfacesComunes;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.Remote;

import servidor.TwitterImpl;

public interface Status extends TwitterImpl.ITweet, Serializable, Remote{
	int getRetweetCount();
	BigInteger getId();
	boolean isFavorite();
}
