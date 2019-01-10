/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroData;

import canvasObjects.Station;

/**
 *
 * @author spitlord
 */

//linked list
public class Node {
    
    Station station;
    Node next, prev;
    boolean visited;
    int distance;
    
    
    public Node(Station station) {
        this.station = station;
        distance = Integer.MAX_VALUE;
    }
    
    
    
    
}
