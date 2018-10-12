/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author spitlord
 */
public interface ChangableFont {
    
 
    public void changeFontBold();
    public void changeFontItal();
    public void changeFontSize(double d);
    public void changeFontFamily(String s);
    public void changeFontColor(Color c);
    public Label getLabel();
    
}
