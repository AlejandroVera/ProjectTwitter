package cliente;

import interfacesComunes.Twitter;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FXMLAutoLoader {

	private Parent root;
	private Controller controller;
	
	protected FXMLAutoLoader (String fxml, Twitter twitter) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		this.root = (Parent) loader.load(getClass().getResource(fxml).openStream());
		
		//Obtenemos el objeto controlador
		this.controller = loader.getController();
		//this.controller.setClientListener(this); TODO
		this.controller.setTwitter(twitter);
		this.controller.postInitialize();
    }

	public Parent getRoot() {
		return root;
	}

	public Controller getController() {
		return controller;
	}
	
	
}
