package cliente;

import interfacesComunes.Status;
import interfacesComunes.Twitter;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLTweetAutoLoader {

	private Parent root;
	private TweetController controller;
	private final static String FXML_URL = "tweet.fxml";
	private static URL resource;
	
	protected FXMLTweetAutoLoader (Twitter twitter, Status status) throws IOException{
    	if(resource == null)
    		resource = getClass().getResource(FXML_URL);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(resource);
		this.root = (Parent) loader.load(resource.openStream());
		
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
