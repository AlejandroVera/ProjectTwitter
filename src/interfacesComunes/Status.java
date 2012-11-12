package interfacesComunes;

import servidor.TwitterImpl;

public interface Status extends TwitterImpl.ITweet{
	int getRetweetCount();
	int getId();
}
