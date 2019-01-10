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
public class AddStationToLineT implements Transaction {
    Station station;
    MetroLine line;

    public AddStationToLineT(MetroLine line, Station station) {
        this.station = station;
        this.line = line;
    }
    
    @Override
    public void undo() {
        line.removeStationFromLine(station);
    }

    @Override
    public void redo() {
        line.addStationNearest(station);
    }
    
    
}
