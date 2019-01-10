package canvasObjects;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import metroApp.App;
import objectUtilities.InsertStation;

public final class MetroLine implements Addable, ChangableColor {

    ArrayList<Station> stations;
    ArrayList<Connection> connections;
    
    private String name;
    private boolean circular;
    private Connection circleArc;
    private final Station beginning;
    private final Station end;
    private Connection begArc;
    private Connection endArc;
    
    
    double lineThickness;
    Color color;

    public MetroLine(String name) {
        // set the name
        this.name = name;
        lineThickness = 2;
        color = Color.BLACK;
        circular = false;

        // one end of the line 
        stations = new ArrayList<>();
        connections = new ArrayList<>();

        beginning = new Station(name, true);
        beginning.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2 - 60);
        beginning.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2 - 60);
        beginning.getCircle().setRadius(15);
        beginning.getCircle().setStrokeWidth(3);

        end = new Station(" ", true);
        end.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2);
        end.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getWidth() / 2);
        end.getCircle().setRadius(15);
        end.getCircle().setStrokeWidth(3);

        stations.add(beginning);
        stations.add(end);

        Connection connection = new Connection(this);
        connections.add(connection);
        connection.connect(beginning, end);

        add();
        App.app.getDataComponent().setSelectedLine(this);
    }

    @Override
    public void add() {
        beginning.add();
        end.add();
        for (int i = 0; i < stations.size() - 1; i++) {
            connections.get(i).showCurve();
        }
        App.app.getDataComponent().getMetroLines().add(this);
    }

    
    public void addStation(Station station, int index) {
        // && !station.isEndOfLine()
        if (!stations.contains(station)) {
            station.lines.add(this);
            connections.get(index - 1).disconnect();
            connections.remove(index - 1);
            Connection minConnection = new Connection(this);
            minConnection.connect(stations.get(index - 1), station);
            Connection maxConnection = new Connection(this);
            maxConnection.connect(stations.get(index), station);
            connections.add(index - 1, minConnection);
            connections.add(index, maxConnection);
            stations.add(index, station);
        }
    }

    public void addStationNearest(Station station) {
            if (!stations.contains(station) && !station.isEndOfLine()) {
                int connection = InsertStation.findWhereToInsert(this, station);
                addStation(station, connection + 1);
                centerStation(station);
        }
    }

    private void centerStation(Station station) {
        int index = stations.indexOf(station);
        double centerX = (stations.get(index - 1).getCircle().getCenterX()
                + stations.get(index).getCircle().getCenterX()) / 2.0;
        double centerY = (stations.get(index - 1).getCircle().getCenterY()
                + stations.get(index).getCircle().getCenterY()) / 2.0;
        connections.get(index - 1).control.setCenterX(centerX);
        connections.get(index - 1).control.setCenterY(centerY);
        centerX = (stations.get(index).getCircle().getCenterX()
                + stations.get(index + 1).getCircle().getCenterX()) / 2.0;
        centerY = (stations.get(index).getCircle().getCenterY()
                + stations.get(index + 1).getCircle().getCenterY()) / 2.0;
        connections.get(index).control.setCenterX(centerX);
        connections.get(index).control.setCenterY(centerY);
    }

    public boolean removeStationFromLine(Station station) {
        // !station.isEndOfLine()
        if (circular && stations.size() <= 2) {
            return false;
        }
        
        if (stations.contains(station) && !station.isEndOfLine()) {
            int index = stations.indexOf(station);
            connections.get(index).disconnect();
            connections.get(index - 1).disconnect();
            Connection newConnection = new Connection(this);
            newConnection.connect(stations.get(index - 1), stations.get(index + 1));
            connections.remove(index);
            connections.remove(index - 1);
            connections.add(index - 1, newConnection);
            stations.remove(station);
            return true;
        }
        return false;
        
        
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
        connections.forEach(connection
                -> connection.connectionCurve.setStrokeWidth(thickness));
    }

    @Override
    public void changeColor(Color c) {
        this.color = c;
        connections.forEach(connection
                -> connection.connectionCurve.setStroke(color));
        beginning.getCircle().setStroke(color);
        end.getCircle().setStroke(color);
    }

    @Override
    public void remove() {
        for (int i = 1; i < stations.size() - 1; i++) {
            stations.get(i).lines.remove(this);
        }
        for (int j = 0; j < connections.size(); j++) {
            connections.get(j).hideCurve();
        }
        beginning.remove();
        end.remove();
        App.app.getDataComponent().getMetroLines().remove(this);
    }

    public void hideEnds() {
        beginning.circle.setVisible(false);
        end.circle.setVisible(false);
    }

    public void showEnds() {
        if (!circular) {
            beginning.circle.setVisible(true);
            end.circle.setVisible(true);
        }
    }

    public boolean circulate() {
        if (stations.size() >= 4) {
            begArc = connections.get(0);
            begArc.hideCurve();
            endArc = connections.get(connections.size()-1);
            endArc.hideCurve();
            hideEnds();
            circleArc = new Connection(this);
            circleArc.connect(stations.get(1), stations.get(stations.size()-2));
            connections.add(circleArc);
            circular = true;
            return true;
        }
        return false;

    }

    public void decirculate() {
        circleArc.disconnect();
//        connections.remo

        circular = false;
    }

    public void editLine(Color c, String newName) {
        changeColor(c);
        name = newName;
        beginning.label.setText(name);
    }

    public void deleteConnection(int index) {
        connections.get(index).disconnect();
        connections.remove(index);
    }

    public void addConnection(int index, Connection c) {
        connections.add(index, c);
        c.showCurve();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        beginning.getLabel().setText(name);
    }

}
