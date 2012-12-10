/**
 * Sample Skeleton for "otraCuenta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.Status;
import interfacesComunes.Twitter_Users;
import interfacesComunes.User;
import interfacesComunes.Twitter.ITweet;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class OtraCuentaController extends Controller{

	@FXML //  fx:id="ScreenName"
    private Label ScreenName; // Value injected by FXMLLoader

    @FXML //  fx:id="botonFollow"
    private VBox botonFollow; // Value injected by FXMLLoader

    @FXML //  fx:id="botonTweet"
    private VBox botonTweet; // Value injected by FXMLLoader

    @FXML //  fx:id="botonUnfollow"
    private VBox botonUnfollow; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSeguidores"
    private VBox cajaSeguidores; // Value injected by FXMLLoader

    @FXML //  fx:id="cajaSiguiendo"
    private VBox cajaSiguiendo; // Value injected by FXMLLoader

    @FXML //  fx:id="contadorCaracteres"
    private Label contadorCaracteres; // Value injected by FXMLLoader

    @FXML //  fx:id="creadorTweets"
    private StackPane creadorTweets; // Value injected by FXMLLoader

    @FXML //  fx:id="desSigueA"
    private Label desSigueA; // Value injected by FXMLLoader

    @FXML //  fx:id="descripcion"
    private TextArea descripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="enviarMensaje"
    private StackPane enviarMensaje; // Value injected by FXMLLoader

    @FXML //  fx:id="favoritosTab"
    private Tab favoritosTab; // Value injected by FXMLLoader

    @FXML //  fx:id="nSeguidores"
    private Label nSeguidores; // Value injected by FXMLLoader

    @FXML //  fx:id="nSiguiendo"
    private Label nSiguiendo; // Value injected by FXMLLoader

    @FXML //  fx:id="nTweets"
    private Label nTweets; // Value injected by FXMLLoader

    @FXML //  fx:id="name"
    private Label name; // Value injected by FXMLLoader

    @FXML //  fx:id="profileImage"
    private ImageView profileImage; // Value injected by FXMLLoader

    @FXML //  fx:id="seguidoresTab"
    private Tab seguidoresTab; // Value injected by FXMLLoader

    @FXML //  fx:id="sigueA"
    private Label sigueA; // Value injected by FXMLLoader

    @FXML //  fx:id="siguiendoTab"
    private Tab siguiendoTab; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsFavoritos"
    private VBox tweetsFavoritos; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsTab"
    private Tab tweetsTab; // Value injected by FXMLLoader

    @FXML //  fx:id="tweetsUsuario"
    private VBox tweetsUsuario; // Value injected by FXMLLoader

    @FXML //  fx:id="twitteaA"
    private Label twitteaA; // Value injected by FXMLLoader

    @FXML //  fx:id="twittear"
    private Button twittear; // Value injected by FXMLLoader
    
    /** 
     * Usuario que se está mostrando actualmente 
     */
    private User user; 


    // Handler for TextArea[id="textoNuevoTweet"] onKeyPressed
    public void cambiaContador(KeyEvent event) {
        // handle the event here
    }

    // Handler for TextArea[fx:id="descripcion"] onKeyPressed
    public void cambiarDescripcion(KeyEvent event) {
        // handle the event here
    }

    // Handler for Label[id="cerrarNuevoTweet"] onMouseClicked
    public void cerrarNuevoTweet(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[fx:id="botonTweet"] onMouseClicked
    public void crearTweetMencion(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[fx:id="botonUnfollow"] onMouseClicked
    public void dejarDeSeguir(MouseEvent event) {
    	User res = getTwitter().users().stopFollowing(this.user);
        if(res != null){
        	botonUnfollow.setVisible(false);
			botonFollow.setVisible(true);
        }	
    }

    // Handler for VBox[id="contenedorMensaje"] onMouseClicked
    public void enviarMensajePrivado(MouseEvent event) {
        // handle the event here
    }

    // Handler for Label[fx:id="ScreenName"] onMouseClicked
    public void irCuenta(MouseEvent event) {
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

    // Handler for Button[fx:id="twittear"] onMouseClicked
    public void publicarTweet(MouseEvent event) {
        // handle the event here
    }

    // Handler for VBox[fx:id="botonFollow"] onMouseClicked
    public void seguir(MouseEvent event) {
        User res = getTwitter().users().follow(this.user);
        if(res != null){
        	botonUnfollow.setVisible(true);
			botonFollow.setVisible(false);
        }	
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	assert ScreenName != null : "fx:id=\"ScreenName\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert botonFollow != null : "fx:id=\"botonFollow\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert botonTweet != null : "fx:id=\"botonTweet\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert botonUnfollow != null : "fx:id=\"botonUnfollow\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert cajaSeguidores != null : "fx:id=\"cajaSeguidores\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert cajaSiguiendo != null : "fx:id=\"cajaSiguiendo\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert contadorCaracteres != null : "fx:id=\"contadorCaracteres\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert creadorTweets != null : "fx:id=\"creadorTweets\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert desSigueA != null : "fx:id=\"desSigueA\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert enviarMensaje != null : "fx:id=\"enviarMensaje\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert favoritosTab != null : "fx:id=\"favoritosTab\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert seguidoresTab != null : "fx:id=\"seguidoresTab\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert sigueA != null : "fx:id=\"sigueA\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert siguiendoTab != null : "fx:id=\"siguiendoTab\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert tweetsFavoritos != null : "fx:id=\"tweetsFavoritos\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert tweetsTab != null : "fx:id=\"tweetsTab\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert tweetsUsuario != null : "fx:id=\"tweetsUsuario\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert twitteaA != null : "fx:id=\"twitteaA\" was not injected: check your FXML file 'otraCuenta.fxml'.";
        assert twittear != null : "fx:id=\"twittear\" was not injected: check your FXML file 'otraCuenta.fxml'.";


        // initialize your logic here: all @FXML variables will have been injected
        creadorTweets.setVisible(false);
        profileImage.setPreserveRatio(false);
        
        
    }

	@Override
	public void postInitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void changeToUser(User user){
		//Limpiamos la información anterior
		this.user = user;
		tweetsUsuario.getChildren().clear();
		
		//Rellenamos la caja de información del usuario
		ScreenName.setText(this.user.getScreenName());
		nTweets.setText(""+this.user.getStatusesCount());
        nSeguidores.setText(""+this.user.getFollowersCount());
        nSiguiendo.setText(""+this.user.getFriendsCount());
        descripcion.setText(this.user.getDescription());
        
        //Su imagen
        Image im = ClientTools.getImage(this.user.getProfileImageUrl().toString());
        if(im != null)
        	profileImage.setImage(im);
		
		//Cargamos su "timeline"
		Iterator<Status> timeline = super.getTwitter().getUserTimeline(this.user.getId()).iterator();
		tweetsUsuario.getChildren().clear();
		while(timeline.hasNext()){
			this.addTweet(tweetsUsuario, timeline.next());
		}
		
		//Cargamos sus favoritos
		Iterator<Status> favoritos = super.getTwitter().getFavorites(this.user.getScreenName()).iterator();
		tweetsFavoritos.getChildren().clear();
		while(favoritos.hasNext()){
			this.addTweet(tweetsFavoritos, favoritos.next());
		}
		
		Twitter_Users tw_users = super.getTwitter().users();
		int count = 0;
		
		//Cargamos sus seguidores
		Iterator<Long> seguidores = tw_users.getFollowerIDs(this.user.getScreenName()).iterator();
		cajaSeguidores.getChildren().clear();
		while(seguidores.hasNext() && count++ < 10){//TODO: limite temporal
			User us = tw_users.getUser(seguidores.next());
			if(us != null)
				this.addUser(cajaSeguidores, us);
		}
		count = 0;
		//Cargamos sus seguidos
		Iterator<Long> seguidos = tw_users.getFollowerIDs(this.user.getScreenName()).iterator();
		cajaSiguiendo.getChildren().clear();
		while(seguidos.hasNext() && count++ < 10){ //TODO: limite temporal
			User us = tw_users.getUser(seguidos.next());
			if(us != null)
				this.addUser(cajaSiguiendo, us);
		}
		
		//Botón de seguidor
		if(super.getTwitter().users().isFollowing(this.user)){ //Si ya le estamos siguiendo
			botonUnfollow.setVisible(true);
			botonFollow.setVisible(false);
		}else{
			botonUnfollow.setVisible(false);
			botonFollow.setVisible(true);
		}
		
		twitteaA.setText(this.user.getScreenName());
		sigueA.setText(this.user.getScreenName());
		desSigueA.setText(this.user.getScreenName());
		
		
	}
	
	/**
	 * Añade un tweet al final de la lista.
	 * @param contendor VBox a la que añadir el tweet.
	 * @param tweet Tweet a añadir.
	 */
	private void addTweet(VBox contendor, ITweet tweet){
		addTweet(contendor, tweet, false);
	}
	
	/**
	 * Añade un tweet.
	 * @param contendor VBox a la que añadir el tweet.
	 * @param tweet Tweet a añadir.
	 * @param onTop True si el tweet se tiene que añadir al principio de la lista.
	 */
	private void addTweet(VBox contendor,ITweet tweet, boolean onTop){
		try {
			FXMLTweetAutoLoader tweetUI = new FXMLTweetAutoLoader(getTwitter(), (Status) tweet);
			tweetUI.getController().setParentController(this);
			
			if(!onTop)
				contendor.getChildren().add(tweetUI.getRoot());
			else{
				LinkedList<Node> list = new LinkedList<Node>(contendor.getChildren());
				list.addFirst(tweetUI.getRoot());
				contendor.getChildren().clear();
				contendor.getChildren().addAll(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addUser(VBox contendor, User user){
		addUser(contendor, user, false);
	}
	
	private void addUser(VBox contendor,User user, boolean onTop){
		try {
			FXMLUserAutoLoader userUI = new FXMLUserAutoLoader(getTwitter(), user);
			userUI.getController().setParentController(this);
			
			if(!onTop)
				contendor.getChildren().add(userUI.getRoot());
			else{
				LinkedList<Node> list = new LinkedList<Node>(contendor.getChildren());
				list.addFirst(userUI.getRoot());
				contendor.getChildren().clear();
				contendor.getChildren().addAll(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

