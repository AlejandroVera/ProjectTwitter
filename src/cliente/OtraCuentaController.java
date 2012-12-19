/**
 * Sample Skeleton for "otraCuenta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
import interfacesComunes.Twitter_Users;
import interfacesComunes.User;
import interfacesComunes.Twitter.ITweet;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class OtraCuentaController extends Controller implements AStream.IListen{

	private static final long serialVersionUID = 460902024550111662L;

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

	@FXML //  fx:id="menuOtraCuenta"
	private TabPane menuOtraCuenta; // Value injected by FXMLLoader

	@FXML //  fx:id="tituloTweet"
	private Label tituloTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="textoTweet"
	private TextArea textoTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="geoActivado"
	private ImageView geoActivado; // Value injected by FXMLLoader

	@FXML //  fx:id="geoDesactivado"
	private ImageView geoDesactivado; // Value injected by FXMLLoader

	@FXML //  fx:id="placeActual"
	private Label placeActual; // Value injected by FXMLLoader

	/** 
	 * Usuario que se está mostrando actualmente 
	 */
	private User user;
	private Place lugar;

	boolean seguidoresLoaded;
	boolean siguiendoLoaded;
	boolean favoritosLoaded;

	ChangeListener<Tab> listenerCambios;

	private HashMap<Number, TweetController> favouritesTable; //Tabla que asocia los favoritos que hay cargados con su id
	private HashMap<Number, TweetController> tweetsTable; //Tabla que asocia los tweets que hay cargados con su id
	private HashMap<Number, UserController> seguidoresTable; //Tabla que asocia los usuarios
	private HashMap<Number, UserController> siguiendoTable; //Tabla que asocia los usuarios




	// Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
	// Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
	public void activarGeo(MouseEvent event) {

		/*El texto pasado a getPlace solo es util con el twitterReal*/
		lugar = (Place)getTwitter().geo().getPlace("Madrid, España", null);

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
	// Handler for ImageView[fx:id="geoActivado"] onMouseClicked
	public void desactivarGeo(MouseEvent event) {
		super.getTwitter().setMyPlace((long)-1);

		geoActivado.setVisible(false);
		geoDesactivado.setVisible(false);
		placeActual.setText("Geolocalizacion desactivada");
		geoDesactivado.setVisible(true);

	}

	// Handler for Label[fx:id="placeActual"] onMouseClicked
	public void mostrarGeo(MouseEvent event) {
		if (super.getTwitter().getMyPlace()!=(long)-1)
			ClientTools.showPlace(this.lugar);
	}

	// Handler for TextArea[id="textoNuevoTweet"] onKeyPressed
	public void cambiaContador(KeyEvent event) {
		contadorCaracteres.setText(""+(140-ClientTools.countCharacters(textoTweet.getText())));
	}

	// Handler for Label[id="cerrarNuevoTweet"] onMouseClicked
	public void cerrarNuevoTweet(MouseEvent event) {
		this.creadorTweets.setVisible(false);
	}

	// Handler for VBox[fx:id="botonTweet"] onMouseClicked
	public void crearTweetMencion(MouseEvent event) {
		this.creadorTweets.setVisible(true);
		if(super.getTwitter().getMyPlace()!=(long)-1)
			this.activarGeo(event);

		else
			this.desactivarGeo(event);
	}

	// Handler for VBox[fx:id="botonUnfollow"] onMouseClicked
	public void dejarDeSeguir(MouseEvent event) {
		getTwitter().users().stopFollowing(this.user);
		botonUnfollow.setVisible(false);
		botonFollow.setVisible(true);
	}

	// Handler for VBox[id="contenedorMensaje"] onMouseClicked
	public void enviarMensajePrivado(MouseEvent event) {
		WorldController parent = (WorldController)super.getParentController();
		parent.responderMensaje("@"+this.user.getScreenName());

	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFollowers(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuOtraCuenta.getSelectionModel();
		selectionModel.select(seguidoresTab);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFriends(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuOtraCuenta.getSelectionModel();
		selectionModel.select(siguiendoTab);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarTweetsUsuario(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuOtraCuenta.getSelectionModel();
		selectionModel.select(tweetsTab);
	}

	// Handler for Button[fx:id="twittear"] onMouseClicked
	public void publicarTweet(MouseEvent event) {
		super.getTwitter().updateStatus(this.textoTweet.getText());
	}

	// Handler for VBox[fx:id="botonFollow"] onMouseClicked
	public void seguir(MouseEvent event) {
		getTwitter().users().follow(this.user);
		botonUnfollow.setVisible(true);
		botonFollow.setVisible(false);
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
		geoActivado.setVisible(false);
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

	protected void changeToUser(User usu){
		//Limpiamos la información anterior
		this.user = usu;
		tweetsUsuario.getChildren().clear();

		//Rellenamos la caja de información del usuario
		ScreenName.setText(this.user.getName());
		name.setText("@"+this.user.getScreenName());
		nTweets.setText(""+this.user.getStatusesCount());
		nSeguidores.setText(""+this.user.getFollowersCount());
		nSiguiendo.setText(""+this.user.getFriendsCount());
		descripcion.setText(this.user.getDescription());

		//Botón de seguidor
		if(super.getTwitter().users().isFollowing(this.user)){ //Si ya le estamos siguiendo
			botonUnfollow.setVisible(true);
			botonFollow.setVisible(false);
		}else{
			botonUnfollow.setVisible(false);
			botonFollow.setVisible(true);
		}

		String screenName = this.user.getScreenName();
		twitteaA.setText("@"+screenName);
		sigueA.setText("@"+screenName);
		desSigueA.setText("@"+screenName);
		tituloTweet.setText("Twittea a @"+screenName);
		textoTweet.setText("@"+screenName);

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

		//Borramos el anterior listener
		if(listenerCambios != null)
			tweetsTab.getTabPane().getSelectionModel().selectedItemProperty().removeListener(listenerCambios);

		//Añadimos un nuevo listener para carga por demanda
		this.listenerCambios = new ChangeListener<Tab>() {
			@Override 
			public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				if(!favoritosLoaded && newTab.equals(favoritosTab)){

					//Cargamos sus favoritos
					Iterator<Status> favoritos = getTwitter().getFavorites(user.getScreenName()).iterator();
					tweetsFavoritos.getChildren().clear();
					while(favoritos.hasNext()){
						addTweet(tweetsFavoritos, favoritos.next());
					}
					favoritosLoaded = true;

				}else if(!seguidoresLoaded && newTab.equals(seguidoresTab)){

					Twitter_Users tw_users = getTwitter().users();
					int count = 0;

					//Cargamos sus seguidores
					Iterator<Long> seguidores = tw_users.getFollowerIDs(user.getScreenName()).iterator();
					cajaSeguidores.getChildren().clear();
					while(seguidores.hasNext() && count++ < 10){//TODO: limite temporal
						User us = tw_users.getUser(seguidores.next());
						if(us != null)
							addUser(cajaSeguidores, us);
					}
					seguidoresLoaded = true;


				}else if(!siguiendoLoaded && newTab.equals(siguiendoTab)){

					Twitter_Users tw_users = getTwitter().users();
					int count = 0;

					//Cargamos sus seguidos
					Iterator<Long> seguidos = tw_users.getFriendIDs(user.getScreenName()).iterator();
					cajaSiguiendo.getChildren().clear();
					while(seguidos.hasNext() && count++ < 10){ //TODO: limite temporal
						User us = tw_users.getUser(seguidos.next());
						if(us != null)
							addUser(cajaSiguiendo, us);
					}	
					siguiendoLoaded = true;

				}else if(siguiendoLoaded && seguidoresLoaded && favoritosLoaded){ //Quitamos el listener
					tweetsTab.getTabPane().getSelectionModel().selectedItemProperty().removeListener(this);
				}
			}
		};
		menuOtraCuenta.getSelectionModel().selectedItemProperty().addListener(listenerCambios);

	}



	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {

		if(event.getType().equals(TwitterEvent.Type.FAVORITE) && event.getSource().getId().equals(user.getId())){

			if(favoritosLoaded)
				this.addTweet(tweetsFavoritos, (Status)event.getTargetObject(), true);
			Number id = ((ITweet) event.getTargetObject()).getId();

			//Mandamos el evento a la lista de tweets propios para que se actualice el icono de favorito
			TweetController controller = tweetsTable.get(id);
			if(controller != null)
				controller.processEvent(event);

		}else if(event.getType().equals(TwitterEvent.Type.UNFAVORITE)  && event.getSource().getId().equals(user.getId())){

			Number id = ((ITweet) event.getTargetObject()).getId();
			this.removeFavourite(favouritesTable.get(id));

			//Mandamos el evento a la lista de tweets propios para que se actualice el icono de favorito
			TweetController controller = tweetsTable.get(id);
			if(controller != null)
				controller.processEvent(event);

		}else if(event.getType().equals(TwitterEvent.Type.USER_UPDATE)){
			if(event.getSource().getId().equals(this.user.getId())){
				this.user = getTwitter().users().getUser(this.user.getId());
				Image im = ClientTools.getImage(this.user.getProfileImageUrl().toString());
				if(im != null)
					profileImage.setImage(im);
				this.name.setText(user.getName());
			}

			//Se manda el evento a todos los tweets (ellos ya comprobarán si son suyos)
			for(TweetController c : favouritesTable.values()){
				c.processEvent(event);
			}
			for(TweetController c : tweetsTable.values()){
				c.processEvent(event);
			}

			//Se manda el evento a los UserController que corresponda
			UserController c = seguidoresTable.get(event.getSource().getId());
			if(c != null)
				c.processEvent(event);
			c = siguiendoTable.get(event.getSource().getId());
			if(c != null)
				c.processEvent(event);


		}else if(event.getType().equals(TwitterEvent.Type.FOLLOW)){ //Si el evento es un follow

			//Si ahora tu sigues a alguien
			if (event.getSource().getId().equals(user.getId())){
				this.addUser(this.cajaSiguiendo, event.getTarget());
				int n=Integer.parseInt(nSiguiendo.getText().trim());
				n++;
				nSiguiendo.setText(String.valueOf(n));
			}
			//Si ahora te sigue alguien
			else if(event.getTarget().getId().equals(user.getId())){
				this.addUser(this.cajaSeguidores, event.getSource());
				int n=Integer.parseInt(nSeguidores.getText().trim());
				n++;
				nSeguidores.setText(String.valueOf(n));

			}
		}else if(event.getType().equals(TwitterEvent.Type.UNFOLLOW)){ //Si el evento es un unfollow

			//Si ahora tu ya no sigues a alguien
			if (event.getSource().getId().equals(user.getId())){
				UserController c = siguiendoTable.get(event.getTarget().getId());
				this.removeSiguiendo(c);
				int n=Integer.parseInt(nSiguiendo.getText().trim());
				n--;
				nSiguiendo.setText(String.valueOf(n));
			}
			//Si y no te sigue alguien
			else if(event.getTarget().getId().equals(user.getId())){
				UserController c = seguidoresTable.get(event.getSource().getId());
				this.removeSeguidor(c);
				int n=Integer.parseInt(nSeguidores.getText().trim());
				n--;
				nSeguidores.setText(String.valueOf(n));
			}
		}

		return true;
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
	
	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		this.addTweet(tweetsUsuario, tweet, true);
		return true;
	}

	protected void removeTweet(TweetController c){
		if(c != null){
			tweetsUsuario.getChildren().remove(c.getContainer());
			tweetsTable.remove(c.getTweet().getId());
		}
	}

	protected void removeFavourite(TweetController c){
		if(c != null){
			tweetsFavoritos.getChildren().remove(c.getContainer());
			favouritesTable.remove(c.getTweet().getId());
		}
	}

	protected void removeSeguidor(UserController c){
		if(c != null){
			cajaSeguidores.getChildren().remove(c.getContainer());
			seguidoresTable.remove(c.getUser().getId());
		}
	}

	protected void removeSiguiendo(UserController c){
		if(c != null){
			cajaSiguiendo.getChildren().remove(c.getContainer());
			siguiendoTable.remove(c.getUser().getId());
		}
	}

}

