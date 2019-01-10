/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroControllers;

import canvasObjects.Addable;
import java.io.File;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metroApp.App;
import metroData.MetroData;
import canvasObjects.Background;
import canvasObjects.DraggableImage;
import canvasObjects.Text;
import canvasObjects.MetroLine;
import canvasObjects.Station;
import canvasObjects.ChangableFont;
import handlers.CustomAlerts;
import transactions.AddObjectT;
import transactions.AddStationToLineT;
import workspace.MouseState;
import transactions.BackgroundColor;
import transactions.ChangeFont;
import transactions.EditLine;
import transactions.EditStation;
import transactions.RemoveObjectT;
import transactions.RemoveStationFromLineT;
import transactions.RemoveStationT;

/**
 *
 * @author XDXD
 */
public class MetroControllersLeft {

    public MetroControllersLeft() {
    }

    public void handleAddLineButton() {
        //prompt for name
        TextInputDialog a = new TextInputDialog();
        a.setTitle("Add a line");
        a.setHeaderText("Enter name: ");
        Optional<String> result = a.showAndWait();
        // discard null string and cancel or close button
        if (result.isPresent() && !(result.get().equals(""))) {
            MetroLine newLine = new MetroLine(result.get());
//            newLine.add();
            App.app.getTransactions().pushUndo(new AddObjectT(newLine));
        }
    }

