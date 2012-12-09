package servidor;
import java.util.Date;
import java.util.List;

import interfacesComunes.AStream;
import interfacesComunes.Conexion;
import interfacesComunes.Message;
import interfacesComunes.Status;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterEvent;
import interfacesComunes.Twitter_Geo;
import interfacesComunes.Twitter_Users;
import excepcionesComunes.TwitterException;
import interfacesComunes.Twitter_Account;
import interfacesComunes.User;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import servidor.db.ConexionImpl;



public class TwitterSuscriptorImpl implements Twitter {

	public static class TweetEntity implements Twitter.TweetEntity{

		private static final long serialVersionUID = -4096887025640652171L;
		
		private KEntityType type;
		private int start;
		private int end;
		private Conexion con;
		private BigInteger tweet_id;
		
		public TweetEntity(BigInteger tweet_id, KEntityType type, int start, int end, Conexion con){
			this.con=con;
			this.type = type;
			this.start = start;
			this.end=end;
			this.tweet_id=tweet_id;
		}
		
		public String displayVersion(){
			String sol = null;
			String textTweet=null;
			if(this.type==KEntityType.urls){
				//TODO: pendiente de si se acortan o no las urls
			}
			else if(this.type==KEntityType.user_mentions){
				ResultSet res=con.query("SELECT texto FROM tweet WHERE id="+this.tweet_id + " LIMIT 1");
				try {
					if(res.next()){
						textTweet=res.getString(1);
					}
				} catch (SQLException e) {
					ServerCommon.TwitterWarning(e, "Error al acceder a la bd");
				}
				sol=textTweet.substring(this.start+1,this.end);
			}
			return sol;
		}

		@Override
		public int getStart() {
			
			return 0;
		}

		@Override
		public int getEnd() {
			
			return 0;
		}

		@Override
		public KEntityType getType() {
			
			return null;
		}

