package servidor;

import interfacesComunes.User;
import java.util.Date;
import java.util.List;

public class Status implements TwitterImpl.ITweet{

	private int id;
	private int retweetCount;
	private String text;
	private User usuario;
	private java.util.Date 	createdAt;
	
	
	public int getId() {
		return id;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return usuario;
	}

	public java.util.Date getCreatedAt() {
		return createdAt;
	}

	public String getLocation(){
		return usuario.getLocation();
	}
	
	public Place getPlace(){
		//TODO
		return null;
	}
	
	public List<TwitterImpl.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type){
		//TODO
		return null;
	}
	
	public List<String> getMentions() {
		// TODO 
		return null;
	}

}
