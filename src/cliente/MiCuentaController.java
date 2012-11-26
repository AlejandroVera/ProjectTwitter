package cliente;

/**
 * Sample Skeleton for "cuenta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class MiCuentaController extends Controller implements AStream.IListen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8554344662002374232L;

	@FXML //  fx:id="ScreenName"
    private Label ScreenName; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSeguidores"
    private VBox cajaSeguidores; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSiguiendo"
    private VBox cajaSiguiendo; // Value injected by FXMLLoader

    @FXML //  fx:id="descripcion"
    private TextArea descripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="nSeguidores"
    private Label nSeguidores; // Value injected by FXMLLoader

    @FXML //  fx:id="nSiguiendo"
    private Label nSiguiendo; // Value injected by FXMLLoader

    @FXML //  fx:id="nTweets"
    private Label nTweets; // Value injected by FXMLLoader

    @FXML //  fx:id="name"
    private Label name; // Value injected by FXMLLoader

    @FXML //  fx:id="profileImage"
    private ImageView profileImage; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsFavoritos"
    private VBox tweetsFavoritos; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsUsuario"
    private VBox tweetsUsuario; // Value injected by FXMLLoader


    // Handler for TextArea[fx:id="descripcion"] onKeyPressed
    public void cambiarDescripcion(KeyEvent event) {
        // handle the event here
    }

    // Handler for Label[fx:id="ScreenName"] onMouseClicked
    public void irCuenta(MouseEvent event) {
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

    // Handler for VBox[id="contenedorMensaje"] onMouseClicked
    public void verMensajesPrivados(MouseEvent event) {
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert ScreenName != null : "fx:id=\"ScreenName\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert cajaSeguidores != null : "fx:id=\"cajaSeguidores\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert cajaSiguiendo != null : "fx:id=\"cajaSiguiendo\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert tweetsFavoritos != null : "fx:id=\"tweetsFavoritos\" was not injected: check your FXML file 'cuenta.fxml'.";
        assert tweetsUsuario != null : "fx:id=\"tweetsUsuario\" was not injected: check your FXML file 'cuenta.fxml'.";

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void postInitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

}