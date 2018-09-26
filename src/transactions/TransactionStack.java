/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.util.ArrayList;

/**
 *
 * @author spitlord
 */
public class TransactionStack {

    public final static int UNDO_LIMIT = 20;
    ArrayList<Transaction> undoStack;
    ArrayList<Transaction> redoStack;

    public TransactionStack() {
        undoStack = new ArrayList();
        redoStack = new ArrayList();
    }

    public void pushUndo(Transaction transaction) {

        if (undoStack.size() >= UNDO_LIMIT) {
            undoStack.remove(0);
        }
        undoStack.add(transaction);
        redoStack.clear();
    }
    
    public void pushUndoFromRedo(Transaction transaction) {

        if (undoStack.size() >= UNDO_LIMIT) {
            undoStack.remove(0);
        }
        undoStack.add(transaction);
    }
    
    public Transaction peekUndo() {
        return undoStack.get(undoStack.size()-1);
    }
    
    
    public void popUndoWithouAction() {
         undoStack.remove(undoStack.size()-1);
    }
    

    public Transaction popUndo() {
        if (undoStack.size() == 0) return null;
        if (undoStack.size() >= UNDO_LIMIT) {
            undoStack.remove(0);
        }
       
        
        Transaction t = undoStack.remove(undoStack.size() - 1);
        t.undo();
        pushRedo(t);

        return t;

    }

    public void pushRedo(Transaction transaction) {
       

        if (redoStack.size() >= UNDO_LIMIT) {
            undoStack.remove(0);
        }
        redoStack.add(transaction);
    }

    public Transaction popRedo() {
        if (redoStack.size() == 0) return null;
        if (redoStack.size() >= UNDO_LIMIT) {
            redoStack.remove(0);
        }
        
        Transaction t = redoStack.remove(redoStack.size()-1);
        t.redo();
        pushUndoFromRedo(t);
        return t;
        
    }
    
    
 
    

    public void undoTransaction() {
    }

    public void redoTransaction() {

    }

}
