/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import javafx.scene.paint.Color;
import canvasObjects.MetroLine;

/**
 *
 * @author spitlord
 */
public class EditLine implements Transaction {
    
    MetroLine line;
    String name;
    double thickness;
    Color color;
    
    

    public EditLine(MetroLine line) {
        this.line = line; 
        this.name = line.getName();
        this.thickness = line.getLineThickness();
        this.color = line.getColor();
    }
      
   
    
    

    @Override
    public void undo() {
        
        String temp = line.getName();
        line.setName(name);
        name = temp;
        
        Color c = line.getColor();
        line.changeColor(color);
        color = c;
        
        double thick = line.getLineThickness();
        line.changeLineThickness(thickness);
        thickness = thick;
        
        
    
    }

    @Override
    public void redo() {
        undo();
    }

  

    public double getThickness() {
        return thickness;
    }
    
}
