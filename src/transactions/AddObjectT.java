/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.Addable;

public class AddObjectT implements Transaction {
    Addable addable;

    public AddObjectT(Addable addable) {
        this.addable = addable;
    }

    
    @Override
    public void undo() {
        addable.remove();
    }

    @Override
    public void redo() {
        addable.add();
    }
    
}
