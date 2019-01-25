/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasObjects;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metroApp.App;
import transactions.MoveElement;
import transactions.Transaction;

/**
 *
 * @author XDXD
 */
public class DraggableImage implements Movable, Addable {
    

    double startX, startY, translateX, translateY;
    Image image;
    File file;
    ImageView imageView;
    
    public DraggableImage(File file) {
        
        this.file = file;
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        add();
        
        imageView.setOnMousePressed(e -> {
            App.app.getTransactions().pushUndo(new MoveElement(this));
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
        imageView.setOnMouseReleased(e -> {
              Transaction temp = App.app.getTransactions().peekUndo();
              if (temp instanceof MoveElement) {
                  if (((MoveElement) temp).getMovable() == this &&
                       ((MoveElement) temp).getX() == imageView.getTranslateX() &&
                       ((MoveElement) temp).getY() == imageView.getTranslateY()) {
                      App.app.getTransactions().popUndoWithouAction();
                  }
              }
        });
    }
    
    public void add() {
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(imageView);
        App.app.getDataComponent().getImages().add(this);
    }
    public boolean remove() {
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(imageView);
        App.app.getDataComponent().getImages().remove(this);
        return true;
    }

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public File getFile() {
        return file;
    }
    
    
     
    @Override
    public void setX(double x) {
         imageView.setTranslateX(x);
    }
    @Override
    public void setY(double y) {
         imageView.setTranslateY(y);
    }

    @Override
    public double getX() {
         return imageView.getTranslateX();    }

    @Override
    public double getY() {
        return imageView.getTranslateY();
    }

    @Override
    public Movable getMovable() {
        return this;
    }
    
    
    
    
    
    
}
