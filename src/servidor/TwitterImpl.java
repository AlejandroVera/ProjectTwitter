package servidor;
import java.util.Date;
import java.util.List;

import interfacesComunes.ClienteCallback;
import interfacesComunes.Message;
import interfacesComunes.Twitter;
import excepcionesComunes.TwitterException;
import interfacesComunes.Twitter_Account;
import interfacesComunes.User;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import servidor.db.Conexion;

import excepcionesComunes.TwitterException;


public class TwitterImpl implements Serializable, Twitter {

	public enum KEntityType {
		hashtags, 
		urls,
		user_mentions;
	}
	
	public class TweetEntity{
		//TODO
		
	}

	//interface que engloba tweets y mensajes.
	interface ITweet{
		Date getCreatedAt();
		int getId();
		String 	getLocation();
		List<String> getMentions();
		Place getPlace(); 
		String 	getText();
		List<TwitterImpl.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type);
		User getUser();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6621123794067420801L;
	
	private User user;
	private static HashMap<Integer, LinkedList<ClienteCallback>> clientes;
	private Conexion con;

	/**
	 * Contructor para conexión sin usuario. Solo lectura.
	 */
	public TwitterImpl() {
		super();
		this.user = null;
		this.con = new Conexion(); //TODO: conexión nueva por cada Twitter o una compartida para todos??
	}
	
	public TwitterImpl(int accountId, ClienteCallback callback){
		this.user = new UserImpl(accountId);
		clientes.get(accountId).add(callback);
		this.con = new Conexion(); //TODO: conexión nueva por cada Twitter o una compartida para todos??
	}


	public Twitter_Account account() {
		// TODO Auto-generated method stub
		return new Twitter_Account(this);
	}

	public int countCharacters(String statusText) {
		final String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		//Devolvemos la longitud sustituyendo las URLs con 20 caracteres.
		return statusText.replaceAll(regex, "********************").length();
	}

	public void destroy(Twitter.ITweet tweet) throws TwitterException{
		if(tweet instanceof Status){
			destroyStatus(tweet.getId());
		}else if (tweet instanceof Message){
			destroyMessage(tweet.getId());
		}else
			throw new TwitterException("Imposible borrar el Twitter.ITweet dado");
		
	}
	
	public void destroyMessage(Number id) {
		if(this.user != null && id != null){
			int userId = this.user.getId();
			//TODO: borrado de mensaje
			//int res = con.query("DELETE FROM mensaje WHERE id = "+id+" AND owner = "+userId+" LIMIT 1");
		}
	}

	
	public void destroyStatus(Number id) throws TwitterException {
		if(this.user != null && id != null){
			int userId = this.user.getId();
			ResultSet res = con.query("SELECT id FROM tweet WHERE id = "+id+" AND autor = "+userId+" LIMIT 1");
			if(res.next()){
				con.updateQuery("DELETE FROM favoritos WHERE id_tweet = "+id+" LIMIT 1");
				con.updateQuery("DELETE FROM retweet WHERE id_tweet = "+id);
				res = con.query("SELECT id_hashtag FROM hashtagsTweets WHERE id_tweet = "+id);
				con.updateQuery("DELETE FROM hashtagsTweets WHERE id_tweet = "+id);
				while(res.next()){
					con.updateQuery("DELETE FROM hashtag WHERE id = "+res.getInt(1)+
							" AND (SELECT COUNT(*) FROM hashtagsTweets id_hashtag = "+res.getInt(1)+") = 0 LIMIT 1");
				}
				con.updateQuery("DELETE FROM tweet WHERE id = "+id+" LIMIT 1");
			}else
				throw new TwitterException("El usuario no es propietario de ese tweet.");
			
		}else
			throw new TwitterException("No se pueden borrar tweets (no logueado)");
	}
	


}
