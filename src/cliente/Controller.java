package cliente;

import interfacesComunes.Twitter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public abstract class Controller implements Initializable {

	private TwitterClient clientListener;
	private Twitter twitter;
	
	@Override
	public abstract void initialize(URL location, ResourceBundle resources);
	
	protected void setClientListener(TwitterClient tc){
		this.clientListener = tc;
	}
	
	protected TwitterClient getClientListener(){
		return this.clientListener;
	}

	protected Twitter getTwitter() {
		return twitter;
	}

	protected void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	/**
	 * Este método se ejecuta después de haber cargado en el controlador los valores de Twitter y TwitterClient
	 */
	public abstract void postInitialize();
	
	
	/**
	 * Método para acceder al AnchosPane raiz de ese controlador.
	 * @return Devuelve el AnchosPane raiz.
	 */
	protected abstract AnchorPane getContainer();

}
