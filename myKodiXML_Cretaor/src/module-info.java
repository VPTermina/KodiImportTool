module myKodiXML_Cretaor {
	exports kodiXML_Creator;
    opens kodiXML_Creator;
    
	requires java.prefs;
	requires myOwnUtilLibrary;
	requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;	
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.web;
	requires javafx.media;
    requires javafx.swing;
    requires javafx.swt;
	
}