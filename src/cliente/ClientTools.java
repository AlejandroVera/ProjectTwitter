package cliente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import interfacesComunes.TwitterInit;

public class ClientTools {
	
	private static UniverseController universeController;
	private static TwitterInit twi;
	private static final String SERVER_URL = "rmi://localhost/Conectar";
	private static final String FALSE_IMAGESERVER_URL = "http://imagestoge.twt/";
	private static final String DEFAULT_PROFILE_IMAGE = ClientTools.class.getResource("."+File.separator+"Imagenes"+File.separator+"default_profile_6_bigger.png").getPath();

	protected static void showDialog(String text){
		showDialog(text, "Error");
    }
	
	protected static void showDialog(String text, String topText){
		if(universeController != null){
			universeController.showError(text, topText);
		}
    }
	
	protected static UniverseController getUniverseController(){
		return universeController;
	}
	
	protected static void setUniverseController(UniverseController controller){
		universeController = controller;
	}
	
	/**
	 * Cuenta el número de caracteres de un tweet teniendo en cuenta las URLs.
	 * @param statusText
	 * @return Número de caracteres del tweet.
	 */
	protected static int countCharacters(String statusText) {
		final String regex = "((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+";
		//Devolvemos la longitud sustituyendo las URLs con 20 caracteres.
		return statusText.replaceAll(regex, "********************").length();
	}
	
	/**
	 * Obtiene una imagen del servidor.
	 * @param url URI ficticia de la imagen.
	 * @return Imagen solicitada.
	 */
	protected static Image getImage(String url) {
		System.out.println(url);
		if(url.startsWith(FALSE_IMAGESERVER_URL)){
			try {
				if(twi == null)
					twi = (TwitterInit) Naming.lookup(SERVER_URL);
				
				//Obtenemos los bytes de la imagen
				byte[] imagen = twi.getImage(url.substring(FALSE_IMAGESERVER_URL.length()));
				ByteArrayInputStream imageStream = new ByteArrayInputStream(imagen);
				
				return new Image(imageStream);
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		}else if(!url.isEmpty()){
			try {
				return new Image(new URL(url).openStream());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(DEFAULT_PROFILE_IMAGE);				
			}
		}
		
		try {
			return new Image(new FileInputStream(DEFAULT_PROFILE_IMAGE));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Guarda una imagen en el servidor.
	 * @param img Imagen a guardar.
	 * @return URL ficticia asociada a la imagen.
	 */
	protected static String saveImage(File img) {
		try {
			if(twi == null)
				twi = (TwitterInit) Naming.lookup(SERVER_URL);
			ByteArrayOutputStream array = new ByteArrayOutputStream();
			InputStream ie = new FileInputStream(img);
			
			byte[] buf = new byte[1024];
			int leidos;
			
			while((leidos = ie.read(buf)) != -1)
				array.write(buf, 0, leidos);
				
			return FALSE_IMAGESERVER_URL+twi.saveImage(array.toByteArray());

		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	
}
