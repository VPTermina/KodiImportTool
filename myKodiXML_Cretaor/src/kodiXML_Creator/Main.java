package kodiXML_Creator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import myOwnUtilLibrary.myFileOperationUtil;
import javafx.scene.Parent;
import javafx.scene.Scene;  



public class Main extends Application {

	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
			
			//BorderPane root = new BorderPane();
			
			
			//Ausgabe alle Syystemeinstellungen
			myFileOperationUtil myFileOp = new myFileOperationUtil();
			
			myFileOp.showSystemProperties();
			
			Parent root = FXMLLoader.load(getClass().getResource("myFxmlGui.fxml"));
			
			Scene scene = new Scene(root,-1.0,-1.0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			

			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
	}
}
 

