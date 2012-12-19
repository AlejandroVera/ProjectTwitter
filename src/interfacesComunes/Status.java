package interfacesComunes;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.Remote;
import java.util.List;

/** Define las operaciones que se pueden realizar sobre un tweet
 *
 */
public interface Status extends Twitter.ITweet, Serializable, Remote{
	
	/**
	 * @return numero de veces que ha sido retwiteado.
	 */
	int getRetweetCount();
	
	/** Devuelve el id del tweet.
	 * @return identificador del tweet
	 */
	BigInteger getId();
	
	/** 
	 * @return true si ha sido marcado como favorito por el usuario loggueado
	 */
	boolean isFavorite();
	

	/**
	 * @param myID Id del usuario
	 * @return true si este tweet ha sido retwiteado por el usuario con id myID
	 */
	boolean isRetweetedByMe(Number myID);
	
	/** Devuelve una lista de menciones a usuarios en este tweet
	 * @return lista con los nombres de usuario que son mencionados 
	 * en este tweet.
	 */
	List<String> getMentions();
	
	/** Devuelve el objeto Place del tweet
	 * @return lugar desde donde se ha twiteado.
	 */
	Place getPlace();
}
