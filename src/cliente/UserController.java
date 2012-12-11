/**
 * Sample Skeleton for "user.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/*Vista pequeñita de un usuario*/

public class UserController extends Controller implements AStream.IListen{
	
	@FXML //  fx:id="candado"
    private ImageView candado; // Value injected by FXMLLoader

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

	private User user;



	// Handler for Hyperlink[fx:id="username"] onMouseClicked
	public void goToPerfilUsuario(Event event) {
		setUser(getTwitter().users().getUser(screename.getText()));
		if (super.getParentController() instanceof BusquedaController)
			((WorldController)((BusquedaController) getParentController()).getParentController()).changeToOtherAccount(this.user);
		else if (super.getParentController() instanceof MiCuentaController)
			((WorldController)((MiCuentaController)this.getParentController()).getParentController()).changeToOtherAccount(this.user);
		else if (super.getParentController() instanceof OtraCuentaController)
			((WorldController)((OtraCuentaController)this.getParentController()).getParentController()).changeToOtherAccount(this.user);
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert descripcionUsuario != null : "fx:id=\"descripcionUsuario\" was not injected: check your FXML file 'user.fxml'.";
		assert screename != null : "fx:id=\"screename\" was not injected: check your FXML file 'user.fxml'.";
		assert tweetBox != null : "fx:id=\"tweetBox\" was not injected: check your FXML file 'user.fxml'.";
		assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'user.fxml'.";
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'user.fxml'.";
		assert worldTweetContainer != null : "fx:id=\"worldTweetContainer\" was not injected: check your FXML file 'user.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

	@Override
	public void postInitialize() {
		loadUserDependantInfo();		
	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setUser(User user){
		this.user = user;
	}
	
	private void loadUserDependantInfo(){
		if (!user.getProtectedUser()){
			descripcionUsuario.setText(this.user.getDescription());
			candado.setVisible(false);
		}
		else{
			descripcionUsuario.setText("Usuario protegido.");
			candado.setVisible(true);
		}
			
		screename.setText(this.user.getScreenName());
		Image im = ClientTools.getImage(this.user.getProfileImageUrl().toString());
		if(im != null)
			userImage.setImage(im);
		username.setText(this.user.getName());
	}

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		if(this.user != null && event.getType().equals(TwitterEvent.Type.USER_UPDATE) 
		  && event.getSource().getId().equals(this.user.getId())){
			this.user = getTwitter().users().getUser(this.user.getId());
			loadUserDependantInfo();
		}
		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		return false;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		return false;
	}

}
