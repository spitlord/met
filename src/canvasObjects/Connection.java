/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import metroApp.App;
import workspace.MetroCanvas;
import transactions.MoveElement;
import transactions.Transaction;

/**
 *
 * @author XDXD
 */
public class Connection implements Movable {
    
    
    QuadCurve connectionCurve;
    MetroLine metroLine;
    Circle control;
    Station a, b;
    double distance;
    
    private double startX, startY;
    
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

        connectionCurve.setFill(Color.TRANSPARENT);
        connectionCurve.setStrokeWidth(metroLine.lineThickness);
        connectionCurve.setStroke(metroLine.color);
    }
    
     private void bindConnection() {
        connectionCurve.startXProperty().bind(a.getCircle().centerXProperty());
        connectionCurve.startYProperty().bind(a.getCircle().centerYProperty());
        connectionCurve.endXProperty().bind(b.getCircle().centerXProperty());
        connectionCurve.endYProperty().bind(b.getCircle().centerYProperty());
        connectionCurve.controlXProperty().bind(control.centerXProperty());
        connectionCurve.controlYProperty().bind(control.centerYProperty());
        control.setVisible(false);
        control.setCenterX((connectionCurve.getEndX() + connectionCurve.getStartX())/2);
        control.setCenterY((connectionCurve.getEndY() + connectionCurve.getStartY())/2);
    }
    
    private void setUpControls() {
        connectionCurve.setOnMousePressed(e -> {
                  App.app.getWorkspace().getLeftPanel().getLineColorPicker().setValue((Color)connectionCurve.getStroke());
        });
        connectionCurve.setOnMouseClicked(e -> {
            // show the control circle and select the metroLine
            if (e.getTarget() == connectionCurve) {
                App.app.getDataComponent().setSelectedLine(metroLine);
                metroLine.showEnds();
                try {
                    App.app.getDataComponent().getSelectedConnection().getControl().setVisible(false);
                } catch (NullPointerException ex) {}
                App.app.getDataComponent().setSelectedConnection(this);
            }
            e.consume();
        });
        control.setOnMouseDragged(e -> {
            control.setCenterX(e.getX());
            control.setCenterY(e.getY());
            e.consume();
        });
        control.setOnMousePressed(e -> {
            startX = control.getCenterX();
            startY = control.getCenterY();
            App.app.getTransactions().pushUndo(new MoveElement(this));

        });
        control.setOnMouseDragged(e -> {
            control.setCenterX(e.getX()+2);
            control.setCenterY(e.getY()+2);
        e.consume();
        });
        
       control.setOnMouseReleased(e -> {
           if  (startX == control.getCenterX() || startY == control.getCenterY()) 
               App.app.getTransactions().popUndoWithouAction();
       });
    }

    void connect(Station a, Station b) {
        this.a = a; 
        this.b = b;
        a.getNeighbors().add(b);
        b.getNeighbors().add(a); 
        bindConnection();
        setUpControls();
    }
    
    
    public void disconnect () {
        metroLine.getConnections().remove(this);
        
        MetroCanvas metroCanvas  = App.app.getWorkspace().getCanvasComponent();
        metroCanvas.getCanvas().getChildren().remove(connectionCurve);
        metroCanvas.getCanvas().getChildren().remove(control);
        
        a.getNeighbors().remove(b);
        b.getNeighbors().remove(a);
    }
    
    private void center() {
        double centerX = (a.getCircle().getCenterX()
                + b.getCircle().getCenterX()) / 2.0;
        double centerY = (a.getCircle().getCenterY()
                + b.getCircle().getCenterY()) / 2.0;
        control.setCenterX(centerX);
        control.setCenterY(centerY);
    }
    
    
    public void add() {
        add(metroLine.getConnections().size());
    }
    
    public void add(int index) {
        metroLine.getConnections().add(index, this);
        MetroCanvas metroCanvas = App.app.getWorkspace().getCanvasComponent();
        metroCanvas.getCanvas().getChildren().addAll(connectionCurve, control);
        connectionCurve.toBack();
    }

    public Circle getControl() {
        return control;
    }

    public QuadCurve getCurve() {
        return connectionCurve;
    }
    
    public void hideControl() {
        control.setVisible(false);
    }
    public void hideCurve() {
        connectionCurve.setVisible(false);
    }
    
    public void showCurve() {
        connectionCurve.setVisible(true);
    }
    
    public void showControl() {
        control.setVisible(true);
    }
    
    @Override
    public void setX(double x) {
        control.setCenterX(x);
    }
    @Override
    public void setY(double y) {
        control.setCenterY(y);
    }

    @Override
    public double getX() {
        return control.getCenterX();    
    }

    @Override
    public double getY() {
        return control.getCenterY();
    }

    @Override
    public Movable getMovable() {
        return this;
    }
    
    @Override
    public String toString() {
        return a.getName() + ", " + b.getName();
    }
    

}
