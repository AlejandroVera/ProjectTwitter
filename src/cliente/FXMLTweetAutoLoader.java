package cliente;

import interfacesComunes.Status;
import interfacesComunes.Twitter;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLTweetAutoLoader {

	private Parent root;
	private TweetController controller;
	
	protected FXMLTweetAutoLoader (String fxml, Twitter twitter, Status status) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		this.root = (Parent) loader.load(getClass().getResource(fxml).openStream());
		
		//Obtenemos el objeto controlador
		this.controller = loader.getController();
		//this.controller.setClientListener(this); TODO
		this.controller.setTwitter(twitter);
		this.controller.setStatus(status);
		this.controller.postInitialize();
    }

	public Parent getRoot() {
		return root;
	}

	public TweetController getController() {
		return controller;
	}
	
	
}
