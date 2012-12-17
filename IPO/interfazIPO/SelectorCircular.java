package interfazIPO;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ParallelTransitionBuilder;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.PathTransition.OrientationType;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
public class SelectorCircular {

	private ParallelTransition[] trans;
	private Ellipse pathe;
	private AnchorPane root;

	public SelectorCircular(List<SelectorEntry> lista, double duracionMilis, double sizeX, double sizeY) {
		root = new AnchorPane();
        root.setMinSize(sizeX*1.1, sizeY*1.1); //400 260
        root.setMaxSize(sizeX*1.1, sizeY*1.1); //400 260
        
        final double DURATION = duracionMilis;
        final double CENTER_X = sizeX/2;
        final double CENTER_Y = sizeY/2;
        final int ELEMENTS = lista.size();
        
        trans = new ParallelTransition[ELEMENTS];
        
        pathe = new Ellipse(CENTER_X, CENTER_Y, sizeY/2, sizeX/2);
        pathe.rotateProperty().setValue(90);
        
        for(int x= 0; x < ELEMENTS; x++){
        	
        	final Node node = lista.get(x).node;
        	final Method handler = lista.get(x).handler;
        	final Object instance = lista.get(x).instance;
        	
            node.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					final long time = System.currentTimeMillis();
					
					node.setOnMouseReleased(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if(System.currentTimeMillis() - time < 250){
							if(handler != null && instance != null)
								try {
									handler.invoke(instance, node);
								} catch (IllegalAccessException
										| IllegalArgumentException
										| InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
					});
					
				}
			});
            root.getChildren().add(node);
            
            trans[x] = ParallelTransitionBuilder.create()
            	.node(node)
            	.children(
            	    FadeTransitionBuilder.create()
            	        .node(node)
            	        .duration(Duration.millis(DURATION/2))
            	        .fromValue(1.0)
            	        .toValue(0.5)
            	        .cycleCount(Timeline.INDEFINITE)
                        .autoReverse(true)
                        .build() ,
            		ScaleTransitionBuilder.create()
                        .node(node)
                        .duration(Duration.millis(DURATION/2))
                        .toX(0.5)
                        .toY(0.5)
                        .cycleCount(Timeline.INDEFINITE)
                        .autoReverse(true)
                        .build() , 
            		PathTransitionBuilder.create()
		                .duration(Duration.millis(DURATION))
		                .path(pathe) //new MoveTo(200/2, 180-35)
		                .node(node)
		                .cycleCount(PathTransition.INDEFINITE)
		                .orientation(OrientationType.NONE)
		                .interpolator(Interpolator.LINEAR)
		                .autoReverse(false)
		                .build()
		           
		            )
		            .cycleCount(Timeline.INDEFINITE)
		            .interpolator(Interpolator.LINEAR)
		            .autoReverse(false)
		            .build();
        	trans[x].setRate(0.001);
            trans[x].jumpTo(new Duration((DURATION/ELEMENTS)*x));
            System.out.println("Unicialmente para "+x+" es "+trans[x].getCurrentTime());
            
            root.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(final MouseEvent event) {			
	            	final Timer t = new Timer();
	            	t.schedule(new TimerTask() {
						
						@Override
						public void run() {
							//if(event.getX() > CENTER_X)
								play();
							//else
							//	backwards();
						}
					}, 200);
	            	
					
					root.setOnMouseReleased(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							t.cancel();
							pause();
						}
					});
				}
			});
                    	
        }
        
        //Lo incializamos al estado correcto
        initialize();
        
	}
	
	
	public AnchorPane getRoot() {
		return root;
	}


	//Hack que me he montado para que se muestre inicialmente en la posicion correcta
    private void initialize() {
    	for(int x= 0; x < trans.length; x++){
    		trans[x].play();
    		final int v = x;
    		final Timer t = new Timer();
    		t.schedule(new TimerTask() {
				
				@Override
				public void run() {
					trans[v].setRate(1);
					trans[v].setRate(0);
				}
			}, 1500);
    		Thread.yield();
    	}
    }
    
    private void play() {
    	for(int x= 0; x < trans.length; x++){
    		trans[x].setRate(1);
    	}
    }

    private void pause() {
    	for(int x= 0; x < trans.length; x++){
    		trans[x].setRate(0);
    	}
    }

}
