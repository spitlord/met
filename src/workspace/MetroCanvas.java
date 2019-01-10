/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workspace;

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
    private final Pane canvasHolder;
    // can be zoomed, increaced in size, without changing the border pane
    private Pane canvas;
    private MetroCanvasControllers controllers;
    private Group grid;
    private boolean showGrid;
    

    
    public MetroCanvas() {
    
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
    }
    
    /**
     * @return the canvasHolder
     */
    public Pane getCanvasHolder() {
        return canvasHolder;
    }


    public Pane getCanvas() {
        return canvas;
    }

    public Group getGrid() {
        return grid;
    }
    
    
    private void makeGrid() {
        grid = new Group();
        Line line;
        for (int i = 0; i < 200; i++) {
            line = new Line(0, i*50, 10000, i*50);
            grid.getChildren().add(line);
        } 
        
        for (int j = 0; j < 200; j++) {
            line = new Line(j*50, 0, j*50, 10000);
            grid.getChildren().add(line);
        }
        // do not show grid yet 
        showGrid = false;
        grid.setVisible(showGrid);
        
        // add grid to canvas
        canvas.getChildren().add(grid);
    }
    
    public void addGrid() {
        canvas.getChildren().add(grid);
    }
    
    
     public void showGrid() {
        showGrid = !showGrid;
        grid.setVisible(showGrid);
    }
     
     
    public void setCanvasColor(Color c) {
        
        App.app.getWorkspace().getCanvasComponent().getCanvas().
                setStyle("-fx-background-color: #" +
                        c.toString().substring(2, 8) +
                        ";");
    }
    
    private  void initControllers() {
        controllers = new MetroCanvasControllers(App.app);
        
        
        App.app.getScene().setOnKeyPressed(e -> {
            if (null != e.getCode())   {
                switch (e.getCode()) {
                    case A:
                        MetroCanvasControllers.aDown = true;
                        break;
                    case D:
                        MetroCanvasControllers.dDown = true;
                        break;
                    case S:
                        MetroCanvasControllers.sDown = true;
                        break;
                    case W:
                        MetroCanvasControllers.wDown = true;
                        break;
                    default:
                        break;
                }
            }
            controllers.handleNavigateCanvas(e);
        });
        
        App.app.getScene().setOnKeyReleased(e -> {
            if (null != e.getCode()) {  
                switch (e.getCode()) {
                case A:
                    MetroCanvasControllers.aDown = false;
                    break;
                case D:
                    MetroCanvasControllers.dDown = false;
                    break;
                case S:
                    MetroCanvasControllers.sDown = false;
                    break;
                case W:
                    MetroCanvasControllers.wDown = false;
                    break;
                default:
                    break;
                }
            }
        
        });
        
        

    }
    
    public Color getCanvasColor() {
        String s = canvas.getStyle();
        s = s.substring(s.length() - 7,s.length()-1);
        Color c = Color.valueOf(s);
        return c;
    }

    public void zoomOut() {
        canvas.setScaleX(canvas.getScaleX() / 1.1);
        canvas.setScaleY(canvas.getScaleY() / 1.1);
    }
    
    public void zoomIn() {
        canvas.setScaleX(canvas.getScaleX() * 1.1);
        canvas.setScaleY(canvas.getScaleY() * 1.1);
    }
    
    public boolean enlargeMap() {
        if (canvas.getWidth() < 10000 &&  canvas.getHeight() < 10000) {
        canvas.setPrefSize(canvas.getWidth() * 1.1, canvas.getHeight() * 1.1); 
        return true;
        }
        return false;
    }
    
    public boolean reduceMap() {
        if (canvas.getWidth() >= 200 && canvas.getHeight() >= 200) {
            canvas.setPrefSize(canvas.getWidth() / 1.1, canvas.getHeight() / 1.1);
            return true;
        }
        return false;
    }
    
}