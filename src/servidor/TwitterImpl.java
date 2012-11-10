package servidor;
import java.util.Date;
import java.util.Iterator;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import servidor.db.Conexion;


public class TwitterImpl implements Twitter {

	public enum KEntityType {
		hashtags, 
		urls,
		user_mentions;
	}
	
	public static class TweetEntity implements Twitter.TweetEntity{
		private KEntityType type;
		private int start;
		private int end;
		public TweetEntity(KEntityType type, int start, int end){
			this.type = type;
			this.start = start;
			this.end=end;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6621123794067420801L;
	private static final int maxAllowedResults = 300;
	
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
		this.con = new Conexion();
	}
	
	public TwitterImpl(int accountId, ClienteCallback callback){
		this.user = new UserImpl(accountId);
		clientes.get(accountId).add(callback);
		this.con = new Conexion();
	}


	public Twitter_Account account() {
		// TODO Auto-generated method stub
		return new Twitter_AccountImpl(this);
	}

	public int countCharacters(String statusText) {
		final String regex = "((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+";
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
			try {
				if(res != null && res.next()){
					con.updateQuery("DELETE FROM favoritos WHERE id_tweet = "+id+" LIMIT 1");
					con.updateQuery("DELETE FROM retweet WHERE id_tweet = "+id);
					res = con.query("SELECT id_hashtag FROM hashtagsTweets WHERE id_tweet = "+id);
					con.updateQuery("DELETE FROM hashtagsTweets WHERE id_tweet = "+id);

					if(res != null)
						while(res.next())
							con.updateQuery("DELETE FROM hashtag WHERE id = "+res.getInt(1)+
								" AND (SELECT COUNT(*) FROM hashtagsTweets id_hashtag = "+res.getInt(1)+") = 0 LIMIT 1");
					
					con.updateQuery("DELETE FROM tweet WHERE id = "+id+" LIMIT 1");
				}else
					throw new TwitterException("El usuario no es propietario de ese tweet.");
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error en el borrado de un tweet");
			}
			
		}else
			throw new TwitterException("No se pueden borrar tweets (no logueado)");
	}

	@Override
	public List<Message> getDirectMessages() {
		LinkedList<Message> list = new LinkedList<Message>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT id FROM mensajes WHERE id_destinatario = " + this.user.getId());
			if(res != null)
				while(res.next())
					list.add(new MessageImpl(res.getInt(1), this.con));
		}
		
