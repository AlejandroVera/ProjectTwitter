package servidor;

import interfacesComunes.*;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfacesComunes.Twitter.KEntityType;


public class StatusImpl implements Status{

	private static final long serialVersionUID = 4967335867172572534L;
	
	private BigInteger id;
	private int retweetCount;
	private String text;
	private User usuario;
	private Date createdAt;
	private Place lugar;
	private Conexion con;
	private User loggedUser;

	public StatusImpl(BigInteger status_id, Conexion con, User loggedUser){
		this.con = con;
		this.id=status_id;
		ResultSet res = con.query("SELECT texto, autor, fecha FROM tweet WHERE id=" + status_id + " LIMIT 1");
		try {
			if(res.next()){
				this.loggedUser=loggedUser;
				this.text=res.getString("texto");
				this.usuario=new UserImpl(res.getLong("autor"), this.con,this.loggedUser);
				this.createdAt=new Date((long)res.getInt("fecha")*1000);
				this.lugar=usuario.getPlace();
			}
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}

	}
	
	public boolean isFavorite(){
		boolean sol = false;
		ResultSet res = con.query("SELECT * FROM favoritos WHERE id_usuario=" 
		+this.loggedUser+" AND id_tweet="+this.id +" LIMIT 1");
		try {
			sol=res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sol;
	}

	public BigInteger getId() {
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
		return lugar;
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
			entities.add(new TwitterImpl.TweetEntity(this.id,type, inicio,m.end(),this.con));
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

	@Override
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

}
