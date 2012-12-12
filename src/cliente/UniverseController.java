/**
 * Sample Skeleton for "universe.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class UniverseController
    implements Initializable {

    @FXML //  fx:id="errorContainer"
    private AnchorPane errorContainer; // Value injected by FXMLLoader
	
    @FXML //  fx:id="errorPane"
    private TitledPane errorPane; // Value injected by FXMLLoader

    @FXML //  fx:id="errorText"
    private TextArea errorText; // Value injected by FXMLLoader
    
    @FXML //  fx:id="loadingContainer"
    private AnchorPane loadingContainer; // Value injected by FXMLLoader

    @FXML //  fx:id="universeContainer"
    private StackPane universeContainer; // Value injected by FXMLLoader

    @FXML //  fx:id="worldContainer"
    private AnchorPane universeWorldContainer; // Value injected by FXMLLoader Es del que cuelgan las páginas
    
    public BooleanProperty loading;


    // Handler for Button[Button[id=null, styleClass=button]] onAction
    public void closeError(ActionEvent event) {
    	errorContainer.setVisible(false);
    }
    
    public void showError(String errorMessage, String errorHeader){
    	errorText.setText(errorMessage);
    	errorPane.setText(errorHeader);
    	errorContainer.setVisible(true);
    }
    
    public AnchorPane getWorldContainer(){
    	return universeWorldContainer;
    }
    
    public void setWorldContainer(AnchorPane node){
    	universeWorldContainer.getChildren().clear();
    	universeWorldContainer.getChildren().add(node);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	assert errorContainer != null : "fx:id=\"errorContainer\" was not injected: check your FXML file 'universe.fxml'.";
        assert errorPane != null : "fx:id=\"errorPane\" was not injected: check your FXML file 'universe.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'universe.fxml'.";
        assert loadingContainer != null : "fx:id=\"loadingContainer\" was not injected: check your FXML file 'universe.fxml'.";
        assert universeContainer != null : "fx:id=\"universeContainer\" was not injected: check your FXML file 'universe.fxml'.";
        assert universeWorldContainer != null : "fx:id=\"worldContainer\" was not injected: check your FXML file 'universe.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        errorContainer.setVisible(false);
        loading = new SimpleBooleanProperty(false);
        loadingContainer.visibleProperty().bind(this.loading);

    }

}
