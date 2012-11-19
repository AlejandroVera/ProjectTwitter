package cliente;

import interfacesComunes.Twitter;
import interfacesComunes.TwitterInit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TwitterClient extends Application {
	
	private TwitterInit stub;
	private Cliente cliente;
	private Stage primaryStage;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
		try {
			
			this.primaryStage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("login.fxml"));
			Parent root = (Parent) loader.load(getClass().getResource("login.fxml").openStream());
			
			//Obtenemos el objeto controlador
			LoginController logControl = loader.getController();
			logControl.setLoginListener(this);
			
			Scene scene = new Scene(root, 900, 600);
			this.primaryStage.getIcons().addAll(
					new Image(getClass().getResource("Imagenes/Twitter-icon-16.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-24.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-32.png").openStream()), 
					new Image(getClass().getResource("Imagenes/Twitter-icon-48.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-128.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-256.png").openStream())
				);
			
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Cliente multitwitter");
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    
    }
    
    protected boolean notifyLogin(String user, String pass, String server){
    	if(server.equals("Twitter real")) return false; //TODO: no soportado todavía
    	
    	String rmiUrl = "rmi://localhost/Conectar";
    	
    	try {
			this.stub = (TwitterInit) Naming.lookup(rmiUrl);
			this.cliente = new Cliente();
			Twitter twitter = stub.login(user, pass, cliente);
			if(twitter ==  null){
				ClientTools.showDialog("Login invalido.");
				return false;
			}else
				System.out.println((twitter.isValidLogin() ? "Logueado" : "No logueado"));
			
			//TODO: lanzar la visión principal (pasandole al controlador el objeto Twitter)
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("timeline.fxml"));
			Parent root = (Parent) loader.load(getClass().getResource("timeline.fxml").openStream());
			
			//Obtenemos el objeto controlador
			//TimelineController timeControl = loader.getController();
			//timeControl.setLoginListener(this);
			
			//Mostramos la nueva vista
			Scene scene = new Scene(root, 900, 600);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
			return true;
			
			
		} catch (NotBoundException | IOException e1) {
			e1.printStackTrace();
			ClientTools.showDialog("Se ha producido un error al conectar con el servidor.");
			return false;
		}
	}
    
    protected int notifyRegistry(String user, String pass, String email){
    	String rmiUrl = "rmi://localhost/Conectar";
    	
    	try {
			this.stub = (TwitterInit) Naming.lookup(rmiUrl);
			return stub.register(user, pass, email);
			
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			return TwitterInit.REG_WRONG_UNKNOWN;
		}
    }
}
