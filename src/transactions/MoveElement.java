/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import canvasObjects.Movable;

/**
 *
 * @author spitlord
 */
public class MoveElement implements Transaction {
    Movable[] movables;
    double [] xs;
    double [] ys;

    public MoveElement(Movable ... movables) {
        this.movables = movables;
        this.xs = new double[movables.length];
        this.ys = new double[movables.length];
        for (int i = 0; i < movables.length; i++) {
            xs[i] = movables[i].getX();
            ys[i] = movables[i].getY();       
        }
    }

    @Override
    public void undo() {
        double tempX, tempY;
        for (int i = 0; i < movables.length; i++) {
        tempX = movables[i].getX();
        tempY = movables[i].getY();
        movables[i].setX(xs[i]);
        movables[i].setY(ys[i]);
        xs[i] = tempX;
        ys[i] = tempY; 
        }
    }

    @Override
    public void redo() {
        undo(); 
    }

    public double getX() {
        return xs[0];
    }

    public double getY() {
        return ys[0];
    }

    public Movable getMovable() {
        return movables[0];
    }

    
    
    
    
    
    
    
}
