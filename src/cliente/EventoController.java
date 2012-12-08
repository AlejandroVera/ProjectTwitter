/**
 * Sample Skeleton for "evento.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class EventoController extends Controller {
	
	@FXML //  fx:id="fecha"
	private Label fecha; // Value injected by FXMLLoader
	
	@FXML //  fx:id="descripcionUsuario"
	private TextArea descripcionUsuario; // Value injected by FXMLLoader

	@FXML //  fx:id="favorito"
	private ImageView favorito; // Value injected by FXMLLoader

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

	
	//Variables privadas propias
	
	private TwitterEvent event;

	// Handler for Hyperlink[fx:id="username"] onAction
	// Handler for Hyperlink[fx:id="username"] onMouseClicked
	// Handler for ImageView[fx:id="userImage"] onMouseClicked
	public void goToPerfilUsuario(Event event) {
		String screenName = ((Hyperlink)event.getSource()).getTooltip().getText();
		User destUser = getTwitter().users().getUser(screenName);
		if(destUser != null)
			((WorldController)((TimeLineController)this.getParentController()).getParentController()).changeToOtherAccount(destUser);
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
		username.setTooltip(new Tooltip(source.getScreenName()));
		
        Image im = ClientTools.getImage(source.getProfileImageUrl().toString());
        if(im != null)
        	userImage.setImage(im);
		
		if(event.getType().equals(TwitterEvent.Type.FAVORITE)){
			Status status=(Status) this.event.getTargetObject();
			favorito.setVisible(true);
			seguir.setVisible(false);
			//System.out.println(status.getText());
			String resumen=status.getText();
			if (resumen.length()<50){
				resumen=resumen.substring(0,resumen.length()-1);
				descripcionUsuario.setText("Ha marcado como favorito tu tweet\n\""+resumen+"\"");
			}
			else{
				resumen=resumen.substring(0,49);
				descripcionUsuario.setText("Ha marcado como favorito tu tweet\n\""+resumen+"...\"");
			}
		}
		
		if(event.getType().equals(TwitterEvent.Type.FOLLOW)){
			favorito.setVisible(false);
			seguir.setVisible(true);
			descripcionUsuario.setText("Te Sigue");
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
	
}


