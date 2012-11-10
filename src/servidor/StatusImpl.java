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
		//TODO: Esperando a tener la clase Place
		return null;
	}

	/**
	 * getTweetEntities
	 * 
	 *@param type Tipo de entity del que se quiere obtener la lista.
	 *@return List(TweetEntity) lista de entities que contiene este status(tweet).
	 */
	public List<TwitterImpl.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type){

		List<TwitterImpl.TweetEntity> entities=new ArrayList<TwitterImpl.TweetEntity>();
		int inicio;
		Pattern p=null;
		Matcher m=null;
		if(type==KEntityType.urls){
			p=Pattern.compile("((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+");//OJO al crear el tweet se acorta la url siempre.
		}
		else if(type==KEntityType.hashtags){
			p=Pattern.compile("(^|\\s)#[a-zA-Z0-9]+");
		}
		else if(type==KEntityType.user_mentions){
			p=Pattern.compile("(^|\\s)@[a-zA-Z0-9]+");
		}
		m= p.matcher(this.text);
		while(m.find()){
			if(this.text.substring(m.start(),m.end()).charAt(0)==' '){
				inicio=m.start()+1;
			}
			else{
				inicio=m.start();
			}
			entities.add(new TwitterImpl.TweetEntity(this,inicio,m.end()));
		}
		return entities;
	}
	

	public List<String> getMentions() {
		List<String> mencionados=new ArrayList<String>();
		Pattern p=Pattern.compile("(^|\\s)@[a-zA-Z0-9]+");
		Matcher m=p.matcher(this.text);
		while(m.find()){
			mencionados.add(this.text.substring(m.start(),m.end()).replaceAll(" ",""));
		}
		return mencionados;
	}

//	public static void main(String[] args){
//		String cadena="@xafilox noob @kmilinho master";
//		Pattern p=Pattern.compile("(^|\\s)@[a-zA-Z0-9]+");
//		Matcher m = p.matcher(cadena);
//		while(m.find()){
//			if(cadena.substring(m.start(),m.end()).charAt(0)==' '){
//				System.out.println("hola");
//			}
//			System.out.println(cadena.substring(m.start(),m.end()));
//		}
//
//	}

}
