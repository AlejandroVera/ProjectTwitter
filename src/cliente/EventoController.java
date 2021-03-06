/**
 * Sample Skeleton for "evento.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class EventoController extends Controller {

	@FXML //  fx:id="botonPeticion"
	private Button botonPeticion; // Value injected by FXMLLoader

	@FXML //  fx:id="descripcionUsuario"
	private TextArea descripcionUsuario; // Value injected by FXMLLoader

	@FXML //  fx:id="desfavorito"
	private ImageView desfavorito; // Value injected by FXMLLoader

	@FXML //  fx:id="favorito"
	private ImageView favorito; // Value injected by FXMLLoader

	@FXML //  fx:id="fecha"
	private Label fecha; // Value injected by FXMLLoader

	@FXML //  fx:id="peticionSeguir"
	private ImageView peticionSeguir; // Value injected by FXMLLoader

	@FXML //  fx:id="screename"
	private Label screename; // Value injected by FXMLLoader

	@FXML //  fx:id="seguir"
	private ImageView seguir; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetBox"
	private HBox tweetBox; // Value injected by FXMLLoader

	@FXML //  fx:id="userImage"
	private ImageView userImage; // Value injected by FXMLLoader

	@FXML //  fx:id="username"
	private Hyperlink username; // Value injected by FXMLLoader

	@FXML //  fx:id="worldTweetContainer"
	private AnchorPane worldTweetContainer; // Value injected by FXMLLoader

	@FXML //  fx:id="unfollow"
	private ImageView unfollow; // Value injected by FXMLLoader

	//Variables privadas propias

	private TwitterEvent event;

	// Handler for Hyperlink[fx:id="username"] onAction
	// Handler for Hyperlink[fx:id="username"] onMouseClicked
	// Handler for ImageView[fx:id="userImage"] onMouseClicked
	public void goToPerfilUsuario(Event event) {
		String screenName = (String)((Hyperlink)event.getSource()).getUserData();
		User destUser = getTwitter().users().getUser(screenName);
		if(destUser != null)
			((WorldController)((ConectaController)this.getParentController()).getParentController()).changeToOtherAccount(destUser);
	}

	// Handler for Button[fx:id="botonPeticion"] onMouseClicked
	public void aceptarPeticion(MouseEvent event) {
		getTwitter().users().confirmarAmistad(this.event.getSource());
		botonPeticion.setVisible(false);
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert descripcionUsuario != null : "fx:id=\"descripcionUsuario\" was not injected: check your FXML file 'evento.fxml'.";
		assert favorito != null : "fx:id=\"favorito\" was not injected: check your FXML file 'evento.fxml'.";
		assert screename != null : "fx:id=\"screename\" was not injected: check your FXML file 'evento.fxml'.";
		assert seguir != null : "fx:id=\"seguir\" was not injected: check your FXML file 'evento.fxml'.";
		assert tweetBox != null : "fx:id=\"tweetBox\" was not injected: check your FXML file 'evento.fxml'.";
		assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'evento.fxml'.";
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'evento.fxml'.";
		assert worldTweetContainer != null : "fx:id=\"worldTweetContainer\" was not injected: check your FXML file 'evento.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

	@Override
	public void postInitialize() {

		User source = this.event.getSource();
		screename.setText("@"+source.getScreenName());
		username.setText(source.getName());
		this.botonPeticion.setVisible(false);

		Image im = ClientTools.getImage(source.getProfileImageUrl().toString());
		if(im != null)
			userImage.setImage(im);

		desfavorito.setVisible(false);
		favorito.setVisible(false);
		seguir.setVisible(false);
		peticionSeguir.setVisible(false);
		unfollow.setVisible(false);

		if(event.getType().equals(TwitterEvent.Type.FAVORITE)){
			Status status=(Status) this.event.getTargetObject();
			favorito.setVisible(true);

			String resumen=status.getText();
			if (resumen.length()<50){
				descripcionUsuario.setText("Ha marcado como favorito tu tweet\n\""+resumen+"\"");
			}
			else{
				resumen=resumen.substring(0,49);
				descripcionUsuario.setText("Ha marcado como favorito tu tweet\n\""+resumen+"...\"");
			}
		}

		if(event.getType().equals(TwitterEvent.Type.UNFAVORITE)){
			Status status=(Status) this.event.getTargetObject();
			desfavorito.setVisible(true);

			String resumen=status.getText();			
			if (resumen.length()<50){
				descripcionUsuario.setText("Ha desmarcado como favorito tu tweet\n\""+resumen+"\"");
			}
			else{
				resumen=resumen.substring(0,49);
				descripcionUsuario.setText("Ha desmarcado como favorito tu tweet\n\""+resumen+"...\"");
			}
		}
		if(event.getType().equals(TwitterEvent.Type.FOLLOW)){
			this.descripcionUsuario.setText("Ahora te sigue");
			this.seguir.setVisible(true);
			botonPeticion.setVisible(false);
		}

		if(event.getType().equals(TwitterEvent.Type.UNFOLLOW)){
			this.descripcionUsuario.setText("Ha dejado de seguirte");
			this.unfollow.setVisible(true);
		}


		if(event.getType().equals(TwitterEvent.Type.FOLLOW_REQUEST)){
			peticionSeguir.setVisible(true);
			if(source.isFollowingYou()){
				botonPeticion.setVisible(false);
			}
			else{
				botonPeticion.setVisible(true);
			}
			descripcionUsuario.setText("Quiere poder seguirte");
		}


		//Parse the time
		Date createdAt = this.event.getCreatedAt();
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
		fecha.setText(timeago);
		ClientTools.addLabelToTimeUpdate(fecha, createdAt);
	}

	@Override
	protected AnchorPane getContainer() {
		return worldTweetContainer;
	}

	public void setEvent (TwitterEvent event){
		this.event=event;
	}

	public TwitterEvent getEvent (){
		return this.event;
	}

	public boolean processEvent(TwitterEvent event) throws RemoteException {
		if((event.getType().equals(TwitterEvent.Type.FOLLOW))&&(this.event.getType().equals(TwitterEvent.Type.FOLLOW_REQUEST))){
			this.descripcionUsuario.setText("Ahora te sigue");
			this.seguir.setVisible(false);
			botonPeticion.setVisible(false);
			this.unfollow.setVisible(true);
		}
		if((event.getType().equals(TwitterEvent.Type.USER_UPDATE))&&(event.getSource().getId().equals(this.event.getSource().getId()))){
			User user = getTwitter().users().getUser(event.getSource().getId());
			Image im = ClientTools.getImage(user.getProfileImageUrl().toString());
			if(im != null)
				userImage.setImage(im);
			username.setText(user.getName());
		}
		return true;
	}

}


