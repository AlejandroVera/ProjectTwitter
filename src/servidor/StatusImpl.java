package servidor;

import interfacesComunes.*;
import interfacesComunes.Twitter.TweetEntity;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servidor.TwitterImpl.KEntityType;
import servidor.db.Conexion;


public class StatusImpl implements TwitterImpl.ITweet, Status{

	private int id;
	private int retweetCount;
	private String text;
	private User usuario;
	private java.util.Date 	createdAt;
	private Conexion con;

	public StatusImpl(int id){
		con = new Conexion();
		this.id=id;
		ResultSet res = con.query("SELECT s.texto, s.autor, s.fecha FROM tweet s WHERE s.id="+id);
		try {
			this.text=res.getString("texto");
			this.usuario=new UserImpl(res.getInt("autor"));
			this.createdAt=res.getTimestamp("fecha");
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}

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
	public List<Twitter.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type){

		List<Twitter.TweetEntity> entities=new ArrayList<Twitter.TweetEntity>();
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
			entities.add(new TwitterImpl.TweetEntity(type, inicio,m.end()));
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

}
