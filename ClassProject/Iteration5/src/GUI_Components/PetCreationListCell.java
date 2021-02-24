package GUI_Components;

import java.util.ArrayList;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * This class handles all pet cells within the pet creation pane.
 * 
 * @author cummingsa
 *
 */
public class PetCreationListCell extends GridPane {

	private ToggleGroup playerTypeGroup, petTypeGroup;
	private TextField petField, playerField;
	private RadioButton human, ai, power, speed, intelligence;

	private String DEFAULT_PET_PROPMT = "Enter Pet Name Here";
	private String DEFAULT_PLAYER_PROPMT = "Enter Player Name Here";
	ArrayList<String> data;
	RNGNames randNameGenerator = new RNGNames();

	/**
	 * Constructor for pet cells
	 * 
	 * @param height
	 * @param width
	 */
	public PetCreationListCell(int height, double width) {
		super();
		this.setMinHeight(height);
		this.setMinWidth(width);
		this.setMaxWidth(width);

		initComponents();
		assignToggleGroups();
		addComponents();
		stylize();
	}

	/**
	 * Initializes all components
	 */
	private void initComponents() {
		this.petField = new TextField();
		this.playerField = new TextField();
		this.playerTypeGroup = new ToggleGroup();
		this.petTypeGroup = new ToggleGroup();
		this.human = new RadioButton("Human");
		this.ai = new RadioButton("AI");
		this.power = new RadioButton("Power");
		this.intelligence = new RadioButton("Intelligence");
		this.speed = new RadioButton("Speed");
		this.petField.setPromptText(DEFAULT_PET_PROPMT);
		this.playerField.setPromptText(DEFAULT_PLAYER_PROPMT);
		TextFieldMasker.addMask(petField, "\\D*");
		TextFieldMasker.addMask(playerField, "\\D*");
		this.setHgap(5);
		this.setVgap(5);
	}

	/**
	 * Retrieves all data from cell.
	 * 
	 * @return
	 */
	public ArrayList<String> getData() {
		data = new ArrayList<String>();
		if (petField.getText().isEmpty()) {
			data.add(randNameGenerator.getRandomName());
		} else {
			data.add(petField.getText());
		}

		if (playerField.getText().isEmpty()) {
			data.add(randNameGenerator.getRandomName());
		} else {
			data.add(playerField.getText());
		}

		data.add(((RadioButton) (petTypeGroup.getSelectedToggle())).getText());
		data.add(((RadioButton) (playerTypeGroup.getSelectedToggle())).getText());
		return data;
	}

	/**
	 * Assigns all toggle groups.
	 */
	private void assignToggleGroups() {
		this.human.setToggleGroup(playerTypeGroup);
		this.ai.setToggleGroup(playerTypeGroup);
		this.power.setToggleGroup(petTypeGroup);
		this.intelligence.setToggleGroup(petTypeGroup);
		this.speed.setToggleGroup(petTypeGroup);

		ai.setSelected(true);
		intelligence.setSelected(true);

	}

	/**
	 * Places component in grid pane.
	 */
	private void addComponents() {
		this.add(petField, 0, 0);
		this.add(human, 1, 0);
		this.add(ai, 2, 0);
		this.add(playerField, 0, 1);
		this.add(power, 1, 1);
		this.add(intelligence, 2, 1);
		this.add(speed, 3, 1);
	}

	/**
	 * Adds Styling to each object in cell.
	 */
	private void stylize() {

		this.petField.setStyle("-fx-control-inner-background: #939393");
		this.playerField.setStyle("-fx-control-inner-background: #939393");

		this.human.setStyle("-fx-text-fill: white");
		this.power.setStyle("-fx-text-fill: white");
		this.intelligence.setStyle("-fx-text-fill: white");
		this.speed.setStyle("-fx-text-fill: white");
		this.ai.setStyle("-fx-text-fill: white");

		this.setVisible(true);
	}

}
