/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroControllers;


        
import java.io.File;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;;
import javafx.stage.Stage;
import metroApp.App;
import metroData.MetroData;
import metroDraggableObjects.DraggableImage;
import metroDraggableObjects.DraggableText;
import metroDraggableObjects.MetroLine;
import metroDraggableObjects.Station;
import metroDraggableObjects.ChangeFont;

/**
 *
 * @author XDXD
 */
public class MetroControllersLeft {
    
  
    
    
    public MetroControllersLeft() {

    }
   
    
   //// Left Panel
   public void handleAddLineButton() {  
       
       //prompt for name
       TextInputDialog a = new TextInputDialog();
       a.setTitle("Add a line");
       a.setHeaderText("Enter name: ");

       Optional<String> result = a.showAndWait();

       // discard null string and cancel or close button
       if (result.isPresent() && !(result.get().equals(""))) {
           MetroLine newLine = new MetroLine(result.get());
       }
   }
   
   
   public void handleRemoveLineButton() {
       
        try {
            // test whether any line is selected;
            // null thrown and code doesn't proceed
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this line?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
        
            if (alert.getResult() == ButtonType.YES) {
                line.deleteLine();
            }
        } catch (NullPointerException ex) {
            
        }
       
       
   }
   public void handleEditLineButton() {
        MetroLine line = App.app.getDataComponent().getSelectedLine();
        if (line != null) {
            TextArea textArea = new TextArea();
            textArea.setText(line.getName());

            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(line.getColor());


            GridPane gridBox = new GridPane();
            gridBox.add(textArea, 0, 0);
            gridBox.add(colorPicker, 1, 0);


            Button OKButton = new Button("OK");
            Button cancel = new Button ("Cancel");
            gridBox.add(OKButton, 0, 1);
            gridBox.add(cancel, 1, 1);

            Scene scene = new Scene(gridBox);
            Stage stage = new Stage();
            stage.setTitle("Edit Line");
            stage.setScene(scene);


            OKButton.setOnMouseClicked(e -> {

                String newName = textArea.getText();
                Color color = colorPicker.getValue();
                try {
                         line.editLine(color, newName);
                         stage.close();  
                } catch (NullPointerException ex) {}
                e.consume();
            });
            cancel.setOnMouseClicked(e -> {
                stage.close();
            });
            stage.showAndWait();
       }
       
      
   }
   public void handleMoveLineEndButton() {}
   public void handleAddStationsToLineButton() {
       
     try {
     Station selectedStation = App.app.getDataComponent().getSelectedStation();
     MetroLine selectedLine = App.app.getDataComponent().getSelectedLine();
     selectedLine.addStationToTheLine(selectedStation);
     } catch (NullPointerException ex) {}
     
   }
   public void handleRemoveStationsFromLineButton() {
       try {
            Station selectedStation = App.app.getDataComponent().getSelectedStation();
            MetroLine selectedLine = App.app.getDataComponent().getSelectedLine();
            selectedLine.removeStationFromTheLine(selectedStation);
       } catch (NullPointerException ex) {}
   }
   public void handleListAllStationsButton() {
       try {
           MetroLine line = App.app.getDataComponent().getSelectedLine(); 
           // i = 1, because at 0 there is a dragging node;
           line.listStations();
          
           
       } catch (NullPointerException e) {
       }   
   }
   public void handleLineComboBox(MetroLine selectedLine) {
        App.app.getDataComponent().setSelectedLine(selectedLine);
   }
   public void handleSetLineColor(Color c) {
       try {
           App.app.getDataComponent().getSelectedLine().changeLineColor(c);
       } catch (NullPointerException ex) {}   
   }
    public void handleSetLineThickness(double thickness) {
       try {
           App.app.getDataComponent().getSelectedLine().changeLineThickness(thickness);
       } catch (NullPointerException ex) {}   
   }
   
   


