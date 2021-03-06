package transactions;

import canvasObjects.MetroLine;


public class CirculateT implements Transaction {
    MetroLine line;

    public CirculateT(MetroLine line) {
        this.line = line;
    }
    
    

    @Override
    public void undo() {
        line.decirculate();
        
    }

    @Override
    public void redo() {
        line.circulate();
    }
    

}
