package kodiXML_Creator;


import myOwnUtilLibrary.*;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDate; 
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javax.swing.event.ChangeListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


 


public class myMainActionController {

	
	private static final Consumer<? super MenuItem> MenuItem = null;


	private final Preferences prefs = Preferences.userNodeForPackage(Main.class);	
	
	
	/**
	 * Tabel view Object 
	 */
	
	@FXML
    private TableView<myVideoProperties> tableViewOriginalVideo;
    
    
    @FXML private TableColumn<myVideoProperties,String> selectedEditColumn;
    @FXML private TableColumn<myVideoProperties,String> fileNameColumn;
    @FXML private TableColumn<myVideoProperties,String> titleColumn;
    @FXML private TableColumn<myVideoProperties,String> genreColumn;
    @FXML private TableColumn<myVideoProperties,String> plotColumn;
    @FXML private TableColumn<myVideoProperties,String> remarksColumn;
    @FXML private TableColumn<myVideoProperties,String> creationDateColumn;
   // Obere Felder zur Eingabe der default Werte 
    @FXML private TextField titleField;
    @FXML private DatePicker dateField;
    @FXML private TextField dateStringField;
    @FXML private TextField plotField;
    @FXML private TextField remarkField;
    @FXML private MenuButton genreMenueButton;
    
	@FXML
    private AnchorPane ap;
    
	@FXML
    private SubScene videoScene;
    
	
	@FXML
    private TextArea statusText;

    
	@FXML
    private MenuItem menue_refresh;

    @FXML
    private MenuItem menue_quit;
    
    @FXML
    private ComboBox<String> choice_destination_file_location;
    
    @FXML
    void run_menue_quit(ActionEvent event) {
    	System.exit(0);
    }
    
    

    @FXML
    private TextArea logTextArea;
    
    
    
    /**
	 * @return the selected date as a String
	 */
	public String getDateField() {
		
	    String help="";
		LocalDate date = dateField.getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");    
        
		if (date!=null) 
		  help = date.format(formatter);
		
		return help; 
		
		
	}

	
	
	
	 /**
		 * @return the selected Genre as a String
		 */
		public String getGenreMenue() {
			
			
			//Am Menu Eintrag wurde als UserData das Object Checkbox angehangen		
    	
			
		    String genreString = "#";  
		   
		    for (int i=0;i<genreMenueButton.getItems().size();i++) {
		    javafx.scene.control.MenuItem selectedItem = genreMenueButton.getItems().get(i);
		    
		    
		    CheckBox cb = (CheckBox) selectedItem.getUserData();  
		    if (cb.isSelected()) {
		    	
		    	genreString = genreString + cb.getText() + "#";
		    }

		   		    
		    
		    }			
	
			
			return genreString;
		}
		
	
	
	/**
	 * @param dateField the dateField to set
	 */
	public void setDateField(DatePicker dateField) {
		this.dateField = dateField;
	}



    //private TableColumn selectColumn ;    
    /**
     * 
     * 
     * 
     * Event um in der Tabelle einen Wert editierten wert zurück zuschreiben
     */
	 @FXML
	public void changeFileNameCellEvent(CellEditEvent edittedCell) {
    	
    	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
    	
    	myVideoPropertiesSelected.setFileName(edittedCell.getNewValue().toString());
    	
    }
    
    /**
     * 
     * 
     * 
     * Event um in der tabelle einen Wert editierten wert zurück zuschreiben
     */
    @FXML
    public void changeTitleColumnCellEvent(CellEditEvent edittedCell) {
    	
    	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
    	
    	myVideoPropertiesSelected.setTitle(edittedCell.getNewValue().toString());
    	
    }
 
 /**
  * 
  * 
  * 
  * Event um in der tabelle einen Wert editierten wert zurück zuschreiben
  */
 @FXML
 public void changeGenreColumnCellEvent(CellEditEvent edittedCell) {
 	
 	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
 	
 	myVideoPropertiesSelected.setGenre(edittedCell.getNewValue().toString());
 	
 }
 
 /**
  * 
  * 
  * 
  * Event um in der tabelle einen Wert editierten wert zurück zuschreiben
  */
 @FXML
 public void changePlotCellEvent(CellEditEvent edittedCell) {
 	
 	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
 	
 	myVideoPropertiesSelected.setPlot(edittedCell.getNewValue().toString());
 	
 }
 
 @FXML 
 public void changeRemarklsCellEvent(CellEditEvent edittedCell) {
 	
 	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
 	
 	myVideoPropertiesSelected.setRemarks(edittedCell.getNewValue().toString());
 
 	
 	
 	
 	
 }
 

 
 /**
  * 
  */
 @FXML
 public void changeSelectedEditCellEvent(CellEditEvent edittedCell) {
	 	
	 	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
	 	
	 	myVideoPropertiesSelected.setSelectEdit(edittedCell.getNewValue().toString());
	 
	 	
	 	
	 	
	 	
	 }
 
 
    
