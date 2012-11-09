package servidor;

import java.util.Date;
import java.util.List;

public class Status implements TwitterImpl.ITweet {

	private int id;
	private int retweetCount;
	private String text;
	private UserImpl usuario;
	private java.util.Date 	createdAt;
	
	public Status(UserImpl usuario,String text,int id){
		this.id=id;
		retweetCount=0;
		this.usuario=usuario;
		this.text=text;
		createdAt=new Date();
		
	}
	
	public int getId() {
		return id;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public String getText() {
		return text;
	}

	public UserImpl getUser() {
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
