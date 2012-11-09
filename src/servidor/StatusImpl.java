package servidor;

import interfacesComunes.*;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusImpl implements TwitterImpl.ITweet, Status{

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
	
	public static void main(String[] args){
		ArrayList<String> hashtags=new ArrayList<String>();
		String cadena="venga #juguemos #starcraft2 error#almoadilla # #terran1";
		Pattern p=Pattern.compile("\\s#[a-zA-Z0-9]+");
		Matcher m = p.matcher(cadena);
		while(m.find()){
			hashtags.add(cadena.substring( m.start(),m.end()).substring(1));
		}
		System.out.println(hashtags.toString());
	}

}
