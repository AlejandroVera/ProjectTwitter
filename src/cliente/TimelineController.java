package cliente;

/**
 * Sample Skeleton for "timeline.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class TimelineController
implements Initializable {

	@FXML //  fx:id="ajustes"
	private HBox ajustes; // Value injected by FXMLLoader

	@FXML //  fx:id="busquedaLabel"
	private TextField busquedaLabel; // Value injected by FXMLLoader

	@FXML //  fx:id="cajaSeleccion"
	private HBox cajaSeleccion; // Value injected by FXMLLoader

	@FXML //  fx:id="cajita"
	private HBox cajita; // Value injected by FXMLLoader

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


	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert ajustes != null : "fx:id=\"ajustes\" was not injected: check your FXML file 'timeline.fxml'.";
		assert busquedaLabel != null : "fx:id=\"busquedaLabel\" was not injected: check your FXML file 'timeline.fxml'.";
		assert cajaSeleccion != null : "fx:id=\"cajaSeleccion\" was not injected: check your FXML file 'timeline.fxml'.";
		assert cajita != null : "fx:id=\"cajita\" was not injected: check your FXML file 'timeline.fxml'.";
		assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'timeline.fxml'.";
		assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'timeline.fxml'.";
		assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'timeline.fxml'.";
		assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'timeline.fxml'.";
		assert screenName != null : "fx:id=\"screenName\" was not injected: check your FXML file 'timeline.fxml'.";
		assert tweetButton != null : "fx:id=\"tweetButton\" was not injected: check your FXML file 'timeline.fxml'.";
		assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'timeline.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

}


