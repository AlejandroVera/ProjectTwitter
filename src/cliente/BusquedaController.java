/**
 * Sample Skeleton for "busqueda.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class BusquedaController
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

}
