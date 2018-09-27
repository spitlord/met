/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroData;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import metroApp.App;
import metroDraggableObjects.Connection;
import metroDraggableObjects.DraggableImage;
import metroDraggableObjects.DraggableText;
import metroDraggableObjects.MetroLine;
import metroDraggableObjects.Station;

/**
 *
 * @author XDXD
 */
public class MetroData {
    
    
    private final ObservableList<MetroLine> metroLines;
    private final ObservableList<Station> metroStations;
    private final ObservableList<String> fontFamilies;
    private final ObservableList<Integer> fontSizes;
    private final ArrayList<DraggableText> text;
    private final ArrayList<DraggableImage> images;

    private MetroLine selectedLine; 
    private Station selectedStation;
    private Connection selectedConnection;
    private Object lastSelectedElement;

    public MetroData() {
        
        // observable lists are for comboboxes
        metroLines = FXCollections.observableArrayList();
        metroStations = FXCollections.observableArrayList(); 
        text = new ArrayList();
        images = new ArrayList();
        fontFamilies = FXCollections.observableArrayList();
        fontFamilies.addAll("Arial", "Courier", "PT Sans", "PT Serif", "Times New Roman");
        
        fontSizes = FXCollections.observableArrayList();
        fontSizes.addAll(8, 10, 11, 12, 14, 16, 18, 20, 24, 28, 36, 48);
    
    }

    public MetroLine getSelectedLine() {
        return selectedLine;
    }

    public Station getSelectedStation() {
        return selectedStation;
    }

    public ObservableList<MetroLine> getMetroLines() {
        return metroLines;
    }

    public ObservableList<Station> getMetroStations() {
        return metroStations;
    }

    public ObservableList<String> getFontFamilies() {
        return fontFamilies;
    }

    public ObservableList<Integer> getFontSizes() {
        return fontSizes;
    }
    
    
    

    public Connection getSelectedConnection() {
        return selectedConnection;
    }

    public Object getLastSelectedElement() {
        return lastSelectedElement;
    }


    
    
    public void setSelectedConnection(Connection newSelectedConnection) {
        
        try {
            if (newSelectedConnection != this.selectedConnection) {
                // before updating, set previous selection to invisible
                this.selectedConnection.getControl().setVisible(false);
                
            }
        } catch (NullPointerException ex) {}
        
        this.selectedConnection = newSelectedConnection;
      
       
    }
    
    
  
    public void setSelectedLine(MetroLine selectedLine) {
        
        // adjust the value of the slider to the line
        try {
            App.app.getWorkspace().getLeftPanel().getLineThicknessSlider().setValue(selectedLine.getLineThickness());

            this.selectedLine = selectedLine;
            // s
            App.app.getWorkspace().getLeftPanel().getLineComboBox().getSelectionModel().select(selectedLine);
            lastSelectedElement = selectedLine;

        } catch (NullPointerException ex) {}
       
    }

    public void setLastSelectedElement(Object lastSelectedElement) {
        this.lastSelectedElement = lastSelectedElement;
        
  
    }

    
    public void setSelectedStation(Station selectedStation) {
        
        
        if (selectedStation == null) {
            this.selectedStation = null;
        }
        else {
            if ( !(selectedStation.getName().charAt(0) == ' ')) {
                
                // adjust slider to the radius of the station
                App.app.getWorkspace().getLeftPanel().getStationRadiusSlider().setValue(selectedStation.getCircle().getRadius());
                this.selectedStation = selectedStation;
                



                // select in the combo box
                App.app.getWorkspace().getLeftPanel().getStationsComboBox().getSelectionModel().select(selectedStation);
                // fonts
               
            }
            
            lastSelectedElement = selectedStation;
        
        }
        //} catch (NullPointerException ex) {}
        
    }
    
    
    
    public  void resetData() {
        
        try {
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().clear();
        App.app.getWorkspace().getCanvasComponent().addGrid();
        
        metroLines.clear();
        metroStations.clear();
        text.clear();
        
        App.app.getWorkspace().getCanvasComponent().setCanvasColor(Color.WHITE);
        
        selectedLine = null;
        selectedStation = null;
        selectedConnection = null;
        lastSelectedElement = null;
        } catch (NullPointerException ex) {}  
    }

    public ArrayList<DraggableText> getText() {
        return text;
    }

    public ArrayList<DraggableImage> getImages() {
        return images;
    }
    
    
    

    
    
    
    
    
    
    
    
    
    
}
