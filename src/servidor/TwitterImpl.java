package servidor;
import java.util.Date;
import java.util.List;

import interfacesComunes.ClienteCallback;
import interfacesComunes.Message;
import interfacesComunes.Status;
import interfacesComunes.Twitter;
import excepcionesComunes.TwitterException;
import interfacesComunes.Twitter_Account;
import interfacesComunes.User;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import servidor.db.Conexion;


public class TwitterImpl implements Serializable, Twitter {

	public enum KEntityType {
		hashtags, 
		urls,
		user_mentions;
	}
	
	public class TweetEntity{
		private Status tweet;
		private int start;
		private int end;
		public TweetEntity(Status tweet, int start, int end){
			this.tweet = tweet;
			this.start = start;
			this.end=end;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6621123794067420801L;
	
	private User user;
	private static HashMap<Integer, LinkedList<ClienteCallback>> clientes;
	private Conexion con;
	private int maxResults = 20;

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
		return new Twitter_AccountImpl(this);
	}

	public int countCharacters(String statusText) {
		final String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		//Devolvemos la longitud sustituyendo las URLs con 20 caracteres.
		return statusText.replaceAll(regex, "********************").length();
	}

	public void destroy(Twitter.ITweet tweet) throws TwitterException{
		if(tweet instanceof StatusImpl){
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

	@Override
	public List<Message> getDirectMessages() {
		LinkedList<Message> list = new LinkedList<Message>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT id FROM mensajes WHERE id_destinatario = " + this.user.getId());
			while(res.next()){
				list.add(new MessageImpl(res.getInt(1)));
			}
		}
		
		return list;
	}

	@Override
	public List<Message> getDirectMessagesSent() {
		LinkedList<Message> list = new LinkedList<Message>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT id FROM mensajes WHERE id_autor = " + this.user.getId());
			while(res.next()){
				list.add(new MessageImpl(res.getInt(1)));
			}
		}
		
		return list;
	}

	@Override
	public List<StatusImpl> getFavorites() {
		LinkedList<StatusImpl> list = new LinkedList<StatusImpl>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT tw.id FROM favoritos fa, tweet tw WHERE fa.id_usuario = " +
								this.user.getId()+" AND tw.id = fa.id_tweet ORDER BY tw.fecha DESC LIMIT "+this.maxResults);
			while(res.next()){
				list.add(new StatusImpl(res.getInt(1)));
			}
		}
		
		return list;
	}

	@Override
	public List<StatusImpl> getFavorites(String screenName) {
		LinkedList<StatusImpl> list = new LinkedList<StatusImpl>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			LinkedList<Object> param = new LinkedList<Object>();
			param.add(screenName);
			
			ResultSet res = con.query("SELECT tw.id FROM favoritos fa, tweet tw, usuario us WHERE us.screenName = ? AND " +
					"fa.id_usuario = us.id AND tw.id = fa.id_tweet ORDER BY tw.fecha DESC LIMIT "+this.maxResults, param);
			while(res.next()){
				list.add(new StatusImpl(res.getInt(1)));
			}
		}
		
		return list;
	}
	


}
