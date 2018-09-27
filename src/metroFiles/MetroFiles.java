/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import metroApp.App;
import metroData.MetroData;
import metroDraggableObjects.Connection;
import metroDraggableObjects.DraggableImage;
import metroDraggableObjects.DraggableText;
import metroDraggableObjects.MetroLine;
import metroDraggableObjects.Station;

/**
 *
 * @author XDXD
 */
public class MetroFiles {

    public MetroFiles() {

    }

    public void proposeNewFile() {
        // propose to create a new file
        TextInputDialog a = new TextInputDialog();
        a.setTitle("New file name");
        a.setHeaderText("Enter the name:");
        Optional<String> result = a.showAndWait();
        if (result.isPresent() && !(result.get().equals(""))) {

            try { // try writing a file
                File file = new File("src/saved/" + result.get() + ".json");
                // no file with this name;
                if (!file.exists()) {
                    OutputStream out;
                    out = new FileOutputStream(file);
                    App.app.getWorkspace().setFileInWorkSpace(true);
                    App.app.getWorkspace().getLeftPanel().getLeftPanel().setDisable(false);
                    App.app.getWorkspace().setCurrentFile(file);

                    out.close();

                } else {
                    Alert badName = new Alert(Alert.AlertType.WARNING);
                    badName.setTitle("File with such name exists");
                    badName.showAndWait();
                }
            } catch (Exception ex) {

            }
            a.close();
        } else {
            // cancel might have been pressed. 
        }
    }


    private void loadStations(JsonObject jsonFile) {
        for (int i = 0; i < jsonFile.getJsonArray("stations").size(); i++) {
            JsonObject stationJson = jsonFile.getJsonArray("stations").getJsonObject(i);

            Station station = new Station(stationJson.getString("name"));
            station.changeCircleFill(Color.valueOf(stationJson.getString("color")));
            station.getCircle().setRadius(stationJson.getJsonNumber("radius").doubleValue());
            station.changeFontFamily(stationJson.getString("font"));
            station.changeFontSize(stationJson.getJsonNumber("fontSize").doubleValue());
            station.changeFontColor(Color.valueOf(stationJson.getString("fontColor")));
            station.setLabelPosition(stationJson.getInt("labelPosition"));
            station.setLabelRotation(stationJson.getInt("labelRotation"));
            if (stationJson.getBoolean("bold")) {
                station.changeFontBold();
            }
            if (stationJson.getBoolean("ital")) {
                station.changeFontItal();
            }
            station.getCircle().setCenterX(stationJson.getJsonNumber("x").doubleValue());
            station.getCircle().setCenterY(stationJson.getJsonNumber("y").doubleValue());

        }
    }

    private void loadLines(JsonObject jsonFile) {

        for (int i = 0; i < jsonFile.getJsonArray("lines").size(); i++) {
            JsonObject lineJson = jsonFile.getJsonArray("lines").getJsonObject(i);
            MetroLine line = new MetroLine(lineJson.getString("name"));
            line.changeLineColor(Color.valueOf(lineJson.getString("color")));

            line.getBeginning().changeFontSize(lineJson.getJsonNumber("fontSize").doubleValue());

            double x, y;
            x = lineJson.getJsonNumber("beginningX").doubleValue();
            y = lineJson.getJsonNumber("beginningY").doubleValue();
            line.getBeginning().getCircle().setCenterX(x);
            line.getBeginning().getCircle().setCenterY(y);

            x = lineJson.getJsonNumber("endX").doubleValue();
            y = lineJson.getJsonNumber("endY").doubleValue();
            line.getEnd().getCircle().setCenterX(x);
            line.getEnd().getCircle().setCenterY(y);

            line.changeLineThickness(lineJson.getJsonNumber("thickness").doubleValue());

            // font stuff
            line.getBeginning().changeFontFamily(lineJson.getString("font"));
            line.getBeginning().changeFontColor(Color.valueOf(lineJson.getString("fontColor")));
            line.getBeginning().setLabelPosition(lineJson.getInt("labelPosition"));
            line.getBeginning().setLabelRotation(lineJson.getInt("labelRotation"));
            if (lineJson.getBoolean("bold")) {
                line.getBeginning().changeFontBold();
            }
            if (lineJson.getBoolean("ital")) {
                line.getBeginning().changeFontItal();
            }

            // add stations to the line 
            line.loadStationsToTheLine(lineJson.getJsonArray("stations"));
            ArrayList<Connection> connections = line.getConnections();
            JsonArray connectionsJson = lineJson.getJsonArray("connections");

            for (int j = 0; j < lineJson.getJsonArray("connections").size(); j++) {
                x = connectionsJson.getJsonObject(j).getJsonNumber("curveX").doubleValue();
                y = connectionsJson.getJsonObject(j).getJsonNumber("curveY").doubleValue();
                connections.get(j).getControl().setCenterY(y);
                connections.get(j).getControl().setCenterX(x);
            }
        }
    }

