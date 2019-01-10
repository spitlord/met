/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroData;

import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import metroApp.App;
import canvasObjects.Background;
import canvasObjects.Connection;
import canvasObjects.DraggableImage;
import canvasObjects.Text;
import canvasObjects.MetroLine;
import canvasObjects.Station;

/**
 *
 * @author XDXD
 */
public class MetroData {

    private final ObservableList<MetroLine> metroLines;
    private final ObservableList<Station> metroStations;
    private final ObservableList<String> fontFamilies;
    private final ObservableList<Integer> fontSizes;
    private final ArrayList<Text> text;
    private final ArrayList<DraggableImage> images;
    private Background background;

    private MetroLine selectedLine;
    private Station selectedStation;
    private Connection selectedConnection;
    private Object lastSelectedElement;
    private Color currentStationColor;
    private File currentFile;

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
        currentStationColor = Color.BLACK;

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
        if (newSelectedConnection == null) return;
        
        try {
            this.selectedConnection.hideControl();
        } catch(NullPointerException x) {}
        
        newSelectedConnection.showControl();
        this.selectedConnection = newSelectedConnection;
        
        

    }

    public void setSelectedLine(MetroLine selectedLine) {

        if (selectedLine == null) {
            return;
        }
        // adjust the value of the slider to the line

        App.app.getWorkspace().getLeftPanel().
                getLineThicknessSlider().setValue(selectedLine.getLineThickness());

        try {
            this.selectedLine.hideEnds();
        } catch (NullPointerException ex) {}

        this.selectedLine = selectedLine;
        App.app.getWorkspace().getLeftPanel().
                getLineComboBox().getSelectionModel().select(selectedLine);
        lastSelectedElement = selectedLine;

    }

    public void setLastSelectedElement(Object lastSelectedElement) {
        this.lastSelectedElement = lastSelectedElement;

    }

    public void setSelectedStation(Station selectedStation) {

        if (selectedStation == null) {
            this.selectedStation = null;
        } else {
            if (!(selectedStation.getName().charAt(0) == ' ')) {

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

    public void setBackground(Background background) {
        this.background = background;
    }

    public void resetData() {

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
            background = null;

        } catch (NullPointerException ex) {
        }
    }

    public ArrayList<Text> getText() {
        return text;
    }

    public ArrayList<DraggableImage> getImages() {
        return images;
    }

    public Background getBackground() {
        return background;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
    
    public File getCurrentFile() {
        return currentFile;
    }

    public Color getCurrentStationColor() {
        return currentStationColor;
    }

    public void setCurrentStationColor(Color currentStationColor) {
        this.currentStationColor = currentStationColor;
    }
    
    public void addStation(Station s) {
        metroStations.add(s);
    }
    
     public boolean removeStation(Station s) {
        return metroStations.remove(s);
    }
    
    
}
