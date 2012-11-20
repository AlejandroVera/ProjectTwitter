package cliente;

import interfacesComunes.Twitter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

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

}
