/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroWorkspace;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class MetroWorkspace {

 
        
    // three components of the gui
    private MetroTopPanel topPanel;
    private MetroLeftPanel leftPanel;
    private MetroCanvas canvasComponent; 

    private HBox topHBox;
    private VBox leftVBox;
    private Pane canvasPane;
    
    // the scene that will be set
    
    // assists the construction of WelcomeDialogue
    ArrayList recentFiles;
    private boolean fileInWorkSpace;
    
    File currentFile;

    
    
    public MetroWorkspace() {
        this.initWorkspace();
    }

    
   
    public void initWorkspace() {
        
        // there is no file in the workspace yet
        setFileInWorkSpace(false);    
        
        
        // container for canvas and panels
        BorderPane root = new BorderPane();
        App.app.setScene(new Scene(root, 1000, 750));

        
        // root is resizable
        root.prefWidthProperty().bind(App.app.getScene().widthProperty());
        
        // get the stylesheet for the GUI (the String is to extract value from json)
        initStylesheets(App.app.getScene(), "cssFileForGui");
        
        // Welcome dialogue that is only shown once
               
        // enable
        
        // create uninitialized components
        topPanel = new MetroTopPanel();
        leftPanel = new MetroLeftPanel();
        
        
        
        canvasComponent =  new MetroCanvas();
      
        
        // initialize canvas and panels
        canvasComponent.initCanvas();
        
        
        
        topHBox = topPanel.initPanel();
        leftVBox = leftPanel.initPanel();
        
        // place them in the borderpane
        root.setCenter(canvasComponent.getCanvasHolder());
        
        
        root.setTop(topHBox);
        root.setLeft(leftVBox);
        
    
        
        
   
      
        

         
    }
    
    
    public void initStylesheets(Scene scene, String key) {
          scene.getStylesheets().add("file:" + App.app.getDataObject().getString(key));
    }
     
    
    void initRecentFiles() {}

    /**
     * @return the fileInWorkSpace
     */
    public boolean isFileInWorkSpace() {
        return fileInWorkSpace;
    }

    /**
     * @param fileInWorkSpace the fileInWorkSpace to set
     */
    public void setFileInWorkSpace(boolean fileInWorkSpace) {
        this.fileInWorkSpace = fileInWorkSpace;
    }

    public MetroLeftPanel getLeftPanel() {
        return leftPanel;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
    
    public MetroCanvas getCanvasComponent() {
        return canvasComponent;
    }
    
    
    
    
    
    
    
}
