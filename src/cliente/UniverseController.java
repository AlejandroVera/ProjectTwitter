/**
 * Sample Skeleton for "universe.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package cliente;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import com.winterwell.jgeoplanet.IPlace;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class UniverseController
    implements Initializable {
	
	@FXML //  fx:id="anchorImage"
    private AnchorPane anchorImage; // Value injected by FXMLLoader
	
	@FXML //  fx:id="imagen"
	private ImageView imagen; // Value injected by FXMLLoader

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
    
    
    
    // Handler for Label[id="X"] onMouseClicked
    public void cerrarImagen(MouseEvent event) {
    	this.imagen.setImage(null);
    	this.anchorImage.setVisible(false);
    }
    
	/**
	 * Dado un Place obtiene y muestra una imagen con la API de imágenes estáticas de Google
	 * Maps. Para ello obtiene el centro en coordenadas del place y obtiene la imagen respecto
	 * a este punto.
	 * @param lugar
	 */
    public void showPlace(IPlace lugar) {
		Double latitude= lugar.getCentroid().getLatitude();
		Double longitude= lugar.getCentroid().getLongitude(); 
		String coord=new String(latitude.toString()+","+longitude.toString());

		URL url;
		try {
			url = new URL("http://maps.google.com/maps/api/staticmap?center="+coord+
					"&size=660x500&zoom=14&maptype=hybrid&markers=color:red|"+coord+"&sensor=false");

			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			Image image= new Image(in);
			this.imagen.setImage(image);
	    	this.anchorImage.setVisible(true);
		} catch (Exception e) {}
		
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

        this.anchorImage.setVisible(false);

        loading = new SimpleBooleanProperty(false);
        loadingContainer.visibleProperty().bind(this.loading);


    }



}
