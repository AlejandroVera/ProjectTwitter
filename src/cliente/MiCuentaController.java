package cliente;

/**
 * Sample Skeleton for "cuenta.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import interfacesComunes.AStream;
import interfacesComunes.Status;
import interfacesComunes.Twitter_Users;
import interfacesComunes.User;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

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
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class MiCuentaController extends Controller implements AStream.IListen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8554344662002374232L;

	@FXML //  fx:id="ScreenName"
	private Label ScreenName; // Value injected by FXMLLoader

	@FXML //  fx:id="cajaSeguidores"
	private VBox cajaSeguidores; // Value injected by FXMLLoader

	@FXML //  fx:id="cajaSiguiendo"
	private VBox cajaSiguiendo; // Value injected by FXMLLoader

	@FXML //  fx:id="descripcion"
	private TextArea descripcion; // Value injected by FXMLLoader

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

	@FXML //  fx:id="siguiendoTab"
	private Tab siguiendoTab; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsFavoritos"
	private VBox tweetsFavoritos; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsTab"
	private Tab tweetsTab; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsUsuario"
	private VBox tweetsUsuario; // Value injected by FXMLLoader

	@FXML //  fx:id="menuMiCuenta"
	private TabPane menuMiCuenta; // Value injected by FXMLLoader


	private User user;

	boolean tweetsLoaded;
	boolean seguidoresLoaded;
	boolean siguiendoLoaded;
	boolean favoritosLoaded;

	private HashMap<Number, TweetController> favouritesTable; //Tabla que asocia los favoritos que hay cargados con su id
	private HashMap<Number, TweetController> tweetsTable; //Tabla que asocia los tweets que hay cargados con su id
	private HashMap<Number, UserController> seguidoresTable; //Tabla que asocia los usuarios
	private HashMap<Number, UserController> siguiendoTable; //Tabla que asocia los usuarios




	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFollowers(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuMiCuenta.getSelectionModel();
		selectionModel.select(seguidoresTab);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFriends(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuMiCuenta.getSelectionModel();
		selectionModel.select(siguiendoTab);
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarTweetsUsuario(MouseEvent event) {
		SingleSelectionModel<Tab> selectionModel=menuMiCuenta.getSelectionModel();
		selectionModel.select(tweetsTab);
	}

	// Handler for VBox[id="contenedorMensaje"] onMouseClicked
	public void verMensajesPrivados(MouseEvent event) {

		((WorldController) this.getParentController()).verMensajesPrivados(event);
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert ScreenName != null : "fx:id=\"ScreenName\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert cajaSeguidores != null : "fx:id=\"cajaSeguidores\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert cajaSiguiendo != null : "fx:id=\"cajaSiguiendo\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert favoritosTab != null : "fx:id=\"favoritosTab\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert nSeguidores != null : "fx:id=\"nSeguidores\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert nSiguiendo != null : "fx:id=\"nSiguiendo\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert nTweets != null : "fx:id=\"nTweets\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert profileImage != null : "fx:id=\"profileImage\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert seguidoresTab != null : "fx:id=\"seguidoresTab\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert siguiendoTab != null : "fx:id=\"siguiendoTab\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert tweetsFavoritos != null : "fx:id=\"tweetsFavoritos\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert tweetsTab != null : "fx:id=\"tweetsTab\" was not injected: check your FXML file 'miCuenta.fxml'.";
		assert tweetsUsuario != null : "fx:id=\"tweetsUsuario\" was not injected: check your FXML file 'miCuenta.fxml'.";

		// initialize your logic here: all @FXML variables will have been injected
		this.favouritesTable = new HashMap<Number, TweetController>();
		this.tweetsTable = new HashMap<Number, TweetController>();
		this.seguidoresTable = new HashMap<Number, UserController>();
		this.siguiendoTable = new HashMap<Number, UserController>();
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
				name.setText(user.getName());
				descripcion.setText(user.getDescription());
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
				
				//Si esta en seguidores ahora el boton no tiene que poner para seguirlo
				UserController c = seguidoresTable.get(event.getTarget().getId());
				if (c!=null)
					c.processEvent(event);
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
				//Si esta en seguidores ahora el boton de unfollow tiene que ponerse en follow
				UserController c2 = seguidoresTable.get(event.getTarget().getId());
				if (c2!=null){
					c2.processEvent(event);
				}
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

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processTweet(ITweet tweet) throws RemoteException {
		if(tweetsLoaded)
			this.addTweet(tweetsUsuario, tweet, true);
		return true;
	}

	@Override
	public void postInitialize() {
		//Limpiamos la información anterior
		this.user = getTwitter().getSelf();

		//Rellenamos la caja de información del usuario
		ScreenName.setText(this.user.getName());
		name.setText("@"+this.user.getScreenName());
		nTweets.setText(""+this.user.getStatusesCount());
		nSeguidores.setText(""+this.user.getFollowersCount());
		nSiguiendo.setText(""+this.user.getFriendsCount());
		descripcion.setText(this.user.getDescription());

		//Su imagen
		Image im = ClientTools.getImage(this.user.getProfileImageUrl().toString());
		if(im != null)
			profileImage.setImage(im);

		tweetsTab.getTabPane().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override 
			public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				if(!favoritosLoaded && newTab.equals(favoritosTab)){

					//Cargamos sus favoritos
					Iterator<Status> favoritos = getTwitter().getFavorites(user.getScreenName()).iterator();
					while(favoritos.hasNext()){
						addTweet(tweetsFavoritos, favoritos.next());
					}
					favoritosLoaded = true;

				}else if(!seguidoresLoaded && newTab.equals(seguidoresTab)){

					Twitter_Users tw_users = getTwitter().users();
					int count = 0;

					//Cargamos sus seguidores
					Iterator<Long> seguidores = tw_users.getFollowerIDs(user.getScreenName()).iterator();
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
		});

	}

	@Override
	protected AnchorPane getContainer() {
		// TODO Auto-generated method stub
		return null;
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

			//Lo añadimos a la tabla de asociacion
			if(contendor.equals(tweetsFavoritos))
				favouritesTable.put(tweet.getId(), tweetUI.getController());
			else if(contendor.equals(tweetsUsuario))
				tweetsTable.put(tweet.getId(), tweetUI.getController());

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

			//Lo añadimos a la tabla de asociacion
			if(contendor.equals(cajaSeguidores))
				seguidoresTable.put(user.getId(), userUI.getController());
			else if(contendor.equals(cajaSiguiendo))
				siguiendoTable.put(user.getId(), userUI.getController());

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

	protected void loadTweets(){
		if(!tweetsLoaded){
			//Cargamos su "timeline"
			Iterator<Status> timeline = getTwitter().getUserTimeline(user.getId()).iterator();
			while(timeline.hasNext()){
				addTweet(tweetsUsuario, timeline.next());
			}
			tweetsLoaded = true;
		}
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