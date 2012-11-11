package interfacesComunes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import servidor.TwitterImpl;

import excepcionesComunes.TwitterException;

public interface Twitter extends Serializable{

	public interface ICallback {
		boolean process(List<Status> statuses);
	}

	public static interface TweetEntity {
		
	}

	public interface ITweet {
		Date getCreatedAt();
		int getId();
		String 	getLocation();
		List<String> getMentions();
		Place getPlace();
		String 	getText();
		List<Twitter.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type);
		User getUser();
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
	
	public List<Status> getUserTimeline(Number userId) throws TwitterException;
	
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
	 * Actaliza el status del usuario conectado (crea un nuevo tweet).
	 * @param statusText Texto del mensaje. No debe tener más de 140 caracteres.
	 * @return El nuevo tweet, o <b>null</b> en caso de error.
	 */
	public Status updateStatus(String statusText);
	
	
	public Twitter_Users users();
	
}
