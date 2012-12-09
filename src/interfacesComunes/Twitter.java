package interfacesComunes;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.RateLimit;
import winterwell.jtwitter.TwitterExceptionImpl;
import winterwell.jtwitter.URLConnectionHttpClient;
import winterwell.jtwitter.TwitterImpl.KRequestType;

import excepcionesComunes.TwitterException;

public interface Twitter extends Serializable, Remote{
	
	

	public static enum KEntityType {
		hashtags, urls, user_mentions
	}
	

	public interface ICallback extends Serializable{
		boolean process(List<Status> statuses);
	}

	public static interface TweetEntity extends Serializable{
		int getStart();
		int getEnd();
		KEntityType getType();
		String displayVersion();
		String toString();
		String getDisplay();
	}
	
	
	/**
	 * Interface for an http client - e.g. allows for OAuth to be used instead.
	 * The standard version is {@link OAuthSignpostClient}.
	 * <p>
	 * If creating your own version, please provide support for throwing the
	 * right subclass of TwitterException - see
	 * {@link URLConnectionHttpClient#processError(java.net.HttpURLConnection)}
	 * for example code.
	 * 
	 * @author Daniel Winterstein
	 */
	public static interface IHttpClient {

		/**
		 * Whether this client is setup to do authentication when contacting the
		 * Twitter server. Note: This is a fast method that does not call the
		 * server, so it does not check whether the access token or password is
		 * valid. See {Twitter#isValidLogin()} or
		 * {@link Twitter_Account#verifyCredentials()} if you need to check a
		 * login.
		 * */
		boolean canAuthenticate();

		/**
		 * Lower-level GET method.
		 * 
		 * @param url
		 * @param vars
		 * @param authenticate
		 * @return
		 * @throws IOException
		 */
		HttpURLConnection connect(String url, Map<String, String> vars,
				boolean authenticate) throws IOException;

		/**
		 * @return a copy of this client. The copy can share structure, but it
		 *         MUST be safe for passing to a new thread to be used in
		 *         parallel with the original.
		 */
		IHttpClient copy();

		/**
		 * Fetch a header from the last http request. This is inherently NOT
		 * thread safe. Headers from error messages should (probably) be cached.
		 * 
		 * @param headerName
		 * @return header value, or null if unset
		 */
		String getHeader(String headerName);

		/**
		 * Send an HTTP GET request and return the response body. Note that this
		 * will change all line breaks into system line breaks!
		 * 
		 * @param uri
		 *            The uri to fetch
		 * @param vars
		 *            get arguments to add to the uri
		 * @param authenticate
		 *            If true, use authentication. The authentication method
		 *            used depends on the implementation (basic-auth, OAuth). It
		 *            is an error to use true if no authentication details have
		 *            been set.
		 * 
		 * @throws TwitterException
		 *             for a variety of reasons
		 * @throws TwitterExceptionImpl.E404
		 *             for resource-does-not-exist errors
		 */
		String getPage(String uri, Map<String, String> vars,
				boolean authenticate) throws TwitterException;

		/**
		 * @see Twitter#getRateLimit(KRequestType) This is where the Twitter
		 *      method is implemented.
		 */
		RateLimit getRateLimit(KRequestType reqType);

		/**
		 * Send an HTTP POST request and return the response body.
		 * 
		 * @param uri
		 *            The uri to post to.
		 * @param vars
		 *            The form variables to send. These are URL encoded before
		 *            sending.
		 * @param authenticate
		 *            If true, send user authentication
		 * @return The response from the server.
		 * 
		 * @throws TwitterException
		 *             for a variety of reasons
		 * @throws TwitterExceptionImpl.E404
		 *             for resource-does-not-exist errors
		 */
		String post(String uri, Map<String, String> vars, boolean authenticate)
				throws TwitterException;

		/**
		 * Lower-level POST method.
		 * 
		 * @param uri
		 * @param vars
		 * @return a freshly opened authorised connection
		 * @throws TwitterException
		 */
		HttpURLConnection post2_connect(String uri, Map<String, String> vars)
				throws Exception;

		/**
		 * Set the timeout for a single get/post request. This is an optional
		 * method - implementations can ignore it!
		 * 
		 * @param millisecs
		 */
		void setTimeout(int millisecs);

