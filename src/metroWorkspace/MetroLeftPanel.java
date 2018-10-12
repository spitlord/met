/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroWorkspace;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import metroApp.App;
import metroControllers.MetroControllersLeft;
import metroDraggableObjects.MetroLine;
import metroDraggableObjects.Station;

/**
 *
 * @author Mark Koshkin
 */
public class MetroLeftPanel {
    
   private MetroControllersLeft controllers;
   
  // VBox is a container that holds other VBoxes, which contain
  // HBoxes for every line of controls. 
   
   VBox leftPanel;  // container of containers

   /// #####################
   /// Container #1: metroLinesToolbar 
   VBox metroLinesToolbar;

   // 1row
   HBox metroLinesToolbarA;
   Label metroLinesLabel;
   ComboBox lineComboBox;
   ColorPicker lineColorPicker;

   // 2row
   HBox metroLinesToolbarB;
   Button addLineButton;
   Button removeLineButton;
   Button editLineButton;
   Button addStationsToLineButton;
   Button removeStationsFromLineButton;
   Button listAllStationsButton;
   
   // 3row implicit
   Slider lineThicknessSlider;
   
   
   
   
   /// #####################
   /// Container #2: metroStametroStationsToolbar
   VBox metroStationsToolbar;
   
   // 1row
   HBox metroStationsToolbarA; 
   Label metroStationsLabel;
   ComboBox stationsComboBox;
   ColorPicker stationColorPicker; 

   // 2row
   HBox metroStationsToolbarB; 
   Button addStationButton;
   Button removeStationButton;
   Button snapToGridButton;
   Button moveLabelButton;
   Button rotateLabelButton;
   
   // 3row
   Slider stationRadiusSlider;
   
 
   /// #####################
   /// Container #3: stationRouterToolbar
   HBox stationRouterToolbar; 

   VBox stationRouterToolbarA;
   Label stationRouterLabel;
   Button findRouteButton;
     
   VBox stationRouterToolbarB;
   ComboBox fromComboBox;
   ComboBox toComboBox;
   
   /// #####################
   /// Container #4: decorToolbar
   VBox decorToolbar;
   
   HBox decorToolbarA; 
   Label decorLabel;
   ColorPicker setBackgroundColorPicker;

   HBox decorToolbarB;
   Button addImageButton;
   Button addTextButton;
   Button setImageBackgroundButton;
   Button removeElementButton;
   
   /// #####################
   /// Container #5: fontToolbar
   VBox fontToolbar;
   
   HBox fontToolbarA; 
   Label fontLabel;
   ColorPicker fontColorPicker; 

   HBox fontToolbarB; 
   Button italButton;
   Button boldButton;
   ComboBox fontSize;
   ComboBox fontFamily;

   /// #####################
   /// Container #6: fontToolbar
   VBox navigationToolbar;
   
   HBox navigationToolbarA; 
   Label navigationLabel;
   ToggleButton showGridToggleButton;

   HBox navigationToolbarB; 
   Button zoomInButton;
   Button zoomOutButton;
   Button enlargeMapButton;
   Button reduceMapButton;
    
    
   
     public MetroLeftPanel() {
     }