 /**
  * 
  * 
  * 
  * Event um in der tabelle einen Wert editierten Wert zurück schreiben
  */
  
 public void changeCreationDateCellEvent(CellEditEvent edittedCell) {
	 	
	 	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
	 	
	 	myVideoPropertiesSelected.setCreationDate(edittedCell.getNewValue().toString());
	 
	 	
	 }   
    
 
 
    
/**
 * 
 * 
 */
    

    
    @FXML
    void run_menue_refresh(ActionEvent event) {
        
    	
    	
    	for( int i=0; i<tableViewOriginalVideo.getItems().size(); i++ ) {
    		
    		tableViewOriginalVideo.getItems().get(i).printAll();
    	}
    	
    	
    	
    	
    	int last_index = tableViewOriginalVideo.getItems().size()-1;
		tableViewOriginalVideo.getItems().remove(last_index);
		

    }    
  
    /**
     * 
     * Löscht alle Zeilen im Status 0
     * 
     * @param event
     */
    @FXML
    void run_menue_remove_line(ActionEvent event) {

 int start,ende;   	
 
 start = tableViewOriginalVideo.getItems().size()-1;
 ende = 0;
 
 
for( int i=start; i>ende; i-- ) 	
        	
    	{
    		
    	 
    		if  (tableViewOriginalVideo.getItems().get(i).getSelectedEdit().equals("0")) {
    
    			tableViewOriginalVideo.getItems().remove(i);
    			
    			
    
    
    }
    	}
    }
    

    
    
    /**
         * 
         * Füllt die Tabelle mit den Default Werten
         * 
         */
        
    @FXML
    void run_menue_setDefaultValues(ActionEvent event) {   
    
    	for( int i=0; i<tableViewOriginalVideo.getItems().size(); i++ ) 	
        	
    	{
    		
    	 	
    		myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
        	
    		

    		if  (tableViewOriginalVideo.getItems().get(i).getSelectedEdit().equals("1")) {
    		
    			
    			
    			if (!dateStringField.getText().isEmpty())
    			
    				
    				
    				tableViewOriginalVideo.getItems().get(i).setCreationDate(replaceKeyValuesWithValues(tableViewOriginalVideo.getItems().get(i),dateStringField.getText()));
    				
    		
    			
    			if (!titleField.getText().isEmpty())
    				tableViewOriginalVideo.getItems().get(i).setTitle(replaceKeyValuesWithValues(tableViewOriginalVideo.getItems().get(i),titleField.getText()));
    			
    			if (!plotField.getText().isEmpty())
    				tableViewOriginalVideo.getItems().get(i).setPlot(replaceKeyValuesWithValues(tableViewOriginalVideo.getItems().get(i),plotField.getText()));
    			
    			if (!remarkField.getText().isEmpty())
    			tableViewOriginalVideo.getItems().get(i).setRemarks(replaceKeyValuesWithValues(tableViewOriginalVideo.getItems().get(i),remarkField.getText()));    			
    			
    			if (!getGenreMenue().isEmpty())
    			tableViewOriginalVideo.getItems().get(i).setGenre(replaceKeyValuesWithValues(tableViewOriginalVideo.getItems().get(i),getGenreMenue()));
        
        		
    			
    			
    			
    			//System.out.println(tableViewOriginalVideo.getItems().get(i).getFileName());
    		}
    		
    		
    	}
    	refresh();
    	
    	
    }
    
    
    /**
     * 
     * Hilfsfunktion um Ersetzungen vorzunehmnen
     */
    
