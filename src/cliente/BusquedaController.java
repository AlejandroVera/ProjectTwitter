/**
 * Sample Skeleton for "busqueda.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Status;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.User;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class BusquedaController extends Controller
implements Initializable {

	@FXML //  fx:id="imagenFondo"
	private AnchorPane imagenFondo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsBusqueda"
	private VBox tweetsBusqueda; // Value injected by FXMLLoader

	@FXML //  fx:id="usersBusqueda"
	private VBox usersBusqueda; // Value injected by FXMLLoader


	// Handler for Label[id="numeroDe"] onMouseClicked
	public void cerrarMenu(MouseEvent event) {
		// handle the event here
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'busqueda.fxml'.";
		assert tweetsBusqueda != null : "fx:id=\"tweetsBusqueda\" was not injected: check your FXML file 'busqueda.fxml'.";
		assert usersBusqueda != null : "fx:id=\"usersBusqueda\" was not injected: check your FXML file 'busqueda.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

	@Override
	public void postInitialize() {

	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addUserResult(List<User> u){
		usersBusqueda.getChildren().clear();
		Iterator<User> busquedas = u.iterator();
		while(busquedas.hasNext()){
			
			try {
				FXMLUserAutoLoader tweetUI = new FXMLUserAutoLoader(getTwitter(), busquedas.next());
				tweetUI.getController().setParentController(this);
				LinkedList<Node> list = new LinkedList<Node>(usersBusqueda.getChildren());
				list.addFirst(tweetUI.getRoot());
				usersBusqueda.getChildren().clear();
				usersBusqueda.getChildren().addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
