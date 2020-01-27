package application;
	
import de.gallas_it.baseutilities.DateAndTime;
import de.gallas_it.baseutilities.MediaInfo;
import de.gallas_it.baseutilities.FileOperations;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.CopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 
 * Meine Test Klasse
 * Dieses Programm testet die Utilitiy Klasse
 * 
 * @author u991712
 *
 */


public class Main extends Application {
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			 
			logger.debug( "Hauptanwendung wurde gestart!" );

			
			Parent root = FXMLLoader.load(getClass().getResource("myXML.fxml"));
			
			Scene scene = new Scene(root,-1.0,-1.0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
// Test der Bibliothek DateAndUtil		
			System.out.println(DateAndTime.getYear());
			

			// Test der Bibliothek FileOperation		
			
			//System.out.println(FileOperations.checkFileExistsJavaNio("C:\\data\\1.mp4"));
			//System.out.println(FileOperations.copyFileJavaNio("C:\\data\\3.mp4", "C:\\data\\1.mp4"));
			//System.out.println(FileOperations.moveFileJavaNio("C:\\data1\\", "C:\\data\\",false));
			//FileOperations.deleteDirectoriesRecursivlyJavaNio("c:\\data1\\");
			
			
			
			//FileOperations test = new FileOperations();
			
			// Test der Bibliothek MediaInfo				
			
			MediaInfo mediaInfo;
			
			mediaInfo    = new MediaInfo();
	        int i = mediaInfo.Open("c:\\data\\1.mp4");
	        logger.info("File opened with status:"+ i + "c:\\data\\1.mp4");
	       // mediaInfo.printMetaInfosToScreen(); 
	        
	        
			
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