		@Override
		public String getDisplay() {
			
			return null;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6621123794067420801L;
	private static final int maxAllowedResults = 300;
	
	private Twitter_Geo geo;
	private User user;
	private Twitter_Users twitter_user;
	private Conexion con;
	private int maxResults = 20;
	

	/**
	 * Contructor para conexión sin usuario. Solo lectura. Cambio: por ahora no lo vamos a soportar
	 */
	/*public TwitterImpl() {
		super();
		this.user = null;
		this.twitter_user = null;
		this.con = new ConexionImpl();
		this.geo= new Twitter_GeoImpl(this.con);
	}*/
	
	public TwitterSuscriptorImpl(Long accountId, HashMap<Long, LinkedList<AStream.IListen>> callbackArray){
		this.con = new ConexionImpl();
		this.user = new UserImpl(accountId, this.con,this.user);
		this.twitter_user = new Twitter_UsersImpl(this.con,this.user);
		this.geo= new Twitter_GeoImpl(this.con);
	}


	public Twitter_Account account() {
		return new Twitter_AccountImpl(this, this.con,this.user);
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
			//int userId = this.user.getId();
			//TODO: borrado de mensaje, si lo borra el emisor se le borra al destinatario??
			//int res = con.query("DELETE FROM mensaje WHERE id = "+id+" AND owner = "+userId+" LIMIT 1");
		}
	}

	
	public void destroyStatus(Number id) throws TwitterException {
		if(this.user != null && id != null){
			Long userId = this.user.getId();
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
				try {
					while(res.next())
						list.add(new MessageImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
				} catch (SQLException e) {
					ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getDirectMessages");
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
			if(res != null)
				try {
					while(res.next())
						list.add(new MessageImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
				} catch (SQLException e) {
					ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getDirectMessagesSent");
				}
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
								(this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults));
			if(res != null)
				try {
					while(res.next())
						list.add(new StatusImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
				} catch (SQLException e) {
					ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getFavorites");
				}
			
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
					(this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults), param);
			if(res != null)
				try {
					while(res.next())
						list.add(new StatusImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
				} catch (SQLException e) {
					ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getFavorites");
				}
		}
		
		return list;
	}

	@Override
	public List<Status> getHomeTimeline() throws TwitterException {
		return this.getTimeline(this.user);
	}
	
	@Override
	public List<Status> getUserTimeline(Long userId) throws TwitterException{
		return this.getTimeline(new UserImpl(userId,this.con,this.user ));
	}
	
	@Override
	public List<Status> getUserTimeline(String screenName) throws TwitterException {
		List<Object> param = new LinkedList<Object>();
		param.add(screenName);
		ResultSet res = this.con.query("SELECT id FROM usuario WHERE screenName = ? LIMIT 1", param);
		try {
			if(res == null || !res.next())
				throw new TwitterException("No existe ningún usuario con ese screeName");
			return this.getTimeline(new UserImpl(res.getLong(1), this.con,this.user)); 
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getRetweetsOfMe");
			return null;
		}
	}
	
	private List<Status> getTimeline(User user) throws TwitterException {
		LinkedList<Status> list = new LinkedList<Status>();
		
		if(user == null)
			throw new TwitterException("Usuario no logueado");
		
		ResultSet res = this.con.query("SELECT DISTINCT id FROM (SELECT id, fecha FROM tweet WHERE autor = "+user.getId()+
				" UNION SELECT id, fecha FROM  tweet tw, seguidores se WHERE se.id_seguidor = "+user.getId()+
				" AND tw.autor = se.id_seguido UNION SELECT id, fecha FROM tweet tw, retweet re, seguidores se WHERE " +
				"se.id_seguidor = "+user.getId()+" AND se.id_seguido = re.id_usuario AND tw.id = re.id_tweet ) res " +
				"ORDER BY fecha DESC " +
				"LIMIT " + (this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults));
		
		if(res != null)
			try {
				while(res.next())
					list.add(new StatusImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error de BD en TwitterImpl.getTimeline");
			}
		
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
		TwitterInitImplSuscriptor.sendThroughTopic(tweet, this.user.getId());
		return tweet;
	}

	@Override
	public List<Status> search(String searchTerm) {
		// TODO 
		return null;
	}
	
	@Override
	public List<Status> search(String searchTerm, ICallback callback, int rpp) {
		// TODO
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
				"LIMIT" + (this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults));
		
		List<User> list = new LinkedList<User>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new UserImpl(res.getLong(1), this.con,this.user));
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
				(this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults));
		
		List<Status> list = new LinkedList<Status>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new StatusImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user));
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
				(this.maxResults == -1 ? TwitterSuscriptorImpl.maxAllowedResults : this.maxResults));
		
