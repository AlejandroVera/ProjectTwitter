package interfazIPO;

/**
 * Sample Skeleton for "IPO.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/


import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class IPOMainController
implements Initializable {



	@FXML //  fx:id="bajarPersiana"
	private ImageView bajarPersiana; // Value injected by FXMLLoader

	@FXML //  fx:id="bisemanal"
	private RadioButton bisemanal; // Value injected by FXMLLoader

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

	@FXML //  fx:id="dias"
	private Label dias; // Value injected by FXMLLoader

	@FXML //  fx:id="domingo"
	private ToggleButton domingo; // Value injected by FXMLLoader

	@FXML //  fx:id="duracion"
	private Label duracion; // Value injected by FXMLLoader

	@FXML //  fx:id="frecuencia"
	private Label frecuencia; // Value injected by FXMLLoader

	@FXML //  fx:id="habitacion"
	private FlowPane habitacion; // Value injected by FXMLLoader

	@FXML //  fx:id="hora"
	private Label hora; // Value injected by FXMLLoader

	@FXML //  fx:id="horaRiego"
	private TextField horaRiego; // Value injected by FXMLLoader

	@FXML //  fx:id="jueves"
	private ToggleButton jueves; // Value injected by FXMLLoader

	@FXML //  fx:id="lunes"
	private ToggleButton lunes; // Value injected by FXMLLoader

	@FXML //  fx:id="martes"
	private ToggleButton martes; // Value injected by FXMLLoader

	@FXML //  fx:id="mensaje"
	private AnchorPane mensaje; // Value injected by FXMLLoader

	@FXML //  fx:id="mensual"
	private RadioButton mensual; // Value injected by FXMLLoader

	@FXML //  fx:id="menuArmario"
	private AnchorPane menuArmario; // Value injected by FXMLLoader

	@FXML //  fx:id="menuArmarioCamisetas"
	private AnchorPane menuArmarioCamisetas; // Value injected by FXMLLoader

	@FXML //  fx:id="menuArmarioCategorias"
	private AnchorPane menuArmarioCategorias; // Value injected by FXMLLoader

	@FXML //  fx:id="menuEditandoPrograma"
	private AnchorPane menuEditandoPrograma; // Value injected by FXMLLoader

	@FXML //  fx:id="menuHabitaciones"
	private AnchorPane menuHabitaciones; // Value injected by FXMLLoader

	@FXML //  fx:id="menuInicialPlanta"
	private AnchorPane menuInicialPlanta; // Value injected by FXMLLoader

	@FXML //  fx:id="menuPersiana"
	private AnchorPane menuPersiana; // Value injected by FXMLLoader

	@FXML //  fx:id="menuPlanta"
	private AnchorPane menuPlanta; // Value injected by FXMLLoader

	@FXML //  fx:id="menuPrincipal"
	private AnchorPane menuPrincipal; // Value injected by FXMLLoader

	@FXML //  fx:id="menuProgramas"
	private AnchorPane menuProgramas; // Value injected by FXMLLoader

	@FXML //  fx:id="miercoles"
	private ToggleButton miercoles; // Value injected by FXMLLoader

	@FXML //  fx:id="minutoRiego"
	private TextField minutoRiego; // Value injected by FXMLLoader

	@FXML //  fx:id="persianaMovil"
	private ImageView persianaMovil; // Value injected by FXMLLoader

	@FXML //  fx:id="programaUtil"
	private HBox programaUtil; // Value injected by FXMLLoader

	@FXML //  fx:id="sabado"
	private ToggleButton sabado; // Value injected by FXMLLoader

	@FXML //  fx:id="salon"
	private FlowPane salon; // Value injected by FXMLLoader

	@FXML //  fx:id="selectorCamisetas"
	private VBox selectorCamisetas; // Value injected by FXMLLoader

	@FXML //  fx:id="selectorCategorias"
	private VBox selectorCategorias; // Value injected by FXMLLoader

	@FXML //  fx:id="selectorGiratorio"
	private VBox selectorGiratorio; // Value injected by FXMLLoader

	@FXML //  fx:id="semanal"
	private RadioButton semanal; // Value injected by FXMLLoader

	@FXML //  fx:id="subirPersiana"
	private ImageView subirPersiana; // Value injected by FXMLLoader

	@FXML //  fx:id="terraza"
	private FlowPane terraza; // Value injected by FXMLLoader

	@FXML //  fx:id="textoMensaje"
	private TextArea textoMensaje; // Value injected by FXMLLoader

	@FXML //  fx:id="tiempoRiegoAhora"
	private TextField tiempoRiegoAhora; // Value injected by FXMLLoader

	@FXML //  fx:id="tiempoRiegoPrograma"
	private TextField tiempoRiegoPrograma; // Value injected by FXMLLoader

	@FXML //  fx:id="viernes"
	private ToggleButton viernes; // Value injected by FXMLLoader



	//VARIABLES DE CONTROL
	double persianaPos = 254; //254 representa bajada, 0 subida totalmente
	Timer t;
	double prevMousePos = -1234567890; //Valor para identificar el no definido (no hay pantalla que tenga tantos pixeles xD)
	

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void aumentarHoraRiegoPrograma(MouseEvent event) {
		programarIncrementador(horaRiego, 1, 23);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void aumentarMinutoRiegoPrograma(MouseEvent event) {
		programarIncrementador(minutoRiego, 1, 59);
	}

	// Handler for ImageView[id="botonflecha"] onMousePressed
	@FXML
	public void aumentarTiempoRiegoAhora(MouseEvent event) {
		programarIncrementador(tiempoRiegoAhora, 1, 99);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void aumentarTiempoRiegoPrograma(MouseEvent event) {
		programarIncrementador(tiempoRiegoPrograma, 1, 99);
	}

	// Handler for Button[id="botonRegarAhora"] onMouseClicked
	@FXML
	public void confirmarPrograma(MouseEvent event) {
		programaUtil.setVisible(true);
		textoMensaje.setText("Programa creado correctamente");
		mensaje.setVisible(true);
		menuProgramas.setVisible(true);
		menuEditandoPrograma.setVisible(false);

		//Rellenamos la info del nuevo 
		//Los dias
		String diassemana = "";
		if(lunes.isSelected())
			diassemana+="L";
		else
			diassemana+="_";
		if(martes.isSelected())
			diassemana+="M";
		else
			diassemana+="_";
		if(miercoles.isSelected())
			diassemana+="X";
		else
			diassemana+="_";
		if(jueves.isSelected())
			diassemana+="J";
		else
			diassemana+="_";
		if(viernes.isSelected())
			diassemana+="V";
		else
			diassemana+="_";
		if(sabado.isSelected())
			diassemana+="S";
		else
			diassemana+="_";
		if(domingo.isSelected())
			diassemana+="D";
		else
			diassemana+="_";
		dias.setText(diassemana);

		//Hora de comienzo
		hora.setText("Hora Inicio: "+horaRiego.getText()+":"+minutoRiego.getText());

		//Duracion del riego
		duracion.setText("Duracion: "+ tiempoRiegoPrograma.getText() +" min");

		//Frecuencia del riego
		String frecstr = "";
		if(bisemanal.isSelected())
			frecstr = "Bisemanal";
		else if(semanal.isSelected())
			frecstr = "Semanal";
		else if(mensual.isSelected())
			frecstr = "Mensual";

		frecuencia.setText("Frecuencia: "+frecstr);

	}

	// Handler for Button[id="botonRegarAhora"] onMouseClicked
	public void crearNuevoPrograma(MouseEvent event) {
		menuEditandoPrograma.setVisible(true);
		menuProgramas.setVisible(false);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void disminuirHoraRiegoPrograma(MouseEvent event) {
		programarIncrementador(horaRiego, -1, 23);
	}

	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void disminuirMinutoRiegoPrograma(MouseEvent event) {
		programarIncrementador(minutoRiego, -1, 59);
	}


	// Handler for ImageView[id="flecha"] onMousePressed
	@FXML
	public void disminuirTiempoRiegoPrograma(MouseEvent event) {
		programarIncrementador(tiempoRiegoPrograma, -1, 99);
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	public void goToMenuArmarioCategorias(MouseEvent event) {
		System.out.println("Desde goToMenuArmarioCategorias");
		menuPrincipal.setVisible(false);
		menuArmario.setVisible(true);
		menuArmarioCategorias.setVisible(true);
		menuArmarioCamisetas.setVisible(false);
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	@FXML
	public void goToMenuInicialPlanta(MouseEvent event) {
		irMenuPlantas(null);
	}

	// Handler for ImageView[id="deshacer"] onMouseClicked
	@FXML
	public void goToMenuPrincipal(MouseEvent event) {
		menuPrincipal.setVisible(true);
		menuPersiana.setVisible(false);
		menuPlanta.setVisible(false);
		menuArmario.setVisible(false);
	}

	// Handler for Button[id="botonRegarAhora"] onMouseClicked
	@FXML
	public void goToMenuProgramas(MouseEvent event) {
		menuPrincipal.setVisible(false);
		menuPlanta.setVisible(true);
		menuInicialPlanta.setVisible(false);
		menuProgramas.setVisible(true);
		menuEditandoPrograma.setVisible(false);
	}

	// Handler for ImageView[id="botonflechaabajo"] onMousePressed
	@FXML
	public void disminuirTiempoRiegoAhora(MouseEvent event) {
		programarIncrementador(tiempoRiegoAhora, -1, 99);
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
		System.out.println("Desde irMenuArmario");
		menuPrincipal.setVisible(false);
		menuArmario.setVisible(true);
		menuArmarioCategorias.setVisible(false);
		menuArmarioCamisetas.setVisible(true);
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
		menuInicialPlanta.setVisible(true);
		menuProgramas.setVisible(false);
		menuEditandoPrograma.setVisible(false);
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
		menuProgramas.setVisible(true);
		menuInicialPlanta.setVisible(false);
	}
	
	// Handler for Button[fx:id="botonProgramarRiego"] onMouseClicked
		public void esconderMessage(MouseEvent event) {
			mensaje.setVisible(false);
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
		mensaje.setVisible(false);
		
		
		//Mostramos solo las opciones del salon
		salon.setVisible(true);
		habitacion.setVisible(false);
		terraza.setVisible(false);

		//Movemos la persiana a la posición 100 (bastante subida)
		persianaMovil.setTranslateY(persianaMovil.getTranslateY()+100-254);
		persianaPos = 100;

		//Cargamos selector giratorio menu principal
		List<SelectorEntry> listaMenuPrincipal = new LinkedList<SelectorEntry>();
		try {
			listaMenuPrincipal.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/salon.png").openStream())),
					this.getClass().getMethod("salonSelected", Node.class), this));
			listaMenuPrincipal.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/cocina.png").openStream())), null, null));
			listaMenuPrincipal.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/dormitorio.png").openStream())),
					this.getClass().getMethod("dormitorioSelected", Node.class), this));
			listaMenuPrincipal.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/baño.png").openStream())), null, null));
			listaMenuPrincipal.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/balcon.png").openStream())),
					this.getClass().getMethod("terrazaSelected", Node.class), this));
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			e.printStackTrace();
		}

		SelectorCircular selector = new SelectorCircular(listaMenuPrincipal, 4000, 400, 90);
		AnchorPane pane = selector.getRoot();
		selectorGiratorio.getChildren().add(pane);

		//Cargamos selector giratorio de categorias de ropa
		List<SelectorEntry> listaCategorias = new LinkedList<SelectorEntry>();
		try {
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/calzado.png").openStream())), null, null));
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisa.png").openStream())), null, null));
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camiseta.png").openStream())),
					this.getClass().getMethod("camisetaSelected", Node.class), this));
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/pantalon.png").openStream())), null, null));
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/ropaAbrigo.png").openStream())), null, null));
			listaCategorias.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/ropainterior.png").openStream())), null, null));
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			e.printStackTrace();
		}

		SelectorCircular selectorCat = new SelectorCircular(listaCategorias, 4000, 600, 150);
		AnchorPane panc = selectorCat.getRoot();
		selectorCategorias.getChildren().add(panc);

		//Cargamos selector giratorio de camisetas
		List<SelectorEntry> listaCamisetas = new LinkedList<SelectorEntry>();
		try {
			listaCamisetas.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisetaLargaBlanca.png").openStream())), null, null));
			listaCamisetas.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisetaNaranja.png").openStream())), null, null));
			listaCamisetas.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisetaNegra.png").openStream())),
					this.getClass().getMethod("camisetaNegraSelected", Node.class), this));
			listaCamisetas.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisetaRoja.png").openStream())), null, null));
			listaCamisetas.add(new SelectorEntry(new ImageView(new Image(getClass().getResource("imagenes/camisetaVerde.png").openStream())), null, null));
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			e.printStackTrace();
		}

		SelectorCircular selectorCam = new SelectorCircular(listaCamisetas, 4000, 600, 150);
		AnchorPane panm = selectorCam.getRoot();
		selectorCamisetas.getChildren().add(panm);



		//Incializamos ciertos valores
		tiempoRiegoPrograma.setText("0");
		tiempoRiegoAhora.setText("0");

	}

	private void programarIncrementador(final TextField field, final int cantidad, final int maximo){
		if(t == null){
			t = new Timer(true);
			t.schedule(new TimerTask() {

				@Override
				public void run() {
					Integer horaRiegoValue = Integer.parseInt(field.getText());
					if(horaRiegoValue+cantidad >= 0 && horaRiegoValue+cantidad <= maximo)
						field.setText(""+(horaRiegoValue+cantidad));
					else
						field.setText("0");
				}
			}, 0, 200);

			field.getScene().setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if(t != null)
						t.cancel();
					t=null;
					field.getScene().setOnMouseReleased(null);
				}
			});
		}
	}

	public void salonSelected(Node n){
		salon.setVisible(true);
		habitacion.setVisible(false);
		terraza.setVisible(false);
	}

	public void dormitorioSelected(Node n){
		salon.setVisible(false);
		habitacion.setVisible(true);
		terraza.setVisible(false);
	}

	public void terrazaSelected(Node n){
		salon.setVisible(false);
		habitacion.setVisible(false);
		terraza.setVisible(true);
	}

	public void camisetaSelected(Node n){
		menuArmario.setVisible(true);
		menuArmarioCamisetas.setVisible(true);
		menuArmarioCategorias.setVisible(false);
	}

	public void camisetaNegraSelected(Node n){
		textoMensaje.setText("Has seleccionado la camiseta negra");
		mensaje.setVisible(true);
	}


}
