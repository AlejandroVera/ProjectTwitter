/**
 * Sample Skeleton for "preferencias.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class AjustesController
    implements Initializable {

    @FXML //  fx:id="descripcion"
    private TextArea descripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="examinarPerfil"
    private Button examinarPerfil; // Value injected by FXMLLoader

    @FXML //  fx:id="fondoURL"
    private TextField fondoURL; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenFondo"
    private AnchorPane imagenFondo; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenPerfilURL"
    private TextField imagenPerfilURL; // Value injected by FXMLLoader

    @FXML //  fx:id="name"
    private TextField name; // Value injected by FXMLLoader


    // Handler for Label[id="numeroDe"] onMouseClicked
    public void cerrar(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[id="twittear"] onContextMenuRequested
    public void examinarImagenFondo(ContextMenuEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="examinarPerfil"] onMouseClicked
    public void examinarImagenPerfil(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[id="twittear"] onMouseClicked
    public void guardarAjustes(MouseEvent event) {
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert examinarPerfil != null : "fx:id=\"examinarPerfil\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert fondoURL != null : "fx:id=\"fondoURL\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert imagenPerfilURL != null : "fx:id=\"imagenPerfilURL\" was not injected: check your FXML file 'preferencias.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'preferencias.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

}