    public void handleRemoveLineButton() {
        try {
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this line?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                line.remove();
                App.app.getDataComponent().setSelectedLine(null);
                App.app.getTransactions().pushUndo(new RemoveObjectT(line));
                
            }
        } catch (NullPointerException ex) {
        }

    }

    public void handleEditLineButton() {
        MetroLine line = App.app.getDataComponent().getSelectedLine();
        if (line != null) {
            App.app.getTransactions().pushUndo(new EditLine(line));
            TextArea textArea = new TextArea();
            textArea.setText(line.getName());

            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(line.getColor());

            GridPane gridBox = new GridPane();
            gridBox.add(textArea, 0, 0);
            gridBox.add(colorPicker, 1, 0);
            Button OKButton = new Button("OK");
            Button cancel = new Button("Cancel");
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
                } catch (NullPointerException ex) {
                }
                e.consume();
            });
            cancel.setOnMouseClicked(e -> {
                App.app.getTransactions().popUndoWithouAction();
                stage.close();
            });
            stage.showAndWait();
        }

    }

    public void handleMoveLineEndButton() {
    }

    public void handleAddStationsToLineButton() {
        try {
            Station station = App.app.getDataComponent().getSelectedStation();
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            line.addStationNearest(station);
            App.app.getTransactions().pushUndo(new AddStationToLineT(line, station));
        } catch (NullPointerException ex) {
        }
    }

    public void handleRemoveStationsFromLineButton() {
        try {
            Station station = App.app.getDataComponent().getSelectedStation();
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            line.removeStationFromLine(station);
            App.app.getTransactions().pushUndo(new RemoveStationFromLineT(line, station));
        } catch (NullPointerException ex) {
        }
    }

    public void handleListAllStationsButton() {
        try {
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            // i = 1, because at 0 there is a dragging node;
            CustomAlerts.listStations(line);
        } catch (NullPointerException e) {
        }
    }

    public void handleLineComboBox(MetroLine selectedLine) {
        App.app.getDataComponent().setSelectedLine(selectedLine);
    }

    public void handleSetLineColor(Color c) {
        try {
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            App.app.getTransactions().pushUndo(new EditLine(line));
            line.changeColor(c);
        } catch (NullPointerException ex) {
        }
    }

    public void handleSetLineThickness(MouseState state, double thickness) {
        if (!(App.app.getDataComponent().getSelectedLine() == null)) {
            MetroLine line = App.app.getDataComponent().getSelectedLine();
            switch (state) {
                case PRESSED:
                    App.app.getTransactions().pushUndo(new EditLine(line));
                    break;
                case RELEASED:
                    EditLine transaction = (EditLine) App.app.getTransactions().peekUndo();
                    if (transaction.getThickness() == line.getLineThickness()) {
                        App.app.getTransactions().popUndoWithouAction();
                    }
                    break;

            }
            line.changeLineThickness(thickness);
        }

    }

    public void handleAddStationButton() {
        TextInputDialog a = new TextInputDialog();
        a.setTitle("Enter Station Name");
        a.setHeaderText("Name: ");
        Optional<String> result = a.showAndWait();
        MetroData data = App.app.getDataComponent();

        // check if the name already exists
        boolean nameAleadyExists = false;
        String resultString;
        // user input stored here
        resultString = null;

        if (result.isPresent()) {
            resultString = result.get();

            for (int i = 0; i < data.getMetroStations().size(); i++) {
                if (data.getMetroStations().get(i).getName().equals(resultString)) {
                    nameAleadyExists = true;
                }
            }

            if ((!(resultString.equals("")) && (!nameAleadyExists))) {
                Station station = new Station(resultString, false);
                station.add();
                App.app.getTransactions().pushUndo(new AddObjectT(station));
                data.setSelectedStation(station);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Name already exists");
                alert.setHeaderText("Station with such name already exits");
                alert.showAndWait();
            }
        }
    }

    public void handleSetStationColor(Color c) {
        Station station = App.app.getDataComponent().getSelectedStation();
        if (station != null && !station.isEndOfLine()) {
            if (!station.getColor().equals(c)) {
                System.out.println(station.getColor().toString());
                App.app.getTransactions().pushUndo(new EditStation(station));
                System.out.println(c.toString());
                station.changeColor(c);
            }
        }
    }

    public void handleStationRadiusSlider(MouseState state, double r) {

        if (!(App.app.getDataComponent().getSelectedStation() == null)) {
            Station station = App.app.getDataComponent().getSelectedStation();
            switch (state) {
                case PRESSED:
                    App.app.getTransactions().pushUndo(new EditStation(station));
                    break;
                case RELEASED:
                    EditStation transaction = (EditStation) App.app.getTransactions().peekUndo();
                    if (transaction.getRadius() == r) {
                        App.app.getTransactions().popUndoWithouAction();
                    }
                    break;
            }
            station.getCircle().setRadius(r);
        }
    }

    public void handleRemoveStationButton() {
        Station station = App.app.getDataComponent().getSelectedStation();
        // check if there are no stations at all
        if (station != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this station?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    App.app.getTransactions().pushUndo(new RemoveStationT(station));
                    station.remove();
                    if (App.app.getDataComponent().getMetroStations().size() > 0) {
                        App.app.getDataComponent().setSelectedStation(
                                App.app.getDataComponent().getMetroStations().get(0));
                    } else {
                        App.app.getDataComponent().setSelectedStation(null);
                    }
                }
            } catch (NullPointerException ex) {
            }
        }
    }

    public void handleStationComboBox(Station selectedStation) {
        try {
            App.app.getDataComponent().setSelectedStation(selectedStation);
        } catch (NullPointerException ex) {
        }
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
            } else {
                circle.setCenterX(circle.getCenterX() - xCenterMod);
            }

            if (yCenterMod > 25) {
                circle.setCenterY(circle.getCenterY() + 50 - yCenterMod);
            } else {
                circle.setCenterY(circle.getCenterY() - yCenterMod);
            }

        }
    }

    public void handleMoveLabelButton() {
        // change was here
        try {
            Station a = App.app.getDataComponent().getSelectedStation();
            Station station = (Station) a;
            App.app.getTransactions().pushUndo(new EditStation(station));
            station.changeLabelPosition();
        } catch (NullPointerException ex) {
        }
    }

    public void handleRotateLabelButton() {
//        try {
        Object a = App.app.getDataComponent().getLastSelectedElement();
        if (a instanceof Station) {
            Station station = (Station) a;
            App.app.getTransactions().pushUndo(new EditStation(station));
            station.switchRotation();
        }
//        } catch (NullPointerException ex) {
//        }
    }

    public void handleFindRouteButton() {
        try {
            Station from = (Station) App.app.getWorkspace().getLeftPanel()
                    .getFromComboBox().getSelectionModel().getSelectedItem();
            Station to = (Station) App.app.getWorkspace().getLeftPanel()
                    .getToComboBox().getSelectionModel().getSelectedItem();
        } catch (NullPointerException npe) {
        }
    }

    public void handleAddImageButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        try {
            File file = fileChooser.showOpenDialog(App.app.getStage());
            String filePath = file.toString();

            if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")
                    || filePath.endsWith(".png") || filePath.endsWith(".gif")) {
                DraggableImage image = new DraggableImage(file);
                App.app.getTransactions().pushUndo(new AddObjectT(image));
                App.app.getDataComponent().setLastSelectedElement(image);
            }
        } catch (NullPointerException ex) {
        }
    }

    public void handleAddTextButton() {

        TextInputDialog a = new TextInputDialog();
        a.setTitle("Add text");
        a.setHeaderText("Enter the text: ");

        Optional<String> result = a.showAndWait();

        // discard null string and cancel or close button
        if (result.isPresent() && !(result.get().equals(""))) {
            Text text = new Text(result.get());
            App.app.getTransactions().pushUndo(new AddObjectT(text));
            App.app.getDataComponent().setLastSelectedElement(text);

        }
    }

    public void handleSetImageBackgroundButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");

        try {
            File file = fileChooser.showOpenDialog(App.app.getStage());
            String filePath = file.toString();
            if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg") || filePath.endsWith(".png") || filePath.endsWith(".gif")) {
                Background background = new Background(file);
                App.app.getTransactions().pushUndo(new AddObjectT(background));
            }
        } catch (NullPointerException ex) {
        }
    }

    public void handleSetBackgroundColorButton(Color color) {
        Color backgroundColor = App.app.getWorkspace().getCanvasComponent().getCanvasColor();
        if (!(color.equals(backgroundColor))) {
            App.app.getTransactions().pushUndo(new BackgroundColor());
            App.app.getWorkspace().getCanvasComponent().setCanvasColor(color);
        }
    }

    public void handleFontColorButton(Color c) {
        Object o = App.app.getDataComponent().getLastSelectedElement();

        if (App.app.getDataComponent().getLastSelectedElement() instanceof Station) {
            Station station = ((Station) o);
            if (!((Color) station.getLabel().getTextFill()).equals(c)) {
                App.app.getTransactions().pushUndo(new ChangeFont(station.getLabel()));
                station.changeFontColor(c);
            }
        } else if (App.app.getDataComponent().getLastSelectedElement() instanceof Text) {
            Text text = (Text) o;
            if (!((Color) text.getLabel().getTextFill()).equals(c)) {
                App.app.getTransactions().pushUndo(new ChangeFont(((Text) o).getLabel()));
                ((Text) o).changeFontColor(c);
            }
        }
    }

    public void handleRemoveElementButton() {
        Addable lastSelected = (Addable) App.app.getDataComponent().getLastSelectedElement();
        try {
            // stations and lines have their own remove buttons
            if (lastSelected instanceof Text
                    || lastSelected instanceof DraggableImage
                    || lastSelected instanceof Background) {
                lastSelected.remove();
                App.app.getTransactions().pushUndo(new RemoveObjectT(lastSelected));
            }
        } catch (NullPointerException ex) {
        }
    }

    public void handleFontFamilyComboBox(String family) {
        Object lastSelected = App.app.getDataComponent().getLastSelectedElement();

        if (lastSelected instanceof ChangableFont) {
            ChangableFont fontNode = (ChangableFont) lastSelected;
            if (!family.equals(((ChangableFont) lastSelected).getLabel().getFont().getFamily())) {
                App.app.getTransactions().pushUndo(new ChangeFont(fontNode.getLabel()));
                fontNode.changeFontFamily(family);
            }
        }

    }

    public void handleFontSizeComboBox(String sizeString) {
        Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
        int size = Integer.valueOf(sizeString);

        if (lastSelected instanceof ChangableFont) {
            ChangableFont fontNode = (ChangableFont) lastSelected;
            App.app.getTransactions().pushUndo(new ChangeFont(fontNode.getLabel()));
            fontNode.changeFontSize(size);
        }
    }

    public void handleItalButton() {
        Object lastSelected = App.app.getDataComponent().getLastSelectedElement();

        if (lastSelected instanceof ChangableFont) {
            ChangableFont fontNode = (ChangableFont) lastSelected;
            App.app.getTransactions().pushUndo(new ChangeFont(fontNode.getLabel()));

            fontNode.changeFontItal();
        }

    }

    public void handleBoldButton() {
        Object lastSelected = App.app.getDataComponent().getLastSelectedElement();
        if (lastSelected instanceof ChangableFont) {
            ChangableFont fontNode = (ChangableFont) lastSelected;
            App.app.getTransactions().pushUndo(new ChangeFont(fontNode.getLabel()));
            fontNode.changeFontBold();
        }
    }

    public void handleShowGridToggleButton() {
        App.app.getWorkspace().getCanvasComponent().showGrid();
    }

    public void handleZoomInButton() {
        App.app.getWorkspace().getCanvasComponent().zoomOut();
    }

    public void handleZoomOutButton() {
//        App.app.getWorkspace().getCanvasComponent().zoomOut();
          App.app.getDataComponent().getSelectedLine().circulate();

    }

    public void handleEnlargeMapButton() {
        if (!App.app.getWorkspace().getCanvasComponent().enlargeMap()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Reached maximum size");
            alert.setHeaderText("Reached maximum size");
            alert.showAndWait();
        }
    }

    public void handleReduceMapButton() {
        if (!App.app.getWorkspace().getCanvasComponent().reduceMap()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Reached minimum size");
            alert.setHeaderText("Reached minimum size");
            alert.showAndWait();
        }
    }

}