    public void handleAddStationButton() {  
        TextInputDialog a = new TextInputDialog();
        a.setTitle("Enter Station Name");
        a.setHeaderText("Name: ");
        Optional<String> result = a.showAndWait();
        MetroData data =  App.app.getDataComponent();
        
        // check if the name already exists
        boolean nameAleadyExists = false;
        String resultString;
        // user input stored here
        resultString = null;
        
        
        if (result.isPresent()) {
             resultString = result.get();
             
            for (int i = 0; i <  data.getMetroStations().size(); i++) { 
                if (data.getMetroStations().get(i).getName().equals(resultString)) {
                    nameAleadyExists = true;
                }
            }
            
            if ( (!(resultString.equals("")) && (!nameAleadyExists))) {
                Station station = new Station(resultString);
            }
            else {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Name already exists");
        alert.setHeaderText("Station with such name already exits");
        alert.showAndWait();
            }
        }
    }

   
   
   public void handleSetStationColor(Color c) {
        Station station = App.app.getDataComponent().getSelectedStation();
        if (station != null && !station.getName().equals("")) {
            station.changeCircleFill(c);
        }
   }
   
   public void handleStationRadiusSlider(double d) {
       try {
            App.app.getDataComponent().getSelectedStation().getCircle().setRadius(d);
       } catch  (NullPointerException ex) {}
   }
     
   public void handleRemoveStationButton() {
       Station station = App.app.getDataComponent().getSelectedStation();
       // check if there are no stations at all
       if (station != null) {
            try {

                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this statin?", ButtonType.YES, ButtonType.CANCEL);
                 alert.showAndWait();

                 if (alert.getResult() == ButtonType.YES) {
                     station.deleteStation(); 
                 }
            } catch (NullPointerException ex) {}
       }
   }
       
       
   
   
   public void handleStationComboBox(Station selectedStation) {
       try {
           App.app.getDataComponent().setSelectedStation(selectedStation);
       } catch (NullPointerException ex){}
   }
  
   public void handleSnapToGridButton() {
       Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
       if (lastSelected instanceof Station) {
           Station station = (Station) lastSelected;
           Circle circle = station.getCircle();
           
           double xCenterMod, yCenterMod;
           xCenterMod = circle.getCenterX() % 50;
           yCenterMod = circle.getCenterY() % 50;
           
           if (xCenterMod > 25) {
               circle.setCenterX(circle.getCenterX() + 50 - xCenterMod);                 
           }
           else {
               circle.setCenterX(circle.getCenterX() - xCenterMod);
           }
           
           if (yCenterMod > 25) {
               circle.setCenterY(circle.getCenterY() + 50 - yCenterMod);                 
           }
           else {
               circle.setCenterY(circle.getCenterY() - yCenterMod);
           }
           
       }
   }
   public void handleMoveLabelButton() {
       
         try {
            Object a  = App.app.getDataComponent().getLastSelectedElement();
            if (a instanceof Station) {
                Station station = (Station) a;
                station.changeLabelPosition();
            }
        } catch (NullPointerException ex) {}
   }
   

         
      
   public void handleRotateLabelButton() {
       
        try {
            Object a  = App.app.getDataComponent().getLastSelectedElement();
            if (a instanceof Station) {
                Station station = (Station) a;
                
                
                if (station.getLabelRotation() == 0) station.setLabelRotation(1);
                else station.setLabelRotation(0);
            }
        } catch (NullPointerException ex) {}
   }

   public void handleFindRouteButton(){}


   public void handleAddImageButton() {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Choose image");
      
       try { 
            File file = fileChooser.showOpenDialog(App.app.getStage());
            String filePath = file.toString();
            if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")  || filePath.endsWith(".png") || filePath.endsWith(".gif"))
                {
                    DraggableImage image = new DraggableImage(App.app, file);
                    App.app.getDataComponent().setLastSelectedElement(image);
                }
       } catch (NullPointerException ex) {}
   }
   
