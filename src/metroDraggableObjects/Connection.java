/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import metroApp.App;
import metroWorkspace.MetroCanvas;

/**
 *
 * @author XDXD
 */
public class Connection {

 
    QuadCurve connectionCurve;
    MetroLine metroLine;
    Circle control;
    
    Connection() {}


    public Connection(MetroLine metroLine) {
        
        // set metroline for if curve is clicked, then the line is selected
        this.metroLine = metroLine;
        
        connectionCurve = new QuadCurve();
        
        // control circle
        control = new Circle(7);
        control.setFill(Color.TRANSPARENT);
        control.setStroke(Color.BLACK);
        control.setStrokeWidth(3);
        // connection curve
        connectionCurve.setFill(Color.TRANSPARENT);
        connectionCurve.setStroke(Color.BLACK);
        
        
 
        
        
    }

    void connect(Station a, Station b) {
        
        // get the canvas
        MetroCanvas metroCanvas  = App.app.getWorkspace().getCanvasComponent();
        
        // match the thickness and color
        connectionCurve.setStrokeWidth(metroLine.lineThickness);
        connectionCurve.setStroke(metroLine.color);

        // bind the ends of the curve to the stations
        connectionCurve.startXProperty().bind(a.getCircle().centerXProperty());
        connectionCurve.startYProperty().bind(a.getCircle().centerYProperty());
        connectionCurve.endXProperty().bind(b.getCircle().centerXProperty());
        connectionCurve.endYProperty().bind(b.getCircle().centerYProperty());
        
        // use controll
        connectionCurve.controlXProperty().bind(control.centerXProperty());
        connectionCurve.controlYProperty().bind(control.centerYProperty());
        control.setVisible(false);
        
        control.setCenterX((connectionCurve.getEndX()+ connectionCurve.getStartX())/2);
        control.setCenterY((connectionCurve.getEndY()+ connectionCurve.getStartY())/2);
        
        connectionCurve.setOnMouseClicked(e -> {
            // show the control circle and select the metroLine
            if (e.getTarget() == connectionCurve) {
                control.setVisible(true);
                
                // select line that connection belongs to
                App.app.getDataComponent().setSelectedLine(metroLine);
                
                // show the line ends
                metroLine.getBeginning().getCircle().setVisible(true);
                metroLine.getEnd().getCircle().setVisible(true);
                
                // unselect the previous control circle!
                try {
                    App.app.getDataComponent().getSelectedConnection().getControl().setVisible(false);
                } catch (NullPointerException ex) {}
                
                // make it selected
                App.app.getDataComponent().setSelectedConnection(this);
                
                
            }
            
            
            e.consume();
        });
        control.setOnMouseDragged(e -> {
            control.setCenterX(e.getX());
            control.setCenterY(e.getY());
            e.consume();
        });
        
        control.setOnMouseReleased(e -> {
            control.setCenterX(e.getX());
            control.setCenterY(e.getY());
            control.setVisible(true);
            e.consume();
        });
        
        metroCanvas.getCanvas().getChildren().addAll(connectionCurve, control);
       
        // put behind the circle not to obstract the dragging
        connectionCurve.toBack();
    }
    
    
    
    public void disconnect () {
        
        // get the canvas
        MetroCanvas metroCanvas  = App.app.getWorkspace().getCanvasComponent();
        
        // remove from the root
        metroCanvas.getCanvas().getChildren().remove(connectionCurve);
        metroCanvas.getCanvas().getChildren().remove(control);
    }

    public Circle getControl() {
        return control;
    }

    public QuadCurve getConnectionCurve() {
        return connectionCurve;
    }

    
    
   
    
    
    
}
