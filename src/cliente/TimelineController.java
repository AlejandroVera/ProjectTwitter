/**
 * Sample Skeleton for "timeline.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Status;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class TimelineController extends Controller implements AStream.IListen {

	private static final long serialVersionUID = 6319965686022119977L;

	@FXML //  fx:id="ajustes"
    private MenuItem ajustes; // Value injected by FXMLLoader

    @FXML //  fx:id="busquedaLabel"
    private TextField busquedaLabel; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaInteracciones"
    private VBox cajaInteracciones; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaNuevoTweet"
    private VBox cajaNuevoTweet; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSeleccion"
    private HBox cajaSeleccion; // Value injected by FXMLLoader

    @FXML //  fx:id="cerrarNuevoTweet"
    private Label cerrarNuevoTweet; // Value injected by FXMLLoader

    @FXML //  fx:id="cerrarSesion"
    private MenuItem cerrarSesion; // Value injected by FXMLLoader

    @FXML //  fx:id="creadorTweets"
    private StackPane creadorTweets; // Value injected by FXMLLoader

    @FXML //  fx:id="nSeguidores"
    private Label nSeguidores; // Value injected by FXMLLoader

    @FXML //  fx:id="nSiguiendo"
    private Label nSiguiendo; // Value injected by FXMLLoader

    @FXML //  fx:id="nTweets"
    private Label nTweets; // Value injected by FXMLLoader

    @FXML //  fx:id="profileImage"
    private ImageView profileImage; // Value injected by FXMLLoader

    @FXML //  fx:id="screenName"
    private Label screenName; // Value injected by FXMLLoader

    @FXML //  fx:id="textoNuevoTweet"
    private TextArea textoNuevoTweet; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetButton"
    private Button tweetButton; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsMenciones"
    private VBox tweetsMenciones; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsTimeline"
    private VBox tweetsTimeline; // Value injected by FXMLLoader

    @FXML //  fx:id="twittear"
    private Button twittear; // Value injected by FXMLLoader

    @FXML //  fx:id="worldContainer"
    private AnchorPane worldContainer; // Value injected by FXMLLoader


    // Handler for TextField[fx:id="busquedaLabel"] onKeyPressed
    public void busca(KeyEvent event) {
        // handle the event here
    }

    // Handler for MenuItem[fx:id="cerrarSesion"] onAction
    public void cerrarSesion(ActionEvent event) {
        getClientListener().notifyLogout();
    }

    // Handler for MenuItem[fx:id="ajustes"] onAction
    public void irAjustes(ActionEvent event) {
        // handle the event here
    }

    // Handler for HBox[id="cajaIz"] onKeyPressed
    // Handler for Label[fx:id="screenName"] onMouseClicked
    public void irCuenta(InputEvent event) {
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarFollowers(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarFriends(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarTweetsUsuario(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="tweetButton"] onAction
    // Handler for Button[fx:id="tweetButton"] onMouseClicked
    public void twittear(Event event) {
    	creadorTweets.setVisible(!creadorTweets.isVisible());
    }
    
 // Handler for Button[fx:id="twittear"] onMouseClicked (publicar ya el tweet como tal)
    public void publicarTweet(MouseEvent event) {
    	String texto = textoNuevoTweet.getText();
        if(getTwitter().updateStatus(texto) == null){ //Error?
        	ClientTools.showDialog("No se ha podido mandar el tweet");
        	return;
        }
        creadorTweets.setVisible(false);
        textoNuevoTweet.clear();
    }
    
    // Handler for Label[fx:id="cerrarNuevoTweet"] onMouseClicked (cierra el menu del nuevo tweet)
    public void cerrarNuevoTweet(MouseEvent event) {
    	creadorTweets.setVisible(false);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	 assert ajustes != null : "fx:id=\"ajustes\" was not injected: check your FXML file 'timeline.fxml'.";
         assert busquedaLabel != null : "fx:id=\"busquedaLabel\" was not injected: check your FXML file 'timeline.fxml'.";
         assert cajaInteracciones != null : "fx:id=\"cajaInteracciones\" was not injected: check your FXML file 'timeline.fxml'.";
         assert cajaSeleccion != null : "fx:id=\"cajaSeleccion\" was not injected: check your FXML file 'timeline.fxml'.";
         assert cerrarSesion != null : "fx:id=\"cerrarSesion\" was not injected: check your FXML file 'timeline.fxml'.";
         assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'timeline.fxml'.";
         assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'timeline.fxml'.";
         assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'timeline.fxml'.";
         assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'timeline.fxml'.";
         assert screenName != null : "fx:id=\"screenName\" was not injected: check your FXML file 'timeline.fxml'.";
         assert tweetButton != null : "fx:id=\"tweetButton\" was not injected: check your FXML file 'timeline.fxml'.";
         assert tweetsMenciones != null : "fx:id=\"tweetsMenciones\" was not injected: check your FXML file 'timeline.fxml'.";
         assert tweetsTimeline != null : "fx:id=\"tweetsTimeline\" was not injected: check your FXML file 'timeline.fxml'.";
         assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'timeline.fxml'.";

         // initialize your logic here: all @FXML variables will have been injected
         creadorTweets.setVisible(false);
        
    }

	@Override
	public void postInitialize() {
		screenName.setText(super.getTwitter().getScreenName());
		User user = super.getTwitter().getSelf();
        nTweets.setText(""+user.getStatusesCount());
        nSeguidores.setText(""+user.getFollowersCount());
        nSiguiendo.setText(""+user.getFriendsCount());
        
        //Inicializar tweets
		Iterator<Status> timeline = super.getTwitter().getHomeTimeline().iterator();
		tweetsTimeline.getChildren().clear();
		while(timeline.hasNext()){
			this.addTweet(timeline.next());
		}
	}

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		this.addTweet(tweet, true);
		nTweets.setText(""+(new Integer(nTweets.getText()).intValue() + 1)); //Inc numero tweets
		return true;
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
			FXMLTweetAutoLoader tweetUI = new FXMLTweetAutoLoader(getTwitter(), tweet);
			if(!onTop)
				tweetsTimeline.getChildren().add(tweetUI.getRoot());
			else{
				LinkedList<Node> list = new LinkedList<Node>(tweetsTimeline.getChildren());
				list.addFirst(tweetUI.getRoot());
				tweetsTimeline.getChildren().clear();
				tweetsTimeline.getChildren().addAll(list);
			}
			((AnchorPane)tweetsTimeline.getParent()).setMinHeight(((AnchorPane)tweetsTimeline.getParent()).getMinHeight()+126);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

