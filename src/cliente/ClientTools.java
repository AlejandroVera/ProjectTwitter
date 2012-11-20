package cliente;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

class ClientTools {

	protected static Stage showDialog(String text){
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
		
		return dialogStage;
    }
	
}