    private void loadText(JsonObject jsonFile) {
        JsonObject textJson;
        for (int i = 0; i < jsonFile.getJsonArray("text").size(); i++) {
            textJson = jsonFile.getJsonArray("text").getJsonObject(i);
            DraggableText t = new DraggableText(textJson.getString("content"));
            t.changeFontFamily(textJson.getString("font"));
            t.changeFontSize(textJson.getJsonNumber("fontSize").doubleValue());
            t.changeFontColor(Color.valueOf(textJson.getString("fontColor")));
            if (textJson.getBoolean("bold")) {
                t.changeFontBold();
            }
            if (textJson.getBoolean("ital")) {
                t.changeFontItal();
            }
            t.getLabel().setTranslateX(textJson.getJsonNumber("x").doubleValue());
            t.getLabel().setTranslateY(textJson.getJsonNumber("y").doubleValue());

        }

    }
    
    private void loadImages(JsonObject jsonFile) {
        JsonObject imageJson;
    
        for (int i = 0; i < jsonFile.getJsonArray("images").size(); i++) {
            imageJson = jsonFile.getJsonArray("images").getJsonObject(i);
            File file = new File(imageJson.getString("path"));
            DraggableImage d = new DraggableImage(file);
           
            d.getImageView().setTranslateX(imageJson.getJsonNumber("x").doubleValue());
            d.getImageView().setTranslateY(imageJson.getJsonNumber("y").doubleValue());
        }
    
    }
    
    public void loadFile() throws FileNotFoundException {

        App.app.getDataComponent().resetData();
        File currentFile = App.app.getWorkspace().getCurrentFile();
        FileInputStream os = new FileInputStream(currentFile);
        JsonReader reader = Json.createReader(os);
        JsonObject jsonFile = reader.readObject();

        loadStations(jsonFile);
        loadLines(jsonFile);
        loadText(jsonFile);
        loadImages(jsonFile);
        

        Color c = Color.valueOf(jsonFile.getString("canvasColor"));
        App.app.getWorkspace().getCanvasComponent().setCanvasColor(c);
        
        double scale = jsonFile.getJsonNumber("scale").doubleValue();
        App.app.getWorkspace().getCanvasComponent().getCanvas().setScaleX(scale);
        App.app.getWorkspace().getCanvasComponent().getCanvas().setScaleY(scale);
        App.app.getWorkspace().getCanvasComponent().getCanvas().setPrefSize(
                jsonFile.getJsonNumber("width").doubleValue(),
                jsonFile.getJsonNumber("height").doubleValue()
        );
        App.app.getWorkspace().getCanvasComponent().getCanvas().setLayoutX(
                jsonFile.getJsonNumber("layoutX").doubleValue());
        App.app.getWorkspace().getCanvasComponent().getCanvas().setLayoutY(
                jsonFile.getJsonNumber("layoutY").doubleValue());

    }

    private JsonObjectBuilder saveStation(Station s) {
        JsonObjectBuilder j = Json.createObjectBuilder();
        j.add("name", s.getName());
        j.add("radius", s.getCircle().getRadius());
        j.add("x", s.getCircle().getCenterX());
        j.add("y", s.getCircle().getCenterY());
        j.add("font", s.getFont().getFamily());
        j.add("color", ((Color) s.getCircle().getFill()).toString());
        j.add("fontColor", ((Color) s.getLabel().getTextFill()).toString());
        j.add("fontSize", s.getLabel().getFont().getSize());
        j.add("bold", s.isBold());
        j.add("ital", s.isItal());
        j.add("labelPosition", s.getLabelPosition());
        j.add("labelRotation", s.getLabelRotation());
        return j;
    }

