/**
 * Sample Skeleton for "login.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class LoginController implements Initializable {

    @FXML //  fx:id="createAccountLabel"
    private Label createAccountLabel; // Value injected by FXMLLoader
    
    @FXML //  fx:id="gridContainer"
    private GridPane gridContainer; // Value injected by FXMLLoader
    
    @FXML //  fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML //  fx:id="password"
    private PasswordField password; // Value injected by FXMLLoader

    @FXML //  fx:id="serverSelector"
    private ChoiceBox<?> serverSelector; // Value injected by FXMLLoader

    @FXML //  fx:id="username"
    private TextField username; // Value injected by FXMLLoader

    @FXML //  fx:id="worldContainer"
    private AnchorPane worldContainer; // Value injected by FXMLLoader
    
    private TwitterClient loginListener;


    // Handler for Button[fx:id="loginButton"] onAction
    public void sendLoginForm(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();
        String server = (String) serverSelector.getSelectionModel().getSelectedItem();
        this.loginListener.notifyLogin(user, pass, server);
    }
    
    // Handler for Label[fx:id="createAccountLabel"] onMouseClicked
    public void showcreateAccount(MouseEvent event) {
    	AnchorPane loginButtonParent =  (AnchorPane) loginButton.getParent();
        loginButton.setVisible(false);
        Button regButton = new Button("Registrase");
        //loginButtonParent.get
        
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert createAccountLabel != null : "fx:id=\"createAccountLabel\" was not injected: check your FXML file 'login.fxml'.";
        assert gridContainer != null : "fx:id=\"gridContainer\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'login.fxml'.";
        assert serverSelector != null : "fx:id=\"serverSelector\" was not injected: check your FXML file 'login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'login.fxml'.";
        assert worldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'login.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        serverSelector.getSelectionModel().selectFirst();

    }
    
    public void setLoginListener(TwitterClient tc){
    	this.loginListener = tc;
    }

}