		List<Status> list = new LinkedList<Status>();
		if(res != null){
			try {
				while(res.next()){
					list.add(new StatusImpl(BigInteger.valueOf(res.getLong(1)), this.con,this.user ));
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
				return new StatusImpl(BigInteger.valueOf(id.longValue()),this.con,this.user );
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
				return new StatusImpl(BigInteger.valueOf(res.getLong(1)),this.con,this.user );
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error en TwitterImpl.getStatus");
			throw new TwitterException("No existe un usuario con ese screenName");
		}
	}

	@Override
	public Message sendMessage(String recipient, String text)
			throws TwitterException {
		
		//Comprobamos la existencia del destinatario y obtenemos su id
		List<Object> param = new LinkedList<Object>();
		param.add(recipient);
		ResultSet res = this.con.query("SELECT id FROM usuario WHERE screenName = ? LIMIT 1", param);
		Long id_dest;
		try {
			if(res == null || !res.next())
				throw new TwitterException("No existe un usuario con ese screenName");
			else
				id_dest = (long) res.getInt(1);
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error en TwitterImpl.sendMessage");
			throw new TwitterException("No existe un usuario con ese screenName");
		}
		
		if(!users().getFollowerIDs().contains(id_dest)){
			throw new TwitterException("Ese usuario no te está siguiendo");
		}
		
		List<Object> params = new LinkedList<Object>();
		params.add(this.user.getId());
		params.add(id_dest);
		params.add(text);
		params.add(new Date().getTime()/1000);
		
		int resul = this.con.updateQuery("INSERT INTO mensajes (id_autor, id_destinatario, texto, fecha) " +
				"VALUES (? , ? , ? , ?)", params);
		
		if(resul == 0)
			throw new TwitterException("No se ha podido enviar el mensaje");
		
		ResultSet last_id = this.con.query("SELECT LAST_INSERT_ID()");
		BigInteger message_id;
		try {
			if(last_id == null || !last_id.next())
				throw new TwitterException("Upss, esto no debería ocurrir nunca");
			else
				message_id = BigInteger.valueOf(last_id.getLong(1));
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error en TwitterImpl.sendMessage");
			throw new TwitterException("No se ha podido obtener el mensaje, pero sí se ha mandado");
		}
		
		Message mes = new MessageImpl(message_id, this.con,this.user);
		
		//Lo enviamos al topic
		TwitterInitImplSuscriptor.sendThroughTopic(mes, id_dest);
		
		return mes;
		
	}

	@Override
	public void setFavorite(Status status, boolean isFavorite) {
		
		if(this.user == null)
			return; //User not logged
		
		String event_type = "0";
		
		if(!isFavorite){
			int res = this.con.updateQuery("DELETE FROM favoritos WHERE id_usuario = "+this.user.getId()+" AND id_tweet = "+status.getId()+" LIMIT 1");
			if(res > 0)
				event_type = TwitterEvent.Type.UNFAVORITE;
			
		}else{
			int res = this.con.updateQuery("INSERT INTO favoritos (id_usuario, id_tweet) VALUES ("+this.user.getId()+","+status.getId()+")");
			if(res > 0)
				event_type = TwitterEvent.Type.FAVORITE;
		}
		
		Long status_owner = status.getUser().getId();
		
		if(!event_type.equals("0")){
			try {
				TwitterEvent event = new TwitterEventImpl(this.user.getId(), status_owner, status, event_type, this.con,this.user);
				TwitterInitImplSuscriptor.sendThroughTopic(event, status_owner);
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "No se ha podido publicar en el topic");
			}
		}
	
	}

	@Override
	public void setPageNumber(Integer pageNumber) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Status updateStatus(String statusText, Number inReplyToStatusId) throws TwitterException {
		
		if(this.user == null)
			return null; //User not logged
		
		//Comprobamos la existencia del tweet al que estamos respondiendo
		int replyId = inReplyToStatusId.intValue();
		if(replyId > 0){
			ResultSet res = this.con.query("SELECT id FROM tweet WHERE id = "+replyId+" LIMIT 1");
			try {
				if(res == null || !res.next())
					throw new TwitterException("No existe un tweet con ese id");
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error en TwitterImpl.sendMessage");
				throw new TwitterException("No existe un tweet con ese id");
			}
		}
		
		//Hay que recortar el mensaje
		if(this.countCharacters(statusText) > 140) {
			statusText = statusText.substring(0, 140);
		}
		
		List<Object> params = new LinkedList<Object>();
		params.add(statusText);
		params.add(this.user.getId());
		params.add(new Date().getTime()/1000);
		params.add(replyId);
		
		//Insertamos el nuevo tweet en la BD
		this.con.updateQuery("INSERT INTO tweet (texto, autor, fecha, inReplyTo) VALUES (?, ?, ?, ?)", params);
		
		ResultSet last_id = this.con.query("SELECT LAST_INSERT_ID()");
		BigInteger status_id;
		try {
			if(last_id == null || !last_id.next())
				return null; //Emmm, esto NUNCA debería ocurrir
			else
				status_id = BigInteger.valueOf(last_id.getLong(1));
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error en TwitterImpl.updateStatus");
			return null;
		}
		
		Status status = new StatusImpl(status_id, this.con,this.user);		
		
		//Mandamos el tweet a todos los seguidores
		TwitterInitImplSuscriptor.sendThroughTopic(status, this.user.getId());
		
		this.user.aumentarContador();
		return status;
	}

	@Override
	public Status updateStatus(String statusText) {
		try{
			return updateStatus(statusText, 0);
		}catch(TwitterException e){
			ServerCommon.TwitterWarning(e, "Ha fallado la creación del status");
			return null;
		}
	}

	@Override
	public Twitter_Users users() {
		return this.twitter_user;
	}
	

	@Override
	public Twitter_Geo geo() {
		return this.geo;
	}


	@Override
	public AStream stream() {
		
		return new AStreamImpl(this, this.con);
	}




}
