
package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class MensajesController
    implements Initializable {

    @FXML //  fx:id="bandejaEntrada"
    private VBox bandejaEntrada; // Value injected by FXMLLoader

    @FXML //  fx:id="bandejaSalida"
    private VBox bandejaSalida; // Value injected by FXMLLoader

    @FXML //  fx:id="destinatario"
    private TextField destinatario; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenFondo"
    private AnchorPane imagenFondo; // Value injected by FXMLLoader

    @FXML //  fx:id="texto"
    private TextArea texto; // Value injected by FXMLLoader


    // Handler for Label[id="numeroDe"] onMouseClicked
    public void cerrarMenu(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[id="twittear"] onMouseClicked
    public void enviarMensaje(MouseEvent event) {
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert bandejaEntrada != null : "fx:id=\"bandejaEntrada\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert bandejaSalida != null : "fx:id=\"bandejaSalida\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert destinatario != null : "fx:id=\"destinatario\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert texto != null : "fx:id=\"texto\" was not injected: check your FXML file 'mensajes.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

}
