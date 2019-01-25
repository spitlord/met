package transactions;

import canvasObjects.Station;
import java.util.ArrayList;


public class RemoveStationT implements Transaction {
    Station station;
    ArrayList<Transaction> removeFromLine;

    public RemoveStationT(Station station) {
        this.station = station;
        removeFromLine = new ArrayList();
        
        station.getLines().forEach(line -> {
            if (line.isCircular()) {
                removeFromLine.add(new RemoveStationFromLineCircularT(line, station));
            }
            else {
                removeFromLine.add(new RemoveStationFromLineT(line, station));
            }
        });
    }
    
    @Override
    public void undo() {
        removeFromLine.forEach(t -> t.undo());
        station.add();
    }

    @Override
    public void redo() {
        removeFromLine.forEach(t -> t.redo());
        station.remove();
    }
    
}
