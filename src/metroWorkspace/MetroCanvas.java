/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroWorkspace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import metroApp.App;
import metroControllers.MetroCanvasControllers;

/**
 *
 * @author XDXD
 */
public class MetroCanvas {
    
        // placed in the border pane and it does not change size or move
    private Pane canvasHolder;
    // can be zoomed, increaced in size, without changing the border pane
    private Pane canvas;
    private MetroCanvasControllers controllers;
    private Group grid;
    private boolean showGrid;
    

    
    public MetroCanvas() {

    }
    
    /**
     * @return the canvasHolder
     */
    public Pane getCanvasHolder() {
        return canvasHolder;
    }

    /**
     * @return the canvas
     */
    public Pane getCanvas() {
        return canvas;
    }

    public Group getGrid() {
        return grid;
    }
    
    
    public void makeGrid() {
        grid = new Group();
        Line line;
        for (int i = 0; i < 200; i++) {
            line = new Line(0,i*50,10000,i*50);
            grid.getChildren().add(line);
        } 
        
        for (int j = 0; j < 200; j++) {
            line = new Line(j*50,0,j*50,10000);
            grid.getChildren().add(line);
        }
        // do not show grid yet 
        showGrid = false;
        grid.setVisible(showGrid);
        
        // add grid to canvas
        canvas.getChildren().add(grid);
    }
    
    public MetroCanvas initCanvas(){
        // place the canvas into the holder
        // this is done in order to 

        
        canvasHolder = new Pane();
        canvasHolder.setStyle("-fx-background-color: black;");
        
        canvas = new Pane();
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        canvas.setPrefWidth(700);
        canvas.setPrefHeight(700);
        
        canvas.setOnMousePressed(e -> {
            if (e.getTarget() == canvas) {
                App.app.getDataComponent().getMetroLines().forEach(line -> {
                line.getBeginning().getCircle().setVisible(false);
                line.getEnd().getCircle().setVisible(false);
                    for (int i = 0; i < line.getConnections().size(); i++) {
                        line.getConnections().get(i).getControl().setVisible(false);    
                    }
                    
                    try {
                    App.app.getDataComponent().getSelectedConnection().getControl().setVisible(false);
                    } catch (NullPointerException ex) {}
                    App.app.getDataComponent().setSelectedConnection(null);
                } );
            }
            
        });
        
        
        // create the grid, set invisible
       
        
        makeGrid();
        
        canvasHolder.getChildren().add(canvas);
        //try
        
        initControllers();
        return this;
    }

    /**
     * @return the canvasColor
     */

    //public void 
    
    public void addGrid() {
        canvas.getChildren().add(grid);
    }
    
               
        // add grid to canvas}
    
    
     public void showGrid() {
        showGrid = !showGrid;
        grid.setVisible(showGrid);
    }
        
    

    /**
     * @param canvasColor the canvasColor to set
     */
    public void setCanvasColor(Color c) {
        
        App.app.getWorkspace().getCanvasComponent().getCanvas().
                setStyle("-fx-background-color: #" +
                        c.toString().substring(2, 8) +
                        ";");
    }
    
    public void initControllers() {
        controllers = new MetroCanvasControllers(App.app);
        
        
        App.app.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A)   MetroCanvasControllers.aDown = true;
            else if (e.getCode() == KeyCode.D)   MetroCanvasControllers.dDown = true;
            else if (e.getCode() == KeyCode.S)   MetroCanvasControllers.sDown = true;
            else if (e.getCode() == KeyCode.W)   MetroCanvasControllers.wDown = true;
            controllers.handleNavigateCanvas(e);

            

        }
        );
        App.app.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A)   MetroCanvasControllers.aDown = false;
            else if (e.getCode() == KeyCode.D)   MetroCanvasControllers.dDown = false;
            else if (e.getCode() == KeyCode.S)   MetroCanvasControllers.sDown = false;
            else if (e.getCode() == KeyCode.W)   MetroCanvasControllers.wDown = false;
        
        });
        
        

    }
    
    public Color getCanvasColor() {
        String s = canvas.getStyle();
        s = s.substring(s.length() - 7,s.length()-1);
        Color c = Color.valueOf(s);
        return c;
    }
    
}