package interfazIPO;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class IPOLauncher extends Application {

	private Stage primaryStage;	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("IPO - Domótica para personas dependientes");
			/*this.primaryStage.getIcons().addAll(
					new Image(getClass().getResource("Imagenes/Twitter-icon-16.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-24.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-32.png").openStream()), 
					new Image(getClass().getResource("Imagenes/Twitter-icon-48.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-128.png").openStream()),
					new Image(getClass().getResource("Imagenes/Twitter-icon-256.png").openStream())
					);
			*/
			//Cargamos las fuentes a utilizar
			Font.loadFont(getClass().getResource("fuentes/SEGOEUI.ttf").openStream(), -1);
			Font.loadFont(getClass().getResource("fuentes/SEGOEUIB.ttf").openStream(), -1);

			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});

			FXMLLoader loader = new FXMLLoader();
			URL resource = getClass().getResource("IPO.fxml");
			loader.setLocation(resource);
			Parent root = (Parent) loader.load(resource.openStream());

			//Mostramos la nueva vista
			Scene scene = new Scene(root, this.primaryStage.getWidth(), this.primaryStage.getHeight());
			this.primaryStage.setScene(scene);
			this.primaryStage.show();		


		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
