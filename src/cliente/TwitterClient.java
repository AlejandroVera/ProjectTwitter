package cliente;

import interfacesComunes.Twitter;
import interfacesComunes.TwitterInit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TwitterClient extends Application {
	
	private static final String SERVER_URL = "rmi://localhost/Conectar";
	
	private Twitter twitter;
	private Cliente cliente;
	private Stage primaryStage;
	
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
			
			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					if(twitter != null)
						notifyLogout();
					System.exit(0);
				}
			});
			
			this.loadFXML("login.fxml");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    
    }
    
    protected boolean notifyLogin(String user, String pass, String server){
    	if(server.equals("Twitter real")) return false; //TODO: no soportado todavía
    	
    	try {
    		TwitterInit stub = (TwitterInit) Naming.lookup(SERVER_URL);
			this.cliente = new Cliente();
			this.twitter = stub.login(user, pass, cliente);
			if(this.twitter ==  null){
				ClientTools.showDialog("Login invalido.");
				return false;
			}			
			//lanzar la visión principal (pasandole al controlador el objeto Twitter)
			this.loadFXML("timeline.fxml");
			
			return true;
			
			
		} catch (NotBoundException | IOException e1) {
			e1.printStackTrace();
			ClientTools.showDialog("Se ha producido un error al conectar con el servidor.");
			return false;
		}
	}
    
    protected void notifyLogout(){
    	try {
    		TwitterInit stub = (TwitterInit) Naming.lookup(SERVER_URL);
    		stub.logout(this.twitter.getSelf().getId(), cliente);
    		this.twitter = null;
    		this.loadFXML("login.fxml");
    		
    	} catch (NotBoundException | IOException e1) {
			e1.printStackTrace();
			ClientTools.showDialog("Se ha producido un error al conectar con el servidor.");
		}
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
    
    private Controller loadFXML(String fxml) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		Parent root = (Parent) loader.load(getClass().getResource(fxml).openStream());
		
		//Obtenemos el objeto controlador
		Controller control = loader.getController();
		control.setClientListener(this);
		control.setTwitter(this.twitter);
		control.postInitialize();
		
		//Mostramos la nueva vista
		Scene scene = new Scene(root, 900, 600);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		
		return control;
    }
}
