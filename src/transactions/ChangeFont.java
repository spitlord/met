/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author spitlord
 */
public class ChangeFont implements Transaction {
    
    Label label;
    Color c;
    Font font;
    
    public ChangeFont(Label label) {
        this.label = label;
        c = (Color)label.getTextFill();
        font = label.getFont();
    }
    

    @Override
    public void undo() {
        Font temp1 = label.getFont();
        label.setFont(font);
        font = temp1;
        
        Color temp2 = (Color)label.getTextFill();
        label.setTextFill(c);
        c = temp2;
        
    }

    @Override
    public void redo() {
        undo();
    }
    
    
    
    
    
    
}
