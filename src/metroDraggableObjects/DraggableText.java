/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class DraggableText implements ChangeFont {
    App app;
    
    String text;
    Label label;
    boolean bold;
    boolean ital;
    Color color;
    Font font;
    
    double startX, startY, translateX, translateY;

    public DraggableText(App app, String text) {
        
        this.app = app;
      
            

        label = new Label(text);
        
        // font
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font); 
        // color
        color = Color.BLACK;
        label.setTextFill(color);
        
        label.setOnMousePressed(e -> {
            app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
            app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
            startX = e.getSceneX();
            startY = e.getSceneY();
            
            translateX = label.getTranslateX();
            translateY = label.getTranslateY();       
            label.toFront();
            app.getDataComponent().setLastSelectedElement(this);
        });
        label.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - startX;
            double offsetY = e.getSceneY() - startY;
            double finalTranslateX = translateX + offsetX;
            double finalTranslateY = translateY + offsetY;
            
            label.setTranslateX(finalTranslateX);
            label.setTranslateY(finalTranslateY);
            
            e.consume();
        });
     
        
        app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(label);
        
    }
    
    
    
      public void changeFontFamily(String family) {
          
          Font oldFont = label.getFont();
          this.font =  (Font.font(family,
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
          
      }
      
      public void changeFontSize(double size) {
          Font oldFont = label.getFont();
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  size));
          label.setFont(font);
      }
      
      public void changeFontColor (Color c) {
          this.color = c;
          label.setTextFill(color);    
      }
      
      public void changeFontBold() {
          Font oldFont = label.getFont();
          bold = !bold;
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
      }
      
      public void changeFontItal() {
          Font oldFont = label.getFont();
          ital = !ital;
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
      }
    
        
    
}
