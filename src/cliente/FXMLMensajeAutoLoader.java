package cliente;

import interfacesComunes.Twitter;
import interfacesComunes.Message;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLMensajeAutoLoader {

	private Parent root;
	private MensajeDirectoController controller;
	private final static String FXML_URL = "mensajeDirecto.fxml";
	private static URL resource;
	
	protected FXMLMensajeAutoLoader (Twitter twitter, Message mensaje, boolean deSalida) throws IOException{
    	if(resource == null)
    		resource = getClass().getResource(FXML_URL);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(resource);
		this.root = (Parent) loader.load(resource.openStream());
		
		//Obtenemos el objeto controlador
		this.controller = loader.getController();
		this.controller.setTwitter(twitter);
		this.controller.setMessage(mensaje);
		this.controller.setRoot(this.root);
		this.controller.setSalida(deSalida);
		this.controller.postInitialize();
    }

	public Parent getRoot() {
		return root;
	}

	public MensajeDirectoController getController() {
		return controller;
	}
	
	
}