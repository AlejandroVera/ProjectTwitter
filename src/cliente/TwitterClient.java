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
import javafx.stage.Stage;

public class TwitterClient extends Application {
	
	private TwitterInit stub;
	private Cliente cliente;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("login.fxml"));
			Parent root = (Parent) loader.load(getClass().getResource("login.fxml").openStream());
			
			//Obtenemos el objeto controlador
			LoginController logControl = loader.getController();
			logControl.setLoginListener(this);
			
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cliente multitwitter");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    
    }
    
    protected void notifyLogin(String user, String pass, String server){
    	if(server.equals("Twitter real")) return; //TODO: no soportado todavía
    	
    	String rmiUrl = "rmi://localhost/Conectar";
    	
    	try {
			this.stub = (TwitterInit) Naming.lookup(rmiUrl);
			this.cliente = new Cliente();
			Twitter twitter = stub.login(user, pass, cliente);
			if(twitter ==  null){
				System.out.println("Login invalido");
				return;
			}else
				System.out.println((twitter.isValidLogin() ? "Logueado" : "No logueado"));
			
			//TODO: lanzar la visión principal (pasandole al controlador el objeto Twitter)
			
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			return;
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
