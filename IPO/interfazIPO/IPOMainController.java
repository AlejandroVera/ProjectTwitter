package interfazIPO;

/**
 * Sample Skeleton for "IPO.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;


public class IPOMainController
    implements Initializable {

    @FXML //  fx:id="bajarPersiana"
    private ImageView bajarPersiana; // Value injected by FXMLLoader

    @FXML //  fx:id="botonArmario"
    private Button botonArmario; // Value injected by FXMLLoader

    @FXML //  fx:id="botonPersianas"
    private Button botonPersianas; // Value injected by FXMLLoader

    @FXML //  fx:id="botonPlantas"
    private Button botonPlantas; // Value injected by FXMLLoader

    @FXML //  fx:id="botonProgramarRiego"
    private Button botonProgramarRiego; // Value injected by FXMLLoader

    @FXML //  fx:id="botonRegarAhora"
    private Button botonRegarAhora; // Value injected by FXMLLoader

    @FXML //  fx:id="botonTelevision"
    private Button botonTelevision; // Value injected by FXMLLoader

    @FXML //  fx:id="habitacion"
    private FlowPane habitacion; // Value injected by FXMLLoader

    @FXML //  fx:id="menuArmario"
    private AnchorPane menuArmario; // Value injected by FXMLLoader

    @FXML //  fx:id="menuHabitaciones"
    private AnchorPane menuHabitaciones; // Value injected by FXMLLoader

    @FXML //  fx:id="menuPersiana"
    private AnchorPane menuPersiana; // Value injected by FXMLLoader

    @FXML //  fx:id="menuPlanta"
    private AnchorPane menuPlanta; // Value injected by FXMLLoader

    @FXML //  fx:id="menuPrincipal"
    private AnchorPane menuPrincipal; // Value injected by FXMLLoader

    @FXML //  fx:id="persianaMovil"
    private ImageView persianaMovil; // Value injected by FXMLLoader

    @FXML //  fx:id="salon"
    private FlowPane salon; // Value injected by FXMLLoader

    @FXML //  fx:id="selectorGiratorio"
    private AnchorPane selectorGiratorio; // Value injected by FXMLLoader

    @FXML //  fx:id="subirPersiana"
    private ImageView subirPersiana; // Value injected by FXMLLoader

    @FXML //  fx:id="terraza"
    private FlowPane terraza; // Value injected by FXMLLoader

    @FXML //  fx:id="tiempoRiegoAhora"
    private TextField tiempoRiegoAhora; // Value injected by FXMLLoader


    // Handler for ImageView[fx:id="bajarPersiana"] onMousePressed
    public void bajarPersiana(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="botonArmario"] onMouseClicked
    public void irMenuArmario(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="botonPersianas"] onMouseClicked
    public void irMenuPersianas(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[id="botonPersianas"] onMouseClicked
    public void irMenuPlantas(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[VBox@3281dca5] onDragDetected
    public void movimientoPersianaDrag(MouseEvent event) {
        // handle the event here
    }

    // Handler for ImageView[fx:id="bajarPersiana"] onMouseReleased
    // Handler for ImageView[fx:id="subirPersiana"] onMouseReleased
    public void pararPersiana(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[VBox@3281dca5] onDragDropped
    public void pararPersianaDrop(DragEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="botonProgramarRiego"] onMouseClicked
    public void programarRiego(MouseEvent event) {
        // handle the event here
    }

    // Handler for ImageView[fx:id="subirPersiana"] onMousePressed
    public void subirPersiana(MouseEvent event) {
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert bajarPersiana != null : "fx:id=\"bajarPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonArmario != null : "fx:id=\"botonArmario\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonPersianas != null : "fx:id=\"botonPersianas\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonPlantas != null : "fx:id=\"botonPlantas\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonProgramarRiego != null : "fx:id=\"botonProgramarRiego\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonRegarAhora != null : "fx:id=\"botonRegarAhora\" was not injected: check your FXML file 'IPO.fxml'.";
        assert botonTelevision != null : "fx:id=\"botonTelevision\" was not injected: check your FXML file 'IPO.fxml'.";
        assert habitacion != null : "fx:id=\"habitacion\" was not injected: check your FXML file 'IPO.fxml'.";
        assert menuArmario != null : "fx:id=\"menuArmario\" was not injected: check your FXML file 'IPO.fxml'.";
        assert menuHabitaciones != null : "fx:id=\"menuHabitaciones\" was not injected: check your FXML file 'IPO.fxml'.";
        assert menuPersiana != null : "fx:id=\"menuPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
        assert menuPlanta != null : "fx:id=\"menuPlanta\" was not injected: check your FXML file 'IPO.fxml'.";
        assert menuPrincipal != null : "fx:id=\"menuPrincipal\" was not injected: check your FXML file 'IPO.fxml'.";
        assert persianaMovil != null : "fx:id=\"persianaMovil\" was not injected: check your FXML file 'IPO.fxml'.";
        assert salon != null : "fx:id=\"salon\" was not injected: check your FXML file 'IPO.fxml'.";
        assert selectorGiratorio != null : "fx:id=\"selectorGiratorio\" was not injected: check your FXML file 'IPO.fxml'.";
        assert subirPersiana != null : "fx:id=\"subirPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
        assert terraza != null : "fx:id=\"terraza\" was not injected: check your FXML file 'IPO.fxml'.";
        assert tiempoRiegoAhora != null : "fx:id=\"tiempoRiegoAhora\" was not injected: check your FXML file 'IPO.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        menuArmario.setVisible(false);
        menuHabitaciones.setVisible(false);
        menuPersiana.setVisible(false);
        menuPlanta.setVisible(false);
        menuPrincipal.setVisible(true);

    }

}
