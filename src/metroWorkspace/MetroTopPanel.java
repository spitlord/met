/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroWorkspace;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import metroApp.App;
import metroControllers.MetroControllersTop;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



/**
 *
 * @author XDXD
 */
public class MetroTopPanel {
    // Top
    private MetroControllersTop controllers;
    
    
    private HBox topPanel;  // Container for all the buttons and button containers;
    
    private HBox fileToolBar;
    private Button newButton;
    private Button loadButton;
    private Button saveButton;
    private Button saveAsButton;
    private Button exportButton;
            
    private HBox undoRedoToobar;
    private Button undoButton;
    private Button redoButton;
    
    private HBox aboutToolbar;
    private Button aboutButton;
    
    private Button exitButton;
    
    
    public MetroTopPanel() {
    }
    
    
 
    
    public HBox initPanel() {
        
        
        fileToolBar = new HBox();
        newButton = new Button();
        loadButton = new Button();
        saveButton = new Button();
        saveAsButton  = new Button();
        exportButton = new Button();
        
        initButton(newButton, fileToolBar, "newButtonIcon", "newButtonTooltip");
        initButton(loadButton, fileToolBar, "loadButtonIcon", "loadButtonTooltip");
        initButton(saveButton, fileToolBar, "saveButtonIcon", "saveButtonTooltip");
        initButton(saveAsButton, fileToolBar, "saveAsButtonIcon", "saveAsButtonTooltip");
        initButton(exportButton, fileToolBar, "exportButtonIcon", "exportButtonTooltip");
        
        undoRedoToobar = new HBox();
        undoButton = new Button();
        redoButton = new Button();
        initButton(undoButton, undoRedoToobar, "undoButtonIcon", "undoButtonTooltip");
        initButton(redoButton, undoRedoToobar, "redoButtonIcon", "redoButtonTooltip");
        
        aboutToolbar = new HBox();
        aboutButton = new Button();
        initButton(aboutButton, aboutToolbar, "aboutButtonIcon", "aboutButtonTooltip");
        
     
        
        
        // add all of those things onto one panel and return it
     
        topPanel = new HBox();
        topPanel.getChildren().add(fileToolBar);
        topPanel.getChildren().add(undoRedoToobar);
        topPanel.getChildren().add(aboutToolbar);
      
        
        
        Rectangle rect = new Rectangle(0,0,200,1);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.TRANSPARENT);
        topPanel.getChildren().add(rect);
        


        exitButton = new Button();
        initButton(exitButton, topPanel, "exitIcon", "exitTooltip");
        
       /// exitButton.
        // styles for containers 
        initStyles();
        initControllers();
        return topPanel;
    }
    
    
    // sets event listeners
    private void initControllers() {
        
       //controllers = app.getTopControllers();
       controllers = new MetroControllersTop();
       
       aboutButton.setOnAction(e -> {controllers.handleAboutButton();
       } );
       
       newButton.setOnAction(e -> {controllers.handleNewButton();
       } );
       loadButton.setOnAction(e -> {controllers.handleLoadButton();
       } );
       saveButton.setOnAction(e -> {controllers.handleSaveButton();
       } );
       saveAsButton.setOnAction(e -> {controllers.handleSaveAsButton();
       } );
       exitButton.setOnAction(e -> {controllers.handleExitButton();    
       } );
       exportButton.setOnAction(e -> {controllers.handleExportButton();    
       } ); 
       
       undoButton.setOnAction(e -> {controllers.handleUndoButton();
       } );
       redoButton.setOnAction(e -> {controllers.handleRedoButton();
       } );
   
        
    }
    
    private void initButton(Button button, Pane pane, String icon, String tooltip ) {
        
        // icon
        button.setGraphic(new ImageView(new Image("file:" + App.app.getProperties().getString(icon))));
        
        // tooltip 
        button.setTooltip(new Tooltip(App.app.getProperties().getString(tooltip)));
        // style from css file
        button.getStyleClass().add("button");
        pane.getChildren().add(button);
    
    }
    
    private void initStyles() {
        topPanel.getStyleClass().add("topPanel"); 
        fileToolBar.getStyleClass().add("topPanelContent");
        undoRedoToobar.getStyleClass().add("topPanelContent");
        aboutToolbar.getStyleClass().add("topPanelContent");       

    }
    
    
    
    
    
}
