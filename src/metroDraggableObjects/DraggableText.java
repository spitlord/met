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
import transactions.MoveElement;
import transactions.Transaction;

/**
 *
 * @author XDXD
 */
public class DraggableText implements ChangableFont, Movable {
    
    Label label;
    boolean bold;
    boolean ital;
    Font font;
    
    double startX, startY, translateX, translateY;

    public DraggableText(String text) {
        label = new Label(text);
        
        // font
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font); 
        // color
        
        label.setOnMousePressed(e -> {
            App.app.getTransactions().pushUndo(new MoveElement(this));
            App.app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
            App.app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
            startX = e.getSceneX();
            startY = e.getSceneY();
            
            translateX = label.getTranslateX();
            translateY = label.getTranslateY();       
            label.toFront();
            App.app.getDataComponent().setLastSelectedElement(this);
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
        label.setOnMouseReleased(e -> {
              Transaction temp = App.app.getTransactions().peekUndo();
              if (temp instanceof MoveElement) {
                  if (((MoveElement) temp).getMovable() == this &&
                       ((MoveElement) temp).getX() == label.getTranslateX() &&
                       ((MoveElement) temp).getY() == label.getTranslateY()) {
                      App.app.getTransactions().popUndoWithouAction();
                  }
              }
        });
        App.app.getDataComponent().getText().add(this);
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(label);
    }
    
    
    
    public void remove() {
         App.app.getDataComponent().getText().remove(this);
         App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(label);
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
          label.setTextFill(c);    
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

    public boolean isBold() {
        return bold;
    }

    public boolean isItal() {
        return ital;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return (Color) label.getTextFill();
    }

    public String getText() {
        return label.getText();
    }

    public Label getLabel() {
        return label;
    }
    
    
    @Override
    public void setX(double x) {
         label.setTranslateX(x);
    }
    @Override
    public void setY(double y) {
         label.setTranslateY(y);
    }

    @Override
    public double getX() {
         return label.getTranslateX();    }

    @Override
    public double getY() {
        return label.getTranslateY();
    }

    @Override
    public Movable getMovable() {
        return this;
    }
    
    
    
    
    
  
    
      
    
        
    
}
