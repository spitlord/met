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

/**
 *
 * @author spitlord
 */
public class Background implements Addable {
    
    private File file;
    private Image image;
    private ImageView imageView;
    
     public Background(File file) {
        
        this.file = file;
        image = new Image(file.toURI().toString()); // questionable af
        imageView = new ImageView(image);
        add();

        
        imageView.setOnMousePressed(e -> {
            if (e.getTarget() == imageView) {
                App.app.getDataComponent().getMetroLines().forEach(line -> {
                line.getBeginning().getCircle().setVisible(false);
                line.getEnd().getCircle().setVisible(false);
                    for (int i = 0; i < line.getConnections().size(); i++) {
                        line.getConnections().get(i).getControl().setVisible(false);    
                    }
                    
                    try {
                    App.app.getDataComponent().getSelectedConnection().getControl().setVisible(false);
                    } catch (NullPointerException ex) {}
                    App.app.getDataComponent().setSelectedConnection(null);
                    App.app.getDataComponent().setLastSelectedElement(this);

                } );
            }
        });
    }
     
    @Override
    public void add() {
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().add(0, imageView);
        App.app.getDataComponent().setBackground(this);
        
    }
    
    
    @Override
    public void remove() {
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(imageView);
        App.app.getDataComponent().getImages().remove(this);
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
    
}
