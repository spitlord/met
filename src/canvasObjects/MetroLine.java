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
    public Connection circleArc;
    private final Station beginning;
    private final Station end;
    private Connection begArc;
    private Connection endArc;

    double lineThickness;
    Color color;

    public MetroLine(String name) {
        this.name = name;
        lineThickness = 15;
        color = Color.BLACK;
        circular = false;
        stations = new ArrayList();
        connections = new ArrayList();

        beginning = new Station(name, true);
        beginning.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2 - 60);
        beginning.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2 - 60);
        beginning.getCircle().setRadius(15);
        beginning.getCircle().setStrokeWidth(3);

        end = new Station("", true);
        end.getCircle().setCenterX(App.app.getWorkspace().getCanvasComponent().getCanvas().getHeight() / 2);
        end.getCircle().setCenterY(App.app.getWorkspace().getCanvasComponent().getCanvas().getWidth() / 2);
        end.getCircle().setRadius(15);
        end.getCircle().setStrokeWidth(3);

        stations.add(beginning);
        stations.add(end);

        Connection connection = new Connection(this);
        connection.connect(beginning, end);
        connection.add();
        this.add();
    }

    @Override
    public void add() {
        beginning.add();
        end.add();
        connections.forEach(c -> c.showCurve());
        App.app.getDataComponent().getMetroLines().add(this);
    }

    public void addStation(Station station, int index) {
        if (!stations.contains(station)) {
            station.lines.add(this);
            if (circular) {
                Connection minConnection = new Connection(this);
                Connection maxConnection = new Connection(this);
                if (index == -1) {
                    circleArc.disconnect();
                    minConnection.connect(station, stations.get(1));
                    minConnection.add(0);
                    maxConnection.connect(station, stations.get(stations.size()-2));
                    maxConnection.add();
                    circleArc = maxConnection;
                    stations.add(1, station);
                } else  {
                    connections.get(index - 1).disconnect();
                    minConnection.connect(stations.get(index), station);
                    minConnection.add(index - 1);
                    if (index != connections.size()) {
                        maxConnection.connect(stations.get(index + 1), station);
                        maxConnection.add(index);
                    } else {
                        System.out.println("eva here");
                        circleArc = maxConnection;
                        maxConnection.connect(station, stations.get(1));
                        maxConnection.add();
                    }
                    stations.add(index + 1, station);
                }
            } else {
                connections.get(index - 1).disconnect();
                Connection minConnection = new Connection(this);
                minConnection.connect(stations.get(index - 1), station);
                minConnection.add(index - 1);
                Connection maxConnection = new Connection(this);
                maxConnection.connect(stations.get(index), station);
                maxConnection.add(index);
                stations.add(index, station);
            }
        }
    }

    public void addStationNearest(Station station) {
        if (!stations.contains(station) && !station.isEndOfLine()) {
            int connection = InsertStation.findWhereToInsert(this, station);
            addStation(station, connection + 1);
        }
    }

    public boolean removeStationFromLine(Station station) {
        if (stations.contains(station) && !station.isEndOfLine()) {
            Connection newConnection = new Connection(this);
            int index = stations.indexOf(station);
            if (circular) {
                if (stations.size() <= 4) return false;
                if (index == 1) {
                    newConnection.connect(stations.get(stations.size() - 2), stations.get(index + 1));
                    stations.remove(station);
                    connections.get(index - 1).disconnect();
                    circleArc.disconnect();
                    newConnection.add();
                    circleArc = newConnection;
                } else if (index == stations.size() - 2) {
                    newConnection.connect(stations.get(index - 1), stations.get(1));
                    stations.remove(station);
                    circleArc.disconnect();
                    connections.get(connections.size()-1).disconnect();
                    newConnection.add();
                    circleArc = newConnection;
                } else {
                    newConnection.connect(stations.get(index - 1), stations.get(index + 1));
                    stations.remove(station);
                    connections.get(index - 2).disconnect();
                    connections.get(index - 2).disconnect();
                    newConnection.add(index - 2);
                }
            } else {
                newConnection.connect(stations.get(index - 1), stations.get(index + 1));
                stations.remove(station);
                connections.get(index - 1).disconnect();
                connections.get(index - 1).disconnect();
                newConnection.add(index - 1);
                // changed
            }
            return true;
        }
        return false;
    }

    public void changeLineThickness(double thickness) {
        lineThickness = thickness;
        connections.forEach(c
                -> c.connectionCurve.setStrokeWidth(thickness));
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
    public boolean remove() {
        for (int i = 1; i < stations.size() - 1; i++) {
            stations.get(i).lines.remove(this);
        }
        connections.forEach(c -> c.hideCurve());
        beginning.remove();
        end.remove();
        App.app.getDataComponent().getMetroLines().remove(this);
        return true;
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
        if (!circular && stations.size() >= 4) {
            circular = true;
            begArc = connections.get(0);
            endArc = connections.get(connections.size() - 1);
            begArc.disconnect();
            endArc.disconnect(); // never the same
            hideEnds(); // for later
            circleArc = new Connection(this);
            circleArc.connect(stations.get(1), stations.get(stations.size() - 2));
            circleArc.add();
            return true;
        }
        return false;
    }

    public boolean decirculate() {
        if (circular) {
            circular = false;
            circleArc.disconnect();
            begArc = new Connection(this);
            begArc.connect(beginning, stations.get(1));
            begArc.add(0);
            
            endArc = new Connection(this);
            endArc.connect(stations.get(stations.size() - 2), end);
            endArc.add();
            showEnds();
            return true;
        }
        return false;
    }

    public void editLine(Color c, String newName) {
        changeColor(c);
        name = newName;
        beginning.label.setText(name);
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
        beginning.getLabel().setText(name);
    }

    public boolean isCircular() {
        return circular;
    }

}
