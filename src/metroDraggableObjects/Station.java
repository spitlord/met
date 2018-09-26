/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import java.util.ArrayList;
import javafx.scene.control.Label;;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import metroApp.App;
import metroData.MetroData;
import metroWorkspace.MetroCanvas;
import transactions.MoveCircle;
import transactions.Transaction;

/**
 *
 * @author XDXD
 */
public class Station implements ChangeFont {
    
    
    final static int labelDistance = 25;
   
    String name;
    Circle circle;
    Label label;
    Font font;
    boolean bold;
    boolean ital;
    Color color;
    Color labelColor;
   
    int labelPosition;
    int labelRotation;
    ArrayList<MetroLine> lines;
    ArrayList<Station> neighbors;
    boolean lineEndNameStation;
    
    
    public Station(String name) {
        
        
       
        lineEndNameStation = false;
        
        MetroCanvas metroCanvas = App.app.getWorkspace().getCanvasComponent();
        
        // all the lines that have this station

        neighbors = new ArrayList<Station>();
        lines =  new ArrayList<MetroLine>();
        
        // set name
        this.name = name;
        
        // create label of the name
        label = new Label(name);
        // set font
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font);
        labelColor = Color.BLACK;
        label.setTextFill(labelColor);
        
        // create circle that represesnts the station
        circle = new Circle(15);
        
        // locate 
        circle.setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight()/2);
        circle.setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getWidth()/2);  
      
        
        // set to be last selected
        
        App.app.getDataComponent().setLastSelectedElement(this);
        
        // on dragged change location
 
        circle.setOnMousePressed(e -> {
              App.app.getTransactions().pushUndo(new MoveCircle(circle));    
        });
        
        circle.setOnMouseDragged(e -> {
        circle.setCenterX(e.getX()+2);
        circle.setCenterY(e.getY()+2);
        e.consume();
        });
        
        circle.setOnMouseReleased(e -> {
              Transaction temp = App.app.getTransactions().peekUndo();
              if (temp instanceof MoveCircle) {
                  if (((MoveCircle) temp).getC() == circle &&
                       ((MoveCircle) temp).getX() == circle.getCenterX() &&
                       ((MoveCircle) temp).getY() == circle.getCenterY()) {
                      App.app.getTransactions().popUndoWithouAction();
                  }
              }
        });
  
        // if station is clicked then it is selected
        circle.setOnMouseClicked(e -> {
            App.app.getDataComponent().setSelectedStation(this);
            App.app.getDataComponent().setLastSelectedElement(this);
            App.app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
            App.app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
            e.consume();
        });
        
        
        
        
        // add station to the canvas
        metroCanvas.getCanvas().getChildren().addAll(circle, label);

        // set initial rotation and position states
        labelPosition = 0;
        labelRotation = 0;
        setLabelPosition(labelPosition);
        setLabelRotation(labelRotation);
        
        
   
        App.app.getWorkspace().getLeftPanel().getFontFamily().setValue(label.getFont().getFamily());
        App.app.getWorkspace().getLeftPanel().getFontSize().setValue((int)label.getFont().getSize());
        if (!(name.charAt(0) == ' ')) {
            App.app.getDataComponent().getMetroStations().add(this);
            App.app.getDataComponent().setSelectedStation(this);
             // update combobox
       
        } 
        
    }
    
    
    public void deleteStation() {
        if (!lineEndNameStation) {
            MetroData data = App.app.getDataComponent();
            
             // remove from all lines
            for (int i = 0; i < lines.size(); i++) {
                lines.get(i).removeStationFromTheLine(this);
            }

            // delete from data object
            data.getMetroStations().remove(this);




           // delete visually
           App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(circle);
           App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(label);

            // deal with new selected station; if there are some, pick one;
           if (data.getMetroStations().size() > 0) {
               data.setSelectedStation(data.getMetroStations().get(0));
           } 
           else {
               App.app.getDataComponent().setSelectedStation(null);
           }
        }
}

    public void setLineEndNameStation(boolean lineEndNameStation) {
        this.lineEndNameStation = lineEndNameStation;
    }
    
    
    
    public Circle getCircle() {
        return circle;
    }

    public ArrayList<MetroLine> getLines() {
        return lines;
    }

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
    
    
    
    
    
    

    public void changeLabelPosition() {
        // change to new state
        labelPosition++;
        // there are four positions
        labelPosition = labelPosition % 4;
        
        setLabelPosition(labelPosition);
        
      
    }
    public void setNewLabel(String name) {
        label = new Label(name);
        // set font
        font = new Font("Arial", 13);
        bold = false;
        ital = false;
        label.setFont(font);
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(label);


        
    }
    
    public void setLabelPosition(int positionState) {
        
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
    
    public void setLabelRotation(int labelRotation) {
        // change to new state
     
        // there are two positions
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
    
    
    
    
    public void changeCircleFill(Color c) {
        color = c;
        circle.setFill(color);
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
      
      public void changeFontBold() {
          Font oldFont = label.getFont();
          bold = !bold;
          this.font =  (Font.font(oldFont.getFamily(),
                  bold ? FontWeight.BOLD : FontWeight.NORMAL, 
                  ital ? FontPosture.ITALIC : FontPosture.REGULAR,
                  oldFont.getSize()));
          label.setFont(font);
      }
      
      public void changeFontColor (Color c) {
          this.labelColor = c;
          label.setTextFill(labelColor);    
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

    public ArrayList<Station> getNeighbors() {
        return neighbors;
    }
      
      
     
   
 
    
}
