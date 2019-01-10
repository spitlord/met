/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.MetroLine;

public class AddLineT implements Transaction {
    MetroLine line;

    public AddLineT(MetroLine line) {
        this.line = line;
    }

    
    @Override
    public void undo() {
        line.getBeginning().remove();
        line.getEnd().remove();
        line.remove();
    }

    @Override
    public void redo() {
        
    }
    
}
