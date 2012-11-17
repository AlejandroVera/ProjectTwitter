package cliente;

import interfacesComunes.Status;
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
    	
    	
		/*try {
			stub = (TwitterInit) Naming.lookup(rmiUrl);
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			return;
		}*/
		//this.twitter = stub.login(user, pass, this);
    	
    	/*Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("login.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}*/

		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("login.fxml"));
			Parent root = (Parent) loader.load(getClass().getResource("login.fxml").openStream());
			
			//Obtenemos el objeto controlador
			LoginController logControl = loader.getController();
			logControl.setLoginListener(this);
			
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    
    }
    
    public void notifyLogin(String user, String pass, String server){
    	if(server.equals("Twitter real")) return;
    	
    	String rmiUrl = "rmi://localhost/Conectar";
    	
    	try {
			stub = (TwitterInit) Naming.lookup(rmiUrl);
			this.cliente = new Cliente();
			Twitter twitter = stub.login(user, pass, cliente);
			if(twitter ==  null){
				System.out.println("Login invalido");
				return;
			}else
				System.out.println((twitter.isValidLogin() ? "Logueado" : "No logueado"));
			
			Status tweet = twitter.updateStatus("Esto es la primera prueba!!");
			if(tweet == null){
				System.out.println("No se ha creado el tweet");
				return;
			}else
				System.out.println("El tweet se ha enviado y dice: \""+ tweet.getText()+"\".");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			return;
		}
	//this.twitter = stub.login(user, pass, this);
	}
}
