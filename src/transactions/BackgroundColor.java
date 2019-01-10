/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import javafx.scene.paint.Color;
import metroApp.App;

/**
 *
 * @author spitlord
 */
public class BackgroundColor  implements  Transaction {
    
    Color color;

    public BackgroundColor() {
        this.color = App.app.getWorkspace().getCanvasComponent().getCanvasColor();
    }

    @Override
    public void undo() {
        Color temp = App.app.getWorkspace().getCanvasComponent().getCanvasColor();
        App.app.getWorkspace().getCanvasComponent().setCanvasColor(color);
        color = temp;


        
    }

    @Override
    public void redo() {
        undo();
    }
    
    
}
