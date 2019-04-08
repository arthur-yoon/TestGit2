package map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import service.IRideService;
import vo.RideVO;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.Map.Entry;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class MapController {

	@FXML
	private VBox root_vbox;
	@FXML
	private ListView<String> map_listview;
	@FXML
	private ScrollPane map_scrollpane;
	@FXML
	private Slider zoom_slider;
	@FXML
	private MenuButton map_pin;
	@FXML
	private MenuItem pin_info;

	private final HashMap<String, ArrayList<Comparable<?>>> hm = new HashMap<>();
	Group zoomGroup;
	
	private IRideService service;
	private List<RideVO> rideList;
    @FXML
    void initialize() throws RemoteException {
        assert map_listview != null : "fx:id=\"map_listview\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert root_vbox != null : "fx:id=\"root_vbox\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert map_scrollpane != null : "fx:id=\"map_scrollpane\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert map_pin != null : "fx:id=\"map_pin\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert pin_info != null : "fx:id=\"pin_info\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert zoom_slider != null : "fx:id=\"zoom_slider\" was not injected: check your FXML file 'airportapp.fxml'.";

        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1088);
            service = (IRideService) reg.lookup("ride");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("실패");
            e.printStackTrace();
        }
        
        String[] pos;
        rideList = service.getAllRide();
        for (int i = 0; i < rideList.size(); i++) {
        	pos = rideList.get(i).getRide_pos().split(",");
        	hm.put(rideList.get(i).getRide_name(), new ArrayList<>(Arrays.asList(Double.parseDouble(pos[0]),Double.parseDouble(pos[1]), rideList.get(i).getRide_content())));
		}
        
        ObservableList<String> names = FXCollections.observableArrayList();
        Set<Entry<String, ArrayList<Comparable<?>>>> set = hm.entrySet();
        Iterator<Entry<String, ArrayList<Comparable<?>>>> i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, ArrayList<Comparable<?>>> me = i.next();
            names.add((String)me.getKey());
        }
        Collections.sort(names);
        
        map_listview.setItems(names);
        map_pin.setVisible(false);

        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));

        // Wrap scroll content in a Group so ScrollPane re-computes scroll bars
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);

        // Add large UI styling and make full screen if we are on device
        if (Platform.isSupported(ConditionalFeature.INPUT_TOUCH)) {
            System.out.println("airportapp.Controller.initialize, device detected");
            root_vbox.getStyleClass().add("touch-sizes");
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            root_vbox.setPrefSize(bounds.getWidth(), bounds.getHeight());
        }
    }

	@FXML
	void listClicked(MouseEvent event) {
		String item = map_listview.getSelectionModel().getSelectedItem();
		List<Comparable<?>> list = hm.get(item);
		String[] a = list.toString().split(",");
		System.out.println(Arrays.toString(a));
		// animation scroll to new position
		double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
		double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
		double scrollH = (Double) list.get(0) / mapWidth;
		double scrollV = (Double) list.get(1) / mapHeight;
		final Timeline timeline = new Timeline();
		final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
		final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
		final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
		timeline.getKeyFrames().add(kf);
		timeline.play();

		// move the pin and set it's info
		double pinW = map_pin.getBoundsInLocal().getWidth();
		double pinH = map_pin.getBoundsInLocal().getHeight();
		map_pin.setLayoutX((Double) list.get(0) - (pinW / 2));
		map_pin.setLayoutY((Double) list.get(1) - (pinH));
		pin_info.setText((String) list.get(2));
		map_pin.setVisible(true);
	}

	@FXML
	void zoomIn(ActionEvent event) {
//    System.out.println("airportapp.Controller.zoomIn");
		double sliderVal = zoom_slider.getValue();
		zoom_slider.setValue(sliderVal += 0.1);
	}

	@FXML
	void zoomOut(ActionEvent event) {
//    System.out.println("airportapp.Controller.zoomOut");
		double sliderVal = zoom_slider.getValue();
		zoom_slider.setValue(sliderVal + -0.1);
	}

	private void zoom(double scaleValue) {
//    System.out.println("airportapp.Controller.zoom, scaleValue: " + scaleValue);
		double scrollH = map_scrollpane.getHvalue();
		double scrollV = map_scrollpane.getVvalue();
		zoomGroup.setScaleX(scaleValue);
		zoomGroup.setScaleY(scaleValue);
		map_scrollpane.setHvalue(scrollH);
		map_scrollpane.setVvalue(scrollV);
	}

}