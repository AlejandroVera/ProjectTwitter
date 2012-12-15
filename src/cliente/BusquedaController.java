/**
 * Sample Skeleton for "busqueda.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;
import interfacesComunes.Twitter.ITweet;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class BusquedaController extends Controller
implements Initializable {

	@FXML //  fx:id="imagenFondo"
	private AnchorPane imagenFondo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsBusqueda"
	private VBox tweetsBusqueda; // Value injected by FXMLLoader

	@FXML //  fx:id="usersBusqueda"
	private VBox usersBusqueda; // Value injected by FXMLLoader

	private HashMap<Number, TweetController> tweetTable;
	private HashMap<Number, UserController> userTable;

	// Handler for Label[id="numeroDe"] onMouseClicked
	public void cerrarMenu(MouseEvent event) {
		// handle the event here
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'busqueda.fxml'.";
		assert tweetsBusqueda != null : "fx:id=\"tweetsBusqueda\" was not injected: check your FXML file 'busqueda.fxml'.";
		assert usersBusqueda != null : "fx:id=\"usersBusqueda\" was not injected: check your FXML file 'busqueda.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected
		this.tweetTable = new HashMap<Number, TweetController>();
		this.userTable = new HashMap<Number, UserController>();

	}

	@Override
	public void postInitialize() {

	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addUserResult(List<User> u){
		usersBusqueda.getChildren().clear();
		Iterator<User> busquedas = u.iterator();
		while(busquedas.hasNext()){

			try {
				User i= busquedas.next();
				FXMLUserAutoLoader userUI = new FXMLUserAutoLoader(getTwitter(), i);
				userUI.getController().setParentController(this);

				userTable.put(i.getId(), userUI.getController());

				LinkedList<Node> list = new LinkedList<Node>(usersBusqueda.getChildren());
				list.addFirst(userUI.getRoot());
				usersBusqueda.getChildren().clear();
				usersBusqueda.getChildren().addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addTweetResult(List<Status> s){
		tweetsBusqueda.getChildren().clear();
		Iterator<Status> busquedas = s.iterator();
		while(busquedas.hasNext()){
				Status tweet=busquedas.next();
			try {
				FXMLTweetAutoLoader tweetUI = new FXMLTweetAutoLoader(getTwitter(), tweet);
				tweetUI.getController().setParentController(this);

				tweetTable.put(tweet.getId(), tweetUI.getController());

				LinkedList<Node> list = new LinkedList<Node>(tweetsBusqueda.getChildren());
				list.addFirst(tweetUI.getRoot());
				tweetsBusqueda.getChildren().clear();
				tweetsBusqueda.getChildren().addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean processEvent(TwitterEvent event) throws RemoteException {
		if((event.getType().equals(TwitterEvent.Type.FOLLOW))||
				(event.getType().equals(TwitterEvent.Type.FOLLOW_REQUEST))){

			UserController u=userTable.get(event.getTarget().getId());
			if(u!=null)
				u.processEvent(event);
		}
		if((event.getType().equals(TwitterEvent.Type.FAVORITE))||
				(event.getType().equals(TwitterEvent.Type.UNFAVORITE))){

			TweetController s=tweetTable.get(event.getTarget().getId());
			if(s!=null)
				s.processEvent(event);
		}
		if(event.getType().equals(TwitterEvent.Type.USER_UPDATE)){
			for(TweetController c : tweetTable.values()){
				c.processEvent(event);
			}
			for(UserController c : userTable.values()){
				c.processEvent(event);
			}
		}
		return true;
	}
}
