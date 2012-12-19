package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Clase que solo nos sirve a nosotros para el método getEvents()
 * 
 *
 */
public interface AStream extends Remote, Serializable{

	/**
	 * Para procesar los distintos tweets y eventos
	 * 
	 *
	 */
	public interface IListen extends Remote, Serializable{
		
		/**
		 * Procesamos un evento del tipo TwitterEvents
		 * @param event de TwitterEvent
		 * @return boolean true si todo va bien
		 * @throws RemoteException
		 */
		public boolean processEvent(TwitterEvent event) throws RemoteException;
		
		/**
		 * Procesa un evento del sistema. No se usa en nuestro cliente.
		 * @param obj
		 * @return boolean true si todo va bien
		 * @throws RemoteException
		 */
		public boolean processSystemEvent(java.lang.Object[] obj) throws RemoteException;
		
		/**
		 * Procesa un tweet nuevo que se acaba de generar
		 * @param tweet
		 * @return boolean true si todo va bien
		 * @throws RemoteException
		 */
		public boolean processTweet(Twitter.ITweet tweet) throws RemoteException;
	}
	
	/**Devuelve los eventos cuyo objetivo es el user loggeado
	 * @return List<TwitterEvent> lista de eventos
	 **/
	public java.util.List<TwitterEvent>	getEvents(); 
}
