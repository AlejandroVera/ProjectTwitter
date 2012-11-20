/**
 * Sample Skeleton for "timeline.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class TimelineController extends Controller {

    @FXML //  fx:id="ajustes"
    private MenuItem ajustes; // Value injected by FXMLLoader

    @FXML //  fx:id="busquedaLabel"
    private TextField busquedaLabel; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSeleccion"
    private HBox cajaSeleccion; // Value injected by FXMLLoader

    @FXML //  fx:id="cerrarSesion"
    private MenuItem cerrarSesion; // Value injected by FXMLLoader

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

    @FXML //  fx:id="tweetButton"
    private Button tweetButton; // Value injected by FXMLLoader

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
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert ajustes != null : "fx:id=\"ajustes\" was not injected: check your FXML file 'timeline.fxml'.";
        assert busquedaLabel != null : "fx:id=\"busquedaLabel\" was not injected: check your FXML file 'timeline.fxml'.";
        assert cajaSeleccion != null : "fx:id=\"cajaSeleccion\" was not injected: check your FXML file 'timeline.fxml'.";
        assert cerrarSesion != null : "fx:id=\"cerrarSesion\" was not injected: check your FXML file 'timeline.fxml'.";
        assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'timeline.fxml'.";
        assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'timeline.fxml'.";
        assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'timeline.fxml'.";
        assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'timeline.fxml'.";
        assert screenName != null : "fx:id=\"screenName\" was not injected: check your FXML file 'timeline.fxml'.";
        assert tweetButton != null : "fx:id=\"tweetButton\" was not injected: check your FXML file 'timeline.fxml'.";
        assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'timeline.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

	@Override
	public void postInitialize() {
		screenName.setText(super.getTwitter().getScreenName());
        nTweets.setText(""+super.getTwitter().getSelf().getStatusesCount());
        nSeguidores.setText(""+super.getTwitter().getSelf().getFollowersCount());
        nSiguiendo.setText(""+super.getTwitter().getSelf().getFriendsCount());
	}

}

