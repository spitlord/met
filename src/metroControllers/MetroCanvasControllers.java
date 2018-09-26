/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroControllers;


import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import metroApp.App;

/**
 *
 * @author XDXD
 */
public class MetroCanvasControllers {

    final static int moveValue = 15;
    App app;
    public static boolean aDown;
    public static boolean dDown;
    public static boolean sDown;
    public static boolean wDown;

    public MetroCanvasControllers(App app) {
        this.app = app;
    }

    public void handleNavigateCanvas(KeyEvent e) {
    
        Pane canvas = app.getWorkspace().getCanvasComponent().getCanvas();
        if (aDown) {
            if (sDown) {
                canvas.setLayoutX(canvas.getLayoutX() - moveValue);
                canvas.setLayoutY(canvas.getLayoutY() + moveValue);
            } else if (wDown) {
                canvas.setLayoutX(canvas.getLayoutX() - moveValue);
                canvas.setLayoutY(canvas.getLayoutY() - moveValue);

            } else {
                canvas.setLayoutX(canvas.getLayoutX() - moveValue);
            }
        } else if (dDown) {
            if (sDown) {
                canvas.setLayoutX(canvas.getLayoutX() + moveValue);
                canvas.setLayoutY(canvas.getLayoutY() + moveValue);
            } else if (wDown) {
                canvas.setLayoutX(canvas.getLayoutX() + moveValue);
                canvas.setLayoutY(canvas.getLayoutY() - moveValue);
            } else {
                canvas.setLayoutX(canvas.getLayoutX() + moveValue);
            }
        } else if (wDown) {
            canvas.setLayoutY(canvas.getLayoutY() - moveValue);
        } else if (sDown) {
            canvas.setLayoutY(canvas.getLayoutY() + moveValue);
        }

    }

}
