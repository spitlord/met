/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroApp;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.json.JsonObject;
import metroData.MetroData;
import metroFiles.MetroFiles;
import metroWorkspace.MetroWorkspace;
import metroWorkspace.WelcomeDialog;



/**
 *
 * @author XDXD
 */
public class App extends Application {

    /**
     * @return the topControllers
     */
    
    public static App app;
    
    private Stage stage;
    private Scene scene;
    private JsonObject propertiesObject;           // contains the properties and strings
    private MetroWorkspace workspace;              // the gui and method of communication between components
    private MetroFiles fileComponent;   
    private MetroData dataComponent;  
    
    
    
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        App.app = this; // global reference to the app,
        
        // initialize properties
        // call app.getDataObject.getString("blah") to get a property;
        
        // 
        propertiesObject = Properties.initJsonDataObject("properties.json");
        
        
        fileComponent = new MetroFiles();
        dataComponent = new MetroData();
        workspace = new MetroWorkspace();
        
        
        
        
        // show the stage after it has been set up
        primaryStage.setTitle(propertiesObject.getString("appTitle"));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNIFIED);
        
        
            

        stage = primaryStage;
        
       
        WelcomeDialog.show(); 

                
                


        primaryStage.show();
    }

   
          
    
    
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
    public void setScene(Scene scene) {
        this.scene = scene;  
    }
    
     public Scene getScene() {
        return this.scene;  
    }

    public Stage getStage() {
        return stage;
    }
     
     

    /**
     * @return the propertiesObject
     */
    public JsonObject getDataObject() {
        return propertiesObject;
    }
    
     public MetroWorkspace getWorkspace() {
        return workspace;
    }
   
    
    public MetroFiles getFileComponent() {
        return fileComponent;
    }

    public MetroData getDataComponent() {
        return dataComponent;
    }
    
    
    

}