		/**
		 * If true, will wait 1/2 second and make a 2nd request when presented with
		 * a server error (E50X). Only retries once -- a 2nd fail will throw an exception.
		 * 
		 * This policy handles most Twitter server glitches.
		 */
		boolean isRetryOnError();

		void setRetryOnError(boolean retryOnError);

	}
	

	public static interface ITweet extends Serializable {

		Date getCreatedAt();

		/**
		 * Twitter IDs are numbers - but they can exceed the range of Java's
		 * signed long.
		 * 
		 * @return The Twitter id for this post. This is used by some API
		 *         methods. This may be a Long or a BigInteger.
		 */
		Number getId();

		/**
		 * @return the location of this tweet. Can be null, never blank. This
		 *         can come from geo-tagging or the user's location. This may be
		 *         a place name, or in the form "latitude,longitude" if it came
		 *         from a geo-tagged source.
		 *         <p>
		 *         Note: This will be set if Twitter supply any geo-information.
		 *         We extract a location from geo and place objects.
		 */
		String getLocation();

		/**
		 * @return list of screen-names this message is to. May be empty, never
		 *         null. For Statuses, this is anyone mentioned in the message.
		 *         For DMs, this is a wrapper round
		 *         {@link Message#getRecipient()}.
		 *         <p>
		 *         Notes: This method is in ITweet as a convenience to allow the
		 *         same code to process both Statuses and Messages where
		 *         possible. It would be better named "getRecipients()", but for
		 *         historical reasons it isn't.
		 */
		List<String> getMentions();

		/**
		 * @return more information on the location of this tweet. This is
		 *         usually null!
		 */
		Place getPlace();

		/** The actual status text. This is also returned by {@link #toString()} */
		String getText();

		/**
		 * Twitter wrap urls with their own url-shortener (as a defence against
		 * malicious tweets). You are recommended to direct people to the
		 * Twitter-url, but use the original url for display.
		 * <p>
		 * Entity support is off by default. Request entity support by setting
		 * {@link Twitter#setIncludeTweetEntities(boolean)}. Twitter do NOT
		 * support entities for search :(
		 * 
		 * @param type
		 *            urls, user_mentions, or hashtags
		 * @return the text entities in this tweet, or null if the info was not
		 *         supplied.
		 */
		List<TweetEntity> getTweetEntities(KEntityType type);

		/** The User who made the tweet */
		User getUser();

		/**
		 * @return text, with the t.co urls replaced.
		 * Use-case: for filtering based on text contents, when we want to
		 * match against the full url.
		 * Note: this does NOT resolve short urls from bit.ly etc. 
		 */
		String getDisplayText();

	}

	public Twitter_Account account();
	
	/**
	 * Devuelve el número de caracteres de un texto teniendo en cuenta que Twitter trata las urls como cadenas de 20 caracteres.
	 * @param statusText Texto del que obtener la longitud
	 * @return Número de caracteres del texto
	 */
	public int countCharacters(String statusText);
	
	public void destroy(Twitter.ITweet tweet) throws TwitterException;
	
	public void destroyMessage(Number id); //Y ESTE POR QUÉ COJONES NO LANZA EXCEPCIÓN??
	
	public void destroyStatus(Number id) throws TwitterException;
	
	/**
	 * Método que devuelve la lista de Message's enviados <u>al</u> usuario actual
	 * @return Lista de Message's enviados <u>al</u> usuario actual
	 */
	public List<Message> getDirectMessages();
	
	/**
	 * Método que devuelve la lista de Message's enviados <u>por</u> el usuario actual
	 * @return Lista de Message's enviados <u>por</u> el usuario actual
	 */
	public List<Message> getDirectMessagesSent();
	
	public List<Status> getFavorites();
	
	public List<Status> getFavorites(String screenName);
	
	/**
	 * Devuelve los 20 (o número definido por maxResults) Status más recientes posteados por el usuario y sus amigos, incluyendo retweets.
	 * @return Lista de los 20 (o número definido por maxResults) Status más recientes del timeline
	 * @throws TwitterException Si no se ha podido obtener el timeline (P.E: el usuario no está logueado)
	 */
	public List<Status> getHomeTimeline() throws TwitterException;
	
	/**
	 * Provee soporte para obtener varias páginas de datos. -1 indica que se quiere obtener el máximo posible.
	 * @return Máximo de resultados que se pueden obtener.
	 */
	public int getMaxResults();
	
	public List<Status> getMentions();
	
