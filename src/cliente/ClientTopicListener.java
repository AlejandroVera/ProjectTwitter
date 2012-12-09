package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.application.Platform;

/**
 * Esta clase se trata de un intermediario entre el cliente y el servidor
 * encargándose de distribuir el evento al listener que está asociado.
 */
public class ClientTopicListener implements javax.jms.MessageListener {

	private AStream.IListen listener;
	private TopicConnection connection;

	protected ClientTopicListener() throws RemoteException {
		super();

		try {

			// Obtiene una conexión JNDI por medio del fichero jndi.properties
			InitialContext ctx = new InitialContext();

			// Busca una factoría de conexiones y crea una conexión
			TopicConnectionFactory conFactory = (TopicConnectionFactory) ctx.lookup("Twitter");
			this.connection = conFactory.createTopicConnection();

			// Se crea una sesión JMS
			TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Se busca un topic JMS
			Topic chatTopic = (Topic) ctx.lookup("Eventos");

			// Generamos la lista de ids a los que escuchar
			String selector = "";
			for (Long id : TwitterClient.amigosDelLogueado()) {
				if (!selector.isEmpty())
					selector += " OR ";
				selector += "userId = " + id;
			}

			// Se crea un subscriptor JMS filtrando por usuarios a los que
			// escuchar.
			TopicSubscriber subscriber = subSession.createSubscriber(chatTopic, selector, true);
			// Establece el listener de mensajes JMS
			subscriber.setMessageListener(this);
			
			//Arrancamos la conexion
			connection.start();

		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
	}

	public void setListener(AStream.IListen listener) {
		this.listener = listener;
	}

	public boolean processEvent(final TwitterEvent event)
			throws RemoteException {
		if (listener != null) {
			// No se puede interactuar directamente con el thread de javafx
			Platform.runLater(new Runnable() { 
				@Override
				public void run() {
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

	public boolean processSystemEvent(final Object[] obj)
			throws RemoteException {
		if (listener != null) {
			// No se puede interactuar directamente con el thread de javafx
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
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

	public boolean processTweet(final ITweet tweet) throws RemoteException {
		if (listener != null && (!(tweet instanceof interfacesComunes.Message) || 
				((interfacesComunes.Message) tweet).getRecipient().getId().equals(TwitterClient.getUserId())
			)) {
			// No se puede interactuar directamente con el thread de javafx
			Platform.runLater(new Runnable() { 
				@Override
				public void run() {
					try {
						if (TwitterClient.amigosDelLogueado().contains(
								tweet.getUser().getId()))
							listener.processTweet(tweet);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return true;
	}

	@Override
	public void onMessage(Message mes) {
		ObjectMessage mensaje = (ObjectMessage) mes;
		try {
			Object obj = mensaje.getObject();
			if (obj instanceof ITweet)
				processTweet((ITweet) obj);
			else if (obj instanceof TwitterEvent)
				processEvent((TwitterEvent) obj);
			else if (obj instanceof Object[])
				processSystemEvent((Object[]) obj);
		} catch (JMSException | RemoteException e) {}
	}

	public void close() {
		try {
			this.connection.close();
		} catch (JMSException e) {}
		this.connection = null;
		this.listener = null;
	}

}
