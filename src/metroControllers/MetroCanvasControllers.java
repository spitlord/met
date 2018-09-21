/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroControllers;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class MetroCanvasControllers {
    App app;
    
    public MetroCanvasControllers(App app) {
        this.app = app;
    }
    
    
    public void handleNavigateCanvas(KeyCode keycode) {
        Pane canvas = app.getWorkspace().getCanvasComponent().getCanvas();
        if (keycode == KeyCode.A) {
            canvas.setLayoutX(canvas.getLayoutX() - 8);
        } else if (keycode == KeyCode.D) {
            canvas.setLayoutX(canvas.getLayoutX() + 8);
        } else if (keycode == KeyCode.W) {
            canvas.setLayoutY(canvas.getLayoutY() - 8);
        } else if (keycode == KeyCode.S) {
            canvas.setLayoutY(canvas.getLayoutY() + 8);
        }

    }
    
}
