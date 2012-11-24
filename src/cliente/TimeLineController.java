package cliente;

/**
 * Sample Skeleton for "timeLine.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class TimeLineController
implements Initializable {

	@FXML //  fx:id="imagenFondo"
	private AnchorPane imagenFondo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsTimeLine"
	private VBox tweetsTimeLine; // Value injected by FXMLLoader



	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'timeLine.fxml'.";
		assert tweetsTimeLine != null : "fx:id=\"tweetsTimeLine\" was not injected: check your FXML file 'timeLine.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected

	}

}



