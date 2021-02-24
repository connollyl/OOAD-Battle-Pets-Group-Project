package GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	private static Stage stageGet;
	private Scene scene;

	/**
	 * Launches the GUI
	 */
	@Override
	public void start(Stage stage) {

		try {
			stageGet = stage;
			Parent root = FXMLLoader.load(getClass().getResource("BattlePets.fxml"));
			initHandlers(root, stage);
			

			
			this.scene = new Scene(root);
			//stage.setMinWidth(1010);
			//stage.setMinHeight(636);
			stage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			stage.setScene(scene);

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the handlers
	 * 
	 * @param root
	 * @param stage
	 */
	private void initHandlers(Parent root, Stage stage) {
		stage.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		
		    }

			
		});
		stage.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        System.out.println("Height: " + newSceneHeight);
		    }
		});
		
		
		
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});

	}

	/**
	 * Launches
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stageGet;
	}

	public Scene getScene() {
		return this.scene;
	}

}
