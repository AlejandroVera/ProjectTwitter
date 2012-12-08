package cliente;

import interfacesComunes.Twitter;
import interfacesComunes.TwitterEvent;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLEventAutoLoader {

	private Parent root;
	private EventoController controller;
	private final static String FXML_URL = "evento.fxml";
	private static URL resource;
	
	protected FXMLEventAutoLoader (Twitter twitter, TwitterEvent event) throws IOException{
    	if(resource == null)
    		resource = getClass().getResource(FXML_URL);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(resource);
		this.root = (Parent) loader.load(resource.openStream());
		
		//Obtenemos el objeto controlador
		this.controller = loader.getController();
		this.controller.setTwitter(twitter);
		this.controller.setEvent(event);
		this.controller.setRoot(this.root);
		this.controller.postInitialize();
    }

	public Parent getRoot() {
		return root;
	}

	public EventoController getController() {
		return controller;
	}
	
	
}