    public String replaceKeyValuesWithValues(myVideoProperties myVideoProperty, String str) {
    	String result =str;
   
    	
    	
    	
    	result = str.replace("%t%", myVideoProperty.getTitle());
    	result = result.replace("%d%", myDateandTimeUtil.getYear());
    	result = result.replace("%f%", myFileOperationUtil.removeFilePathAndExtension(myVideoProperty.getFileName()));
    	result = result.replace("%m%", "Rocco.Gallas@gallas-it.de");
    	
    	
    	// Spezielle funktion um Dateinamen anzupassen
    	// Bsp: David - Das dritte Jahr -  S1E01 - 2009_01 David lernt  malen.mpg
    	// Ergebniss: David lernt  malen
if (result.contains("%f2%")) {
	          
    		
    		String help2 = myVideoProperty.getFileName();
    		
    		//entspricht dem Suchmuster S01E01 
        	String regex2 = "[0-9][0-9][0-9][0-9]_[0-9][0-9]";
        	
            //String search = "test123";
            Pattern pattern2 = Pattern.compile(regex2);
            Matcher matcher2 = pattern2.matcher(help2);
            
            //System.out.println(matcher.find());
            String help3 ="0";
            if (matcher2.find()){
            help3 = help2.substring(matcher2.end()+1, help2.length());
            help3=myFileOperationUtil.removeFileExtension(help3);
            
            result = result.replace("%f2%", help3);
            
            }
}
    	
    	// Date Field Manipulations
           
    	if (result.contains("%d2%")) {
	          
    		
    		String help1 = myVideoProperty.getFileName();
    		
    		//entspricht dem Suchmuster S01E01 
        	String regex = "[0-9][0-9][0-9][0-9]_[0-9][0-9]";
        	
            //String search = "test123";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(help1);
            
            //System.out.println(matcher.find());
            String help ="0";
            if (matcher.find()){
            help = help1.substring(matcher.end()-7,matcher.end());
            
            help = "01." + help1.substring(matcher.end()-2,matcher.end());
            help = help + ".";
            help = help + help1.substring(matcher.end()-7,matcher.end()-3);
            
            result = result.replace("%d2%", help);
            
            }
      		
		}
    	
    	
    	// Date Field Manipulations
        
    	if (result.contains("%d4%")) {
    		
    		String help = getVideoFileProperties(myVideoProperty.getFileName());
    		result = result.replace("%d4%", help);
    	}
    	
if (result.contains("%d5%")) {
    		
    		String help = getVideoFilePropertiesDate(myVideoProperty.getFileName());
    		
    		result = result.replace("%d5%", help);
    	}
    	
    	
    	result = result.replace("%d3%", myDateandTimeUtil.getActualDateAsString());
   	
    return result;	
    }
    
    
    /**
     * Spezielle Funktion um die Eigenschaften der Datei mittels Media Info auszulesen 
     */
      
       public String getVideoFileProperties(String fullFileName)
       { 
       	myVideoInfo mediaInfo    = new myVideoInfo();
           
       mediaInfo.setMetaInfo(fullFileName);	
       
       
       String help = mediaInfo.getMetaInfo(fullFileName);
       
       return  help; 

        	
   	    
   	    //Schreibt das Datum
       	//getMyTable().setValueAt(help, myTable.getRowCount()-1, column);
       }  
       /**
        * Spezielle Funktion um die Eigenschaften der Datei mittels Media Info auszulesen 
        */
         
          public String getVideoFilePropertiesDate(String fullFileName)
          { 
          	myVideoInfo mediaInfo    = new myVideoInfo();
              
          mediaInfo.setMetaInfo(fullFileName);	
          
          String help = mediaInfo.getMyGeneral().getEncodedDate();
          
          
          return  help;

           	
      	    
      	    //Schreibt das Datum
          	//getMyTable().setValueAt(help, myTable.getRowCount()-1, column);
          }  
    
    
    
    /**
     * 
     * Füllt die Tabelle mit den Default Werten
     * 
     */
    
@FXML
void run_menue_SetStatus2(ActionEvent event) {   

	for( int i=0; i<tableViewOriginalVideo.getItems().size(); i++ ) 	
    	
	{
		if  (tableViewOriginalVideo.getItems().get(i).getSelectedEdit().equals("1")) 
		  tableViewOriginalVideo.getItems().get(i).setSelectEdit("2");
			
		}

	refresh();
	
}
    
@FXML
void run_menue_MediaInfoDLLInfo(ActionEvent event) {   

	
	 
	myVideoInfo mediaInfo    = new myVideoInfo();
    
    //mediaInfo.setMetaInfo(fullFileName);	
    
   
    String help = mediaInfo.getMediaInfoDLLVersionInfo();
    
	
    TextArea infoText;
    infoText = new TextArea();
    infoText.appendText(help);
    infoText.home();
    StackPane secondaryLayout = new StackPane();
    secondaryLayout.getChildren().add(infoText);

    Scene secondScene = new Scene(secondaryLayout, 600, 600);

    // New window (Stage)
    Stage newWindow = new Stage();
    newWindow.setTitle("Information MediaInfo DLL");
    newWindow.setScene(secondScene);

    // Set position of second window, related to primary window.
    //newWindow.setX(primaryStage.getX() + 200);
    //newWindow.setY(primaryStage.getY() + 100);

    newWindow.show();
	
}  
    