     public  VBox initPanel() {
         
         
        // #1 container
        // #1 container
        
        metroLinesToolbar = new VBox();

        // row 1
        metroLinesToolbarA = new HBox();
        metroLinesLabel = new Label("Metro Lines");
        
        // LINE COMBOBOX
        lineComboBox = new ComboBox(App.app.getDataComponent().getMetroLines());
        lineComboBox.setPromptText("Pick a line");
        
        Callback cellFactory = new Callback<ListView<MetroLine>,ListCell<MetroLine>>(){
 
            @Override
            public ListCell<MetroLine> call(ListView<MetroLine> p) {
                 
                final ListCell<MetroLine> cell = new ListCell<MetroLine>(){
 
                    @Override
                    protected void updateItem(MetroLine t, boolean bln) {
                        super.updateItem(t, bln);
                         
                        if(t != null){
                            setText(t.getName());
                        }else{
                            setText("");
                        }
                    }
  
                };
                 
                return cell;
            }
        
        };
        lineComboBox.setButtonCell((ListCell)cellFactory.call(null));
        lineComboBox.setCellFactory(cellFactory);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //

        lineColorPicker = new ColorPicker();
        lineColorPicker.setValue(Color.BLACK);
        // set tooltip
        lineColorPicker.setTooltip(new Tooltip(App.app.getProperties().getString("lineColorPickerTooltip")));

         
        metroLinesToolbarA.getChildren().add(metroLinesLabel);
        metroLinesToolbarA.getChildren().add(lineComboBox);
        metroLinesToolbarA.getChildren().add(lineColorPicker);
        
        // row 2
        metroLinesToolbarB = new HBox();
        
        addLineButton = new Button();
        removeLineButton = new Button();
        editLineButton = new Button();
        addStationsToLineButton = new Button();
        removeStationsFromLineButton = new Button();
        listAllStationsButton = new Button();         
        initButton(addLineButton, metroLinesToolbarB, "addLineIcon", "addLineTooltip");  
        initButton(removeLineButton, metroLinesToolbarB, "removeLineIcon", "removeLineTooltip");  
        initButton(editLineButton, metroLinesToolbarB, "editLineIcon", "editLineTooltip");
        initButton(addStationsToLineButton, metroLinesToolbarB, "addStationsToLineIcon", "addStationsToLineTooltip");
        initButton(removeStationsFromLineButton, metroLinesToolbarB, "removeStationsFromLineIcon", "removeStationsFromLineTooltip");
        initButton(listAllStationsButton, metroLinesToolbarB, "listAllStationsIcon", "listAllStationsTooltip");  
        
        // row three
        lineThicknessSlider = new Slider();
        lineThicknessSlider.setMin(1);
        lineThicknessSlider.setMax(15);
        
        lineThicknessSlider.setTooltip(new Tooltip(App.app.getProperties().getString("lineThicknessSliderTooltip")));
        
        
        // add three rows in one container 
        metroLinesToolbar.getChildren().add(metroLinesToolbarA);
        metroLinesToolbar.getChildren().add(metroLinesToolbarB);
        metroLinesToolbar.getChildren().add(lineThicknessSlider);

        
        // #2 container
        // #2 container
        
        metroStationsToolbar = new VBox();
        
        // row 1
        metroStationsToolbarA = new HBox();
        
        
        // STATION COMBO BOX
        metroStationsLabel = new Label("Metro Stations");
        
        
        
        stationsComboBox = new ComboBox(App.app.getDataComponent().getMetroStations());
        stationsComboBox.setPromptText("Pick a station");
        
        
        
        
        cellFactory = new Callback<ListView<Station>,ListCell<Station>>(){
 
            @Override
            public ListCell<Station> call(ListView<Station> p) {
                 
                final ListCell<Station> cell = new ListCell<Station>(){
 
                    @Override
                    protected void updateItem(Station t, boolean bln) {
                        super.updateItem(t, bln);
                         
                        if(t != null){
                            setText(t.getName());
                        }else{
                            setText("");
                        }
                    }
  
                };
                 
                return cell;
            }
        
        };
        stationsComboBox.setButtonCell((ListCell)cellFactory.call(null));
        stationsComboBox.setCellFactory(cellFactory); 
        
        

        stationColorPicker = new ColorPicker();
        stationColorPicker.setValue(Color.BLACK);
        stationColorPicker.setTooltip(new Tooltip(App.app.getProperties().getString("stationColorPickerTooltip")));

        
        metroStationsToolbarA.getChildren().add(metroStationsLabel);
        metroStationsToolbarA.getChildren().add(stationsComboBox);
        metroStationsToolbarA.getChildren().add(stationColorPicker);
        
        // row 2
        
        metroStationsToolbarB = new HBox();
        
        addStationButton = new Button();
        removeStationButton = new Button();
        snapToGridButton = new Button();
        moveLabelButton = new Button();
        rotateLabelButton = new Button();
        initButton(addStationButton, metroStationsToolbarB, "addStationIcon", "addStationTooltip");
        initButton(removeStationButton, metroStationsToolbarB, "removeStationIcon", "removeStationTooltip");
        initButton(snapToGridButton, metroStationsToolbarB, "snapToGridIcon", "snapToGridTooltip");
        initButton(moveLabelButton, metroStationsToolbarB, "moveLabelIcon", "moveLabelTooltip");
        initButton(rotateLabelButton, metroStationsToolbarB, "rotateLabelIcon", "rotateLabelTooltip");    
         
        // row 3 
        
        stationRadiusSlider = new Slider();
        stationRadiusSlider.setMin(3);
        stationRadiusSlider.setMax(30);
        
        stationRadiusSlider.setTooltip(new Tooltip(App.app.getProperties().getString("stationRadiusSliderTooltip")));

        
        // add together
        
        metroStationsToolbar.getChildren().add(metroStationsToolbarA);
        metroStationsToolbar.getChildren().add(metroStationsToolbarB);
        metroStationsToolbar.getChildren().add(stationRadiusSlider);
        
        
        // #3 container
        // #3 container
        
        stationRouterToolbar = new HBox();
         
        // column 1 
        stationRouterToolbarA = new VBox();
        stationRouterLabel = new Label("Find Route");
        
        stationRouterToolbarA.getChildren().add(stationRouterLabel);
        findRouteButton = new Button();
        initButton(findRouteButton, stationRouterToolbarA, "findRouteIcon", "findRouteTooltip");

        
        
        // column 2
        stationRouterToolbarB = new VBox();
        fromComboBox = new ComboBox(App.app.getDataComponent().getMetroStations());
        fromComboBox.setPromptText("From");
        
        fromComboBox.setButtonCell((ListCell)cellFactory.call(null));
        fromComboBox.setCellFactory(cellFactory); 

        toComboBox = new ComboBox(App.app.getDataComponent().getMetroStations());
        toComboBox.setPromptText("To");
        toComboBox.setButtonCell((ListCell)cellFactory.call(null));
        toComboBox.setCellFactory(cellFactory);
        
        
       
        
        stationRouterToolbarB.getChildren().add(fromComboBox);
        stationRouterToolbarB.getChildren().add(toComboBox);
        
        stationRouterToolbar.getChildren().add(stationRouterToolbarA);
        stationRouterToolbar.getChildren().add(stationRouterToolbarB);
        
        
        // #4 container
        // #4 container

        decorToolbar = new VBox();
        
        // row 1 
        decorToolbarA = new HBox();
        
        decorLabel = new Label("Decor");
        setBackgroundColorPicker = new ColorPicker();
        setBackgroundColorPicker.setValue(Color.WHITE);
        setBackgroundColorPicker.setTooltip(new Tooltip(App.app.getProperties().getString("setBackgroundColorPickerTooltip")));
        
        decorToolbarA.getChildren().add(decorLabel);
        
        Rectangle rect = new Rectangle(0,0,140,27);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.TRANSPARENT);
        decorToolbarA.getChildren().add(rect);
        decorToolbarA.getChildren().add(setBackgroundColorPicker);
        
