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
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.winterwell.jgeoplanet.IPlace;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import interfacesComunes.TwitterInit;

public class ClientTools {
	
	static class TimingEntry{
		Date createdAt; //Tiempo origen
		Label label; //Etique que actualizar con el parseado del tiempo
		
		public TimingEntry(Date createdAt, Label label) {
			this.createdAt = createdAt;
			this.label = label;
		}
		
		public boolean equals(Object o){
			if(! (o instanceof TimingEntry) )
				return false;
			
			TimingEntry test = (TimingEntry) o;
			
			return createdAt.equals(test.createdAt) && label.equals(test.label);
			
		}
	}
	
	private static UniverseController universeController;
	private static TwitterInit twi;
	private static final String SERVER_URL = "rmi://localhost/Conectar";
	private static final String FALSE_IMAGESERVER_URL = "http://imagestoge.twt/";
	private static final String DEFAULT_PROFILE_IMAGE = ClientTools.class.getResource("."+File.separator+"Imagenes"+File.separator+"default_profile_6_bigger.png").getPath();
	private static final long TIMER_FREQUENCY = 15000; //15s
	private static Timer timer;
	private static List<TimingEntry> timingList;
	private static boolean timerRunning = false;
	
	
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
		if(url.startsWith(FALSE_IMAGESERVER_URL)){
			try {
				if(twi == null)
					twi = (TwitterInit) Naming.lookup(SERVER_URL);
				
				//Obtenemos los bytes de la imagen
				byte[] imagen = twi.getImage(url.substring(FALSE_IMAGESERVER_URL.length()));
				if(imagen != null){
					ByteArrayInputStream imageStream = new ByteArrayInputStream(imagen);
					return new Image(imageStream);
				}
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				//e.printStackTrace(); //No se ha encontrado la imagen, no es necesario mostrar el trace ya	
			}
		}else if(!url.isEmpty()){
			try {
				return new Image(new URL(url).openStream());
			} catch (IOException e) {
				//e.printStackTrace(); //No se ha encontrado la imagen, no es necesario mostrar el trace ya			
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
				
			ie.close();
			return FALSE_IMAGESERVER_URL+twi.saveImage(array.toByteArray());

		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	protected static void addLabelToTimeUpdate(Label label, Date createdAt){
		if(!timerRunning){
			timerRunning = true;
			timer = new Timer(true);
			timingList = new LinkedList<TimingEntry>();
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							Date now = new Date();
							List<TimingEntry> toRemove = new LinkedList<TimingEntry>();
							Calendar cal = Calendar.getInstance();
							
							for (TimingEntry par : timingList){
								if(!par.label.isVisible())
									continue; //No me mateis, que otra indentacion ya era demasiado!

								Date createdAt = par.createdAt;
								int timedif = (int)((now.getTime() - createdAt.getTime())/1000);
								String timeago;
								if(timedif < 60)
									timeago = timedif+"s";
								else if(timedif < 3600)
									timeago = (timedif/60)+"m";
								else if(timedif < 86400)
									timeago = (timedif/3600)+"h";
								else{
									toRemove.add(par); //Ya no tiene que ser actualizado
									cal.setTime(createdAt);
									timeago = ""+cal.get(Calendar.DAY_OF_MONTH);
									switch(Calendar.MONTH){
										case Calendar.JANUARY: timeago += " Ene"; break;
										case Calendar.FEBRUARY: timeago += " Feb"; break;
										case Calendar.MARCH: timeago += " Mar"; break;
										case Calendar.APRIL: timeago += " Abr"; break;
										case Calendar.MAY: timeago += " Mayo"; break;
										case Calendar.JUNE: timeago += " Jun"; break;
										case Calendar.JULY: timeago += " Jul"; break;
										case Calendar.AUGUST: timeago += " Ago"; break;
										case Calendar.SEPTEMBER: timeago += " Sept"; break;
										case Calendar.OCTOBER: timeago += " Oct"; break;
										case Calendar.NOVEMBER: timeago += " Nov"; break;
										case Calendar.DECEMBER: timeago += " Dic"; break;
									}
								}
								
								//Actualizamos el texto
								par.label.setText(timeago);
							}
							
							//Sacamos de la lista de actualización a los marcados
							for(TimingEntry par : toRemove){
								timingList.remove(par);
							}
							
						}
					});
					
					
				}
			}, 5000, ClientTools.TIMER_FREQUENCY);
		}
		
		timingList.add(new TimingEntry(createdAt, label));
	}
	
	protected static void removeLabelFromTimeUpdate(Label label, Date createdAt){
		timingList.remove(new TimingEntry(createdAt, label));
	}
	
	protected static void stopTimeUpdate(){
		if(timer != null)
			timer.cancel();
		timerRunning = false;
	}
	
	protected static void setLoading(boolean load){
		universeController.loading.set(load);
	}
	
	protected static void showPlace(IPlace place){
		universeController.showPlace(place);
	}
	
	
}
