/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroWorkspace;

import javafx.scene.Group;
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
    private Color canvasColor;
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
        canvasColor = Color.BLUE;
        canvas.setStyle("-fx-background-color: #ffffff;");
        

        
        
        // create the grid, set invisible
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
        
        
    

        
        canvasHolder.getChildren().add(canvas);
        //try
        
        initControllers();
        return this;
    }

    /**
     * @return the canvasColor
     */
    public Color getCanvasColor() {
        return canvasColor;
    }
    
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
    public void setCanvasColor(Color canvasColor) {
        this.canvasColor = canvasColor;
    }
    
    public void initControllers() {
        controllers = new MetroCanvasControllers(App.app);
        App.app.getScene().setOnKeyPressed(e -> {controllers.handleNavigateCanvas(e.getCode());});
    }
    
}