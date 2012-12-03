package winterwell.jtwitter;

import java.util.ArrayList;
import java.util.List;

public class PruebaNotificaciones {

	static String JKEY="MTRvDLFJqnQ5W4VjIGob0w";
	static String JSECRET="QXVQNJg4cywAk7xvBuSRT9BMPzb6yKHtijAvU0GV2is";
	static String TKEY="207417955-0z0e3I8GoOcjx4zb4hqJAYeoDoCfl1JQGyR9yD2g";
	static String TSECRET="87UVVsVey5zQpjJe8cFzJswUiSwt9N2oUtqVt8NAC1M";
	public static void main(String[] args){
		OAuthSignpostClient oauthClient = new OAuthSignpostClient(JKEY, JSECRET, TKEY,TSECRET);
		TwitterImpl twitter = new TwitterImpl("kmilinh0",oauthClient);
		TwitterStream twitterSteam = new TwitterStream(twitter);
		List<Long> l= twitter.users().getFriendIDs();
		l.add(twitter.getSelf().getId());
		twitterSteam.setFollowUsers(l);
		twitterSteam.addListener(new PruebaListener());
		twitterSteam.connect();
		twitterSteam.popTweets();
		if(twitterSteam.isAlive()){
			System.out.println("Stream conectado \n");

		}

		while(true){}
	}
}
