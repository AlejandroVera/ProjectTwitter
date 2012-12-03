/**
 * Sample Skeleton for "preferencias.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Twitter_Account;
import interfacesComunes.User;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


public class AjustesController extends Controller{

    @FXML //  fx:id="descripcion"
    private TextArea descripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="examinarPerfil"
    private Button examinarPerfil; // Value injected by FXMLLoader

    @FXML //  fx:id="fondoURL"
    private TextField fondoURL; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenFondo"
    private AnchorPane imagenFondo; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenPerfilURL"
    private TextField imagenPerfilURL; // Value injected by FXMLLoader

    @FXML //  fx:id="name"
    private TextField name; // Value injected by FXMLLoader


    // Handler for Label[id="numeroDe"] onMouseClicked
    public void cerrar(MouseEvent event) {
        this.hideWindow();
    }

    // Handler for Button[id="twittear"] onContextMenuRequested
    public void examinarImagenFondo(ContextMenuEvent event) {
    	 
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imágenes (*.jpg *.png)", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
       
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        
        //TODO: guardar la imagen en el servidor y obtener la ruta
       
        fondoURL.setText(file.getPath());
    }

    // Handler for Button[fx:id="examinarPerfil"] onMouseClicked
    public void examinarImagenPerfil(MouseEvent event) {
    	 //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imágenes (*.jpg *.png)", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
       
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        
        //TODO: guardar la imagen en el servidor y obtener la ruta
       
        imagenPerfilURL.setText(file.getPath());
    }

    // Handler for Button[id="twittear"] onMouseClicked
    //TODO: falta completar algunas cosillas
    public void guardarAjustes(MouseEvent event) {
    	Twitter_Account account = super.getTwitter().account();
    	User user = super.getTwitter().getSelf();
    	account.setProfile(name.getText(), "UNKNOWN", user.getLocation(), descripcion.getText());
    	account.setProfileColors(null);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert examinarPerfil != null : "fx:id=\"examinarPerfil\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert fondoURL != null : "fx:id=\"fondoURL\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert imagenPerfilURL != null : "fx:id=\"imagenPerfilURL\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'preferencias.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

	@Override
	public void postInitialize() {
		User user = super.getTwitter().getSelf();
		fondoURL.setText(user.getProfileBackgroundImageUrl().toString());
		imagenPerfilURL.setText(user.getProfileImageUrl().toString());
		name.setText(user.getName());
		descripcion.setText(user.getDescription());
	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void showWindow(){
		imagenFondo.setVisible(true);
	}
	
	protected void hideWindow(){
		imagenFondo.setVisible(false);
	}

}