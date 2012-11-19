/**
 * Sample Skeleton for "login.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import interfacesComunes.TwitterInit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML //  fx:id="createAccountLabel"
    private Label createAccountLabel; // Value injected by FXMLLoader
    
    @FXML //  fx:id="gridContainer"
    private GridPane gridContainer; // Value injected by FXMLLoader
    
    @FXML //  fx:id="HBoxServer"
    private HBox HBoxServer; // Value injected by FXMLLoader
    
    @FXML //  fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML //  fx:id="loginGridPane"
    private GridPane loginGridPane; // Value injected by FXMLLoader

    @FXML //  fx:id="password"
    private PasswordField password; // Value injected by FXMLLoader

    @FXML //  fx:id="serverSelector"
    private ChoiceBox<String> serverSelector; // Value injected by FXMLLoader

    @FXML //  fx:id="username"
    private TextField username; // Value injected by FXMLLoader

    @FXML //  fx:id="worldContainer"
    private AnchorPane worldContainer; // Value injected by FXMLLoader
    
    private TwitterClient loginListener;
    private HBox emailHBox;
    private TextField emailField;
    private int serverIndex1;
    private int serverIndex2;


    // Handler for Button[fx:id="loginButton"] onAction
    public void sendLoginForm(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();
        String server = (String) serverSelector.getSelectionModel().getSelectedItem();
        this.loginListener.notifyLogin(user, pass, server);
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
            	switch(loginListener.notifyRegistry(username.getText(), password.getText(), emailField.getText())){
	            	case TwitterInit.REG_OK:
	        			showDialog("El registro se ha realizado correctamente");
	        			restoreLoginFromRegistry();
	        			break;	
	            	case TwitterInit.REG_WRONG_EMAIL:
            			showDialog("Ese email ya está siendo usado.");
            			break;
            		case TwitterInit.REG_WRONG_USER:
            			showDialog("Ya existe un usuario con ese nombre.");
            			break;
            		case TwitterInit.REG_WRONG_UNKNOWN:
            		default:
            			showDialog("Se ha producido un error desconocido");
            			break;
            		
            	}
            }
   
        });
        
        //Crea el campo de texto para el email y lo añadimos al formulario donde antes estaba el selector de servidor
    	emailHBox = new HBox();
        Label emailLabel = new Label("Email");
        emailLabel.setAlignment(Pos.CENTER_RIGHT);
        emailHBox.getChildren().add(emailLabel);
        emailHBox.setAlignment(Pos.CENTER_RIGHT);
        emailField = new TextField();
        emailField.setMaxWidth(password.getMaxWidth());
        emailField.setMinWidth(password.getMinWidth());

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
        assert createAccountLabel != null : "fx:id=\"createAccountLabel\" was not injected: check your FXML file 'login.fxml'.";
        assert gridContainer != null : "fx:id=\"gridContainer\" was not injected: check your FXML file 'login.fxml'.";
        assert HBoxServer != null : "fx:id=\"HBoxServer\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert loginGridPane != null : "fx:id=\"loginGridPane\" was not injected: check your FXML file 'login.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'login.fxml'.";
        assert serverSelector != null : "fx:id=\"serverSelector\" was not injected: check your FXML file 'login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'login.fxml'.";
        assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'login.fxml'.";
        

        // initialize your logic here: all @FXML variables will have been injected
        serverSelector.getSelectionModel().selectFirst();
        
        //Si se elige el servidor real de twitter, no permitimos el registro      
        serverSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {                
            	if(serverSelector.getSelectionModel().getSelectedIndex() == 1)
        			createAccountLabel.setVisible(false);
        		else
        			createAccountLabel.setVisible(true);
            }    
        });       
       serverIndex1 = gridContainer.getChildren().indexOf(HBoxServer);
       serverIndex2 = gridContainer.getChildren().indexOf(serverSelector);
       System.out.println(serverIndex1+" "+serverIndex2);

    }
    
    public void setLoginListener(TwitterClient tc){
    	this.loginListener = tc;
    }
    
    /**
     * Realiza las acciones necesarias para volver al login desde el registro
     */
    private void restoreLoginFromRegistry(){
    	loginGridPane.getChildren().clear();
    	loginGridPane.addRow(0, loginButton, createAccountLabel);
    	gridContainer.getChildren().remove(emailHBox);
        gridContainer.getChildren().remove(emailField);
        gridContainer.add(HBoxServer, 0, 2);
        gridContainer.add(serverSelector, 1, 2);
    }
    
    private void showDialog(String text){
		final Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Button boton = new Button("Ok");
		boton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e) {
            	dialogStage.close();
            }
        });
		dialogStage.setScene(new Scene(VBoxBuilder.create().
				children(new Text(text), boton).
				alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.show();
    }

}
