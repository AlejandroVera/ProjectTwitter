package interfazIPO;

/**
 * Sample Skeleton for "IPO.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;


public class IPOMainController
implements Initializable {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="bajarPersiana"
	private ImageView bajarPersiana; // Value injected by FXMLLoader

	@FXML // fx:id="bisemanal"
	private RadioButton bisemanal; // Value injected by FXMLLoader

	@FXML // fx:id="botonArmario"
	private Button botonArmario; // Value injected by FXMLLoader

	@FXML // fx:id="botonPersianas"
	private Button botonPersianas; // Value injected by FXMLLoader

	@FXML // fx:id="botonPlantas"
	private Button botonPlantas; // Value injected by FXMLLoader

	@FXML // fx:id="botonProgramarRiego"
	private Button botonProgramarRiego; // Value injected by FXMLLoader

	@FXML // fx:id="botonRegarAhora"
	private Button botonRegarAhora; // Value injected by FXMLLoader

	@FXML // fx:id="botonTelevision"
	private Button botonTelevision; // Value injected by FXMLLoader

	@FXML // fx:id="habitacion"
	private FlowPane habitacion; // Value injected by FXMLLoader

	@FXML // fx:id="horaRiego"
	private TextField horaRiego; // Value injected by FXMLLoader

	@FXML // fx:id="mensual"
	private RadioButton mensual; // Value injected by FXMLLoader

	@FXML // fx:id="menuArmario"
	private AnchorPane menuArmario; // Value injected by FXMLLoader

	@FXML // fx:id="menuArmarioCategorias"
	private AnchorPane menuArmarioCategorias; // Value injected by FXMLLoader

	@FXML // fx:id="menuEditandoPrograma"
	private AnchorPane menuEditandoPrograma; // Value injected by FXMLLoader

	@FXML // fx:id="menuHabitaciones"
	private AnchorPane menuHabitaciones; // Value injected by FXMLLoader

	@FXML // fx:id="menuInicialPlanta"
	private AnchorPane menuInicialPlanta; // Value injected by FXMLLoader

	@FXML // fx:id="menuPersiana"
	private AnchorPane menuPersiana; // Value injected by FXMLLoader

	@FXML // fx:id="menuPlanta"
	private AnchorPane menuPlanta; // Value injected by FXMLLoader

	@FXML // fx:id="menuPrincipal"
	private AnchorPane menuPrincipal; // Value injected by FXMLLoader

	@FXML // fx:id="menuProgramas"
	private AnchorPane menuProgramas; // Value injected by FXMLLoader

	@FXML // fx:id="minutoRiego"
	private TextField minutoRiego; // Value injected by FXMLLoader

	@FXML // fx:id="persianaMovil"
	private ImageView persianaMovil; // Value injected by FXMLLoader

	@FXML // fx:id="programaUtil"
	private HBox programaUtil; // Value injected by FXMLLoader

	@FXML // fx:id="salon"
	private FlowPane salon; // Value injected by FXMLLoader

	@FXML // fx:id="selectorGiratorio"
	private AnchorPane selectorGiratorio; // Value injected by FXMLLoader

	@FXML // fx:id="semanal"
	private RadioButton semanal; // Value injected by FXMLLoader

	@FXML // fx:id="subirPersiana"
	private ImageView subirPersiana; // Value injected by FXMLLoader

	@FXML // fx:id="terraza"
	private FlowPane terraza; // Value injected by FXMLLoader

	@FXML // fx:id="tiempoRiegoAhora"
	private TextField tiempoRiegoAhora; // Value injected by FXMLLoader

	@FXML // fx:id="tiempoRiegoPrograma"
	private TextField tiempoRiegoPrograma; // Value injected by FXMLLoader

	@FXML //  fx:id="menuArmarioCamisetas"
	private AnchorPane menuArmarioCamisetas; // Value injected by FXMLLoader


	//VARIABLES DE CONTROL
	double persianaPos = 254; //254 representa bajada, 0 subida totalmente
	Timer t;
	double prevMousePos = -1234567890; //Valor para identificar el no definido (no hay pantalla que tenga tantos pixeles xD)


	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void aumentarHoraRiegoPrograma(MouseEvent event) {
		programarIncrementador(horaRiego, 1);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void aumentarMinutoRiegoPrograma(MouseEvent event) {
		programarIncrementador(minutoRiego, 1);
	}

	// Handler for ImageView[id="botonflecha"] onMousePressed
	@FXML
	void aumentarTiempoRiegoAhora(MouseEvent event) {
		programarIncrementador(tiempoRiegoAhora, 1);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void aumentarTiempoRiegoPrograma(MouseEvent event) {
		programarIncrementador(tiempoRiegoPrograma, 1);
	}

	// Handler for Button[id="botonRegarAhora"] onMouseClicked
	@FXML
	void confirmarPrograma(MouseEvent event) {
		// handle the event here
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void disminuirHoraRiegoPrograma(MouseEvent event) {
		programarIncrementador(horaRiego, -1);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void disminuirMinutoRiegoPrograma(MouseEvent event) {
		programarIncrementador(minutoRiego, -1);
	}


	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	void disminuirTiempoRiegoPrograma(MouseEvent event) {
		programarIncrementador(tiempoRiegoPrograma, -1);
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	@FXML
	void goToMenuInicialPlanta(MouseEvent event) {
		// handle the event here
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	@FXML
	void goToMenuPrincipal(MouseEvent event) {
		// handle the event here
	}

	// Handler for Button[id="botonRegarAhora"] onMouseClicked
	@FXML
	void goToMenuProgramas(MouseEvent event) {
		// handle the event here
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	@FXML
	void goToMenuProgramasBack(MouseEvent event) {
		// handle the event here
	}

	// Handler for ImageView[id="botonflechaabajo"] onMousePressed
	@FXML
	void disminuirTiempoRiegoAhora(MouseEvent event) {
		programarIncrementador(tiempoRiegoAhora, -1);
	}

	// Handler for ImageView[fx:id="bajarPersiana"] onMousePressed
	public void bajarPersiana(MouseEvent event) {
		t = new Timer(true);
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				if(persianaPos <= 252){
					persianaMovil.setTranslateY(persianaMovil.getTranslateY()+2);
					persianaPos += 2;
				}
			}
		}, 0, 100);

	}

	// Handler for Button[fx:id="botonArmario"] onMouseClicked
	public void irMenuArmario(MouseEvent event) {
		menuPrincipal.setVisible(false);
		menuArmario.setVisible(true);
	}

	// Handler for Button[fx:id="botonPersianas"] onMouseClicked
	public void irMenuPersianas(MouseEvent event) {
		menuPrincipal.setVisible(false);
		menuPersiana.setVisible(true);
	}

	// Handler for Button[id="botonPersianas"] onMouseClicked
	public void irMenuPlantas(MouseEvent event) {
		menuPrincipal.setVisible(false);
		menuPlanta.setVisible(true);
	}

	// Handler for VBox[VBox@50d1cbcc] onMouseDragged
	public void movimientoDedoPersiana(MouseEvent event) {

		//Primer movimiento?
		if(prevMousePos == -1234567890) 
			prevMousePos = event.getSceneY();

		double newMousePos = event.getSceneY();
		double diferencia = Math.abs(newMousePos-prevMousePos);

		if(newMousePos > prevMousePos){ //Estamos haciendo el gesto de bajar
			if(persianaPos+diferencia <= 254){
				persianaMovil.setTranslateY(persianaMovil.getTranslateY()+diferencia);
				persianaPos += diferencia;
			}
		}else if(newMousePos < prevMousePos){ //Hacemos el gesto de subir
			if(persianaPos-diferencia >= 0){
				persianaMovil.setTranslateY(persianaMovil.getTranslateY()-diferencia);
				persianaPos -= diferencia;
			}
		}

		//Actualizamos los valores
		prevMousePos = newMousePos;

	}

	// Handler for ImageView[fx:id="bajarPersiana"] onMouseReleased
	// Handler for ImageView[fx:id="subirPersiana"] onMouseReleased
	public void pararPersiana(MouseEvent event) {
		if(t != null)
			t.cancel();
		t=null;
	}

	// Handler for Button[fx:id="botonProgramarRiego"] onMouseClicked
	public void programarRiego(MouseEvent event) {
		// handle the event here
	}

	// Handler for ImageView[fx:id="subirPersiana"] onMousePressed
	public void subirPersiana(MouseEvent event) {
		t = new Timer(true);
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				if(persianaPos >= 2){
					persianaMovil.setTranslateY(persianaMovil.getTranslateY()-2);
					persianaPos -= 2;
				}
			}
		}, 0, 100);
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert bajarPersiana != null : "fx:id=\"bajarPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
		assert bisemanal != null : "fx:id=\"bisemanal\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonArmario != null : "fx:id=\"botonArmario\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonPersianas != null : "fx:id=\"botonPersianas\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonPlantas != null : "fx:id=\"botonPlantas\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonProgramarRiego != null : "fx:id=\"botonProgramarRiego\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonRegarAhora != null : "fx:id=\"botonRegarAhora\" was not injected: check your FXML file 'IPO.fxml'.";
		assert botonTelevision != null : "fx:id=\"botonTelevision\" was not injected: check your FXML file 'IPO.fxml'.";
		assert habitacion != null : "fx:id=\"habitacion\" was not injected: check your FXML file 'IPO.fxml'.";
		assert horaRiego != null : "fx:id=\"horaRiego\" was not injected: check your FXML file 'IPO.fxml'.";
		assert mensual != null : "fx:id=\"mensual\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuArmario != null : "fx:id=\"menuArmario\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuArmarioCategorias != null : "fx:id=\"menuArmarioCategorias\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuEditandoPrograma != null : "fx:id=\"menuEditandoPrograma\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuHabitaciones != null : "fx:id=\"menuHabitaciones\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuInicialPlanta != null : "fx:id=\"menuInicialPlanta\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuPersiana != null : "fx:id=\"menuPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuPlanta != null : "fx:id=\"menuPlanta\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuPrincipal != null : "fx:id=\"menuPrincipal\" was not injected: check your FXML file 'IPO.fxml'.";
		assert menuProgramas != null : "fx:id=\"menuProgramas\" was not injected: check your FXML file 'IPO.fxml'.";
		assert minutoRiego != null : "fx:id=\"minutoRiego\" was not injected: check your FXML file 'IPO.fxml'.";
		assert persianaMovil != null : "fx:id=\"persianaMovil\" was not injected: check your FXML file 'IPO.fxml'.";
		assert programaUtil != null : "fx:id=\"programaUtil\" was not injected: check your FXML file 'IPO.fxml'.";
		assert salon != null : "fx:id=\"salon\" was not injected: check your FXML file 'IPO.fxml'.";
		assert selectorGiratorio != null : "fx:id=\"selectorGiratorio\" was not injected: check your FXML file 'IPO.fxml'.";
		assert semanal != null : "fx:id=\"semanal\" was not injected: check your FXML file 'IPO.fxml'.";
		assert subirPersiana != null : "fx:id=\"subirPersiana\" was not injected: check your FXML file 'IPO.fxml'.";
		assert terraza != null : "fx:id=\"terraza\" was not injected: check your FXML file 'IPO.fxml'.";
		assert tiempoRiegoAhora != null : "fx:id=\"tiempoRiegoAhora\" was not injected: check your FXML file 'IPO.fxml'.";
		assert tiempoRiegoPrograma != null : "fx:id=\"tiempoRiegoPrograma\" was not injected: check your FXML file 'IPO.fxml'.";

		//Mostramos el menu principal
		menuArmario.setVisible(false);
		menuPersiana.setVisible(false);
		menuPlanta.setVisible(false);
		menuPrincipal.setVisible(true);

		//Mostramos solo las opciones del salon
		salon.setVisible(true);
		habitacion.setVisible(false);
		terraza.setVisible(false);

		//Movemos la persiana a la posición 100 (bastante subida)
		persianaMovil.setTranslateY(persianaMovil.getTranslateY()+100-254);
		persianaPos = 100;


	}

	private void programarIncrementador(final TextField field, final int cantidad){
		t = new Timer(true);
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				int horaRiegoValue = Integer.getInteger(field.getText());
				if(horaRiegoValue < 23)
					field.setText(""+(horaRiegoValue+cantidad));
				else
					field.setText("0");
			}
		}, 0, 100);

		field.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(t != null)
					t.cancel();
				t=null;
				field.setOnMouseReleased(null);
			}
		});
	}

}
