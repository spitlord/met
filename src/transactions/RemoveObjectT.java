/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.Addable;

/**
 *
 * @author spitlord
 */
    
public class RemoveObjectT implements Transaction {
    Addable addable;

    public RemoveObjectT(Addable addable) {
        this.addable = addable;
    }

    
    @Override
    public void undo() {
        addable.add();
    }

    @Override
    public void redo() {
        addable.remove();
    }
    
}
