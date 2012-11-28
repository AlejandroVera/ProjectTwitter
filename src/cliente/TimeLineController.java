package cliente;

/**
 * Sample Skeleton for "timeLine.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import interfacesComunes.AStream;
import interfacesComunes.Status;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class TimeLineController extends Controller implements AStream.IListen {

	private static final long serialVersionUID = 2837430220399757290L;

	@FXML //  fx:id="imagenFondo"
	private AnchorPane imagenFondo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsTimeLine"
	private VBox tweetsTimeLine; // Value injected by FXMLLoader



	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'timeLine.fxml'.";
		assert tweetsTimeLine != null : "fx:id=\"tweetsTimeLine\" was not injected: check your FXML file 'timeLine.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}



	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		this.addTweet(tweet, true);
		return true;
	}



	@Override
	public void postInitialize() {
        //Inicializar tweets
		Iterator<Status> timeline = super.getTwitter().getHomeTimeline().iterator();
		tweetsTimeLine.getChildren().clear();
		while(timeline.hasNext()){
			this.addTweet(timeline.next());
		}
	}
	
	
	/**
	 * Añade un tweet al final de la lista.
	 * @param tweet Tweet a añadir.
	 */
	private void addTweet(ITweet tweet){
		addTweet(tweet, false);
	}
	
	/**
	 * Añade un tweet.
	 * @param tweet Tweet a añadir.
	 * @param onTop True si el tweet se tiene que añadir al principio de la lista.
	 */
	private void addTweet(ITweet tweet, boolean onTop){
		try {
			FXMLTweetAutoLoader tweetUI = new FXMLTweetAutoLoader(getTwitter(), (Status) tweet);
			if(!onTop)
				tweetsTimeLine.getChildren().add(tweetUI.getRoot());
			else{
				LinkedList<Node> list = new LinkedList<Node>(tweetsTimeLine.getChildren());
				list.addFirst(tweetUI.getRoot());
				tweetsTimeLine.getChildren().clear();
				tweetsTimeLine.getChildren().addAll(list);
			}
			//((AnchorPane)tweetsTimeLine.getParent()).setMinHeight(((AnchorPane)tweetsTimeLine.getParent()).getMinHeight()+126);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return imagenFondo;
	}

}



