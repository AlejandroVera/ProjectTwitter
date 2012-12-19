package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterInit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.TwitterStream;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TwitterClient extends Application {

	private static final String SERVER_URL = "rmi://localhost/Conectar";
	private static TwitterStream twitterStreamS;
	
	private Twitter twitter;
	private ClientCallbackListener cliente;
	private Stage primaryStage;
	private UniverseController universeController;
	private TwitterStream twitterStream;
	private static Twitter tw;
	private static boolean twitterReal;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Cliente multitwitter");
			this.primaryStage.getIcons().addAll(
					new Image(getClass().getResource("Imagenes/Twitter-icon-16.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-24.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-32.png").openStream()), 
					new Image(getClass().getResource("Imagenes/Twitter-icon-48.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-128.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-256.png").openStream())
					);
			
			//Cargamos las fuentes a utilizar
			Font.loadFont(getClass().getResource("fuentes/nanum/NanumGothic.ttf").openStream(), -1);
			Font.loadFont(getClass().getResource("fuentes/nanum/NanumGothicBold.ttf").openStream(), -1);

			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					if(twitter != null){
						notifyLogout(true);
					}
					System.exit(0);
				}
			});
 
			FXMLLoader loader = new FXMLLoader();
			URL resource = getClass().getResource("universe.fxml");
			loader.setLocation(resource);
			Parent root = (Parent) loader.load(resource.openStream());

			//Obtenemos el objeto controlador
			this.universeController = loader.getController();
			ClientTools.setUniverseController(this.universeController);

			//Mostramos la nueva vista
			Scene scene = new Scene(root, this.primaryStage.getWidth(), this.primaryStage.getHeight());
			this.primaryStage.setScene(scene);
			this.primaryStage.show();		

			this.loadFXMLAndShow("login.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	//argucia 
	public static List<Long> amigosDelLogueado(){
		List<Long> l= tw.users().getFriendIDs();
		l.add(tw.getSelf().getId());
		return l;
	}

	protected boolean notifyLogin(String user, String pass,OAuthSignpostClient oauthClient){
		Controller control;
		try{
			ClientTools.setLoading(true);
			if(oauthClient!=null) {
				twitterReal=true;
				this.twitter = new winterwell.jtwitter.TwitterImpl(user, oauthClient);
				TwitterClient.tw=this.twitter;//argucia
				try {
					this.twitterStream = new TwitterStream(twitter);
					twitterStreamS = this.twitterStream ;
					List<Long> l= twitter.users().getFriendIDs();
					l.add(twitter.getSelf().getId());
					twitterStream.setFollowUsers(l);
					this.cliente = new ClientCallbackListener();
					twitterStream.addListener(this.cliente);
					
					twitterStream.connect();
					twitterStream.popTweets();
					control = this.loadFXMLAndShow("world.fxml");
					this.cliente.setListener((AStream.IListen) control);
	
					
				} 
				catch (Exception e1) {
					e1.printStackTrace();
					ClientTools.showDialog("Se ha producido un error al conectar con el servidor.\n"+e1.getMessage());
				}
				return true;
			}
			else{
				try {
					twitterReal=false;
					TwitterInit stub = (TwitterInit) Naming.lookup(SERVER_URL);
					this.cliente = new ClientCallbackListener();
					this.twitter = stub.login(user, pass, cliente);
					if(this.twitter ==  null){
						ClientTools.showDialog("Login invalido.");
						return false;
					}	
					TwitterClient.tw=this.twitter;//argucia
					//lanzar la visión principal (pasandole al controlador el objeto Twitter)
					control = this.loadFXMLAndShow("world.fxml");
	
					//Ponemos al controlador a la escucha de los eventos de twitter
					this.cliente.setListener((AStream.IListen) control);
	
					return true;
	
	
				} catch (NotBoundException | IOException e1) {
					e1.printStackTrace();
					ClientTools.showDialog("Se ha producido un error al conectar con el servidor.");
					return false;
				}
			}
		}finally{
			ClientTools.setLoading(false);
		}
	}

	public static TwitterStream getStream(){
		return twitterStreamS;
	}
	public static boolean isReal(){
		return twitterReal;
	}
	
	/**
	 * Desloguea
	 * @param force Este parametro está a true si el logout es forzoso (cerrar ventana)
	 */
	private void notifyLogout(boolean force){
		try {
			if(this.twitterStream!=null){
				this.twitterStream.close();
				this.twitterStream = null;
			}else{
				TwitterInit stub = (TwitterInit) Naming.lookup(SERVER_URL);
				stub.logout(this.twitter.getSelf().getId(), cliente);
			}
			
			ClientTools.stopTimeUpdate();
			
			this.twitter = null;
			this.cliente.setListener(null);
			this.cliente = null;
			tw = null;
			
			if(!force){
				loadFXMLAndShow("login.fxml");
			}
			
		} catch (NotBoundException | IOException e1) {
			e1.printStackTrace();
			ClientTools.showDialog("Se ha producido un error al conectar con el servidor.");
		}
	}
	
	protected void notifyLogout(){
		notifyLogout(false);
	}

	protected int notifyRegistry(String user, String pass, String email){

		try {
			TwitterInit stub = (TwitterInit) Naming.lookup(SERVER_URL);
			return stub.register(user, pass, email);

		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			return TwitterInit.REG_WRONG_UNKNOWN;
		}
	}

	private Controller loadFXMLAndShow(String fxml) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		Parent root = (Parent) loader.load(getClass().getResource(fxml).openStream());

		//Obtenemos el objeto controlador
		Controller control = loader.getController();
		control.setClientListener(this);
		control.setTwitter(this.twitter);
		control.setParentController(this.universeController);
		control.postInitialize();

		//Mostramos la nueva vista
		this.universeController.setWorldContainer((AnchorPane)root);

		return control;
	}
	
	protected static Long getUserId(){
		return tw.getSelf().getId();
	}
}
