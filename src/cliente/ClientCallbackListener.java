package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.application.Platform;

/**
 * Esta clase se trata de un intermediario entre el cliente y el servidor encargándose de distribuir el evento al 
 * listener que está asociado.
 */
public class ClientCallbackListener extends UnicastRemoteObject implements AStream.IListen, Remote, ClientListener{

	private static final long serialVersionUID = 6865106167203455251L;
	private AStream.IListen listener;

	/* (non-Javadoc)
	 * @see cliente.ClientListener#setListener(interfacesComunes.AStream.IListen)
	 */
	@Override
	public void setListener(AStream.IListen listener) {
		this.listener = listener;
	}

	protected ClientCallbackListener() throws RemoteException {
		super();
	}

	public static void main(String[] args){

	}

	@Override
	public boolean processEvent(final TwitterEvent event) throws RemoteException {
		if(listener != null){
			Platform.runLater(new Runnable() { //No se puede interactuar directamente con el thread de javafx
				@Override public void run() {
					try {
						listener.processEvent(event);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return true;
	}

	@Override
	public boolean processSystemEvent(final Object[] obj) throws RemoteException {
		if(listener != null){
			Platform.runLater(new Runnable() { //No se puede interactuar directamente con el thread de javafx
				@Override public void run() {
					try {
						listener.processSystemEvent(obj);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return true;
	}

	@Override
	public boolean processTweet(final ITweet tweet) throws RemoteException {
		if(listener != null){
			Platform.runLater(new Runnable() { //No se puede interactuar directamente con el thread de javafx
				@Override public void run() {
					try {
						if(TwitterClient.amigosDelLogueado().contains(tweet.getUser().getId()))
							listener.processTweet(tweet);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return true;
	}

}
