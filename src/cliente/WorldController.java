/**
 * Sample Skeleton for "timeline.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Message;
import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import com.winterwell.jgeoplanet.IPlace;
import com.winterwell.jgeoplanet.MFloat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class WorldController extends Controller implements AStream.IListen {

	private static final long serialVersionUID = 6319965686022119977L;


	@FXML //  fx:id="ajustes"
	private MenuItem ajustes; // Value injected by FXMLLoader

	@FXML //  fx:id="busquedaLabel"
	private TextField busquedaLabel; // Value injected by FXMLLoader

	@FXML //  fx:id="cajaNuevoTweet"
	private VBox cajaNuevoTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="cajaSeleccion"
	private HBox cajaSeleccion; // Value injected by FXMLLoader

	@FXML //  fx:id="cerrarNuevoTweet"
	private Label cerrarNuevoTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="cerrarSesion"
	private MenuItem cerrarSesion; // Value injected by FXMLLoader

	@FXML //  fx:id="creadorTweets"
	private StackPane creadorTweets; // Value injected by FXMLLoader

	@FXML //  fx:id="conectaTab"
	private Tab conectaTab; // Value injected by FXMLLoader

	@FXML //  fx:id="infCuenta"
	private StackPane infCuenta; // Value injected by FXMLLoader

	@FXML //  fx:id="miCuentaTab"
	private Tab miCuentaTab; // Value injected by FXMLLoader

	@FXML //  fx:id="nSeguidores"
	private Label nSeguidores; // Value injected by FXMLLoader

	@FXML //  fx:id="nSiguiendo"
	private Label nSiguiendo; // Value injected by FXMLLoader

	@FXML //  fx:id="nTweets"
	private Label nTweets; // Value injected by FXMLLoader

	@FXML //  fx:id="busquedaTab"
	private Tab busquedaTab; // Value injected by FXMLLoader

	@FXML //  fx:id="otraCuentaTab"
	private Tab otraCuentaTab; // Value injected by FXMLLoader

	@FXML //  fx:id="profileImage"
	private ImageView profileImage; // Value injected by FXMLLoader

	@FXML //  fx:id="screenName"
	private Label screenName; // Value injected by FXMLLoader

	@FXML //  fx:id="textoNuevoTweet"
	private TextArea textoNuevoTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="timeLineTab"
	private Tab timeLineTab; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetButton"
	private Button tweetButton; // Value injected by FXMLLoader

	@FXML //  fx:id="twittear"
	private Button twittear; // Value injected by FXMLLoader

	@FXML //  fx:id="worldContainer"
	private AnchorPane worldContainer; // Value injected by FXMLLoader

	@FXML //  fx:id="caracteresTweet"
	private Label caracteresTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="nuevaVentana"
	private StackPane nuevaVentana; // Value injected by FXMLLoader

	@FXML //  fx:id="geoActivado"
	private ImageView geoActivado; // Value injected by FXMLLoader

	@FXML //  fx:id="geoDesactivado"
	private ImageView geoDesactivado; // Value injected by FXMLLoader

	@FXML //  fx:id="menuPrincipal"
	private TabPane menuPrincipal; // Value injected by FXMLLoader

	@FXML //  fx:id="placeActual"
	private Label placeActual; // Value injected by FXMLLoader

	@FXML //  fx:id="cerrarNuevoTweet"
	private Label name; // Value injected by FXMLLoader


	//privadas propias
	private MiCuentaController miCuentaController;
	private ConectaController conectaController;
	private TimeLineController timeLineController;
	private AjustesController ajustesController;
	private OtraCuentaController otraCuentaController;
	private BusquedaController busquedaController;
	private MensajesController mensajesController;

	private Place lugar;

	// Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
	public void activarGeo(MouseEvent event) {

		/*El texto pasado a getPlace solo es util con el twitterReal
		 * No se porque pero siempre devuelve null con jtwitter :(*/
		MFloat f= new MFloat(1);
		IPlace lugar = getTwitter().geo().getPlace("www.google.com", f);
		

		if (lugar!=null){

			placeActual.setText(getTwitter().geo().getPlace(null,null).toString());
			geoActivado.setVisible(true);
			geoDesactivado.setVisible(false);
			if(super.getTwitter().getMyPlace()==-1)
				super.getTwitter().setMyPlace(Long.parseLong(lugar.getUID()));
		}
		else if (lugar==null){
			ClientTools.showDialog("Geolocalizacion no disponible");
		}

	}


	// Handler for ImageView[fx:id="geoActivado"] onMouseClicked
	public void desactivarGeo(MouseEvent event) {

		super.getTwitter().setMyPlace((long)-1);
		geoActivado.setVisible(false);
		geoDesactivado.setVisible(false);
		placeActual.setText("Geolocalizacion desactivada");
		geoDesactivado.setVisible(true);

	}

	// Handler for ImageView[id="lupa"] onMouseClicked
	public void busca(MouseEvent event) {
		menuPrincipal.getSelectionModel().select(this.busquedaTab);
		infCuenta.setVisible(false);
		List<User> l=super.getTwitter().users().searchUsers(this.busquedaLabel.getText());
		if(l!=null)
			busquedaController.addUserResult(l);
		List<Status> x=super.getTwitter().search(this.busquedaLabel.getText());
		if(x!=null)
			busquedaController.addTweetResult(x);
	}
	
	 // Handler for TextField[fx:id="busquedaLabel"] onKeyPressed
    public void buscaKey(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    		busca(null);
    }

	// Handler for TextArea[fx:id="textoNuevoTweet"] onKeyPressed
	public void cambiaContador(KeyEvent event) {
		caracteresTweet.setText(""+(140-ClientTools.countCharacters(textoNuevoTweet.getText())));
	}

	// Handler for MenuItem[fx:id="cerrarSesion"] onAction
	public void cerrarSesion(ActionEvent event) {
		getClientListener().notifyLogout();
	}

	// Handler for MenuItem[fx:id="ajustes"] onAction
	public void irAjustes(ActionEvent event) {
		this.ajustesController.postInitialize(); //Truquito para que se cargue siempre la info por defecto
		this.ajustesController.showWindow();
	}

	// Handler for HBox[id="cajaIz"] onKeyPressed
	// Handler for Label[fx:id="screenName"] onMouseClicked
	public void irCuenta(InputEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuPrincipal.getSelectionModel();
		this.infCuenta.setVisible(false);
		selectionModel.select(miCuentaTab);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFollowers(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuPrincipal.getSelectionModel();
		selectionModel.select(miCuentaTab);
		this.infCuenta.setVisible(false);
		this.miCuentaController.mostrarFollowers(event);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFriends(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuPrincipal.getSelectionModel();
		selectionModel.select(miCuentaTab);
		this.infCuenta.setVisible(false);
		this.miCuentaController.mostrarFriends(event);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarTweetsUsuario(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuPrincipal.getSelectionModel();
		selectionModel.select(miCuentaTab);
		this.infCuenta.setVisible(false);
		this.miCuentaController.mostrarTweetsUsuario(event);
	}

	// Handler for Button[fx:id="tweetButton"] onAction
	// Handler for Button[fx:id="tweetButton"] onMouseClicked
	public void twittear(MouseEvent event) {
		creadorTweets.setVisible(!creadorTweets.isVisible());
		if (creadorTweets.isVisible()){
			if (super.getTwitter().getMyPlace()!=(long)-1){
				activarGeo(event);		
			}
			else 
				desactivarGeo(event);
		}
	}
	// Handler for TabPane[fx:id="menuPrincipal"] onMouseClicked
	public void ocultarPerfil(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuPrincipal.getSelectionModel();

		if (selectionModel.getSelectedItem().equals(this.miCuentaTab)){
			this.infCuenta.setVisible(false);

		}
	}

	// Handler for Button[fx:id="twittear"] onMouseClicked (publicar ya el tweet como tal)
	public void publicarTweet(MouseEvent event) {
		String texto = textoNuevoTweet.getText();
		if(getTwitter().updateStatus(texto) == null){ //Error?
			ClientTools.showDialog("No se ha podido mandar el tweet");
			return;
		}
		creadorTweets.setVisible(false);
		textoNuevoTweet.clear();
	}


	// Handler for Label[fx:id="cerrarNuevoTweet"] onMouseClicked (cierra el menu del nuevo tweet)
	public void cerrarNuevoTweet(MouseEvent event) {
		creadorTweets.setVisible(false);
	}

	// Handler for TextArea[TextArea[id=null, styleClass=text-input text-area]] onKeyPressed
	public void cambiarDescripcion(KeyEvent event) {
		// handle the event here
	}

	// Handler for VBox[fx:id="contenedorMensaje"] onMouseClicked
	public void verMensajesPrivados(MouseEvent event) {
		this.mensajesController.postInitialize();
		this.mensajesController.showWindow();
	}

	public void responderMensaje(String destino) {
		this.mensajesController.postInitialize();
		this.mensajesController.responderMensaje(destino);
		this.mensajesController.showWindow();
	}


	// Handler for Label[fx:id="placeActual"] onMouseClicked
	public void mostrarGeo(MouseEvent event) {
		if (super.getTwitter().getMyPlace()!=(long)-1)
			ClientTools.showPlace(this.lugar);
	}


	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert ajustes != null : "fx:id=\"ajustes\" was not injected: check your FXML file 'world.fxml'.";
		assert busquedaLabel != null : "fx:id=\"busquedaLabel\" was not injected: check your FXML file 'world.fxml'.";
		assert cajaNuevoTweet != null : "fx:id=\"cajaNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
		assert cajaSeleccion != null : "fx:id=\"cajaSeleccion\" was not injected: check your FXML file 'world.fxml'.";
		assert caracteresTweet != null : "fx:id=\"caracteresTweet\" was not injected: check your FXML file 'world.fxml'.";
		assert cerrarNuevoTweet != null : "fx:id=\"cerrarNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
		assert cerrarSesion != null : "fx:id=\"cerrarSesion\" was not injected: check your FXML file 'world.fxml'.";
		assert conectaTab != null : "fx:id=\"conectaTab\" was not injected: check your FXML file 'world.fxml'.";
		assert creadorTweets != null : "fx:id=\"creadorTweets\" was not injected: check your FXML file 'world.fxml'.";
		assert geoActivado != null : "fx:id=\"geoActivado\" was not injected: check your FXML file 'world.fxml'.";
		assert geoDesactivado != null : "fx:id=\"geoDesactivado\" was not injected: check your FXML file 'world.fxml'.";
		assert infCuenta != null : "fx:id=\"infCuenta\" was not injected: check your FXML file 'world.fxml'.";
		assert menuPrincipal != null : "fx:id=\"menuPrincipal\" was not injected: check your FXML file 'world.fxml'.";
		assert miCuentaTab != null : "fx:id=\"miCuentaTab\" was not injected: check your FXML file 'world.fxml'.";
		assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'world.fxml'.";
		assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'world.fxml'.";
		assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'world.fxml'.";
		assert nuevaVentana != null : "fx:id=\"nuevaVentana\" was not injected: check your FXML file 'world.fxml'.";
		assert otraCuentaTab != null : "fx:id=\"otraCuentaTab\" was not injected: check your FXML file 'world.fxml'.";
		assert placeActual != null : "fx:id=\"placeActual\" was not injected: check your FXML file 'world.fxml'.";
		assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'world.fxml'.";
		assert screenName != null : "fx:id=\"screenName\" was not injected: check your FXML file 'world.fxml'.";
		assert textoNuevoTweet != null : "fx:id=\"textoNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
		assert timeLineTab != null : "fx:id=\"timeLineTab\" was not injected: check your FXML file 'world.fxml'.";
		assert tweetButton != null : "fx:id=\"tweetButton\" was not injected: check your FXML file 'world.fxml'.";
		assert twittear != null : "fx:id=\"twittear\" was not injected: check your FXML file 'world.fxml'.";
		assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'world.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected
		creadorTweets.setVisible(false);
		profileImage.setPreserveRatio(false);
		geoActivado.setVisible(false);


		//Eventos para volver a mostrar el cuadro de informacion del usuario de la izquierda
		timeLineTab.getTabPane().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override 
			public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				if(newTab.equals(timeLineTab)) {
					infCuenta.setVisible(true);
				}else if (newTab.equals(conectaTab)){
					infCuenta.setVisible(true);
					conectaController.loadEvents();//Entramos por primera vez y hay que cargar eventos
				}
				else if(newTab.equals(miCuentaTab)){
					infCuenta.setVisible(false);
					miCuentaController.loadTweets(); //Entramos por primera vez y hay que cargar tweets?
				}
			}
		});

	}

	@Override
	public void postInitialize() {
		super.getTwitter().setMyPlace((long)-1);
		name.setText(super.getTwitter().getSelf().getName());
		screenName.setText("@"+super.getTwitter().getScreenName());
		User user = super.getTwitter().getSelf();
		nTweets.setText(""+user.getStatusesCount());
		nSeguidores.setText(""+user.getFollowersCount());
		nSiguiendo.setText(""+user.getFriendsCount());
		Image im = ClientTools.getImage(super.getTwitter().getSelf().getProfileImageUrl().toString());
		if(im != null)
			profileImage.setImage(im);

		try {
			this.timeLineController = (TimeLineController) loadFXMLAndAppendToTab("timeLine.fxml", this.timeLineTab);
			this.miCuentaController = (MiCuentaController) loadFXMLAndAppendToTab("miCuenta.fxml", this.miCuentaTab);
			this.conectaController = (ConectaController) loadFXMLAndAppendToTab("conecta.fxml", this.conectaTab);
			this.ajustesController = (AjustesController) loadFXMLAndAppendToTab("preferencias.fxml", null);
			this.otraCuentaController = (OtraCuentaController) loadFXMLAndAppendToTab("otraCuenta.fxml", this.otraCuentaTab);
			this.busquedaController = (BusquedaController) loadFXMLAndAppendToTab("busqueda.fxml", this.busquedaTab);
			this.mensajesController= (MensajesController) loadFXMLAndAppendToTab("mensajes.fxml",null);
			nuevaVentana.getChildren().add(this.ajustesController.getRoot());
			nuevaVentana.getChildren().add(this.mensajesController.getRoot());
			this.ajustesController.hideWindow();
			this.mensajesController.hideWindow();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		if(event.getType().equals(TwitterEvent.Type.USER_UPDATE)){
			if(event.getSource().getId().equals(getTwitter().getSelf().getId())){
				User u = getTwitter().users().getUser(getTwitter().getSelf().getId());
				Image im = ClientTools.getImage(u.getProfileImageUrl().toString());
				if(im != null)
					profileImage.setImage(im);
				name.setText(u.getName());
			}
		}
		if(event.getType().equals(TwitterEvent.Type.FOLLOW)){
			if(event.getTarget().getId().equals(this.getTwitter().getSelf().getId())){
				int n=Integer.parseInt(nSeguidores.getText().trim());
				n++;
				nSeguidores.setText(String.valueOf(n));
			}
			else if(event.getSource().getId().equals(this.getTwitter().getSelf().getId())){
				int n=Integer.parseInt(nSiguiendo.getText().trim());
				n++;
				nSiguiendo.setText(String.valueOf(n));
			}
		}

		if(event.getType().equals(TwitterEvent.Type.UNFOLLOW)){
			if(event.getTarget().getId().equals(this.getTwitter().getSelf().getId())){
				int n=Integer.parseInt(nSeguidores.getText().trim());
				n--;
				nSeguidores.setText(String.valueOf(n));
			}
			else if(event.getSource().getId().equals(this.getTwitter().getSelf().getId())){
				int n=Integer.parseInt(nSiguiendo.getText().trim());
				n--;
				nSiguiendo.setText(String.valueOf(n));
			}
		}
		this.miCuentaController.processEvent(event);
		this.conectaController.processEvent(event);
		this.timeLineController.processEvent(event);
		this.busquedaController.processEvent(event);

		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		if (tweet instanceof Status){
			//Inc numero tweets
			if(super.getTwitter().getSelf().getId().equals(tweet.getUser().getId())){
				nTweets.setText(""+(new Integer(nTweets.getText()).intValue() + 1));
				this.miCuentaController.processTweet(tweet);
			}

			//Propagamos el evento

			this.timeLineController.processTweet(tweet);
			this.conectaController.processTweet(tweet);
		}
		else if (tweet instanceof Message){
			this.mensajesController.processTweet(tweet);
		}
		return true;
	}

	private Controller loadFXMLAndAppendToTab(String fxml, Tab parent) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		Parent root = (Parent) loader.load(getClass().getResource(fxml).openStream());

		//Obtenemos el objeto controlador
		Controller control = loader.getController();
		control.setClientListener(super.getClientListener());
		control.setTwitter(super.getTwitter());
		control.setParentController(this);
		control.setRoot(root);
		control.postInitialize();

		//Mostramos la nueva vista
		if(parent != null)
			parent.setContent(root);

		return control;
	}

	@Override
	protected AnchorPane getContainer() {
		return worldContainer;
	}

	protected void changeToOtherAccount(User user){
		//Queremos entrar a la cuenta de un usuario diferente
		if(!user.getId().equals(getTwitter().getSelf().getId())){
			this.otraCuentaController.changeToUser(user);
			this.otraCuentaTab.getTabPane().getSelectionModel().select(this.otraCuentaTab);
			infCuenta.setVisible(false);
		}else{ //Queremos entrar a nuestra cuenta
			this.infCuenta.setVisible(false);
			this.miCuentaTab.getTabPane().getSelectionModel().select(this.miCuentaTab);
		}
	}

	public void buscaHashtags(String text) {
		menuPrincipal.getSelectionModel().select(this.busquedaTab);
		infCuenta.setVisible(false);
		List<Status> x=super.getTwitter().search(text);
		if(x!=null)
			busquedaController.addTweetResult(x);
	}


}

