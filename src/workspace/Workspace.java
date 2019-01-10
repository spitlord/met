/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workspace;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class Workspace {
    
    // three components of the gui
    private final TopPanel topPanel;
    private final LeftPanel leftPanel;
    private final MetroCanvas canvasComponent;
    
    // assists the construction of WelcomeDialogue
    ArrayList recentFiles;  
    
    public Workspace() {
        
        
        // container for canvas and panels
        BorderPane root = new BorderPane();
        App.app.setScene(new Scene(root, 1000, 750));
        
        // root is resizable
        root.prefWidthProperty().bind(App.app.getScene().widthProperty());
        
        // get the stylesheet for the GUI (the String is to extract value from json)
        initStylesheets(App.app.getScene(), "cssFileForGui");
        
        // create uninitialized components
        topPanel = new TopPanel();
        leftPanel = new LeftPanel();
        canvasComponent =  new MetroCanvas();
        
        // place them in the borderpane
        root.setCenter(canvasComponent.getCanvasHolder()); 
        root.setTop(topPanel.getTopPanel());
        root.setLeft(leftPanel.getLeftPanel());     
    }
    
    public static void initStylesheets(Scene scene, String key) {
          scene.getStylesheets().add("file:" + App.app.getProperties().getString(key));
    }
    
    public LeftPanel getLeftPanel() {
        return leftPanel;
    }
    
    public MetroCanvas getCanvasComponent() {
        return canvasComponent;
    }
}
