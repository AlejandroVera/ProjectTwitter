package servidor;

import interfacesComunes.*;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servidor.TwitterImpl.KEntityType;


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
		List<TwitterImpl.TweetEntity> entities=new ArrayList<TwitterImpl.TweetEntity>();
		if(type==KEntityType.urls){
				Pattern p=Pattern.compile("((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+");//OJO al crear el tweet se acorta la url siempre.
				Matcher m = p.matcher(this.text);
				while(m.find()){
					entities.add(new TwitterImpl.TweetEntity(this,m.start()+1,m.end()));
				}
		}
		else if(type==KEntityType.hashtags){
			Pattern p=Pattern.compile("\\s#[a-zA-Z0-9]+");
			Matcher m = p.matcher(this.text);
			while(m.find()){
				entities.add(new TwitterImpl.TweetEntity(this,m.start()+1,m.end()));
			}
		}
		else if(type==KEntityType.user_mentions){
			Pattern p=Pattern.compile("\\s@[a-zA-Z0-9]+");
			Matcher m = p.matcher(this.text);
			while(m.find()){
				entities.add(new TwitterImpl.TweetEntity(this,m.start()+1,m.end()));
			}
		}

		return entities;
	}
	
	public List<String> getMentions() {
		// TODO 
		return null;
	}
	
public static void main(String[] args){
	String cadena="vamos.com";
	Pattern p=Pattern.compile("((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+");
	Matcher m = p.matcher(cadena);
	while(m.find()){
		System.out.println(cadena.substring(m.start(),m.end()));
	}

}

}
