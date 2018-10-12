/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import metroDraggableObjects.Movable;

/**
 *
 * @author spitlord
 */
public class MoveElement implements Transaction {
    Movable m;
    double x, y;

    public MoveElement(Movable m) {
        this.m = m;
        this.x = m.getX();
        this.y = m.getY();
    }

    @Override
    public void undo() {
        double tempX, tempY;
        tempX = m.getX();
        tempY = m.getY();
        m.setX(x);
        m.setY(y);
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

    public Movable getMovable() {
        return m;
    }

    
    
    
    
    
    
    
}
