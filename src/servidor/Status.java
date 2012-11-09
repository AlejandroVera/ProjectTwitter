package servidor;

import java.util.Date;
import java.util.List;

public class Status {

	private int id;
	private int retweetCount;
	private String text;
	private UserImpl usuario;
	private java.util.Date 	createdAt;
	
	public Status(UserImpl usuario,String text,int id){
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

	public String getMention(){
		//TODO
		return null;
	}
	
	public String getLocation(){
		//TODO
		return null;
	}
	
	public Place getPlace(){
		//TODO
		return null;
	}
	
	public List<TwitterImpl.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type){
		return null;
		
	}
}
