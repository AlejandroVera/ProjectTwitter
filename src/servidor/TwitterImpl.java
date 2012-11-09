package servidor;

import interfacesComunes.Twitter;

public class TwitterImpl implements Twitter {

	@Override
	public void destroyStatus(int id) {
		// TODO Auto-generated method stub
		
	}
	
	
	public enum KEntityType {
		hashtags, 
		urls,
		user_mentions;
	}
	
	public class TweetEntity{
		//TODO
		
	}

}
