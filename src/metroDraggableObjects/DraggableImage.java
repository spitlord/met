/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class DraggableImage {
    
    App app;
    double startX, startY, translateX, translateY;
    Image image;
    ImageView imageView;
    
    public DraggableImage(App app, File file) {
        
        this.app = app;
        
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(imageView);
        
        imageView.setOnMousePressed(e -> {
            startX = e.getSceneX();
            startY = e.getSceneY();
            
            translateX = imageView.getTranslateX();
            translateY = imageView.getTranslateY();       
            imageView.toFront();
            app.getDataComponent().setLastSelectedElement(this);
        });
        imageView.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - startX;
            double offsetY = e.getSceneY() - startY;
            double finalTranslateX = translateX + offsetX;
            double finalTranslateY = translateY + offsetY;
            
            imageView.setTranslateX(finalTranslateX);
            imageView.setTranslateY(finalTranslateY);
            
            e.consume();
        });
    }
    
    public void deleteImage() {
        app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(imageView);
    }
    
}
