
package transactions;

import javafx.scene.paint.Color;
import canvasObjects.Station;

public class EditStation implements Transaction {
    
    Station station;
    double radius;
    Color color;
    int labelPosition, labelRotation;

    public EditStation(Station station) {
        this.station = station; 
        this.radius = station.getCircle().getRadius();
        this.color = station.getColor();
        this.labelPosition = station.getLabelPosition();
        this.labelRotation = station.getLabelRotation();
    }

    @Override
    public void undo() {
        int temp = station.getLabelPosition();
        station.setLabelPosition(labelPosition);
        labelPosition = temp;
        
        temp = station.getLabelRotation();
        station.setLabelRotation(labelRotation);
        labelRotation = temp;
        
        Color c = station.getColor();
        station.changeColor(color);
        color = c;
        
        double rad = station.getCircle().getRadius();
        station.getCircle().setRadius(radius);
        radius = rad;
    }

    @Override
    public void redo() {
        undo();
    }

    public Station getStation() {
        return station;
    }

    public double getRadius() {
        return radius;
    }
    
}
