package transactions;

import canvasObjects.Connection;
import canvasObjects.MetroLine;
import canvasObjects.Station;


public class RemoveStationFromLineCircularT implements Transaction {
    
    MetroLine line;
    Station station;
    int index;
    Connection oldA, oldB;
    Connection newConnection;
    boolean circular;


    public RemoveStationFromLineCircularT(MetroLine line, Station station) {
        this.station = station;
        this.line = line;
        index = line.getStations().indexOf(station);
        if (index == 1) {
            oldA = line.getConnections().get(line.getConnections().size() - 1);
        }
        else {
            oldA = line.getConnections().get(index - 2);
        }
        oldB = line.getConnections().get(index - 1);
    }

    
    @Override
    public void undo() {
        
        
        Connection tempA, tempB;
        
        if (index == 1) {
            line.addStation(station, -1);
            tempA = line.circleArc;
            tempB = line.getConnections().get(0);
        }
        else {
            line.addStation(station, index - 1);
            tempA = line.getConnections().get(index - 2);
            tempB = line.getConnections().get(index - 1);
        }
      
        tempA.getControl().setCenterX(oldA.getControl().getCenterX());
        tempA.getControl().setCenterY(oldA.getControl().getCenterY());
        tempB.getControl().setCenterX(oldB.getControl().getCenterX());
        tempB.getControl().setCenterY(oldB.getControl().getCenterY());
        
    }

    @Override
    public void redo() {
        line.removeStationFromLine(station);
    }
    
}
