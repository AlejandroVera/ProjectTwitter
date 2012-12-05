/**
 * Sample Skeleton for "login.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.TwitterInit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import winterwell.jtwitter.OAuthSignpostClient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LoginController extends Controller{

	String JKEY="MTRvDLFJqnQ5W4VjIGob0w";
	String JSECRET="QXVQNJg4cywAk7xvBuSRT9BMPzb6yKHtijAvU0GV2is";


	@FXML //  fx:id="HBoxServer"
	private HBox HBoxServer; // Value injected by FXMLLoader

	@FXML //  fx:id="avisoConexion"
	private VBox avisoConexion; // Aviso que sale preguntando si quieres añadir una nueva conexión

	@FXML //  fx:id="createAccountLabel"
	private Label createAccountLabel; // Value injected by FXMLLoader

	@FXML //  fx:id="gridContainer"
	private GridPane gridContainer; // Value injected by FXMLLoader

	@FXML //  fx:id="loginButton"
	private Button loginButton; // Value injected by FXMLLoader

	@FXML //  fx:id="loginGridPane"
	private GridPane loginGridPane; // Value injected by FXMLLoader

	@FXML //  fx:id="password"
	private PasswordField password; // Value injected by FXMLLoader

	@FXML //  fx:id="peticionPassword"
	private VBox peticionPassword; // Aviso que sale una vez ha obtenido el token para preguntar la pass con la que cifrar

	@FXML //  fx:id="repetirPassword"
	private PasswordField repetirPassword; // Value injected by FXMLLoader

	@FXML //  fx:id="serverSelector"
	private ChoiceBox<String> serverSelector; // Value injected by FXMLLoader

	@FXML //  fx:id="twitterRealPassword"
	private PasswordField twitterRealPassword; // Value injected by FXMLLoader

	@FXML //  fx:id="username"
	private TextField username; // Value injected by FXMLLoader

	@FXML //  fx:id="worldContainer"
	private AnchorPane worldContainer; // Value injected by FXMLLoader

	private HBox emailHBox;
	private TextField emailField;

	// Handler for Button[fx:id="loginButton"] onAction
	public void sendLoginForm(ActionEvent event) {
		if(serverSelector.getSelectionModel().getSelectedIndex() == 1){
			//Si no está el usuario en el archivo
			String us;
			if((us=userLine(username.getText()))==null){
				serverSelector.setValue("Nuestro twitter");
				avisoConexion.setVisible(true);	
			}
			//si está
			else{
				String[] aux=us.split(":");
				OAuthSignpostClient oauthClient = new OAuthSignpostClient(JKEY, JSECRET, aux[1], aux[2]);
				loginReal(oauthClient);
			}
		}
		else{
			login();
		}
	}

	// Handler for Label[fx:id="createAccountLabel"] onMouseClicked
	public void showcreateAccount(MouseEvent event) {

		//Añade el botón de registro
		Button regButton = new Button("Registrarse");
		regButton.setId("regButton");
		regButton.setMinHeight(loginButton.getMinHeight());
		regButton.setMaxHeight(loginButton.getMaxHeight());
		regButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				register();
			}
		});

		username.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER)
					register();
			}
		});
		password.setOnKeyPressed(username.getOnKeyPressed());
		regButton.setOnKeyPressed(username.getOnKeyPressed());

		//Crea el campo de texto para el email y lo añadimos al formulario donde antes estaba el selector de servidor
		emailHBox = new HBox();
		Label emailLabel = new Label("Email");
		emailLabel.setAlignment(Pos.CENTER_RIGHT);
		emailHBox.getChildren().add(emailLabel);
		emailHBox.setAlignment(Pos.CENTER_RIGHT);
		emailField = new TextField();
		emailField.setMaxWidth(password.getMaxWidth());
		emailField.setMinWidth(password.getMinWidth());
		emailField.setOnKeyPressed(username.getOnKeyPressed());

		gridContainer.getChildren().remove(HBoxServer);
		gridContainer.getChildren().remove(serverSelector);
		gridContainer.add(emailHBox, 0, 2);
		gridContainer.add(emailField, 1, 2);

		//Añade el texto de volver
		Label returnLabel = new Label("Volver atrás");
		returnLabel.setId("returnLabel");
		returnLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				restoreLoginFromRegistry();
			}

		});

		loginGridPane.getChildren().clear();
		loginGridPane.addRow(0, regButton, returnLabel);
		GridPane.setMargin(returnLabel, new Insets(0, 15, 0, 0));


	}

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert HBoxServer != null : "fx:id=\"HBoxServer\" was not injected: check your FXML file 'login.fxml'.";
		assert avisoConexion != null : "fx:id=\"avisoConexion\" was not injected: check your FXML file 'login.fxml'.";
		assert createAccountLabel != null : "fx:id=\"createAccountLabel\" was not injected: check your FXML file 'login.fxml'.";
		assert gridContainer != null : "fx:id=\"gridContainer\" was not injected: check your FXML file 'login.fxml'.";
		assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
		assert loginGridPane != null : "fx:id=\"loginGridPane\" was not injected: check your FXML file 'login.fxml'.";
		assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'login.fxml'.";
		assert peticionPassword != null : "fx:id=\"peticionPassword\" was not injected: check your FXML file 'login.fxml'.";
		assert repetirPassword != null : "fx:id=\"repetirPassword\" was not injected: check your FXML file 'login.fxml'.";
		assert serverSelector != null : "fx:id=\"serverSelector\" was not injected: check your FXML file 'login.fxml'.";
		assert twitterRealPassword != null : "fx:id=\"twitterRealPassword\" was not injected: check your FXML file 'login.fxml'.";
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'login.fxml'.";
		assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'login.fxml'.";



		// initialize your logic here: all @FXML variables will have been injected
		serverSelector.getSelectionModel().selectFirst();

		//Ocultamos los paneles auxiliares
		avisoConexion.setVisible(false);
		peticionPassword.setVisible(false);

		//Si se elige el servidor real de twitter, no permitimos el registro      
		serverSelector.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {                
				if(serverSelector.getSelectionModel().getSelectedIndex() == 1){
					createAccountLabel.setVisible(false);
				}else
					createAccountLabel.setVisible(true);
			}    
		});       

		loginButton.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					login();
				}
			}
		});
		username.setOnKeyPressed(loginButton.getOnKeyPressed());
		password.setOnKeyPressed(loginButton.getOnKeyPressed());
		serverSelector.setOnKeyPressed(loginButton.getOnKeyPressed());


	}



	/**
	 * Realiza las acciones necesarias para volver al login desde el registro
	 */
	private void restoreLoginFromRegistry(){

		//Restauramos la apariencia
		loginGridPane.getChildren().clear();
		loginGridPane.addRow(0, loginButton, createAccountLabel);
		gridContainer.getChildren().remove(emailHBox);
		gridContainer.getChildren().remove(emailField);
		gridContainer.add(HBoxServer, 0, 2);
		gridContainer.add(serverSelector, 1, 2);

		//Restauramos los eventos
		username.setOnKeyPressed(loginButton.getOnKeyPressed());
		password.setOnKeyPressed(loginButton.getOnKeyPressed());
	}

	private void register(){
		switch(this.getClientListener().notifyRegistry(username.getText(), password.getText(), emailField.getText())){
		case TwitterInit.REG_OK:
			ClientTools.showDialog("El registro se ha realizado correctamente");
			restoreLoginFromRegistry();
			break;	
		case TwitterInit.REG_WRONG_EMAIL:
			ClientTools.showDialog("Ese email ya está siendo usado.");
			break;
		case TwitterInit.REG_WRONG_USER:
			ClientTools.showDialog("Ya existe un usuario con ese nombre.");
			break;
		case TwitterInit.REG_WRONG_UNKNOWN:
		default:
			ClientTools.showDialog("Se ha producido un error desconocido");
			break;
		}
	}

	// Handler for Button[Button[id=null, styleClass=button]] onMouseClicked
	public void addNewConection(MouseEvent event) {
		avisoConexion.setVisible(false);
		/** no tengo el TOKEN, conectando por primera vez con twitter*/
		OAuthSignpostClient oauthClient = new OAuthSignpostClient(JKEY, 
				JSECRET, "oob");
		oauthClient.authorizeDesktop();
		String v = oauthClient.askUser("Please enter the verification PIN from Twitter");
		oauthClient.setAuthorizationCode(v);
		String[] ats = oauthClient.getAccessToken();
		//peticionPassword.setVisible(true);
		//TODO encriptar
		String cadena=username.getText()+":"+ats[0]+":"+ats[1];
		guardarToken(cadena);
		loginReal(oauthClient);

	}

	// Handler for Button[Button[id=null, styleClass=button]] onMouseClicked
	public void cancelAddNewConection(MouseEvent event) {
		avisoConexion.setVisible(false);
	}

	// Handler for Button[Button[id=null, styleClass=button]] onMouseClicked
	// Handler for PasswordField[fx:id="repetirPassword"] onKeyTyped
	// Handler for PasswordField[fx:id="twitterRealPassword"] onKeyTyped
	public void sendFormAddNewConection(InputEvent event) {

	}

	// Handler for PasswordField[fx:id="twitterRealPassword"] onKeyTyped
	public void sendFormAddNewConection(KeyEvent event) {
	}

	private void login(){
		String user = username.getText();
		String pass = password.getText();
		this.getClientListener().notifyLogin(user, pass, null);
	}

	private void loginReal(OAuthSignpostClient oauthClient ){
		this.getClientListener().notifyLogin( username.getText(), "", oauthClient);
	}


	@Override
	public void postInitialize() {
		//Vacio, no se hace nada
	}

	@Override
	protected AnchorPane getContainer() {
		return worldContainer;
	}

	//para buscar en el fichero twitterTokens.tw el usuario
	private String userLine(String s){
		String sol=null;
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {

			String homeUsuario = System.getProperty("user.home");
			archivo = new File(homeUsuario+"/twitterTokens.tw");
			if(!archivo.exists()){
				archivo.createNewFile();
			}
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea;
			while((linea=br.readLine())!=null){
				if(linea.contains(s)){
					sol=linea; //TODO arreglar esto
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
		return sol;
	}

	private void guardarToken(String s){
		File archivo = null;
		try {
			String homeUsuario = System.getProperty("user.home");
			archivo = new File(homeUsuario+"/twitterTokens.tw");
			if(!archivo.exists()){
				archivo.createNewFile();
			}
			FileWriter TextOut = new FileWriter(archivo, true);
			TextOut.write(s+"\n");
			TextOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
