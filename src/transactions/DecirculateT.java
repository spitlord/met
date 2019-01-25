package transactions;

import canvasObjects.MetroLine;


public class DecirculateT implements Transaction {
    MetroLine line;

    public DecirculateT(MetroLine line) {
        this.line = line;
    }
    
    

    @Override
    public void undo() {
        line.circulate();
    }

    @Override
    public void redo() {
        line.decirculate();
    }
    

}
