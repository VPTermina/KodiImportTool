package application;
	
import de.gallas_it.baseutilities.DateAndTime;
import de.gallas_it.baseutilities.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 
 * MEine Test Klasse
 * 
 * @author u991712
 *
 */







public class Main extends Application {
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			logger.info( "Hauptanwendung wurde gestart!" );

			
			Parent root = FXMLLoader.load(getClass().getResource("myXML.fxml"));
			
			Scene scene = new Scene(root,-1.0,-1.0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
// Test der Bibliothek DateAndUtil		
			System.out.println(DateAndTime.getYear());
			

			
			MediaInfo mediaInfo;
			
			mediaInfo    = new MediaInfo();
	        int i = mediaInfo.Open("c:\\data\\1.mp4");
	        mediaInfo.printMetaInfosToScreen(); 
	        
	        
			
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			*/
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
