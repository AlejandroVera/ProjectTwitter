/**
 * Sample Skeleton for "timeline.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    
    @FXML //  fx:id="mapa"
    private ImageView mapa; // Value injected by FXMLLoader
    
    @FXML //  fx:id="menuPrincipal"
    private TabPane menuPrincipal; // Value injected by FXMLLoader
    
    @FXML //  fx:id="stackMapa"
    private StackPane stackMapa; // Value injected by FXMLLoader
    
    @FXML //  fx:id="placeActual"
    private Label placeActual; // Value injected by FXMLLoader

    
    //privadas propias
    private MiCuentaController miCuentaController;
    private ConectaController conectaController;
    private TimeLineController timeLineController;
    private AjustesController ajustesController;
        
    private boolean geoLocation=false;

    // Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
    public void activarGeo(MouseEvent event) {
    	geoLocation=true;
    	//Cuando este twitter.geo
    	/*Double latitude= getTwitter().geo().getPlace(null,null).getCenter().getLatitude();
    	Double longitude= getTwitter().geo().getPlace(null,null).getCenter().getLongitude(); 
    	String coord=new String(latitude.toString()+","+longitude.toString());
    	
    	  
    	URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center="+coord+
    			"&size=200*150&maptype=hybrid&markers=color:red%"+coord+"&sensor=false");
    	URLConnection conn = new URLConnection(url);
    	InputStream in = conn.getInputStream();
    	mapa.setImage((Image) in);
    	placeActual.setText("getTwitter().geo().getPlace(null,null).toString()")*/
    	geoActivado.setVisible(true);
    	geoDesactivado.setVisible(false);
    	
    	
        
    }
    
    // Handler for ImageView[fx:id="geoActivado"] onMouseClicked
    public void desactivarGeo(MouseEvent event) {
    	
    	geoLocation=false;
        geoActivado.setVisible(false);
        stackMapa.setVisible(false);
        geoDesactivado.setVisible(false);
        mapa.setImage(null);
        placeActual.setText("Geolocalizacion desactivada");
        
    }
    
    // Handler for ImageView[id="lupa"] onMouseClicked
    public void busca(MouseEvent event) {
        // handle the event here
    }
    
    // Handler for TextArea[fx:id="textoNuevoTweet"] onKeyPressed
    public void cambiaContador(KeyEvent event) {
        // handle the event here
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
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarFollowers(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarFriends(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[id="cajita"] onMouseClicked
    public void mostrarTweetsUsuario(MouseEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="tweetButton"] onAction
    // Handler for Button[fx:id="tweetButton"] onMouseClicked
    public void twittear(Event event) {
    	creadorTweets.setVisible(!creadorTweets.isVisible());
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
        // handle the event here
    }
    
    // Handler for Label[fx:id="placeActual"] onMouseClicked
    public void mostrarGeo(MouseEvent event) {
    	stackMapa.setVisible(true);
    }
    
 // Handler for Label[id="cerrarNuevoTweet"] onMouseClicked
    public void cerrarMapa(MouseEvent event) {
        stackMapa.setVisible(false);
    }
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	 assert ajustes != null : "fx:id=\"ajustes\" was not injected: check your FXML file 'world.fxml'.";
         assert busquedaLabel != null : "fx:id=\"busquedaLabel\" was not injected: check your FXML file 'world.fxml'.";
         assert cajaNuevoTweet != null : "fx:id=\"cajaNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
         assert cajaSeleccion != null : "fx:id=\"cajaSeleccion\" was not injected: check your FXML file 'world.fxml'.";
         assert cerrarNuevoTweet != null : "fx:id=\"cerrarNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
         assert cerrarSesion != null : "fx:id=\"cerrarSesion\" was not injected: check your FXML file 'world.fxml'.";
         assert conectaTab != null : "fx:id=\"conectaTab\" was not injected: check your FXML file 'world.fxml'.";
         assert creadorTweets != null : "fx:id=\"creadorTweets\" was not injected: check your FXML file 'world.fxml'.";
         assert infCuenta != null : "fx:id=\"infCuenta\" was not injected: check your FXML file 'world.fxml'.";
         assert miCuentaTab != null : "fx:id=\"miCuentaTab\" was not injected: check your FXML file 'world.fxml'.";
         assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'world.fxml'.";
         assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'world.fxml'.";
         assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'world.fxml'.";
         assert otraCuentaTab != null : "fx:id=\"otraCuentaTab\" was not injected: check your FXML file 'world.fxml'.";
         assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'world.fxml'.";
         assert screenName != null : "fx:id=\"screenName\" was not injected: check your FXML file 'world.fxml'.";
         assert textoNuevoTweet != null : "fx:id=\"textoNuevoTweet\" was not injected: check your FXML file 'world.fxml'.";
         assert timeLineTab != null : "fx:id=\"timeLineTab\" was not injected: check your FXML file 'world.fxml'.";
         assert tweetButton != null : "fx:id=\"tweetButton\" was not injected: check your FXML file 'world.fxml'.";
         assert twittear != null : "fx:id=\"twittear\" was not injected: check your FXML file 'world.fxml'.";
         assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'world.fxml'.";
         assert menuPrincipal != null : "fx:id=\"menuPrincipal\" was not injected: check your FXML file 'world.fxml'.";

         // initialize your logic here: all @FXML variables will have been injected
         creadorTweets.setVisible(false);
         profileImage.setPreserveRatio(false);
         geoActivado.setVisible(false);
         stackMapa.setVisible(false);
                 
    }

	@Override
	public void postInitialize() {
		screenName.setText(super.getTwitter().getScreenName());
		User user = super.getTwitter().getSelf();
		nTweets.setText(""+user.getStatusesCount());
        nSeguidores.setText(""+user.getFollowersCount());
        nSiguiendo.setText(""+user.getFriendsCount());
        double height = profileImage.getFitHeight();
        double width = profileImage.getFitWidth();
        Image im = ClientTools.getImage(super.getTwitter().getSelf().getProfileImageUrl().toString());
        if(im != null)
        	profileImage.setImage(im);
        //profileImage.setPreserveRatio(false);
        //profileImage.setFitHeight(height);
        //profileImage.setFitWidth(width);
        
		try {
		   	this.timeLineController = (TimeLineController) loadFXMLAndAppendToTab("timeLine.fxml", this.timeLineTab);
		   	this.miCuentaController = (MiCuentaController) loadFXMLAndAppendToTab("miCuenta.fxml", this.miCuentaTab);
		   	this.conectaController = (ConectaController) loadFXMLAndAppendToTab("conecta.fxml", this.conectaTab);
		   	this.ajustesController = (AjustesController) loadFXMLAndAppendToTab("preferencias.fxml", null);
		   	nuevaVentana.getChildren().add(this.ajustesController.getRoot());
		   	this.ajustesController.hideWindow();
		   	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		this.conectaController.processEvent(event);
		return true;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		
		//Inc numero tweets
		if(super.getTwitter().getSelf().getId().equals(tweet.getUser().getId())){
			nTweets.setText(""+(new Integer(nTweets.getText()).intValue() + 1));
		}
	
		
		//Propagamos el evento
		this.timeLineController.processTweet(tweet);
		
		//TODO: si el usuario del tweet es el mismo cuyo timeline tenemos abierto, mandamos el tweet al timeline
		//
		
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

	

}

