/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.Connection;
import canvasObjects.MetroLine;
import canvasObjects.Station;
import java.util.HashMap;

/**
 *
 * @author spitlord
 */
public class RemoveStationT implements Transaction {
    Station station;
    HashMap<MetroLine, StoredConnection> connectionStorage;
    
    class StoredConnection {
        Connection oldA, oldB;
        Connection newConnection;
        int index;
    }

    public RemoveStationT(Station station) {
        this.station = station;
        
        connectionStorage = new HashMap<>();
        
        station.getLines().forEach(line -> {
            
            StoredConnection stored = new StoredConnection();
            stored.index = line.getStations().indexOf(station);
            stored.oldA = line.getConnections().get(stored.index - 1);
            stored.oldB = line.getConnections().get(stored.index);
            connectionStorage.put(line, stored);
        });
    }
    
    
    @Override
    public void undo() {
        connectionStorage.forEach((line, stored) ->  {
            stored.newConnection = line.getConnections().get(stored.index - 1);
            line.addStation(station, stored.index);
            line.getConnections().get(stored.index-1).getControl().setCenterX(
                    stored.oldA.getControl().getCenterX());
            line.getConnections().get(stored.index-1).getControl().setCenterY(
                    stored.oldA.getControl().getCenterY());
            line.getConnections().get(stored.index).getControl().setCenterX(
                    stored.oldB.getControl().getCenterX());
            line.getConnections().get(stored.index).getControl().setCenterY(
                    stored.oldB.getControl().getCenterY());
        }); 
        station.add();
    }

    @Override
    public void redo() {
        connectionStorage.forEach((MetroLine line, StoredConnection stored) -> {
            line.removeStationFromLine(station);
        });
        station.remove();
    }
    
}
