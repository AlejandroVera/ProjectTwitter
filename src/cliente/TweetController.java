/**
 * Sample Skeleton for "tweet.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/
package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import excepcionesComunes.TwitterException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.HyperlinkBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


public class TweetController extends Controller implements AStream.IListen{

	private static final long serialVersionUID = 136870617271640893L;


	@FXML //  fx:id="globalContainer"
	private VBox globalContainer; // Value injected by FXMLLoader

	@FXML //  fx:id="infoExtra"
	private HBox infoExtra; // Value injected by FXMLLoader

	@FXML //  fx:id="location"
	private Label location; // Value injected by FXMLLoader

	@FXML //  fx:id="nFavoritos"
	private Label nFavoritos; // Value injected by FXMLLoader

	@FXML //  fx:id="nRetweets"
	private Label nRetweets; // Value injected by FXMLLoader

	@FXML //  fx:id="numeroDe"
	private Label numeroDe; // Value injected by FXMLLoader

	@FXML //  fx:id="screename"
	private Label screename; // Value injected by FXMLLoader

	@FXML //  fx:id="stackAbrir"
	private StackPane stackAbrir; // Value injected by FXMLLoader

	@FXML //  fx:id="stackBorrar"
	private StackPane stackBorrar; // Value injected by FXMLLoader

	@FXML //  fx:id="stackFavorito"
	private StackPane stackFavorito; // Value injected by FXMLLoader

	@FXML //  fx:id="stackResponder"
	private StackPane stackResponder; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRespuesta"
	private StackPane stackRespuesta; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRetwitteado"
	private StackPane stackRetwitteado; // Value injected by FXMLLoader

	@FXML //  fx:id="stackRetwittear"
	private StackPane stackRetwittear; // Value injected by FXMLLoader

	@FXML //  fx:id="stackVerConve"
	private StackPane stackVerConve; // Value injected by FXMLLoader

	@FXML //  fx:id="stackYaFavorito"
	private StackPane stackYaFavorito; // Value injected by FXMLLoader

	@FXML //  fx:id="textoNuevoTweet"
	private TextArea textoNuevoTweet; // Value injected by FXMLLoader

	@FXML //  fx:id="timeAgo"
	private Label timeAgo; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetBox"
	private HBox tweetBox; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetFlow"
	private FlowPane tweetFlow; // Value injected by FXMLLoader

	@FXML //  fx:id="tweetsRespuesta"
	private VBox tweetsRespuesta; // Value injected by FXMLLoader

	@FXML //  fx:id="userImage"
	private ImageView userImage; // Value injected by FXMLLoader

	@FXML //  fx:id="username"
	private Hyperlink username; // Value injected by FXMLLoader

	@FXML //  fx:id="worldTweetContainer"
	private AnchorPane worldTweetContainer; // Value injected by FXMLLoader

	@FXML //  fx:id="geoActivado"
	private ImageView geoActivado; // Value injected by FXMLLoader

	@FXML //  fx:id="geoDesactivado"
	private ImageView geoDesactivado; // Value injected by FXMLLoader

	@FXML //  fx:id="placeActual"
	private Label placeActual; // Value injected by FXMLLoader

	private Status tweet;

	private boolean desplegado = false;
	private String currentImage = "";
	private User user; //Usuario del tweet
	private Place lugar; //Place asociado al tweet
	private Place lugar2;


	// Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
	// Handler for ImageView[fx:id="geoDesactivado"] onMouseClicked
	public void activarGeo(MouseEvent event) {

		/*El texto pasado a getPlace solo es util con el twitterReal*/
		lugar2 = (Place)getTwitter().geo().getPlace("Madrid, España", null);

		if (lugar2!=null){

			placeActual.setText(getTwitter().geo().getPlace(null,null).toString());
			geoActivado.setVisible(true);
			geoDesactivado.setVisible(false);
			if(super.getTwitter().getMyPlace()==-1){
				super.getTwitter().setMyPlace(Long.parseLong(lugar2.getId()));			
			}
		}
		else if (lugar2==null){
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

	// Handler for Label [fx:id="location"] onMouseClicked
	public void mostrarGeo (MouseEvent event){

		if (super.getTwitter().getMyPlace()!=(long)-1){
			ClientTools.showPlace(this.lugar2);
		}
	}

	// Handler for Label [fx:id="location"] onMouseClicked
	public void mostrarGeodeTweet (MouseEvent event){
		ClientTools.showPlace(this.lugar);
	}


	// Handler for Label[id="opcion"] onMouseClicked
	public void abrirTweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void anadirFavorito(MouseEvent event) {
		super.getTwitter().setFavorite((Status) tweet, true);
	}

	// Handler for Label[id="opcionRealizada"] onMouseClicked
	public void borrarFavorito(MouseEvent event) {
		super.getTwitter().setFavorite((Status) tweet, false);
	}

	// Handler for Label[id="opcionRealizada"] onMouseClicked
	public void borrarRetweet(MouseEvent event) {
		// handle the event here
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void borrarTweet(MouseEvent event) {
		try{
			super.getTwitter().destroyStatus(tweet.getId());
			this.worldTweetContainer.setVisible(false);
			ClientTools.removeLabelFromTimeUpdate(timeAgo, tweet.getCreatedAt());
			Object parent = super.getParentController();
			if(parent instanceof TimeLineController)
				((TimeLineController)parent).removeTweet(this);
			else if(parent instanceof MiCuentaController)
				((MiCuentaController)parent).removeTweet(this);
		}catch(TwitterException e){}
	}

	// Handler for TextArea[id="textoNuevoTweet"] onKeyPressed
	public void cambiaContador(KeyEvent event) {
		numeroDe.setText(""+(140-ClientTools.countCharacters(textoNuevoTweet.getText())));
	}

	// Handler for Label[id="cerrarNuevoTweet"] onMouseClicked
	public void cerrarNuevoTweet(MouseEvent event) {
		stackRespuesta.setVisible(false);
		globalContainer.getChildren().remove(stackRespuesta);
		//worldTweetContainer.setMinHeight(originalSize);
		//worldTweetContainer.setMaxHeight(originalSize);
		this.desplegado = false;

	}

	// Handler for Hyperlink[fx:id="username"] onAction
	// Handler for ImageView[fx:id="userImage"] onMouseClicked
	public void goToPerfilUsuario(Event event) {
		if(!this.tweet.getUser().getProtectedUser()){
			User destUser = getTwitter().users().getUser(this.screename.getText().substring(1));
			if(destUser != null){
				if (super.getParentController() instanceof TimeLineController)
					((WorldController)((TimeLineController)this.getParentController()).getParentController()).changeToOtherAccount(destUser);
				else if (super.getParentController() instanceof ConectaController)
					((WorldController)((ConectaController)this.getParentController()).getParentController()).changeToOtherAccount(destUser);
				else if (super.getParentController() instanceof MiCuentaController)
					((WorldController)((MiCuentaController)this.getParentController()).getParentController()).changeToOtherAccount(destUser);
			}
		}
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarFavoritosPor(MouseEvent event) {
		// handle the event here
	}

	// Handler for VBox[id="cajita"] onMouseClicked
	public void mostrarRetweeters(MouseEvent event) {
		// handle the event here
	}

	// Handler for Button[id="twittear"] onMouseClicked
	public void publicarTweet(MouseEvent event) {
		if(ClientTools.countCharacters(textoNuevoTweet.getText()) < 140){
			this.getTwitter().updateStatus(textoNuevoTweet.getText(), tweet.getId());
		}else{
			//TODO: avisar de que se va a cortar el mensaje??
		}
	}

	// Handler for Label[id="opcion"] onMouseClicked
	// Handler for VBox[id="cajita"] onMouseClicked
	public void responderTweet(MouseEvent event) {
		if(!this.desplegado){
			stackRespuesta.setVisible(true);
			globalContainer.getChildren().add(stackRespuesta);
			this.desplegado = true;
			this.textoNuevoTweet.requestFocus();

			nFavoritos.setText("?");
			nRetweets.setText(""+this.tweet.getRetweetCount());
			if (super.getTwitter().getMyPlace()!=(long)-1){
				this.activarGeo(event);
			}
			else
				desactivarGeo(event);
		}
	}

	// Handler for Label[id="opcion"] onMouseClicked
	public void retwittearTweet(MouseEvent event) {
		//TODO: comprobar si ya está retwitteado??
		super.getTwitter().retweet(tweet);
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert globalContainer != null : "fx:id=\"globalContainer\" was not injected: check your FXML file 'tweet.fxml'.";
		assert infoExtra != null : "fx:id=\"infoExtra\" was not injected: check your FXML file 'tweet.fxml'.";
		assert nFavoritos != null : "fx:id=\"nFavoritos\" was not injected: check your FXML file 'tweet.fxml'.";
		assert nRetweets != null : "fx:id=\"nRetweets\" was not injected: check your FXML file 'tweet.fxml'.";
		assert numeroDe != null : "fx:id=\"numeroDe\" was not injected: check your FXML file 'tweet.fxml'.";
		assert screename != null : "fx:id=\"screename\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackAbrir != null : "fx:id=\"stackAbrir\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackBorrar != null : "fx:id=\"stackBorrar\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackFavorito != null : "fx:id=\"stackFavorito\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackResponder != null : "fx:id=\"stackResponder\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackRespuesta != null : "fx:id=\"stackRespuesta\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackRetwitteado != null : "fx:id=\"stackRetwitteado\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackRetwittear != null : "fx:id=\"stackRetwittear\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackVerConve != null : "fx:id=\"stackVerConve\" was not injected: check your FXML file 'tweet.fxml'.";
		assert stackYaFavorito != null : "fx:id=\"stackYaFavorito\" was not injected: check your FXML file 'tweet.fxml'.";
		assert textoNuevoTweet != null : "fx:id=\"textoNuevoTweet\" was not injected: check your FXML file 'tweet.fxml'.";
		assert timeAgo != null : "fx:id=\"timeAgo\" was not injected: check your FXML file 'tweet.fxml'.";
		assert tweetBox != null : "fx:id=\"tweetBox\" was not injected: check your FXML file 'tweet.fxml'.";
		assert tweetFlow != null : "fx:id=\"tweetTextArea\" was not injected: check your FXML file 'tweet.fxml'.";
		assert tweetsRespuesta != null : "fx:id=\"tweetsRespuesta\" was not injected: check your FXML file 'tweet.fxml'.";
		assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'tweet.fxml'.";
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'tweet.fxml'.";
		assert worldTweetContainer != null : "fx:id=\"worldTweetContainer\" was not injected: check your FXML file 'tweet.fxml'.";


		// initialize your logic here: all @FXML variables will have been injected
		cerrarNuevoTweet(null);
		userImage.setPreserveRatio(false);
	}

	@Override
	public void postInitialize() {

		lugar=this.tweet.getPlace();
		if(lugar!=null)
			this.location.setText(lugar.toString());
		else
			this.location.setText("");
		this.user = this.tweet.getUser();

		tweetFlow.setVgap(0);
		tweetFlow.setHgap(4);
		String tweetText = tweet.getText();
		tweetText = tweetText.replaceAll("\\s+", " ");
		String[] words = tweetText.split(" ");
		for (int i = 0; i < words.length; i++) {
			final String word = words[i];
			Node wordNode;
			if (word.startsWith("@")) {
				try{
					System.out.println("STOY AQUI: "+word);
					final User destUser = getTwitter().users().getUser(word.substring(1));
					System.out.println("objeto creado: "+destUser.getName());
					wordNode =  HyperlinkBuilder.create()
							.text(word)
							.onAction(new EventHandler<ActionEvent>() {
								@Override public void handle(ActionEvent e) {


									if( (!destUser.getProtectedUser()) || (destUser.isFollowedByYou())){
										if(destUser != null){
											if (getParentController() instanceof TimeLineController){

												((WorldController)((TimeLineController)getParentController()).getParentController()).changeToOtherAccount(destUser);
											}
											else if (getParentController() instanceof ConectaController){

												((WorldController)((ConectaController)getParentController()).getParentController()).changeToOtherAccount(destUser);
											}

											else if (getParentController() instanceof MiCuentaController){

												((WorldController)((MiCuentaController)getParentController()).getParentController()).changeToOtherAccount(destUser);
											}

											else if (getParentController() instanceof BusquedaController){

												((WorldController)((BusquedaController)getParentController()).getParentController()).changeToOtherAccount(destUser);

											}
										}
									}
								}
							})
							.build();
					((Hyperlink)wordNode).setTextFill(Paint.valueOf("#00BFFF"));
				}catch(Exception e){
					wordNode = new Label(word);
				}

			}
			else if (word.startsWith("#") && word.length() > 1) {
				wordNode =  HyperlinkBuilder.create()
						.text(word)
						.onAction(new EventHandler<ActionEvent>() {
							@Override public void handle(ActionEvent e) {

								if (getParentController() instanceof TimeLineController){

									((WorldController)((TimeLineController)getParentController()).getParentController()).buscaHashtags(word);
								}
								else if (getParentController() instanceof ConectaController){

									((WorldController)((ConectaController)getParentController()).getParentController()).buscaHashtags(word);
								}

								else if (getParentController() instanceof MiCuentaController){

									((WorldController)((MiCuentaController)getParentController()).getParentController()).buscaHashtags(word);
								}

								else if (getParentController() instanceof BusquedaController){

									((WorldController)((BusquedaController)getParentController()).getParentController()).buscaHashtags(word);

								}
							}
						})
						.build();
				((Hyperlink)wordNode).setTextFill(Paint.valueOf("#00BFFF"));
			}
			else if (word.matches("^(http(s)?://)?([a-zA-Z0-9_]+(\\.[a-zA-Z0-9/_#]+)+)")){
				String p;

				if(word.matches("^(http://).*")){
					p=word.substring(7);
				}
				else if(word.matches("^(https://).*")){
					p=word.substring(8);
				}
				else{
					p= word;
				}
				if(p.length()>20){
					p=p.substring(0,20)+"...";
				}
				System.out.println(p);
				wordNode =  HyperlinkBuilder.create()
						.text(p)
						.onMouseClicked(new EventHandler<MouseEvent>() {
							@Override public void handle(MouseEvent me) {
								URL url;
								try {
									String wordAux;
									if(word.matches("^(http(s)?://).*")){
										wordAux=word;
									}
									else{
										wordAux="http://"+word;

									}
									url = new URL(wordAux);
								} 
								catch (MalformedURLException exception) {
									throw new RuntimeException(exception);
								}

								try {
									java.awt.Desktop.getDesktop().browse(url.toURI());
								} 
								catch (URISyntaxException exception) {
									throw new RuntimeException(exception);
								} 
								catch (IOException exception) {
									throw new RuntimeException(exception);
								}
							}
						})
						.build();
				((Hyperlink)wordNode).setTextFill(Paint.valueOf("#00BFFF"));
			}
			else {
				wordNode = new Label(word);
			}
			tweetFlow.getChildren().add(wordNode);
		}

		//Cargamos la información que el usuario puede modificar en cualquier momento
		loadUserDependantInfo();

		//Parse the time
		Date createdAt = this.tweet.getCreatedAt();
		Date now = new Date();
		int timedif = (int)((now.getTime() - createdAt.getTime())/1000);
		String timeago;
		if(timedif < 60)
			timeago = timedif+"s";
		else if(timedif < 3600)
			timeago = (timedif/60)+"m";
		else if(timedif < 86400)
			timeago = (timedif/3600)+"h";
		else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(createdAt);
			timeago = ""+cal.get(Calendar.DAY_OF_MONTH);
			switch(Calendar.MONTH){
			case Calendar.JANUARY: timeago += " Ene"; break;
			case Calendar.FEBRUARY: timeago += " Feb"; break;
			case Calendar.MARCH: timeago += " Mar"; break;
			case Calendar.APRIL: timeago += " Abr"; break;
			case Calendar.MAY: timeago += " Mayo"; break;
			case Calendar.JUNE: timeago += " Jun"; break;
			case Calendar.JULY: timeago += " Jul"; break;
			case Calendar.AUGUST: timeago += " Ago"; break;
			case Calendar.SEPTEMBER: timeago += " Sept"; break;
			case Calendar.OCTOBER: timeago += " Oct"; break;
			case Calendar.NOVEMBER: timeago += " Nov"; break;
			case Calendar.DECEMBER: timeago += " Dic"; break;
			}
		}

		timeAgo.setText(timeago);
		ClientTools.addLabelToTimeUpdate(timeAgo, createdAt);

		if(this.user.getId().equals(getTwitter().getSelf().getId())){ //Es propio
			stackBorrar.setVisible(true);
			stackRetwitteado.setVisible(false);
			stackRetwittear.setVisible(false);
		}else if(this.tweet.isRetweetedByMe(getTwitter().getSelf().getId())){
			stackRetwitteado.setVisible(true);
			stackBorrar.setVisible(false);
			stackRetwittear.setVisible(false);
		}else{
			stackRetwittear.setVisible(true);
			stackRetwitteado.setVisible(false);
			stackBorrar.setVisible(false);
		}

		if(this.tweet.isFavorite()){
			stackFavorito.setVisible(false);
			stackYaFavorito.setVisible(true);
		}else{
			stackFavorito.setVisible(true);
			stackYaFavorito.setVisible(false);
		}


	}

	private void loadUserDependantInfo(){
		screename.setText("@"+this.user.getScreenName());
		username.setText(this.user.getName());
		username.setUserData(this.user.getScreenName());

		String url = this.user.getProfileImageUrl().toString();
		if(!currentImage.equals(url)){	
			Image im = ClientTools.getImage(this.user.getProfileImageUrl().toString());
			if(im != null)
				userImage.setImage(im);
		}
	}

	protected void setTweet(Status tweet){
		this.tweet = tweet;
	}

	public Status getTweet() {
		return tweet;
	}

	@Override
	protected AnchorPane getContainer() {
		return worldTweetContainer;
	}

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		if(event.getSource().getId().equals(getTwitter().getSelf().getId())){
			if(event.getType().equals(TwitterEvent.Type.FAVORITE)){
				stackYaFavorito.setVisible(true);
				stackFavorito.setVisible(false);
			}else if(event.getType().equals(TwitterEvent.Type.UNFAVORITE)){
				stackYaFavorito.setVisible(false);
				stackFavorito.setVisible(true);
			}
		}
		if(event.getType().equals(TwitterEvent.Type.USER_UPDATE) && event.getSource().getId().equals(this.user.getId())){
			this.user = getTwitter().users().getUser(this.user.getId());
			loadUserDependantInfo();
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
		// TODO Auto-generated method stub
		return false;
	}

}
