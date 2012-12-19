package interfacesComunes;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;

import winterwell.jtwitter.RateLimit;
import winterwell.jtwitter.TwitterImpl.KRequestType;

import excepcionesComunes.TwitterException;

/**
 * Es el punto inicial a partir de la cual se puede acceder a Twitter y todos sus metodos.
 */
public interface Twitter extends Serializable, Remote{


	/**Enumerado que especifica los diferentes 
	 *
	 */
	public static enum KEntityType {
		hashtags, urls, user_mentions
	}


	/** Interfaz para el manejo de busquedas extensas.
	 */
	public interface ICallback extends Serializable{
		boolean process(List<Status> statuses);
	}

	/** Representa las entities en un tweet y la forma de obtener su información
	 *
	 */
	public static interface TweetEntity extends Serializable{
		int getStart();
		int getEnd();
		KEntityType getType();
		String displayVersion();
		String toString();
		String getDisplay();
	}


	/**
	 * Interface par el cliente http client. Utilidad que permite el funcionamiento
	 * de OAuth para la autenticación del usuario.
	 * No usado directamente por nosotros.
	 */
	public static interface IHttpClient {

		boolean canAuthenticate();

		HttpURLConnection connect(String url, Map<String, String> vars,
				boolean authenticate) throws IOException;

		IHttpClient copy();

		String getHeader(String headerName);

		String getPage(String uri, Map<String, String> vars,
				boolean authenticate) throws TwitterException;

		RateLimit getRateLimit(KRequestType reqType);

		String post(String uri, Map<String, String> vars, boolean authenticate)
				throws TwitterException;

		HttpURLConnection post2_connect(String uri, Map<String, String> vars)
				throws Exception;

		void setTimeout(int millisecs);

		boolean isRetryOnError();

		void setRetryOnError(boolean retryOnError);
	}

	/**
	 * Interfaz comun para tweets y mensajes de twitter.
	 *
	 */
	public static interface ITweet extends Serializable {

		/**
		 * @return fecha de creacion
		 */
		Date getCreatedAt();

		/**
		 * @return identificador
		 */
		Number getId();

		/**
		 * @return localizacion
		 */
		String getLocation();

		/**
		 * @return lista de screen names de usuarios mencionados
		 */
		List<String> getMentions();

		/**
		 * @return objeto Place 
		 */
		Place getPlace();

		/** Obtiene el texto del tweet o mensaje
		 */
		String getText();

		/**
		 * Obtiene las entities de un tipo determinado 
		 * @param type tipo del a entity requerida
		 * @return lista de entities del tipo especificado 
		 */
		List<TweetEntity> getTweetEntities(KEntityType type);

		/** 
		 * @return usuario que hizo el tweet
		 */
		User getUser();

		/**
		 * @return texto del tweet o mensaje
		 */
		String getDisplayText();
	}

	/**
	 * Obtiene el objeto Twitter_Account
	 */
	public Twitter_Account account();

	/**
	 * Devuelve el numero de caracteres de un texto teniendo en cuenta que Twitter trata las urls como cadenas de 20 caracteres.
	 * @param statusText Texto del que obtener la longitud
	 * @return Numero de caracteres del texto
	 */
	public int countCharacters(String statusText);

	/**
	 * Elimina un Tweet o Mensaje
	 * @param tweet Elemento a eliminar.
	 * @throws TwitterException Si no se ha podido borrar el elemento (p.e: no nos pertenece)
	 */
	public void destroy(Twitter.ITweet tweet) throws TwitterException;

	/**
	 * Elimina un mensaje privado.
	 * @param id Id del mensaje a eliminar.
	 */
	public void destroyMessage(Number id); //Y ESTE POR QUe COJONES NO LANZA EXCEPCIoN??

	/**
	 * Elimina un Tweet (Status).
	 * @param id Id del Status a eliminar.
	 * @throws TwitterException Si no se ha podido borrar el elemento (p.e: no nos pertenece)
	 */
	public void destroyStatus(Number id) throws TwitterException;

	/**
	 * Metodo que devuelve la lista de Message's enviados <u>al</u> usuario actual
	 * @return Lista de Message's enviados <u>al</u> usuario actual
	 */
	public List<Message> getDirectMessages();

	/**
	 * Metodo que devuelve la lista de Message's enviados <u>por</u> el usuario actual
	 * @return Lista de Message's enviados <u>por</u> el usuario actual
	 */
	public List<Message> getDirectMessagesSent();

	/**
	 * Obtiene una lista de status favoritos para el usuario autenticado.
	 * @return Lista de status favoritos mas recientes ordenados de mas a menos recientes.
	 */
	public List<Status> getFavorites();

