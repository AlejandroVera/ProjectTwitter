/**
 * Sample Skeleton for "tweet.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/
package cliente;

import interfacesComunes.Twitter.ITweet;
import interfacesComunes.User;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class TweetController extends Controller{

	@FXML //  fx:id="nFavoritos"
	private Label nFavoritos; // Value injected by FXMLLoader

	@FXML //  fx:id="nRetweets"
	private Label nRetweets; // Value injected by FXMLLoader

	@FXML //  fx:id="screename"
	private Label screename; // Value injected by FXMLLoader

	@FXML //  fx:id="stackAbrir"
	private StackPane stackAbrir; // Value injected by FXMLLoader

	@FXML //  fx:id="stackBorrar"
	private StackPane stackBorrar; // Value injected by FXMLLoader

	@FXML //  fx:id="stackFavorito"
	private StackPane stackFavorito; // Value injected by FXMLLoader

	@FXML //  fx:id="stackResponder"
	private StackPane stackResponder; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRespuesta"
	private StackPane stackRespuesta; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRetwitteado"
	private StackPane stackRetwitteado; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRetwittear"
	private StackPane stackRetwittear; // Value injected by FXMLLoader

	@FXML //  fx:id="stackVerConve"
	private StackPane stackVerConve; // Value injected by FXMLLoader

	@FXML //  fx:id="stackYaFavorito"
	private StackPane stackYaFavorito; // Value injected by FXMLLoader

	@FXML //  fx:id="timeAgo"
	private Label timeAgo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetTextArea"
	private TextArea tweetTextArea; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsRespuesta"
	private VBox tweetsRespuesta; // Value injected by FXMLLoader

	@FXML //  fx:id="userImage"
	private ImageView userImage; // Value injected by FXMLLoader

	@FXML //  fx:id="username"
	private Hyperlink username; // Value injected by FXMLLoader

	@FXML //  fx:id="worldTweetContainer"
	private AnchorPane worldTweetContainer; // Value injected by FXMLLoader

	private ITweet tweet;



	// Handler for Label[id="opcion"] onMouseClicked
	public void abrirTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void anadirFavorito(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcionRealizada"] onMouseClicked
	public void borrarFavorito(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcionRealizada"] onMouseClicked
	public void borrarRetweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void borrarTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for TextArea[id="textoNuevoTweet"] onKeyPressed
	public void cambiaContador(KeyEvent event) {
		// handle the event here
	}

	// Handler for Label[id="cerrarNuevoTweet"] onMouseClicked
	public void cerrarNuevoTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Hyperlink[fx:id="username"] onAction
	// Handler for ImageView[fx:id="userImage"] onMouseClicked
	public void goToPerfilUsuario(Event event) {
		// handle the event here
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFavoritosPor(MouseEvent event) {
		// handle the event here
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarRetweeters(MouseEvent event) {
		// handle the event here
	}

	// Handler for Button[id="twittear"] onMouseClicked
	public void publicarTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	// Handler for VBox[id="cajita"] onMouseClicked
	public void responderTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void retwittearTweet(MouseEvent event) {
		// handle the event here
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert screename != null : "fx:id=\"screename\" was not injected: check your FXML file 'tweet.fxml'.";
		assert timeAgo != null : "fx:id=\"timeAgo\" was not injected: check your FXML file 'tweet.fxml'.";
		assert tweetTextArea != null : "fx:id=\"tweetTextArea\" was not injected: check your FXML file 'tweet.fxml'.";
		assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'tweet.fxml'.";
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'tweet.fxml'.";
		assert worldTweetContainer != null : "fx:id=\"worldTweetContainer\" was not injected: check your FXML file 'tweet.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

	@Override
	public void postInitialize() {
		tweetTextArea.setText(this.tweet.getText());
		User user = this.tweet.getUser();
		screename.setText("@"+user.getScreenName());
		username.setText(user.getName());

		//Parse the time
		Date createdAt = this.tweet.getCreatedAt();
		Date now = new Date();
		int timedif = (int)((now.getTime() - createdAt.getTime())/1000);
		String timeago;
		if(timedif < 60)
			timeago = timedif+"s";
		else if(timedif < 3600)
			timeago = (timedif/60)+"m";
		else if(timedif < 86400)
			timeago = (timedif/3600)+"h";
		else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(createdAt);
			timeago = ""+cal.get(Calendar.DAY_OF_MONTH);
			switch(Calendar.MONTH){
			case Calendar.JANUARY: timeago += " Ene"; break;
			case Calendar.FEBRUARY: timeago += " Feb"; break;
			case Calendar.MARCH: timeago += " Mar"; break;
			case Calendar.APRIL: timeago += " Abr"; break;
			case Calendar.MAY: timeago += " Mayo"; break;
			case Calendar.JUNE: timeago += " Jun"; break;
			case Calendar.JULY: timeago += " Jul"; break;
			case Calendar.AUGUST: timeago += " Ago"; break;
			case Calendar.SEPTEMBER: timeago += " Sept"; break;
			case Calendar.OCTOBER: timeago += " Oct"; break;
			case Calendar.NOVEMBER: timeago += " Nov"; break;
			case Calendar.DECEMBER: timeago += " Dic"; break;
			}
		}

		timeAgo.setText(timeago);


	}

	protected void setTweet(ITweet tweet){
		this.tweet = tweet;
	}

	@Override
	protected AnchorPane getContainer() {
		return worldTweetContainer;
	}

}
