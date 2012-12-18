
package cliente;

import interfacesComunes.AStream;
import interfacesComunes.Message;
import interfacesComunes.Twitter.ITweet;
import interfacesComunes.TwitterEvent;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class MensajesController extends Controller implements AStream.IListen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5651504061524623882L;

	@FXML //  fx:id="bandejaEntrada"
    private VBox bandejaEntrada; // Value injected by FXMLLoader

    @FXML //  fx:id="bandejaSalida"
    private VBox bandejaSalida; // Value injected by FXMLLoader

    @FXML //  fx:id="destinatario"
    private TextField destinatario; // Value injected by FXMLLoader

    @FXML //  fx:id="imagenFondo"
    private AnchorPane imagenFondo; // Value injected by FXMLLoader

    @FXML //  fx:id="texto"
    private TextArea texto; // Value injected by FXMLLoader
    
    @FXML //  fx:id="menuMensaje"
    private TabPane menuMensaje; // Value injected by FXMLLoader
    
    @FXML //  fx:id="redactar"
    private Tab redactar; // Value injected by FXMLLoader
    
    // Handler for Label[id="numeroDe"] onMouseClicked
    public void cerrarMenu(MouseEvent event) {
    	//Limpiamos los campos
    	this.texto.setText(null);
    	this.destinatario.setText(null);
    	//Cerramos la ventana
    	this.hideWindow();
    }

    // Handler for Button[id="twittear"] onMouseClicked
    public void enviarMensaje(MouseEvent event) {
    	String destino=destinatario.getText();
    	destino=destino.substring(1, destino.length());
    	try{
    		super.getTwitter().sendMessage(destino, texto.getText());
    		destinatario.setText(null);
    		texto.setText(null);
    		ClientTools.showDialog("Mensaje enviado correctamente");
    	} catch (Exception e){
    		ClientTools.showDialog("Error al enviar el mensaje: Este usuario no te sigue");
    	}
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert bandejaEntrada != null : "fx:id=\"bandejaEntrada\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert bandejaSalida != null : "fx:id=\"bandejaSalida\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert destinatario != null : "fx:id=\"destinatario\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert imagenFondo != null : "fx:id=\"imagenFondo\" was not injected: check your FXML file 'mensajes.fxml'.";
        assert texto != null : "fx:id=\"texto\" was not injected: check your FXML file 'mensajes.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

	@Override
	public boolean processEvent(TwitterEvent event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processSystemEvent(Object[] obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processTweet(ITweet mensaje) throws RemoteException {
		if (mensaje instanceof Message){
			mensaje=(Message) mensaje;
			System.out.println("Llega a mensajesController");
			if (((Message) mensaje).getSender().getId().equals(super.getTwitter().getSelf().getId()))
				
				addMessage(bandejaSalida, (Message) mensaje, true);
			
			else if (((Message) mensaje).getRecipient().getId().equals(super.getTwitter().getSelf().getId()))
				addMessage(bandejaEntrada, (Message) mensaje, false);
		}
		return false;
	}

	@Override
	public void postInitialize() {
		this.destinatario.setText("@");
		Iterator<Message> entrada =super.getTwitter().getDirectMessages().iterator();
		bandejaEntrada.getChildren().clear();
		while(entrada.hasNext()){
			this.addMessage(bandejaEntrada, entrada.next(), false);
		}
		
		Iterator<Message> salida =super.getTwitter().getDirectMessagesSent().iterator();
		bandejaSalida.getChildren().clear();
		while(salida.hasNext()){
			this.addMessage(bandejaSalida, salida.next(), true);
		}
	}
	
	public void responderMensaje(String destino){
		SingleSelectionModel<Tab> selectionModel=menuMensaje.getSelectionModel();
        selectionModel.select(redactar);
        destinatario.setText(destino);		
	}
	
	
	@Override
	protected AnchorPane getContainer() {
		return imagenFondo;
	}
	
	/**
	 * Añade un mensaje al final de la lista.
	 * @param contendor VBox a la que añadir el tweet.
	 * @param mensaje Mensaje a añadir.
	 */
	private void addMessage(VBox contendor, ITweet message, boolean deSalida){
		addMessage(contendor, message, deSalida, true);
	}
	
	/**
	 * Añade un mensaje.
	 * @param contendor VBox a la que añadir el mensaje.
	 * @param mensaje Mensaje a añadir.
	 * @param onTop True si el mensaje se tiene que añadir al principio de la lista.
	 */
	private void addMessage(VBox contendor,ITweet message,boolean deSalida, boolean onTop){
		try {
			FXMLMensajeAutoLoader messageUI = new FXMLMensajeAutoLoader(getTwitter(), (Message) message, deSalida);
			messageUI.getController().setParentController(this);
			if(!onTop)
				contendor.getChildren().add(messageUI.getRoot());
			else{
				LinkedList<Node> list = new LinkedList<Node>(contendor.getChildren());
				list.addFirst(messageUI.getRoot());
				contendor.getChildren().clear();
				contendor.getChildren().addAll(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void showWindow(){
		imagenFondo.setVisible(true);
	}
	
	protected void hideWindow(){
		imagenFondo.setVisible(false);
	}
}
