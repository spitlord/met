/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workspace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metroApp.App;


/**
 *
 * @author XDXD
 */
public class WelcomeDialog {
    
    public static void show() {
        
    HBox mainContainer = new HBox();
    VBox recentBox = new VBox();
    VBox newBox = new VBox();
        
    Label recentLabel = new Label("Recent Maps");
    recentBox.getChildren().add(recentLabel);
    
    
    newBox.getChildren().add(new ImageView(new Image ("file:" + "src/images/metroLogo.png")));
    
    Label newMapLabel = new Label("Create a New Map");
    
       
    newBox.getChildren().add(newMapLabel);
    Button cancel = new Button();
    cancel.setText("Cancel"); 
    
    newBox.getChildren().add(cancel);
    newBox.setAlignment(Pos.CENTER);
    
    mainContainer.getChildren().add(recentBox);
    mainContainer.getChildren().add(newBox);
    Scene sceneW = new Scene(mainContainer, 400, 400);
    
    // style
    
    Workspace workspace = App.app.getWorkspace();
    Workspace.initStylesheets(sceneW, "cssForWelcomeDialog");
    newMapLabel.getStyleClass().add("menu-label");
    recentLabel.getStyleClass().add("menu-label");
    
    // cancel
    
    cancel.getStyleClass().add("button");
    
    mainContainer.getStyleClass().add("welcome-dialog");
    recentBox.getStyleClass().add("welcome-dialog");
    newBox.getStyleClass().add("welcome-dialog");
    
    Stage welcomeDialogStage = new Stage();
    getRecentFileArrayAndPutInBox(welcomeDialogStage, recentBox, workspace);
    welcomeDialogStage.setTitle("Welcome To MetroMapMaker");
    welcomeDialogStage.setScene(sceneW);
       
    // action:
    cancel.setOnAction(e -> welcomeDialogStage.close());
    
    /// later put it in the controller class
    /// AND ALSO COMPRESS IT; ITS ABSOLUTELY EMBARRASING;
    newMapLabel.setOnMouseClicked(e -> {
                                        // prompts a text dialogue
                                        TextInputDialog a = new TextInputDialog();
                                        a.setTitle("New file name");
                                        a.setHeaderText("Enter the name");
                                        // opens dialogue and gets button
                                        // result is presnt if ok is pressed
                                        Optional<String> result = a.showAndWait();
                                        
                                        // discard null string and cancel or close button
                                        if (result.isPresent() && !(result.get().equals(""))) {
                                            try { // try writing a file
                                                File file = new File("src/saved/" + result.get() +".json");
                                                
                                                if (!file.exists()) {
                                                    // no file with this name;
                                                    // hence, write
                                                    OutputStream out;
                                                    out = new FileOutputStream(file);
                                                    // a new file was created
                                                    App.app.getDataComponent().setCurrentFile(file);
                                                    
                                                    out.close();
                                                }
                                                else {
                                                    // file with such name exists;
                                                    Alert badName = new Alert(Alert.AlertType.WARNING);
                                                    badName.setTitle("File with such name exists");
                                                    badName.showAndWait();
                                                }
                                            } catch (Exception ex) {

                                            }
                                            welcomeDialogStage.close();
                                              
                                       } else { /*// cancel might have been pressed. */ } 
                                        
                                        }
                                  );

    welcomeDialogStage.showAndWait(); 
    
    }
    

    
    
    static void getRecentFileArrayAndPutInBox(Stage stage, VBox box, Workspace workspace) {
        
        // go to that directory 
    File exportDirectory = new File("src/saved/");
        // and extract those files
    File[] recentFiles = exportDirectory.listFiles();
    
    // gently traverse

    for (File f : recentFiles ) {
           // if it is a file and it is a json file
           if (f.isFile() && (f.toString().endsWith(".json"))) {
               // create a label and put mf in the box;
               Label label = new Label (f.getName());
               box.getChildren().add(label);
               // set action
               label.setOnMouseClicked(e -> {
                    try {
                         App.app.getFileComponent().loadFile(f);
                         System.out.println("oeueu");
                         App.app.getDataComponent().setCurrentFile(f);
                         System.out.println("thnth");

                     } catch (Exception ex) {
                     }
                    
                   stage.close();
               
               });
             
           }
      }
    }
    
}