	/**
	 * Obtiene una lista de status favoritos para el usuario indicado.
	 * @param screenName
	 * @return Lista de status favoritos mas recientes ordenados de mas a menos recientes para el usuario indicado.
	 */
	public List<Status> getFavorites(String screenName);

	/**
	 * Devuelve los 20 (o numero definido por maxResults) Status mas recientes posteados por el usuario y sus amigos, incluyendo retweets.
	 * @return Lista de los 20 (o numero definido por maxResults) Status mas recientes del timeline
	 * @throws TwitterException Si no se ha podido obtener el timeline (P.E: el usuario no esta logueado)
	 */
	public List<Status> getHomeTimeline() throws TwitterException;

	/**
	 * Provee soporte para obtener varias paginas de datos. -1 indica que se quiere obtener el maximo posible.
	 * @return Maximo de resultados que se pueden obtener.
	 */
	public int getMaxResults();

	/**
	 * Obtiene una lista de las menciones mas recientes al usuario autenticado.
	 * @return Lista de las menciones mas recientes ordenadas de mas a menos reciente.
	 */
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
	 * @return El User actual o <b>null</b> si no se ha logeado y es una sesion anonima.
	 */
	public User getSelf();

	/**
	 * Obtiene el "status" del usuario (su ultimo tweet).
	 * @return El ultimo Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no esta logueado.
	 */
	public Status getStatus() throws TwitterException;

	/**
	 * Obtiene el "status" del usuario indicado(su ultimo tweet).
	 * @param id identificador del usuario del que obtener el status.
	 * @return El ultimo Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no existe.
	 */
	public Status getStatus(Number id) throws TwitterException;

	/**
	 * Obtiene el "status" del usuario indicado(su ultimo tweet).
	 * @param screenName Usuario del que obtener el status.
	 * @return El ultimo Status o <b>null</b> en caso de que el usuario no haya publicado nunca un tweet.
	 * @throws TwitterException Si el usuario no existe.
	 */
	public Status getStatus(String screenName) throws TwitterException;

	/**
	 * Devuelve los Status mas recientes del usuario indicado.
	 * @param userId Id del usuario del que obtener sus Status
	 * @return Lista de Status del usuario ordenada de mas a menos reciente.
	 * @throws TwitterException Si no se ha podido encontrar al usuario o no se ha podido obtener sus status.
	 */
	public List<Status> getUserTimeline(Long userId) throws TwitterException;

	/**
	 * Devuelve los Status mas recientes del usuario indicado.
	 * @param screenName Screenname del usuario del que obtener sus Status
	 * @return Lista de Status del usuario ordenada de mas a menos reciente.
	 * @throws TwitterException Si no se ha podido encontrar al usuario o no se ha podido obtener sus status.
	 */
	public List<Status> getUserTimeline(String screenName) throws TwitterException;

	/**
	 * Inica si el usuario esta logueado
	 * @return True si esta logueado. False en caso contrario.
	 */
	public boolean isValidLogin();

	/**
	 * Hace un retweet sin modificar el texto.
	 * @param tweet Tweet a retweetear
	 * @return El mismo tweet. Null si no se ha podido hacer retweet (el usuario no esta logueado).
	 */
	public Status retweet(Status tweet);

	/**
	 * Realiza una busqueda en Tweets
	 * @param searchTerm Termino a buscar
	 * @return Lista de tweets coincidentes.
	 */
	public List<Status> search(String searchTerm);

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
	 * @param statusText Texto del mensaje. No debe tener mas de 140 caracteres.
	 * @return El nuevo tweet, o <b>null</b> en caso de error.
	 */
	public Status updateStatus(String statusText);

	/**
	 * Actualiza el status del usuario conectado (crea un nuevo tweet) en respuesta a otro.
	 * @param statusText Texto del tweet. No debe tener mas de 140 caracteres.
	 * @param inReplyToStatusId Id del tweet al cual estamos respondiendo.
	 * @return Status publicado
	 * @throws TwitterException Si no se ha podido publicar el status (el id de respuesta no es correcto)
	 */
	public Status updateStatus(String statusText, Number inReplyToStatusId) throws TwitterException;

	/**
	 * Devuelve un objeto Twitter_Users para realizar acciones sobre usuarios en Twitter.
	 * @return Objeto Twitter_Users
	 */
	public Twitter_Users users();

	/**
	 * Devuelve el objeto con los metodos tipo Geo
	 */
	public Twitter_Geo geo();


	/**
	 * Cambia el valor del placeId asociado a Twitter. Este Id es el del place por geolocalizacion.
	 * Si se le pasa -1 simboliza que la geolocalizacion esta desactivada
	 * @param placeId
	 */
	public void setMyPlace(Long placeId);


	/**
	 * El id del Place asociado a la sesion por Geolocalizacion, si es -1 es que no esta activada
	 * @return placeId
	 */
	public Long getMyPlace();
	public AStream stream();

}
