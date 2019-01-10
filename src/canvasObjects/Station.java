/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasObjects;

import java.util.ArrayList;
import javafx.scene.control.Label;;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import metroApp.App;
import metroData.MetroData;
import transactions.MoveElement;
import transactions.Transaction;

public final class Station implements ChangableFont, ChangableColor, Movable, Addable{
    
    
    final static int labelDistance = 25;
   
    String name;
    Circle circle;
    Label label;
    Font font;
    boolean bold;
    boolean ital;
    Color color;
    Color labelColor;
    boolean endOfLine;
   
    
    int labelPosition;
    int labelRotation;
    ArrayList<MetroLine> lines;
    ArrayList<Station> neighbors;
    
    
    public Station(String name, boolean endOfLine) {
        
        neighbors = new ArrayList();
        lines =  new ArrayList();
        
        this.name = name;
        this.endOfLine = endOfLine;
        
        //create label
        label = new Label(name);
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font);
        labelColor = Color.BLACK;
        label.setTextFill(labelColor);
        
        
        // create circle that represesnts the station
        circle = new Circle(15);
        color = App.app.getDataComponent().getCurrentStationColor();
        changeColor(color);
        
        // locate 
        circle.setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight()/2);
        circle.setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getWidth() /2);
        
        // on dragged change location
        if (!endOfLine) {
            circle.setOnMousePressed(e -> {
                App.app.getWorkspace().getLeftPanel().getStationColorPicker().setValue(color);
                App.app.getTransactions().pushUndo(new MoveElement(this));
            });
        }
        else {
            circle.setOnMousePressed(e -> {
                App.app.getTransactions().pushUndo(new MoveElement(this));
            });
        }
        
        circle.setOnMouseDragged(e -> {
        circle.setCenterX(e.getX()+2);
        circle.setCenterY(e.getY()+2);
        e.consume();
        });
        
        circle.setOnMouseReleased(e -> {
              Transaction temp = App.app.getTransactions().peekUndo();
              if (temp instanceof MoveElement) {
                  if (((MoveElement) temp).getMovable() == this &&
                       ((MoveElement) temp).getX() == circle.getCenterX() &&
                       ((MoveElement) temp).getY() == circle.getCenterY()) {
                      App.app.getTransactions().popUndoWithouAction();
                  }
              }
        });
  
        // if station is clicked then it is selected
        if (!endOfLine) {
            circle.setOnMouseClicked(e -> {
                App.app.getDataComponent().setSelectedStation(this);
                App.app.getDataComponent().setLastSelectedElement(this);
                App.app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
                App.app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
                e.consume();
            });
        }
        labelPosition = 0;
        labelRotation = 0;
        setLabelPosition(labelPosition);
        setLabelRotation(labelRotation);
        
        App.app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
        App.app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
    }

    public boolean isEndOfLine() {
        return endOfLine;
    }
    
    
    @Override
    public void remove() {
        if (!endOfLine) {
            MetroData data = App.app.getDataComponent();
            
            lines.forEach(line -> line.removeStationFromLine(this));
            data.removeStation(this);
            App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(circle);
            App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(label);
        }
        else {
           //new Part
           App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(circle);
           App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(label);
        }
    }
    
    public Circle getCircle() {
        return circle;
    }

    public ArrayList<MetroLine> getLines() {
        return lines;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItal() {
        return ital;
    }

    public void setItal(boolean ital) {
        this.ital = ital;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }
    
    @Override
    public void add() {
        App.app.getWorkspace().getCanvasComponent().
                getCanvas().getChildren().addAll(circle, label);
        if (!endOfLine) {
            App.app.getDataComponent().addStation(this);
        }
    }
    

    public void changeLabelPosition() {
        labelPosition++;
        labelPosition = labelPosition % 4;
        setLabelPosition(labelPosition);
    }
    
    public void setNewLabel(String name) {
        label = new Label(name);
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font);
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(label);
    }
    
    public void setLabelPosition(int positionState) {
        this.labelPosition = positionState;
        switch (positionState) {
              case 0:
                  label.layoutXProperty().bind(circle.centerXProperty().subtract(circle.radiusProperty().add(label.widthProperty()).add(labelDistance)));
                  label.layoutYProperty().bind(label.heightProperty().divide(-2).add(circle.centerYProperty()));

                  break;
              case 1:
                  label.layoutXProperty().bind(label.widthProperty().divide(-2).add(circle.centerXProperty()));
                  label.layoutYProperty().bind(circle.centerYProperty().subtract(circle.radiusProperty()).subtract(label.heightProperty()).subtract(labelDistance));
                  break;  
              case 2:
                  label.layoutXProperty().bind(circle.centerXProperty().add(circle.radiusProperty().add(labelDistance)));
                  label.layoutYProperty().bind(label.heightProperty().divide(-2).add(circle.centerYProperty()));
                  break;          
              case 3:
                  label.layoutXProperty().bind(label.widthProperty().divide(-2).add(circle.centerXProperty()));
                  label.layoutYProperty().bind(circle.centerYProperty().add(circle.radiusProperty()).add(labelDistance));
                  break;
        }
    }
    
    public void lineEndName(String name) {
        label = new Label(name);
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(label);
        
        label.layoutXProperty().bind(circle.centerXProperty().subtract(circle.radiusProperty().add(label.widthProperty()).add(10)));
        label.layoutYProperty().bind(label.heightProperty().divide(-2).add(circle.centerYProperty())); 
    }
    
    public void switchRotation() {
        if (labelRotation == 0) setLabelRotation(1);
        else setLabelRotation(0);
    }
    
    
    public void setLabelRotation(int labelRotation) {
       this.labelRotation = labelRotation;
        
        switch (labelRotation) {
            case 0:
                label.setRotate(0);
                if (labelPosition == 1) {
                    label.layoutYProperty().bind(circle.centerYProperty().subtract(circle.radiusProperty()).subtract(label.heightProperty()).subtract(labelDistance));
                }
                else if (labelPosition == 3) {
                        label.layoutYProperty().bind(circle.centerYProperty().add(circle.radiusProperty()).add(labelDistance));               
                }
                break;
            case 1:
                label.setRotate(-90);
                if (labelPosition == 1) {
                    label.layoutYProperty().bind(circle.centerYProperty().subtract(circle.radiusProperty()).subtract(label.widthProperty().divide(2)).subtract(labelDistance));
                }
                else if (labelPosition == 3) {
                    label.layoutYProperty().bind(circle.centerYProperty().add(circle.radiusProperty()).add(label.widthProperty().divide(2)).add(labelDistance));
                }
                break;
        }
    }

    public int getLabelPosition() {
        return labelPosition;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public int getLabelRotation() {
        return labelRotation;
        
    }
    
    @Override
    public void changeColor(Color c) { 
            color = c;
            circle.setFill(color);
    }
     
    @Override
    public void changeFontFamily(String family) {
          
          Font oldFont = label.getFont();
          this.font =  (Font.font(family,
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
          
      }
      
    @Override
      public void changeFontSize(double size) {
          Font oldFont = label.getFont();
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  size));
          label.setFont(font);
      }
      
    @Override
      public void changeFontBold() {
          Font oldFont = label.getFont();
          bold = !bold;
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
      }
      
    @Override
      public void changeFontColor (Color c) {
          this.labelColor = c;
          label.setTextFill(labelColor);    
      }
      
    @Override
      public void changeFontItal() {
          Font oldFont = label.getFont();
          ital = !ital;
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
      }

    public ArrayList<Station> getNeighbors() {
        return neighbors;
    }

    @Override
    public void setX(double x) {
        circle.setCenterX(x);
    }
    @Override
    public void setY(double y) {
        circle.setCenterY(y);
    }

    @Override
    public double getX() {
        return circle.getCenterX();    
    }

    @Override
    public double getY() {
        return circle.getCenterY();
    }

    @Override
    public Movable getMovable() {
        return this;
    }

  
    
    
    
    
    
      
     
   
 
    
}
