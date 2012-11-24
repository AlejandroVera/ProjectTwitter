package cliente;

/**
 * Sample Skeleton for "conecta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class ConectaController
    implements Initializable {

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

}