    /**
     * Kopiert das Original File und schreibt die NFO für die Season
     * 
     */
    @FXML
    void run_menue_moveOriginalVideo(ActionEvent event) {
    	
    	
    	
    	
    	String parentFolder= choice_destination_file_location.getValue();
    	
    	for( int i=0; i<tableViewOriginalVideo.getItems().size(); i++ ) 	
    	
    	{ 
    		 	
    	
    		if  (tableViewOriginalVideo.getItems().get(i).getSelectedEdit().equals("2")) {
    			myFileOperationUtil myFileOp = new myFileOperationUtil();
    			

    			String destinationFolder = createSeasonFolder(i, parentFolder);
    			String newFileName= generateNewFileName(i,destinationFolder);
    			
    			String source_filename =tableViewOriginalVideo.getItems().get(i).getFileName();
    			String destination_filename= tableViewOriginalVideo.getItems().get(i).getNewFolder() + "\\" +tableViewOriginalVideo.getItems().get(i).getNewFilename();
    			
    			
    			statusText.appendText("Copy done:" + source_filename + "-> " + destination_filename + "\n");
    			 			
    			myFileOp.doSingleFileCopy(source_filename,destination_filename); 
    			
    			statusText.appendText("nfo written:" +  "\n");
         		
    			writeEpisodeNFO(tableViewOriginalVideo.getItems().get(i).getNewFilename(),tableViewOriginalVideo.getItems().get(i).getNewFolder(),i); 
    	   
    			String help1 = "c:\\util\\scripte\\ffmpeg_kodi.bat";
       			String help2=destinationFolder +"\\";
    			help2 = help2 + tableViewOriginalVideo.getItems().get(i).getNewThumbnailName();
    			
    			
    			
    			String[] cmd = { help1, source_filename, help2};
    			statusText.appendText(cmd[0] + cmd[1] + cmd [2]);
    			myFileOp.runExternalProgramm(cmd);
    			
    			
    			
    		}
	 	
    	
    	}
    	
    	 
    	 
    	 
    	 
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
    	 alert.setTitle("Confirmation dialog!");
    	 String s = "Confirm to remove the original Video files !";
    	 alert.setContentText(s);

    	 Optional<ButtonType> result = alert.showAndWait();

    	 if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

    	     //remove the Original video file
    	     
    	 }
    	 
    	 
          
        
    }
    
    
    
    /*
     * 
     * 
     */
   
    @FXML
    void run_menue_CreateAllInformation(ActionEvent event) {
    
String parentFolder= choice_destination_file_location.getValue();
    	
    	for( int i=0; i<tableViewOriginalVideo.getItems().size(); i++ ) 	
    	
    	{
    		 	
    	
    	
    		
		String destinationFolder = createSeasonFolder(i, parentFolder);
		String newFileName= generateNewFileName(i,destinationFolder);
	    	
    	}
    	
    	 
    }
    
    
    
    /**
     * 
     * @param event
     */
    
    @FXML
    void run_menue_load_original_videos(ActionEvent event) {
       
        

		Stage stage = (Stage) ap.getScene().getWindow();
      
		
		
       File lastPath = new File(prefs.get("loadOriginalVideoPath", "E:\\video\\OwnVideo\\"));
       
        FileChooser fileChooser = new FileChooser();
        //configureFileChooser(fileChooser, lastPath, Constants.TXT_EXTENSION_FILTER);
        configureFileChooser(fileChooser, lastPath,new FileChooser.ExtensionFilter("Video files (*.mov/avi/m2ts/mpg/mp4/mod/mts)", "*.mov", "*.avi" ,"*.m2ts", "*.mpg","*.mp4", "*.mod","*.mts") );
               
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files != null && !files.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            files.forEach((item) -> {
          //      sb.append(item.getAbsolutePath()).append(Constants.MULTI_FILE_PATH_DELIMITER);
            	//InsertNewVideoIntoTable(item.getAbsolutePath(),"Titel", "Genre","Plot", "Remarks", LocalDate.of(1966,Month.MARCH,31));
            	
            	InsertNewVideoIntoTable("1",item.getAbsolutePath(),item.getName(), "Genre","Plot", "Remarks", "1.10.1966");
            	
            	
            });
 
        }
        
        
        
    }
   
    
    
    /**
     * 
     * Löscht die original VideoTabelle 
     * 
     */
    @FXML
    
    public void run_menue_clearTableOriginalVideo() 
    {
    	
    	tableViewOriginalVideo.getItems().clear();
    	
    
    	
    }
    
    public void run_menue_ViewVideo() 
    {
    	
    	
    	myVideoProperties selected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();
    	
    	//Person person = taview.getSelectionModel().getSelectedItem();
    	//String help = "file:///" + selected.getFileName().replace('\\', '/');
    	
    	try {
    	File file = new File(selected.getFileName());
		URL url = file.toURI().toURL();
    	String help = "file://" + url.getFile();
    	
    	
    		
    	 help = URLDecoder.decode(help, "UTF-8");
    	
    	 //help ="file:///C://daten//Schlossauersbach.mpg";
    	    
    	 
			System.out.println(help);    
	    	
	    	//Media m = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + fileName);
	    	   
	    	
	    	 Media media = new Media(help);
	    	 
	    	 MediaPlayer mediaPlayer = new MediaPlayer(media);
	         mediaPlayer.setAutoPlay(false);
	         
	         // rocco myMediaControl mediaControl = new myMediaControl(mediaPlayer);
	         //videoScene.setRoot(mediaControl);
	         StackPane secondaryLayout = new StackPane();
	         Scene secondScene = new Scene(secondaryLayout, 600, 600);
             // rocco secondScene.setRoot(mediaControl); 
	          //New window (Stage)
	         Stage newWindow = new Stage();
	         newWindow.setTitle("Information MediaInfo DLL");
	         newWindow.setScene(secondScene);

	         // Set position of second window, related to primary window.
	         //newWindow.setX(primaryStage.getX() + 200);
	         //newWindow.setY(primaryStage.getY() + 100);

	         newWindow.show();
	     	
	         
	         
	         
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		 
      
         
  		
  		
    	
    
    	
    }
    
    
    
    /**
     * Hilfsfunktion um das nfo file für die Video Episode zu schreiben 
     * 
     * 
     */
    
    public void writeEpisodeNFO(String newFileName,String destinationFolder,int itemIndex) 
    {
    
    	
    	
    	
    episodeNFO myEpisodeNFO= new episodeNFO();		
	myEpisodeNFO.setNfoValue("title", tableViewOriginalVideo.getItems().get(itemIndex).getTitle());
	myEpisodeNFO.setNfoValue("year", tableViewOriginalVideo.getItems().get(itemIndex).getYear());
	myEpisodeNFO.setNfoValue("season", tableViewOriginalVideo.getItems().get(itemIndex).getSeason());
	myEpisodeNFO.setNfoValue("episode", tableViewOriginalVideo.getItems().get(itemIndex).getEpisode());
	myEpisodeNFO.setNfoValue("plot", tableViewOriginalVideo.getItems().get(itemIndex).getPlot());
	//myEpisodeNFO.setNfoValue("outline", tableViewOriginalVideo.getItems().get(itemIndex).getOutline();
	//myEpisodeNFO.setNfoValue("tag", tableViewOriginalVideo.getItems().get(itemIndex).getTag());	
	myEpisodeNFO.setNfoValue("genre", tableViewOriginalVideo.getItems().get(itemIndex).getGenre());
	
	myEpisodeNFO.setNfoValue("aired", tableViewOriginalVideo.getItems().get(itemIndex).getCreationDate().toString());
	myEpisodeNFO.setNfoValue("premiered", tableViewOriginalVideo.getItems().get(itemIndex).getCreationDate().toString());
	
	
	myEpisodeNFO.createNFOFileEpisode(destinationFolder + "\\" + newFileName );	
    }
    
    
    
   /**
    *  Hilfsfunktion um die Optionen für den Fileauswahldialog zu setzen
    * @param fileChooser
    * @param initDir
    * @param filter
    */
    
    private static void configureFileChooser(final FileChooser fileChooser,
            File initDir, FileChooser.ExtensionFilter filter) {
        // fileChooser.setTitle("View Pictures");
        if (initDir != null && initDir.exists()) {
            if (initDir.isDirectory()) {
                fileChooser.setInitialDirectory(initDir);
            } else {
                fileChooser.setInitialDirectory(initDir.getParentFile());
            }
        } else {
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home")));
        }

        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                filter,
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
    }
    
    
    /**
     * 
     * Wird am Begin aufrufen
     * Initialisiert ale notwendigen elemente der Oberfläche
     * 
     */
    
    
    @FXML
	public void initialize() {
    	// Speichert default Werte in der Registry
    	
      	//prefs.put("loadOriginalVideoPath", "C:\\daten\\kodi_test_documents\\videos");
    	
    	//prefs.put("loadOriginalVideoPath", "C:\\daten\\video\\einsortieren");
 
    	// Fill out the File location DropBox with values
	    choice_destination_file_location.getItems().removeAll(choice_destination_file_location.getItems());
	    choice_destination_file_location.getItems().addAll("c:\\temp\\", "Y:\\video\\homevideo\\geschnitten\\", "Y:\\video\\homevideo\\original\\");
	    choice_destination_file_location.getSelectionModel().select("c:\\temp\\");
	    
	    //Fill out the StusText Field with Welcome message
	    statusText.setText("");
	    statusText.appendText("Welcome ! Please load video files!");
	    statusText.appendText("\n");
	    statusText.appendText("The programm need the MediaInfo.dll (download the package from: https://mediaarea.net/de/MediaInfo/Download/Windows!");
	    statusText.appendText("\n");
	    statusText.appendText("Put the MediaInfo.dll in a System lib path (ex: c:\\system32");
	    statusText.appendText("\n");
	    statusText.appendText("Welcome ! Please load video files!");
	    
	    
	    // Fill out the genreMenueButton with values
	    CheckBox cb0 = new CheckBox("Urlaub");  
	    CustomMenuItem item0 = new CustomMenuItem(cb0);  
	
	    
	    CheckBox cb1 = new CheckBox("Ausflug");  
	    CustomMenuItem item1 = new CustomMenuItem(cb1);
	    
	    CheckBox cb2 = new CheckBox("Sonstiges");  
	    CustomMenuItem item2 = new CustomMenuItem(cb2); 
	    CheckBox cb3 = new CheckBox("madyByDavid");  
	    CustomMenuItem item3 = new CustomMenuItem(cb3); 
	    CheckBox cb4 = new CheckBox("Familie und Freunde");  
	    CustomMenuItem item4 = new CustomMenuItem(cb4); 
	    
	    
	    
	    item0.setHideOnClick(false);  
	    item1.setHideOnClick(false);  
	    item2.setHideOnClick(false);  
	    item3.setHideOnClick(false);  
	    item4.setHideOnClick(false);  
	    
	    //An das Object wird die jeweilige Checkbox als Object angefügt um sie später abfragen zu können
	    
	    item0.setUserData(cb0);
	    item1.setUserData(cb1);
	    item2.setUserData(cb2);
	    item3.setUserData(cb3);
	    item4.setUserData(cb4);
	    
	    
	    genreMenueButton.getItems().setAll(item0,item1,item2,item3,item4);
	    
	    //
	   //selectColumn = new TableColumn("Select");
	    
	    //tableViewOriginalVideo.getColumns().add(selectColumn);
	    //tableViewOriginalVideo.getColumns().clear();
	    //tableViewOriginalVideo.getColumns().add(0,selectColumn);
	    
	    // Set up the columns in the table
	   
	    
	    //selectColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("select"));
	    
	    
	    fileNameColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("fileName"));
	    titleColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("title"));
	    genreColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("genre"));
	    plotColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("plot"));
	    remarksColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("remarks"));
	    creationDateColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("creationDate"));
	    selectedEditColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("selectEdit"));
	    
	    
	    //creationDateColumn.setCellValueFactory(new PropertyValueFactory<myVideoProperties,String>("creationDate"));
	    

		
		// create media player
		
		 //Media media = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + "Schlossauersbach.mp4");
		   
		
	    
	    


	    
	    
	    
	    
	    //
	    
	    
	    
	    // load dummy data
	    tableViewOriginalVideo.setItems(getVideoProperties());  
	    
	    
	    // Eventhandler für rechte Maustaste einbauen
	    // Wenn rechte Maustaste auf der Spalte 0 gedrückt wird, wird status auf 2 oder 1 gesetzt
	    
	    tableViewOriginalVideo.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	  @Override
	    	  public void handle(MouseEvent event) {
	    	    if (event.getButton().equals(MouseButton.SECONDARY) ) {
	    	      @SuppressWarnings("rawtypes")
	    	      ObservableList<TablePosition> cells =tableViewOriginalVideo.getSelectionModel().getSelectedCells();
	    	      for (@SuppressWarnings("rawtypes") TablePosition p : cells) {                 
	    	        int r = p.getRow();
	    	        int c = p.getColumn();
	    	        Object cell = tableViewOriginalVideo.getColumns().get(c).getCellData(r);
	    	        
	    	        if (c == 0) {
	    	        	
	    	        	if  (tableViewOriginalVideo.getItems().get(r).getSelectedEdit().equals("1")) 
	    	      		  tableViewOriginalVideo.getItems().get(r).setSelectEdit("2");
	    	        	else
	    	      		  tableViewOriginalVideo.getItems().get(r).setSelectEdit("1");
	    	        	
	    	        	
	    	        	refresh();
	    	        }
	    	        
	    	        if (c == 1) {
	    	        
	    	        	myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();	
	    	        String help = getVideoFileProperties(myVideoPropertiesSelected.getFileName());
	    	        statusText.setText(help);
	    	        
	    	        }
	    	        
	    	        //System.err.println(r + " " + c);
	    	      }                     
	    	    }
	    	    
	    	 // Left Mouse button    
	    	    if (event.getButton().equals(MouseButton.PRIMARY) ) {
		    	      @SuppressWarnings("rawtypes")
		    	      ObservableList<TablePosition> cells =tableViewOriginalVideo.getSelectionModel().getSelectedCells();
		    	      for (@SuppressWarnings("rawtypes") TablePosition p : cells) {                 
		    	        int r = p.getRow();
		    	        int c = p.getColumn();
		    	        Object cell = tableViewOriginalVideo.getColumns().get(c).getCellData(r);
		    	        
		    	        
		    	        
		    	        
		    	        
		    	        myVideoProperties myVideoPropertiesSelected = tableViewOriginalVideo.getSelectionModel().getSelectedItem();	
		    	        String help = getVideoFileProperties(myVideoPropertiesSelected.getFileName());
		    	        statusText.setText(help);
		    	        
		    	       
		    	        
		    	        //System.err.println(r + " " + c);
		    	      }                     
		    	    }
	    	    
	    	  }
	    	});
	    
	    
	    
	    // Versetzt die Spalten in den Editiermode
	    
	    tableViewOriginalVideo.setEditable(true);
	    fileNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());	    
	    //selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
	    selectedEditColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    
	    genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    plotColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    remarksColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    creationDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    
	   // Setze die Tooltips
	 /*
	   Tooltip tooltip = new Tooltip();
	   tooltip.setStyle("-fx-background-color: yellow; -fx-text-fill: red;");
	    tooltip.setText(
	    	"Datumsfeld setzen mit %...% \n" +	
	        "d Übernimmt Datum aus dem DateField \n" +
	        "d1 Übernimmt Erstellungsdatum der Datei\n"  
	    );
	    //dateStringField.setTooltip(tooltip);   
	    //dateField.setTooltip(tooltip);
	 */
	    
	 // A browser.
	       WebView  webView1 = new WebView();
	       WebEngine webEngine1 = webView1.getEngine();
	       webEngine1.loadContent
	       (
	            "<p> Folgende Werte zum Setzen: </p>" +  		   
	            helpCreateHTMLLine("f","Übernimmt den Dateinamen. Erweiterung wird gelöscht") +
	            helpCreateHTMLLine("t","Übernimmt den Wert aus dem Titel Feld") +
	            helpCreateHTMLLine("m","Fügt die mail Adresse ein") +
	            helpCreateHTMLLine("t1","") +
	            helpCreateHTMLLine("f2","Übernimmt Teil des Filenamens Muster: David - Das dritte Jahr -  S1E01 - 2009_01 David lernt  malen.mpg") 
	           
	       );
	 
	       Tooltip  tooltip1 = new Tooltip();
	       tooltip1.setPrefSize(400, 300);
	       tooltip1.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	      
	       // Set tooltip content
	       tooltip1.setGraphic(webView1);
	     
	   
	   titleField.setTooltip(tooltip1);
	   plotField.setTooltip(tooltip1);
	   remarkField.setTooltip(tooltip1);
	   
	   titleField.setText("%f2%");
	   plotField.setText("%t%");
	   remarkField.setText("%t%");
	   dateStringField.setText("%d2%");
	   
	// A browser.
       WebView  webView = new WebView();
       WebEngine webEngine = webView.getEngine();
       webEngine.loadContent
       (
            "<p> Folgende Werte zum Setzen des Datum </p>" +  		   
            helpCreateHTMLLine("d","Übernimmt Datum aus dem DateField") +
            helpCreateHTMLLine("d1","Übernimmt Datum aus dem Creation Date") +
            helpCreateHTMLLine("d2","Übernimmt Datum aus dem Fielenamen (yyyy_MM)") +
            helpCreateHTMLLine("d3","Übernimmt das aktuelle Datum") +
            helpCreateHTMLLine("d4","Übernimmt Mehrere Kommentare zum video aus MediaInfo. Achtung: Mehrzeilig ")+ 
            helpCreateHTMLLine("d5","Übernimmt Datum aus dem Feld EncodedDate von MediaInfo")
           
       );
 
       Tooltip  tooltip = new Tooltip();
       tooltip.setPrefSize(400, 300);
       tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      
       // Set tooltip content
       tooltip.setGraphic(webView);
       
 
       dateStringField.setTooltip(tooltip);   
	   dateField.setTooltip(tooltip); 
	    
	}

    /**
     * Hilfsfunktion um einen String in HTML Syntax zu verändern
     * 
     * @return
     */
    
    public String helpCreateHTMLLine(String str_formater, String str_description) {
    	String str = "<p>";
    	str = str + "<i>%</i>" + "<b>" + str_formater + "</b>" + "<i>%</i>";
    	
    	str = str + " - " + str_description + "</p>";
    
    	return str; 
    }
    
    public ObservableList<myVideoProperties> getVideoProperties()
    {
    	ObservableList<myVideoProperties> myVideos = FXCollections.observableArrayList();
    	
    	//myVideos.add(new myVideoProperties("test","Titel", "Genre","Plot", "Remarks", "31.01.2017"));
   
    
    	return myVideos;
    }
    
    /**
     * 
     * Fügt eine Zeile in die originalvideoTabelle ein
     * 
     */
    public void InsertNewVideoIntoTable(String select, String filename,String title,String genre, String plot,String remarks,String date) 
    {
    	
    	
    	
    	
    	myVideoProperties  newVideoProperties = new myVideoProperties(select,filename, title, genre, plot, remarks, date);
    	tableViewOriginalVideo.getItems().add(newVideoProperties);
    	
    
    	
    }
    
    /**
     * Hilfsfunktion: Erstellt  Jahresfolder und Monatsfolder (SeasonXX zur Ablage der Video files
     * @param row
     * @param parentfolder 
     * 
     **/
    public String createSeasonFolder(int itemIndex,String parentfolder) {
    	
    	
    	String newParentFolder= createYearFolder(itemIndex,parentfolder);
    	String newFolder = newParentFolder + "\\Season" + tableViewOriginalVideo.getItems().get(itemIndex).getSeason();
    	
    	myFileOperationUtil.createDir(newFolder);
    	
    	return newFolder;
    }
    
    /**
     * Create Jahresfolder zur Ablage der Video files
     * @Return der erstellte Ordner!
     **/
    public String createYearFolder(int itemIndex,String parentfolder) {
    	
    	
    	
    	String newFolder = parentfolder + tableViewOriginalVideo.getItems().get(itemIndex).getYear();
    	    	
    	myFileOperationUtil.createDir(newFolder);
    	
    	return newFolder;
    } 
    
    
    
    /**
     * 
     * Methode erstellt einen neuen Filename basierend auf dem Season name und der Episodennummer
     * und schreibt folgende Informationen zurück:
     * Neuer Filename
     * Neuer Folder
     * Name der ThumbnailDatei
     */

    public String generateNewFileName(int itemIndex,String parentFolder) {

    	tableViewOriginalVideo.getItems().get(itemIndex);
    	
    	String help="";
    	int erg = getLastepisodenNumber(parentFolder);
    	if ( erg <10) {
    		help = "0" + String.valueOf(erg);
    	}
    	else {
    		help = String.valueOf(erg);
    	}
    	
    	String help1 = tableViewOriginalVideo.getItems().get(itemIndex).getTitle() + " S" + tableViewOriginalVideo.getItems().get(itemIndex).getSeason();
    	help1 = help1 + "E" + help;
    	
    	String help2= help1+ "-thumb.jpg";
    	
    			
    	help1 = help1 +"." + getFileNameExtension(tableViewOriginalVideo.getItems().get(itemIndex).getFileName()); 
    	
    	// Schreibt Werte in die Struktur
    	
    	tableViewOriginalVideo.getItems().get(itemIndex).setEpisode(help);
    	tableViewOriginalVideo.getItems().get(itemIndex).setNewThumbnailName(help2);		
    	tableViewOriginalVideo.getItems().get(itemIndex).setNewFilename(help1);
    	tableViewOriginalVideo.getItems().get(itemIndex).setNewFolder(parentFolder);
    	
    	return help1;
    	

    }


    /**
     * suchtt innerhalb eines Strings nach dem Muster S01E01 um die letzte Episodennummer im aktuellen Verzeichniss zu finden  
     * @param search
     * @return
     */

    private Integer getSubString(String search) {
        
    	//entspricht dem Suchmuster S01E01 
    	String regex = "S[0-9][0-9]E[0-9][0-9]";
    	
        //String search = "test123";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(search);
        
        //System.out.println(matcher.find());
        String help ="0";
        if (matcher.find()){
        help = search.substring(matcher.end()-2,matcher.end());
        
        
        }
        
       
        return new Integer(help);
       
    }


    /**
     * 
     * Hilfsfunktion um alle Episoden in einem Directory zu lesen und die nächste freie Episodennummer zu finden
     * 
     * 
     */
    public int getLastepisodenNumber(String folderName) {

    File workDir = new File (folderName);	
    String[] files = workDir.list(); 
    int help;
    int max=0;
    for (int j = 0; j < files.length; j++) { 
            

    	help = getSubString(files[j]);
    	if (help>max) {
    		max=help;
    	}else
    	{
    		
    	}
    	
    	
    } 


    return max+1;
    }

    /**
     * 
     */



    /**
     * Hilfsfunktion: Liefert die File Extension
     * 
     * 
     * @param str  Filename des Erweiterung gesucht wird
     */

    public String getFileNameExtension(String str) {
    	
    	  // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(pos+1);
    	

    }

    
    /**
	 * Refresh Table
	 * 
	 */
	
	public void refresh() {
		
		
		
		
		//InsertNewVideoIntoTable("0","w","Please start Refresh Menue button to remove this line ", "Genre","Plot", "Remarks", "1.10.1966");
		
		//run_menue_remove_line(null);
		 //tableView.getColumns().get(0).setVisible(false);
		 //tableView.getColumns().get(0).setVisible(true);
		
		int size =tableViewOriginalVideo.getItems().size();
		
		List<myVideoProperties> tempListe = new ArrayList<myVideoProperties>();
		
		
		for( int i=0; i<size; i++ ) {
			//tableViewOriginalVideo.getItems().remove(i);
			//myTempList o1 = new myTempList("1", "one");			
            tempListe.add(tableViewOriginalVideo.getItems().get(i));
		
		}
		tableViewOriginalVideo.getItems().clear();
		
		for( int i=0; i<size; i++ ) {
	

			
			InsertNewVideoIntoTable(tempListe.get(i).getSelectedEdit(),tempListe.get(i).getFileName() ,tempListe.get(i).getTitle(),tempListe.get(i).getGenre(),tempListe.get(i).getPlot(),tempListe.get(i).getRemarks(),tempListe.get(i).getCreationDate()); 
			//tableViewOriginalVideo.add(tempListe.get(i));
			
		}
		
    			

		
	}  
    
    
    
}
