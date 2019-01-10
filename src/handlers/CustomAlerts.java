package handlers;

import canvasObjects.MetroLine;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import metroApp.App;


public class CustomAlerts {
    
    
    public static void listStations(MetroLine line) {
        // create stage
        VBox root = new VBox();
        Label lineName = new Label(line.getName());
        lineName.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        root.getChildren().add(lineName);
        // for color
        Circle c = new Circle(7);
        c.setFill(line.getColor());
        root.getChildren().add(c);

        for (int i = 1; i < line.getStations().size() - 1; i++) {
            root.getChildren().add(new Label(line.getStations().get(i).getName()));
        }
        Stage stage = new Stage();
        Scene sceneX = new Scene(root);
        App.app.getWorkspace().initStylesheets(sceneX, "cssForWelcomeDialog");
        root.getStyleClass().add("welcome-dialog");
        stage.setScene(sceneX);
        stage.showAndWait();
    }

}