		return list;
	}

	@Override
	public List<Message> getDirectMessagesSent() {
		LinkedList<Message> list = new LinkedList<Message>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT id FROM mensajes WHERE id_autor = " + this.user.getId());
			if(res != null)
				while(res.next())
					list.add(new MessageImpl(res.getInt(1), this.con));
		}
		
		return list;
	}

	@Override
	public List<Status> getFavorites() {
		LinkedList<Status> list = new LinkedList<Status>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			ResultSet res = con.query("SELECT tw.id FROM favoritos fa, tweet tw WHERE fa.id_usuario = " +
								this.user.getId()+" AND tw.id = fa.id_tweet ORDER BY tw.fecha DESC LIMIT " +
								(this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults));
			if(res != null)
				while(res.next())
					list.add(new StatusImpl(res.getInt(1), this.con));
			
		}
		
		return list;
	}

	@Override
	public List<Status> getFavorites(String screenName) {
		LinkedList<Status> list = new LinkedList<Status>();
		
		//El usuario debe estar logueado
		if(this.user != null){
			LinkedList<Object> param = new LinkedList<Object>();
			param.add(screenName);
			
			ResultSet res = this.con.query("SELECT tw.id FROM favoritos fa, tweet tw, usuario us WHERE us.screenName = ? AND " +
					"fa.id_usuario = us.id AND tw.id = fa.id_tweet ORDER BY tw.fecha DESC LIMIT " +
					(this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults), param);
			if(res != null)
				while(res.next())
					list.add(new StatusImpl(res.getInt(1), this.con));
		}
		
		return list;
	}

	@Override
	public List<Status> getHomeTimeline() throws TwitterException {
		return this.getTimeline(this.user);
	}
	
	@Override
	public List<Status> getUserTimeline(Number userId) throws TwitterException{
		return this.getTimeline(new UserImpl(userId.intValue()));
	}
	
	@Override
	public List<Status> getUserTimeline(String screenName) throws TwitterException {
		List<Object> param = new LinkedList<Object>();
		param.add(screenName);
		ResultSet res = this.con.query("SELECT id FROM usuario WHERE screenName = ? LIMIT 1", param);
		try {
			if(res == null || !res.next())
				throw new TwitterException("No existe ningún usuario con ese screeName");
			return this.getTimeline(new UserImpl(res.getInt(1))); 
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getRetweetsOfMe");
			return null;
		}
	}
	
	private List<Status> getTimeline(User user) throws TwitterException {
		LinkedList<Status> list = new LinkedList<Status>();
		
		if(user == null)
			throw new TwitterException("Usuario no logueado");
		
		ResultSet res = this.con.query("SELECT DISTINCT tw.id FROM tweet tw, retweet re, seguidores se WHERE " +
				"tw.autor = " + user.getId() + " OR ( se.id_seguidor = "+user.getId()+" AND " +
					"((tw.autor = se.id_seguido) OR ( se.id_seguido = re.id_usuario AND tw.id = re.id_tweet ) ) ) " +
					"ORDER BY tw.fecha DESC " +
					"LIMIT " + (this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults));
		
		if(res != null)
			while(res.next())
				list.add(new StatusImpl(res.getInt(1), this.con));
		
		return list;
	}

	@Override
	public boolean isValidLogin() {
		return this.user != null;
	}

	@Override
	public Status retweet(Status tweet) {
		if(this.user == null)
			return null;
		this.con.updateQuery("INSERT INTO retweet VALUES ("+this.user.getId()+", "+tweet.getId()+")");
		return tweet;
	}

	@Override
	public List<Status> search(String searchTerm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxResults() {
		return this.maxResults;
	}

	@Override
	public List<Status> getMentions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getRetweeters(Status tweet) {
		if(this.user == null)
			return null;
		ResultSet res = this.con.query("SELECT id_usuario FROM retweet WHERE re.id_tweet = "+tweet.getId() + 
				"LIMIT" + (this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults));
		
		List<User> list = new LinkedList<User>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new UserImpl(res.getInt(1), this.con));
				}
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getRetweetsOfMe");
			}
		}
		return null;
	}

	@Override
	public List<Status> getRetweetsByMe() {
		if(this.user == null)
			return null;
		ResultSet res = this.con.query("SELECT tw.id FROM tweet tw, retweet re WHERE re.id_usuario = "+this.user.getId() + 
				" AND re.id_tweet = tw.id ORDER BY tw.fecha DESC LIMIT" +
				(this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults));
		
		List<Status> list = new LinkedList<Status>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new StatusImpl(res.getInt(1), this.con));
				}
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getRetweetsOfMe");
			}
		}
		return null;
	}

	@Override
	public List<Status> getRetweetsOfMe() {
		if(this.user == null)
			return null;
		ResultSet res = this.con.query("SELECT tw.id FROM tweet tw, retweet re WHERE tw.autor = "+this.user.getId() + 
				" AND re.id_tweet = tw.id ORDER BY tw.fecha DESC LIMIT" +
				(this.maxResults == -1 ? TwitterImpl.maxAllowedResults : this.maxResults));
		
		List<Status> list = new LinkedList<Status>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new StatusImpl(res.getInt(1), this.con));
				}
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getRetweetsOfMe");
			}
		}
		return null;
	}

	@Override
	public String getScreenName() {
		if(this.user == null)
			return null;
		
		return this.user.getScreenName();
	}

	@Override
	public User getSelf() {
		return this.user;
	}

	@Override
	public Status getStatus() throws TwitterException {
		return this.user.getStatus();
	}

	@Override
	public Status getStatus(Number id) throws TwitterException {
		ResultSet res = this.con.query("SELECT id FROM usuario WHERE id = " + id.intValue() + " LIMIT 1");
		try {
			if(res == null || !res.next())
				throw new TwitterException("No existe un usuario con ese ID");
			else
				return new StatusImpl(id.intValue());
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getStatus");
			throw new TwitterException("No existe un usuario con ese ID");
		}
	}

	@Override
	public Status getStatus(String screenName) throws TwitterException {
		List<Object> param = new LinkedList<Object>();
		param.add(screenName);
		ResultSet res = this.con.query("SELECT id FROM usuario WHERE screenName = ? LIMIT 1", param);
		try {
			if(res == null || !res.next())
				throw new TwitterException("No existe un usuario con ese screenName");
			else
				return new StatusImpl(res.getInt(1));
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error en TwitterImpl.getStatus");
			throw new TwitterException("No existe un usuario con ese ID");
		}
	}

	@Override
	public Message sendMessage(String recipient, String text)
			throws TwitterException {
		// TODO Auto-generated method stub
		return null;
	}
	


}