   public void handleAddTextButton() {
       
       TextInputDialog a = new TextInputDialog();
       a.setTitle("Add text");
       a.setHeaderText("Enter the text: ");

       Optional<String> result = a.showAndWait();

       // discard null string and cancel or close button
       if (result.isPresent() && !(result.get().equals(""))) {
           DraggableText text = new DraggableText(App.app, result.get());
           App.app.getDataComponent().setLastSelectedElement(text);
       }
   }
   public void handleSetImageBackgroundButton() {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Choose image");
      
       try { 
            File file = fileChooser.showOpenDialog(App.app.getStage());
            String filePath = file.toString();
            if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")  || filePath.endsWith(".png") || filePath.endsWith(".gif"))
                {
                    Image image = new Image(filePath);
                    ImageView imageView = new ImageView(image);
                    App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(imageView);
                }
       } catch (NullPointerException ex) {}
   }
       
   
   
   public void handleSetBackgroundColorButton(Color color) {
        App.app.getDataComponent().setBackGroundColor(color);
        App.app.getWorkspace().getCanvasComponent().setCanvasColor(color);
        
        // now change the css of the canvas
        App.app.getWorkspace().getCanvasComponent().getCanvas().
                setStyle("-fx-background-color: #" +
                        color.toString().substring(2, 8) +
                        ";");
    }
   
   
     
   public void handleFontColorButton(Color c) {
       Object o = App.app.getDataComponent().getLastSelectedElement();
      
       if (App.app.getDataComponent().getLastSelectedElement() instanceof Station)
           ((Station) o).changeFontColor(c);
       else if (App.app.getDataComponent().getLastSelectedElement() instanceof DraggableText)
           ((DraggableText)o).changeFontColor(c);
    }
   
   
   public void handleRemoveElementButton() {
       Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
       
       try {
           if (lastSelected instanceof DraggableText) {
           } 
           else if (lastSelected instanceof DraggableImage) {
               ((DraggableImage) lastSelected).deleteImage();
           }
       } catch (NullPointerException ex) {}
       
   }

   public void handleFontFamilyComboBox(String family) {
        Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
        
       if (lastSelected instanceof ChangeFont) {
           ChangeFont fontNode = (ChangeFont) lastSelected;
           fontNode.changeFontFamily(family);
       }
       
   }
   
   public void handleFontSizeComboBox(String sizeString) {
       Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
       int size = Integer.valueOf(sizeString);
       
       if (lastSelected instanceof ChangeFont) {
           ChangeFont fontNode = (ChangeFont) lastSelected;
           fontNode.changeFontSize(size);
       }
   }

   public void handleItalButton() {
       Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
       
       if (lastSelected instanceof ChangeFont) {
           ChangeFont fontNode = (ChangeFont) lastSelected;
           fontNode.changeFontItal();
       }
       
   }
   
   
   public void handleBoldButton() {
       Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
       if (lastSelected instanceof ChangeFont) {
           ChangeFont fontNode = (ChangeFont) lastSelected;
           fontNode.changeFontBold();
       }
     
       
   }

   public void handleShowGridToggleButton() {
       App.app.getWorkspace().getCanvasComponent().showGrid();
   }

   public void handleZoomInButton() {
      
       Pane canvas = App.app.getWorkspace().getCanvasComponent().getCanvas(); 
       canvas.setScaleX(canvas.getScaleX()*1.1);
       canvas.setScaleY(canvas.getScaleY()*1.1);
   }
   
    public void handleZoomOutButton() {
        
        Pane canvas = App.app.getWorkspace().getCanvasComponent().getCanvas();
        canvas.setScaleX(canvas.getScaleX()/1.1);
        canvas.setScaleY(canvas.getScaleY()/1.1);
    }
   public void handleEnlargeMapButton() {
       Pane canvas = App.app.getWorkspace().getCanvasComponent().getCanvas();
       canvas.setPrefSize(canvas.getWidth()*1.1, canvas.getHeight()*1.1);
       
   }
    public void handleReduceMapButton() {
        Pane canvas = App.app.getWorkspace().getCanvasComponent().getCanvas();
        if (canvas.getWidth() >= 200 && canvas.getHeight() >= 200) {
            canvas.setPrefSize(canvas.getWidth() / 1.1, canvas.getHeight() / 1.1);
        }
    }
    
    
}