	/**
	 * Obtiene una lista de usuarios que han retweeteado un determinado tweet.
	 * @param tweet Tweet del que obtener los retweeters
	 * @return Lista de usuarios que han retweeteado ese tweet. 
	 */
	public List<User> getRetweeters(Status tweet);
	
	/**
	 * Obtiene los tweets que has retweeteado.
	 * @return Lista de tweets que has retweeteado.  Null si el usuario no esta logueado.
	 */
	public List<Status> getRetweetsByMe();
	
	/**
	 * Obtiene la lista de tus tweets que han sido retweeteados.
	 * @return Lista de tus tweets que han sido retweeteados. Null si el usuario no esta logueado.
	 */
	public List<Status> getRetweetsOfMe();
	
	/**
	 * Obtiene el screenName (ej: xafilox) del usuario.
	 * @return String con el screeName.
	 */
	public String getScreenName();
	
	/**
	 * Devuelve al usuario actual.
	 * @return El User actual o <b>null</b> si no se ha logeado y es una sesión anónima.
	 */
	public User getSelf();
	
	/**
	 * Obtiene el "status" del usuario (su último tweet).
	 * @return El último Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no está logueado.
	 */
	public Status getStatus() throws TwitterException;
	
	/**
	 * Obtiene el "status" del usuario indicado(su último tweet).
	 * @param userId Usuario del que obtener el status.
	 * @return El último Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no existe.
	 */
	public Status getStatus(Number id) throws TwitterException;
	
	/**
	 * Obtiene el "status" del usuario indicado(su último tweet).
	 * @param screenName Usuario del que obtener el status.
	 * @return El último Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no existe.
	 */
	public Status getStatus(String screenName) throws TwitterException;
	
	public List<Status> getUserTimeline(Long userId) throws TwitterException;
	
	public List<Status> getUserTimeline(String screenName) throws TwitterException;
	
	/**
	 * Inica si el usuario está logueado
	 * @return True si está logueado. False en caso contrario.
	 */
	public boolean isValidLogin();
	
	/**
	 * Hace un retweet sin modificar el texto.
	 * @param tweet Tweet a retweetear
	 * @return El mismo tweet. Null si no se ha podido hacer retweet (el usuario no está logueado).
	 */
	public Status retweet(Status tweet);
	
	/**
	 * Realiza una búsqueda en Tweets
	 * @param searchTerm Término a buscar
	 * @return Lista de tweets coincidentes.
	 * @see search(String searchTerm, Twitter.ICallback callback, int rpp)
	 */
	public List<Status> search(String searchTerm);
	
	public List<Status> search(String searchTerm, Twitter.ICallback callback, int rpp);
	
	/**
	 * Envia un mensaje privado desde el usuario actual al usuario especificado.
	 * @param recipient ScreenName del usuario al que mandar el mensaje.
	 * @param text Texto del mensaje.
	 * @return Message enviado.
	 * @throws TwitterException
	 */
	public Message sendMessage(String recipient, String text) throws TwitterException;
	
	/**
	 * Marca/desmarca como favorito un Status
	 * @param status Status al que poner/quitar de favoritos.
	 * @param isFavorite True para añadir a favoritos, False para quitarlo.
	 */
	public void setFavorite(Status status, boolean isFavorite);
	
	public void setPageNumber(Integer pageNumber);
	
	/**
	 * Actualiza el status del usuario conectado (crea un nuevo tweet).
	 * @param statusText Texto del mensaje. No debe tener más de 140 caracteres.
	 * @return El nuevo tweet, o <b>null</b> en caso de error.
	 */
	public Status updateStatus(String statusText);
	
	/**
	 * Actualiza el status del usuario conectado (crea un nuevo tweet) en respuesta a otro.
	 * @param statusText Texto del tweet. No debe tener más de 140 caracteres.
	 * @param inReplyToStatusId Id del tweet al cual estamos respondiendo.
	 * @return Status publicado
	 * @throws TwitterException Si no se ha podido publicar el status (el id de respuesta no es correcto)
	 */
	public Status updateStatus(String statusText, Number inReplyToStatusId) throws TwitterException;
	
	
	public Twitter_Users users();
	/**
	 * Devuelve el objeto con los metodos tipo Geo
	 */
	public Twitter_Geo geo();
	

	/**
	 * Devuelve los metodos del AStream, esto no se puede usar con JTwitter
	 */
	public AStream stream();
	
}
