/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import javafx.scene.shape.Circle;

/**
 *
 * @author spitlord
 */
public class MoveCircle implements Transaction {
    Circle c;
    double x, y;

    public MoveCircle(Circle c) {
        this.c = c;
        this.x = c.getCenterX();
        this.y = c.getCenterY();
    }

    @Override
    public void undo() {
        double tempX, tempY;
        tempX = c.getCenterX();
        tempY = c.getCenterY();
        c.setCenterX(x);
        c.setCenterY(y);
        x = tempX;
        y = tempY;        
    }

    @Override
    public void redo() {
        undo(); 
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Circle getC() {
        return c;
    }
    
    
    
    
    
    
    
}
