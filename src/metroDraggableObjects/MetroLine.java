/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroDraggableObjects;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.json.JsonArray;
import metroApp.App;
import metroWorkspace.MetroCanvas;

/**
 *
 * @author XDXD
 */
public class MetroLine implements ChangableColor {
    private String name;
    private boolean circular;
    ArrayList<Station> stations;
    ArrayList<Connection> connections;
    private final Station beginning;
    private final Station end;
    double lineThickness;
    Color color;
    
    public MetroLine(String name) {
        MetroCanvas metroCanvas = App.app.getWorkspace().getCanvasComponent(); 
       
       // set the name
       this.name = name;
       
       // create corresponding label
       // come back to this line!!!!!!!!!!!!!!!!!!!!!


       // initialize thickness and color
       lineThickness = 2;
       color = Color.BLACK;
       
       // circular stations are not supported
       circular = false;
       
       // one end of the line 
       beginning = new Station(" " + name);
       beginning.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight()/2 - 60);
       beginning.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight()/2 - 60);
       beginning.getCircle().setRadius(15);
       beginning.getCircle().setStrokeWidth(3);
       
       // second end of the line
       end = new Station(" ");
       end.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight()/2);
       end.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getWidth()/2);
       end.getCircle().setRadius(15);
       end.getCircle().setStrokeWidth(3);
       
       
       // set up arays
       // the array stations represents order of stations in the line
       // the array connection represents the lines in between stations
       stations =  new ArrayList<Station>();
       connections = new ArrayList<Connection>();

      
       // the array represents order of stations in the line
       // functions are designed in such way that the line ends
       // are never affected
       stations.add(beginning);
       stations.add(end); 
       
       
       // Curve connecting two inital ends which hApp.appen to be nameless stations
       Connection connection = new Connection(this);
       connection.connect(beginning, end);
       
       // add the connection into the respective array
       connections.add(connection);
       
       // for the combobox and selection
       App.app.getDataComponent().getMetroLines().add(this);
       App.app.getDataComponent().setSelectedLine(this);
      
    }

    public String getName() {
        return name;
    }
    
    public void addStationToTheLine(Station station) {
        
        // if station is only a part of one line, then we can move it gently;
        if ( (!stations.contains(station))){ 
            
            // add to the array
            station.lines.add(this);
            
            // find two stations to add in between
            int firstClosest = firstClosestStation(station);
            int secondClosest = secondClosestStation(firstClosest, station);
            
            
           

            // disconnect them, the connection is in connections array (at min(a,b))
            int min = Math.min(firstClosest, secondClosest);
            int max = Math.max(firstClosest, secondClosest);
            connections.get(min).disconnect();
            
            // connect both stations to the new one
            Connection minConnection = new Connection(this);
            minConnection.connect(stations.get(min), station);
            
            
            Connection maxConnection = new Connection(this);
            maxConnection.connect(stations.get(max), station);
            connections.remove(min);
            connections.add(min, minConnection);
            connections.add(max, maxConnection);
            
            stations.add(max, station); 
            
                    // center the station in between
            if (station.lines.size() == 0) {
            
                double centerX = (stations.get(min).getCircle().getCenterX() +
                                  stations.get(max).getCircle().getCenterX())/2.0;

                double centerY = (stations.get(min).getCircle().getCenterY() +
                                  stations.get(max).getCircle().getCenterY())/2.0;

                station.getCircle().setCenterX(centerX);
                station.getCircle().setCenterY(centerY);

                // insert the station is the middle the new station in between 


                // center the control circles

                centerX = (stations.get(min).getCircle().getCenterX() +
                           stations.get(max).getCircle().getCenterX())/2.0;

                centerY = (stations.get(min).getCircle().getCenterY() +
                           stations.get(max).getCircle().getCenterY())/2.0;

                connections.get(min).control.setCenterX(centerX);
                connections.get(min).control.setCenterY(centerY);

                centerX = (stations.get(max).getCircle().getCenterX() +
                           stations.get(max+1).getCircle().getCenterX())/2.0;

                centerY = (stations.get(max).getCircle().getCenterY() +
                           stations.get(max+1).getCircle().getCenterY())/2.0;

                connections.get(max).control.setCenterX(centerX);
                connections.get(max).control.setCenterY(centerY);
            }
        }
    }
    
    
    
    
    
    
    
    
    public void loadStationsToTheLine(JsonArray jsonStations) {
        Station station;
        connections.get(0).disconnect();
        connections.remove(0);
        
        
        for (int j = 0; j < App.app.getDataComponent().getMetroStations().size(); j++) {
            station = App.app.getDataComponent().getMetroStations().get(j);
            String temp;
            for (int k = 0; k < jsonStations.size(); k++) {
                temp = jsonStations.getString(k);
                if (station.getName().equals(temp)) {
                   stations.add(this.stations.size()-1, station);
                }
            }
        }
        
        
          for (int j = 1; j < stations.size(); j++) {
              Connection c = new Connection(this);
              c.connect(stations.get(j), stations.get(j-1));
              connections.add(c);
              
          }
        
    }
    
    
    public void removeStationFromTheLine(Station station) {
        // if a station is actually on the line && it's not a line's end
        try {
        if (stations.contains(station) && !station.getName().equals("")) {
            
            // index in the stations array
            int stationIndex = stations.indexOf(station);
            
            // destroy connections with surrounding stations
            connections.get(stationIndex).disconnect();
            connections.get(stationIndex-1).disconnect();
            
            // connect the surrounding stations
            Connection newConnection = new Connection(this);
            newConnection.connect(stations.get(stationIndex-1), stations.get(stationIndex+1));
            
            // remove the connections
            connections.remove(stationIndex);
            connections.remove(stationIndex-1);
            
            // put connection in the array
            connections.add(stationIndex-1, newConnection);

            // remove station from the array
            stations.remove(station);     
        }   
        } catch (Exception exc) {}
        
        
    }
    
    public int firstClosestStation(Station target) {
        
        // set first station on the list to be the closest
        int closest = 0;
        Station temp;
        double minDistance = 0;
        double tempDistance;
        for (int i = 0; i < stations.size(); i++) {
            
            // get station from the list
            temp = stations.get(i);
            
            if (i == 0) {
                tempDistance = Math.abs(
                        Math.pow(temp.getCircle().getCenterX() - target.getCircle().getCenterX(), 2) +
                        Math.pow(temp.getCircle().getCenterY() - target.getCircle().getCenterY(), 2));
                tempDistance = Math.sqrt(tempDistance);
                minDistance = tempDistance;
            }
            else {
                // find distance between target station and the current
                tempDistance = Math.abs(
                        Math.pow(temp.getCircle().getCenterX() - target.getCircle().getCenterX(), 2) +
                        Math.pow(temp.getCircle().getCenterY() - target.getCircle().getCenterY(), 2));
                tempDistance = Math.sqrt(tempDistance);
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    // change index of closest station on the list to new one
                    closest = i;     
                }
            
            }
        }
        return closest;
    }
        
    public int secondClosestStation(int closest, Station target) {
        
        // is closest a line end?
        if (closest == 0) return 1;
        else if (closest == stations.size() - 1) return stations.size() - 2;
        // no, then check both right and left neightbours
        else {
            
            Station leftNeighbour = stations.get(closest-1);
            Station rightNeighbour = stations.get(closest+1);
            
            double tempDistanceLeft = Math.abs(
                        Math.pow(leftNeighbour.getCircle().getCenterX() - target.getCircle().getCenterX(), 2) +
                        Math.pow(leftNeighbour.getCircle().getCenterY() - target.getCircle().getCenterY(), 2));
            tempDistanceLeft = Math.sqrt(tempDistanceLeft);
                
                
            double tempDistanceRight =  Math.abs(
                        Math.pow(rightNeighbour.getCircle().getCenterX() - target.getCircle().getCenterX(), 2) +
                        Math.pow(rightNeighbour.getCircle().getCenterY() - target.getCircle().getCenterY(), 2));
            tempDistanceRight = Math.sqrt(tempDistanceRight);
            
            
            if (tempDistanceLeft < tempDistanceRight) return closest-1;
            else return closest+1;
        }
        
    }

    public double getLineThickness() {
        return lineThickness;
    }

    public Station getBeginning() {
        return beginning;
    }

    public Station getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }
    
    

    public ArrayList<Station> getStations() {
        return stations;
    }
    
    

    public ArrayList<Connection> getConnections() {
        return connections;
    }
    
    
    
    
    public void changeLineThickness(double thickness) {
        lineThickness = thickness;
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).connectionCurve.setStrokeWidth(thickness);
        }
    }

    @Override
    public void changeColor(Color c) {
        this.color = c;

        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).connectionCurve.setStroke(c);
        }
        
       beginning.getCircle().setStroke(c);
       end.getCircle().setStroke(c);
    }
    
    public void deleteLine() {
        
        
        // for every station, delete the line from list of lines.
        // i.e. everyone forget this line;
       
        for (int i = 1; i < stations.size()-1; i++) {
            stations.get(i).lines.remove(this);
        }
        
        // visually dissapear
        for (int j = 0; j < connections.size(); j++) {
            connections.get(j).disconnect();
        }
        
        // the label
        
        beginning.label.setVisible(false);
        // remove the handles
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(this.getEnd().getCircle());
        App.app.getWorkspace().getCanvasComponent().getCanvas().getChildren().remove(this.getBeginning().getCircle());
        
        
        // remove this line from the data object
        App.app.getDataComponent().getMetroLines().remove(this);
        
        
        // no line is selected
        App.app.getDataComponent().setSelectedLine(null);
                
                
        
        
    }
    
    public void hideEnds() {
        beginning.circle.setVisible(false);
        end.circle.setVisible(false);
    }
    
    public void showEnds() {
        beginning.circle.setVisible(true);
        end.circle.setVisible(true);
    }
    
    
    
    
    
    public void listStations() {
        String listOfStations = null;
        
        // create stage
        VBox root = new VBox();
        Label lineName = new Label(name);
        lineName.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        root.getChildren().add(lineName);
        // for color
        Circle c = new Circle(7);
        c.setFill(color);
        root.getChildren().add(c);
        
        for (int i = 1; i < stations.size()-1; i++) {
            root.getChildren().add(new Label(stations.get(i).getName()));
        }
        
        Stage stage = new Stage();
        
        Scene sceneX = new Scene(root);
        App.app.getWorkspace().initStylesheets(sceneX, "cssForWelcomeDialog");
        root.getStyleClass().add("welcome-dialog");
        stage.setScene(sceneX);
        stage.showAndWait();
    }
    
   
    
    public void editLine(Color c, String newName) {
        changeColor(c);
        name = newName;
        beginning.label.setText(name);
    }


    
    
    
 
         

}
        
    
      
           