        // row 2
        decorToolbarB = new HBox();
        addTextButton = new Button();
        addImageButton = new Button();
        setImageBackgroundButton = new Button();
        removeElementButton = new Button();
        initButton(addTextButton, decorToolbarB, "addTextIcon", "addTextTooltip");
        initButton(addImageButton, decorToolbarB, "addImageIcon", "addImageTooltip");
        initButton(setImageBackgroundButton, decorToolbarB, "setImageBackgroundIcon", "setImageBackgroundTooltip");
        initButton(removeElementButton, decorToolbarB, "removeElementIcon", "removeElementTooltip");
        
        
        // Add together

        decorToolbar.getChildren().add(decorToolbarA);
        decorToolbar.getChildren().add(decorToolbarB);
        
        
        // #5 container
        // #5 container
        
        fontToolbar = new VBox();
        
        // row 1
        
        fontToolbarA = new HBox();
        fontLabel = new Label("Font");
        fontColorPicker  = new ColorPicker();
        fontColorPicker.setValue(Color.BLACK);
        fontColorPicker.setTooltip(new Tooltip(App.app.getProperties().getString("fontColorPickerTooltip")));


        
        fontToolbarA.getChildren().add(fontLabel);
        rect = new Rectangle(0,0,140,27);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.TRANSPARENT);
        fontToolbarA.getChildren().add(rect);
        fontToolbarA.getChildren().add(fontColorPicker);

        // row 2

        fontToolbarB = new HBox();

        italButton = new Button();
        boldButton = new Button();
        initButton(italButton, fontToolbarB, "italIcon", "italTooltip");
        initButton(boldButton, fontToolbarB, "boldIcon", "boldTooltip");

        fontSize = new ComboBox();
        fontSize.setPromptText("Size");
        fontSize.setItems(App.app.getDataComponent().getFontSizes());
        
        fontFamily = new ComboBox();
        fontFamily.setPromptText("Font");
        fontFamily.setItems(App.app.getDataComponent().getFontFamilies());
        
        


        fontToolbarB.getChildren().add(fontSize);
        fontToolbarB.getChildren().add(fontFamily);

        // add together

        fontToolbar.getChildren().add(fontToolbarA);
        fontToolbar.getChildren().add(fontToolbarB);


        // #6 container
        // #6 container

        navigationToolbar = new VBox();
        
        // row 1
        navigationToolbarA = new HBox();
        navigationLabel = new Label("Navigation");
        
        showGridToggleButton = new ToggleButton();
        showGridToggleButton.setGraphic(new ImageView(new Image
                        ("file:" + App.app.getProperties().getString("showGridToggleIcon"))));
        
        showGridToggleButton.setTooltip(new Tooltip(App.app.getProperties().getString("showGridToggleTooltip")));
        


        
        
        navigationToolbarA.getChildren().add(navigationLabel);
        navigationToolbarA.getChildren().add(showGridToggleButton);

        
        // row 2
        navigationToolbarB = new HBox();
        zoomInButton = new Button();
        zoomOutButton = new Button();
        enlargeMapButton = new Button();
        reduceMapButton = new Button();
        initButton(zoomInButton, navigationToolbarB, "zoomInIcon", "zoomInTooltip");
        initButton(zoomOutButton, navigationToolbarB, "zoomOutIcon", "zoomOutTooltip");
        initButton(enlargeMapButton, navigationToolbarB, "enlargeMapIcon", "enlargeMapTooltip");
        initButton(reduceMapButton, navigationToolbarB, "reduceMapIcon", "reduceMapTooltip");
        
        // add together
        
        navigationToolbar.getChildren().add(navigationToolbarA);
        navigationToolbar.getChildren().add(navigationToolbarB);
        
        
        // add all the containers to the left panel

        
        leftPanel = new VBox();
      
        leftPanel.setStyle(  "-fx-background-insets: 0, 1 1 0 0" );
        leftPanel.getChildren().add(metroLinesToolbar);
        leftPanel.getChildren().add(metroStationsToolbar);
        leftPanel.getChildren().add(stationRouterToolbar);
        leftPanel.getChildren().add(decorToolbar);
        leftPanel.getChildren().add(fontToolbar);
        leftPanel.getChildren().add(navigationToolbar);



        
        initStyles();
        initControllers();
       
        return leftPanel;
         
         
         
     }
    
    
     private void initButton(Button button, Pane pane,  String icon, String tooltip) {
       
        // icon
        button.setGraphic(new ImageView(new Image("file:" + App.app.getProperties().getString(icon))));
        
        // tooltip 
        button.setTooltip(new Tooltip(App.app.getProperties().getString(tooltip)));
       
        // style from css file
        button.getStyleClass().add("button");
        
        pane.getChildren().add(button);

        
    
    }
     
  
      
  

    
    private void initStyles() {

      // toggle button
      showGridToggleButton.getStyleClass().add("toggle-button");
              
      // menu labels style 
      metroLinesLabel.getStyleClass().add("menu-label");
      metroStationsLabel.getStyleClass().add("menu-label");
      stationRouterLabel.getStyleClass().add("menu-label");
      decorLabel.getStyleClass().add("menu-label");
      fontLabel.getStyleClass().add("menu-label");
      navigationLabel.getStyleClass().add("menu-label");
      
      // Vboxes
      metroLinesToolbar.getStyleClass().add("left-panel-vbox");
      metroStationsToolbar.getStyleClass().add("left-panel-vbox");    
      stationRouterToolbarA.getStyleClass().add("left-panel-odd-vbox");
      stationRouterToolbarB.getStyleClass().add("left-panel-odd-vbox");
      decorToolbar.getStyleClass().add("left-panel-vbox");
      fontToolbar.getStyleClass().add("left-panel-vbox");
      navigationToolbar.getStyleClass().add("lowest-left-panel-vbox");
      
      
      // color pickers
      fontColorPicker.getStyleClass().add("color-picker");
      stationColorPicker.getStyleClass().add("color-picker");  
      lineColorPicker.getStyleClass().add("color-picker");
      setBackgroundColorPicker.getStyleClass().add("color-picker");
            
      // combo boxes
      fontSize.getStyleClass().add("font-combo-box");
      fontFamily.getStyleClass().add("font-combo-box");
      lineComboBox.getStyleClass().add("combo-box");
      stationsComboBox.getStyleClass().add("combo-box");
      
      // HBoxes
      
        navigationToolbarB.getStyleClass().add("left-panel-hbox");
        navigationToolbarA.getStyleClass().add("left-panel-hbox");    
        fontToolbarB.getStyleClass().add("left-panel-hbox");
        fontToolbarA.getStyleClass().add("left-panel-hbox");   
        decorToolbarA.getStyleClass().add("left-panel-hbox");  
        decorToolbarB.getStyleClass().add("left-panel-hbox");
        stationRouterToolbar.getStyleClass().add("left-panel-odd-hbox");
        metroStationsToolbarB.getStyleClass().add("left-panel-hbox");
        metroStationsToolbarA.getStyleClass().add("left-panel-hbox");
        metroLinesToolbarA.getStyleClass().add("left-panel-hbox");
        metroLinesToolbarB.getStyleClass().add("left-panel-hbox");

      
      // 
      
      
      leftPanel.getStyleClass().add("leftPanel");
    }

    public ComboBox getStationsComboBox() {
        return stationsComboBox;
    }

    public ComboBox getFontFamily() {
        return fontFamily;
    }

    public ComboBox getFromComboBox() {
        return fromComboBox;
    }
    
     public ComboBox getToComboBox() {
        return toComboBox;
    }

    public ComboBox getLineComboBox() {
        return lineComboBox;
    }

    public ComboBox getFontSize() {
        return fontSize;
    }

    
    public Slider getStationRadiusSlider() {
        return stationRadiusSlider;
    }

    public Slider getLineThicknessSlider() {
        return lineThicknessSlider;
    }

    public VBox getLeftPanel() {
        return leftPanel;
    }
    
    
    
    
    
    // sends events to the MetroControllers Class
    private void initControllers() {
        controllers = new MetroControllersLeft();
        
        addLineButton.setOnMouseClicked(e -> {controllers.handleAddLineButton();});
        removeLineButton.setOnMouseClicked(e -> {controllers.handleRemoveLineButton();});
        editLineButton.setOnMouseClicked(e -> {controllers.handleEditLineButton();});
        addStationsToLineButton.setOnMouseClicked(e -> {controllers.handleAddStationsToLineButton();});
        removeStationsFromLineButton.setOnMouseClicked(e -> {controllers.handleRemoveStationsFromLineButton();});
        listAllStationsButton.setOnMouseClicked(e -> {controllers.handleListAllStationsButton();});
        lineComboBox.setOnAction(e -> {
            try {
            controllers.handleLineComboBox((MetroLine)lineComboBox.getValue());
            } catch (NullPointerException ex) {
            }
        });
        lineThicknessSlider.setOnMouseDragged(e -> {
            controllers.handleSetLineThickness(lineThicknessSlider.getValue());
            e.consume();
        });
        lineThicknessSlider.setOnMouseReleased(e -> {
            controllers.handleSetLineThickness(lineThicknessSlider.getValue());
            e.consume();
        });
        
        
        lineColorPicker.setOnAction(e -> {
            Color c = lineColorPicker.getValue();
            controllers.handleSetLineColor(c);
            e.consume();});

        addStationButton.setOnMouseClicked(e -> {controllers.handleAddStationButton();});
        removeStationButton.setOnMouseClicked(e -> {controllers.handleRemoveStationButton();});
        snapToGridButton.setOnMouseClicked(e -> {controllers.handleSnapToGridButton();});
        stationColorPicker.setOnAction(e -> { 
            Color c = stationColorPicker.getValue();
            controllers.handleSetStationColor(c);
            e.consume();
        });
        fontColorPicker.setOnAction(e -> {
            Color c = fontColorPicker.getValue();
            controllers.handleFontColorButton(c);
            e.consume();
        });
                    
        
        stationsComboBox.setOnAction(e -> {
            controllers.handleStationComboBox((Station)stationsComboBox.getValue());
            e.consume();
        });
        
        moveLabelButton.setOnMouseClicked(e -> {
            controllers.handleMoveLabelButton();
            e.consume();
        });
        
        rotateLabelButton.setOnMouseClicked(e -> {
            controllers.handleRotateLabelButton();
            e.consume();
        });
        
        stationRadiusSlider.setOnMouseDragged(e -> {
            controllers.handleStationRadiusSlider(stationRadiusSlider.getValue());
            e.consume();
        } );
        stationRadiusSlider.setOnMouseReleased(e -> {
            controllers.handleStationRadiusSlider(stationRadiusSlider.getValue());
            e.consume();
        } );

        findRouteButton.setOnMouseClicked(e -> {controllers.handleFindRouteButton();});

        addImageButton.setOnMouseClicked(e -> {controllers.handleAddImageButton();});
        addTextButton.setOnAction(e -> {
            controllers.handleAddTextButton();
        });
        setImageBackgroundButton.setOnMouseClicked(e -> {controllers.handleSetImageBackgroundButton();});
        
        removeElementButton.setOnMouseClicked(e -> {controllers.handleRemoveElementButton();});
        
        setBackgroundColorPicker.setOnAction(e -> {
            Color c = setBackgroundColorPicker.getValue();
            controllers.handleSetBackgroundColorButton(c);
        });
        
        

        italButton.setOnMouseClicked(e -> {controllers.handleItalButton();});
        boldButton.setOnMouseClicked(e -> {controllers.handleBoldButton();});
        
        fontFamily.setOnAction(e -> {
            controllers.handleFontFamilyComboBox(fontFamily.getValue().toString());
            e.consume();
        });
       
        fontSize.setOnAction(e -> {
            controllers.handleFontSizeComboBox(fontSize.getValue().toString());
          //  controllers.handleStationComboBox((Station)stationsComboBox.getValue());
            e.consume();
        });
        

        showGridToggleButton.setOnMouseClicked(e -> {controllers.handleShowGridToggleButton();});
        
        zoomInButton.setOnMouseClicked(e -> controllers.handleZoomInButton());
        zoomOutButton.setOnMouseClicked(e -> controllers.handleZoomOutButton());
        
        enlargeMapButton.setOnMouseClicked(e -> controllers.handleEnlargeMapButton());
        reduceMapButton.setOnMouseClicked(e -> controllers.handleReduceMapButton());


         
     }
    
   
   
    
}
