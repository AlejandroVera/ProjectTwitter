package interfacesComunes;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.Remote;

public interface Status extends Twitter.ITweet, Serializable, Remote{
	int getRetweetCount();
	BigInteger getId();
	boolean isFavorite();
	boolean isRetweetedByMe(Number myID);
}
