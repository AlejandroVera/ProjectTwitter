/**
 * Sample Skeleton for "mensajeDirecto.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Message;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class MensajeDirectoController extends Controller  {

    @FXML //  fx:id="descripcionUsuario"
    private TextArea descripcionUsuario; // Value injected by FXMLLoader

    @FXML //  fx:id="screename"
    private Label screename; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetBox"
    private HBox tweetBox; // Value injected by FXMLLoader

    @FXML //  fx:id="userImage"
    private ImageView userImage; // Value injected by FXMLLoader

    @FXML //  fx:id="username"
    private Hyperlink username; // Value injected by FXMLLoader

    @FXML //  fx:id="worldTweetContainer"
    private AnchorPane worldTweetContainer; // Value injected by FXMLLoader
    
    @FXML //  fx:id="fecha"
    private Label fecha; // Value injected by FXMLLoader
    
    private Message mensaje;
    private boolean deSalida;

    // Handler for Hyperlink[fx:id="username"] onAction
    // Handler for Hyperlink[fx:id="username"] onMouseClicked
    // Handler for ImageView[fx:id="userImage"] onMouseClicked
    public void goToPerfilUsuario(Event event) {
        // handle the event here
    }

    // Handler for Label[id="opcion"] onMouseClicked
    public void responderMensaje(MouseEvent event) {
    	Object parent = super.getParentController();
    	String text;
    	if (!this.deSalida)
    		text= new String("@"+this.mensaje.getSender().getScreenName());
    	else
    		text= new String("@"+this.mensaje.getRecipient().getScreenName());
		if(parent instanceof MensajesController)
			
			((MensajesController)parent).responderMensaje(text);		
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert descripcionUsuario != null : "fx:id=\"descripcionUsuario\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";
        assert screename != null : "fx:id=\"screename\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";
        assert tweetBox != null : "fx:id=\"tweetBox\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";
        assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";
        assert worldTweetContainer != null : "fx:id=\"worldTweetContainer\" was not injected: check your FXML file 'mensajeDirecto.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

	@Override
	public void postInitialize() {
		descripcionUsuario.setText(this.mensaje.getText());
		User user;
		if (deSalida==true)
			user=this.mensaje.getRecipient();			
		else
			user=this.mensaje.getSender();
	
		screename.setText("@"+user.getScreenName());
		username.setText(user.getName());
		username.setTooltip(new Tooltip(user.getScreenName()));
		
        Image im = ClientTools.getImage(user.getProfileImageUrl().toString());
        if(im != null)
        	userImage.setImage(im);
        
        
		//Parse the time
		Date createdAt = this.mensaje.getCreatedAt();
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
	
	public void setMessage (Message mensaje){
		this.mensaje=mensaje;
	}
	
	public Message getMessage (){
		return this.mensaje;
	}
	
	public void setSalida (boolean deSalida){
		this.deSalida=deSalida;
	}
}

