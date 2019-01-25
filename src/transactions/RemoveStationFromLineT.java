/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.Connection;
import canvasObjects.MetroLine;
import canvasObjects.Station;

/**
 *
 * @author spitlord
 */
public class RemoveStationFromLineT implements Transaction {
    
    MetroLine line;
    Station station;
    int index;
    Connection oldA, oldB;
    Connection newConnection;
    boolean circular;


    public RemoveStationFromLineT(MetroLine line, Station station) {
        this.station = station;
        this.line = line;
        index = line.getStations().indexOf(station);
        oldA = line.getConnections().get(index - 1);
        oldB = line.getConnections().get(index);
        
    }
    
    @Override
    public void undo() {
        line.addStation(station, index);
        Connection tempA = line.getConnections().get(index - 1);
        Connection tempB = line.getConnections().get(index);
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
