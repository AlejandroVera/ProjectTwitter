package cliente;

import interfacesComunes.Twitter;
import interfacesComunes.Status;
import interfacesComunes.User;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLUserAutoLoader {

	private Parent root;
	private UserController controller;
	private final static String FXML_URL = "user.fxml";
	private static URL resource;
	
	protected FXMLUserAutoLoader (Twitter twitter, User user) throws IOException{
    	if(resource == null)
    		resource = getClass().getResource(FXML_URL);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(resource);
		this.root = (Parent) loader.load(resource.openStream());
		
		//Obtenemos el objeto controlador
		this.controller = loader.getController();
		this.controller.setTwitter(twitter);
		this.controller.setUser(user);
		this.controller.setRoot(this.root);
		this.controller.postInitialize();
    }

	public Parent getRoot() {
		return root;
	}

	public UserController getController() {
		return controller;
	}
	
	
}
