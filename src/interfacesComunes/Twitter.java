package interfacesComunes;

import java.util.Date;
import java.util.List;

import servidor.TwitterImpl;

import excepcionesComunes.TwitterException;

public interface Twitter {

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
	
	public void destroyMessage(java.lang.Number id); //Y ESTE POR QUÉ COJONES NO LANZA EXCEPCIÓN??
	
	public void destroyStatus(java.lang.Number id) throws TwitterException;
	
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
	
	
	
	
}
