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
    

    double startX, startY, translateX, translateY;
    Image image;
    File file;
    ImageView imageView;
    
    public DraggableImage(File file) {
               

        
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(imageView);
        App.app.getDataComponent().getImages().add(this);
        
        imageView.setOnMousePressed(e -> {
            startX = e.getSceneX();
            startY = e.getSceneY();
            
            translateX = imageView.getTranslateX();
            translateY = imageView.getTranslateY();       
            imageView.toFront();
            App.app.getDataComponent().setLastSelectedElement(this);
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
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(imageView);
    }
    
}