    private JsonArrayBuilder saveStations() {
        ObservableList<Station> stations = App.app.getDataComponent().getMetroStations();
        JsonArrayBuilder stationsJson = Json.createArrayBuilder();
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().charAt(0) == ' ') {
                continue;
            }
            stationsJson.add(saveStation(stations.get(i)));
        }

        return stationsJson;
    }

    private JsonObjectBuilder saveLine(MetroLine l) {
        JsonObjectBuilder j = Json.createObjectBuilder();
        j.add("name", l.getName());
        j.add("color", l.getColor().toString());
        j.add("thickness", l.getLineThickness());
        j.add("font", l.getBeginning().getFont().getFamily());
        j.add("fontSize", l.getBeginning().getFont().getSize());
        j.add("fontColor", ((Color) l.getBeginning().getLabel().getTextFill()).toString());
        j.add("bold", l.getBeginning().isBold());
        j.add("ital", l.getBeginning().isItal());
        j.add("labelPosition", l.getBeginning().getLabelPosition());
        j.add("labelRotation", l.getBeginning().getLabelRotation());

        j.add("beginningX", l.getBeginning().getCircle().getCenterX());
        j.add("beginningY", l.getBeginning().getCircle().getCenterY());
        j.add("endX", l.getEnd().getCircle().getCenterX());
        j.add("endY", l.getEnd().getCircle().getCenterY());

        // Record all the stations
        ArrayList<Station> stations = l.getStations();

        JsonArrayBuilder stationsJson = Json.createArrayBuilder();
        for (int i = 1; i < stations.size() - 1; i++) {
            stationsJson.add(stations.get(i).getName());
        }

        j.add("stations", stationsJson);

        // Record connections
        ArrayList<Connection> connections = l.getConnections();
        JsonArrayBuilder connectionsJson = Json.createArrayBuilder();
        JsonObjectBuilder connection = Json.createObjectBuilder();

        for (int i = 0; i < connections.size(); i++) {

            connection.add("curveX", connections.get(i).getControl().getCenterX());
            connection.add("curveY", connections.get(i).getControl().getCenterY());
            connectionsJson.add(connection);
        }
        j.add("connections", connectionsJson);

        return j;

    }

    private JsonArrayBuilder saveLines() {
        // Deal with lines
        ObservableList<MetroLine> lines = App.app.getDataComponent().getMetroLines();

        JsonArrayBuilder linesJson = Json.createArrayBuilder();
        for (int i = 0; i < lines.size(); i++) {
            linesJson.add(saveLine(lines.get(i)));
        }
        return linesJson;
    }
    
        private JsonArrayBuilder saveText() {

        JsonArrayBuilder array = Json.createArrayBuilder();
        JsonObjectBuilder j = Json.createObjectBuilder();

        ArrayList<DraggableText> text = App.app.getDataComponent().getText();
        for (int i = 0; i < text.size(); i++) {
            j.add("content", text.get(i).getText());
            j.add("font", text.get(i).getFont().getFamily());
            j.add("fontColor", text.get(i).getColor().toString());
            j.add("fontSize", text.get(i).getFont().getSize());
            j.add("bold", text.get(i).isBold());
            j.add("ital", text.get(i).isItal());
            j.add("x", text.get(i).getLabel().getTranslateX());
            j.add("y", text.get(i).getLabel().getTranslateY());
            array.add(j);
        }
        return array;
    }


    private JsonArrayBuilder saveImages() {
               // Deal with lines
        JsonArrayBuilder imagesJson = Json.createArrayBuilder();
        JsonObjectBuilder j = Json.createObjectBuilder();
        
        ArrayList<DraggableImage> images = App.app.getDataComponent().getImages();
        for (int i = 0; i < images.size(); i++) {
            j.add("path", images.get(i).getFile().toPath().toAbsolutePath().toString());
            j.add("x", images.get(i).getImageView().getTranslateX());
            j.add("y", images.get(i).getImageView().getTranslateY());
            imagesJson.add(j);
        }
        return imagesJson;
    
    }
    public void saveFile() throws FileNotFoundException {

        MetroData data = App.app.getDataComponent();
        
        

        JsonObjectBuilder file = Json.createObjectBuilder();

        // Deal with stations
        file.add("stations", saveStations());
        file.add("lines", saveLines());
        file.add("text", saveText());
        file.add("images", saveImages());
        
        
        file.add("scale",  App.app.getWorkspace()
                .getCanvasComponent().getCanvas().getScaleX());
        file.add("canvasColor", App.app.getWorkspace().getCanvasComponent()
                .getCanvasColor().toString());
        
        file.add("width", App.app.getWorkspace().getCanvasComponent().getCanvas().getPrefWidth());
        file.add("height", App.app.getWorkspace().getCanvasComponent().getCanvas().getPrefHeight());
        file.add("layoutX", App.app.getWorkspace().getCanvasComponent().getCanvas().getLayoutX());
        file.add("layoutY", App.app.getWorkspace().getCanvasComponent().getCanvas().getLayoutY());


        String name = App.app.getWorkspace().getCurrentFile().getName();

        File currentFile = App.app.getWorkspace().getCurrentFile();

        FileOutputStream os = new FileOutputStream(currentFile);

        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeObject(file.build());
        jsonWriter.close();

    }

    public void saveFileAs() {

    }

    public void export() {
        snapshot();
        try {
            jsonexport();
        } catch (Exception ex) {
        }

    }

    public void snapshot() {
        Pane pane = App.app.getWorkspace().getCanvasComponent().getCanvas();

        WritableImage writableImage = new WritableImage((int) pane.getWidth(), (int) pane.getHeight());
        pane.snapshot(null, writableImage);
        String name = App.app.getWorkspace().getCurrentFile().getName();
        name = name.replace(".json", "");
        File outFile = new File("src/exp/" + name + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", outFile);
        } catch (Exception eu) {
        }

    }

    public void jsonexport() throws FileNotFoundException {
    }

}
