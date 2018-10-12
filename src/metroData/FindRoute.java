/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroData;

import java.util.ArrayList;
import metroDraggableObjects.Station;

/**
 *
 * @author spitlord
 */
public class FindRoute {
    
    ArrayList<Node> queue;
    
    
    
    public void shortestPath(Station from, Station to) {
        ArrayList<Node> stack = new ArrayList();
        Node start = new Node(from);
        start.visited = true;
        
        while(!stack.isEmpty()) {
            
        }
    }
    
    
    private void enqueue(Node a) {
        queue.add(0,a);
    }
    
    private Node dequeue() {
        return queue.remove(0);
        
    }
}
