package interfacesComunes;

import java.util.Date;
import java.util.List;

import servidor.TwitterImpl;

import excepcionesComunes.TwitterException;

public interface Twitter {

	public interface KEntityType {

	}

	public static interface TweetEntity {
		java.util.Date 	getCreatedAt();
		int	getId();
		String 	getLocation();
		java.util.List<java.lang.String> getMentions(); 
		Place getPlace();
		String 	getText();
		List<Twitter.TweetEntity> getTweetEntities(TwitterImpl.KEntityType type);
		User getUser();
	}

	public interface ITweet {
		Date getCreatedAt();
		int getId();
		String 	getLocation();
		List<String> getMentions();
		Place getPlace();
		String 	getText();
		List<Twitter.TweetEntity> getTweetEntities(Twitter.KEntityType type);
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
	
}
