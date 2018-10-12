/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroControllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class MetroControllersTop {
    
    
    public MetroControllersTop() {
    }
    
    
   
    public void handleNewButton() {
        
        // propose to save the current one (if there is)
        // before making a new one
        
        if (App.app.getDataComponent().getCurrentFile() != null) {
            
            Alert alert = new Alert(AlertType.NONE, "Save current file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            
            // save file if yes was pressed
            if (alert.getResult() == ButtonType.YES) {
                
                try {
                    App.app.getFileComponent().saveFile();
                } catch (Exception ex) {
                    System.out.println("suka");
                }
            }
            if (alert.getResult() == ButtonType.CANCEL) return;
        }
        
            try {
            App.app.getDataComponent().resetData();
            } catch (NullPointerException ex) {}
            App.app.getFileComponent().proposeNewFile();
    }
    
    
    public void handleLoadButton() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File file = fileChooser.showOpenDialog(App.app.getStage());
        try {
            App.app.getFileComponent().loadFile(file);
        } catch (Exception x){}
    }
    
    
    
    
    public void handleSaveButton() {
        try {
            App.app.getFileComponent().saveFile();
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(MetroControllersTop.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void handleSaveAsButton() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File selectedDirectory = chooser.showDialog(App.app.getStage());
        
    }
    
    
    public void handleUndoButton() {
        App.app.getTransactions().popUndo();
    }
    public void handleRedoButton() {
        App.app.getTransactions().popRedo();
    }
    
 
    public void handleAboutButton() {
        // Summons an alert to tell about the app
        // later put into the json
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About MetroMapMaker");
        alert.setHeaderText("MetroMapMaker");
        alert.setContentText("Author: Mark Koshkin" +
                     "\n" + 
                     "Frameworks used: JDK 1.8, javax.json" +
                     "\n" + 
                     "Years of development: 2018");
        alert.showAndWait();
        
    }
    
    
    
    public void handleExitButton() {
        Alert alert = new Alert(AlertType.NONE, "Save current file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        
        
        if (alert.getResult() == ButtonType.YES) {
            try {
                App.app.getFileComponent().saveFile();
            } catch (FileNotFoundException ex) {
            }
            
            App.app.getStage().close();
        }
        else if (alert.getResult() == ButtonType.NO) {
            // no saving
            App.app.getStage().close();
        }
        else {}
            
        }
    
    
    void proposeNewFile() {
        // propose to create a new file

        TextInputDialog a = new TextInputDialog();
        a.setTitle("New file name");
        a.setHeaderText("Enter the name:");
        Optional<String> result = a.showAndWait();
        if (result.isPresent() && !(result.get().equals(""))) {
            // try writing a file
            try { 
                File file = new File("src/saved/" + result.get() + ".json");
                // no file with this name; we can write
                if (!file.exists()) {
                    OutputStream out;
                    out = new FileOutputStream(file);
                    App.app.getWorkspace().getLeftPanel().getLeftPanel().setDisable(false);

                    out.close();
                } else {
                    // file already exists
                    Alert badName = new Alert(AlertType.WARNING);
                    badName.setTitle("File with such name exists");
                    badName.showAndWait();
                }
            } catch (Exception ex) {

            }
            a.close();
        } else {
            // cancel might have been pressed. 
        }
    }

    public void handleExportButton() {
        
        App.app.getFileComponent().export();
        
        
        
    }
    
    public void snapshot() {
        
        Pane pane = App.app.getWorkspace().getCanvasComponent().getCanvas();
        boolean gridVisible = App.app.getWorkspace().getCanvasComponent().getGrid().isVisible();
        App.app.getWorkspace().getCanvasComponent().getGrid().setVisible(false);
        
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

  
    
    File file = App.app.getDataComponent().getCurrentFile();
            new File("src/exp/" + file.getName() + ".png");

    try {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    } catch (IOException e) {
    }
    
   //  app.getWorkspace().getCanvasComponent().getGrid().setVisible(gridVisible);
    

    }
    
}
 
