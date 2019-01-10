/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasObjects;

/**
 *
 * @author spitlord
 */
public interface Movable {
    public void setX(double x);
    public void setY(double y);
    public double  getX();
    public double  getY();
    public Movable getMovable();
    
    
}
