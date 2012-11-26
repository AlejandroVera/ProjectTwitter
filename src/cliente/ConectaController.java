package cliente;

/**
 * Sample Skeleton for "conecta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class ConectaController extends Controller implements AStream.IListen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4970760782255840436L;

	@FXML //  fx:id="cajaInteracciones"
    private VBox cajaInteracciones; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenFondo"
    private AnchorPane imagenFondo; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsInteracciones"
    private VBox tweetsInteracciones; // Value injected by FXMLLoader


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert cajaInteracciones != null : "fx:id=\"cajaInteracciones\" was not injected: check your FXML file 'conecta.fxml'.";
        assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'conecta.fxml'.";
        assert tweetsInteracciones != null : "fx:id=\"tweetsInteracciones\" was not injected: check your FXML file 'conecta.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }


	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void postInitialize() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return imagenFondo;
	}

}

