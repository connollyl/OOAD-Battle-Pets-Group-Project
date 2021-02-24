package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import GUI_Components.GameWonPrompt;
import GUI_Components.ImageHandler;
import GUI_Components.PetCreationListCell;
import GUI_Components.SkillPredictPrompt;
import GUI_Components.TextFieldMasker;
import Game.Battle;
import Game.Fight;
import Game.Referee;
import Pets.AIPet;
import Pets.Pet;
import Pets.PetTypes;
import Pets.PlayerTypes;
import Pets.Skills;
import interfaces.Playable;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FXMLPresenter implements Initializable {
	private final int MAX_PETS_ALLOWED = 1100;
	private PetCreationListCell defaultCell;
	private String powerImage = "power1.gif";
	private String speedImage = "speed1.gif";
	private String intelImage = "intelligence1.gif";
	private final String regex_double = "\\d*\\.?+\\d*?";
	private final String regex_int_long = "\\d*";

	private final static int PET_CELL_SIZE = 50;
	private int numPets, fightNum;
	private int skillChoiceIndex = -1;
	private double maxHp;

	private KeyCode keyPressed;
	private EventTarget buttonId;
	private EventTarget target;

	private ArrayList<PetCreationListCell> cellList = new ArrayList<PetCreationListCell>();
	private ArrayList<String> currrentPetCellData;

	private List<String> petNames = new ArrayList<String>();
	private List<String> masterNames = new ArrayList<String>();
	private List<PetTypes> petTypes = new ArrayList<PetTypes>();
	private List<PlayerTypes> playerTypes = new ArrayList<PlayerTypes>();
	private List<Playable> pets = new ArrayList<Playable>();

	private Referee ref;
	private ImageHandler imageHandler = new ImageHandler("src\\Resources\\");
	private Battle battle;
	private Fight fight;

	private GameWonPrompt gameWon = new GameWonPrompt();
	private SkillPredictPrompt shootMoonPrompt = new SkillPredictPrompt();

	static Scene scene;
	@FXML
	private AnchorPane anchorPane_BattlePets;

	@FXML
	private AnchorPane panel_IntroScreen;

	@FXML
	private Button btn_ExitGame;

	@FXML
	private Button btn_StartGame;

	@FXML
	private AnchorPane panel_fightScreen;

	@FXML
	private Label label_enemyPetHp;

	@FXML
	private Pane panel_skillChoice;

	@FXML
	private Button btn_rockThrow;

	@FXML
	private Button btn_scissorPoke;

	@FXML
	private Button btn_shootTheMoon;

	@FXML
	private Button btn_RoF;

	@FXML
	private Button btn_paperCut;

	@FXML
	private Button btn_ExitMidGame;

	@FXML
	private Button btn_NewGame;

	@FXML
	private TextArea textArea_output;

	@FXML
	private ImageView image_currentPet;

	@FXML
	private ImageView image_enemyPet;

	@FXML
	private Label label_currentPlayerName;

	@FXML
	private Label label_enemyPetName;

	@FXML
	private Label label_currentPlayerHp;

	@FXML
	private Label label_currentPetName;

	@FXML
	private Label label_enemyPlayerName;

	@FXML
	private ProgressBar pBar_playerHealth;

	@FXML
	private ProgressBar pBar_enemyHealth;

	@FXML
	private AnchorPane panel_SetupScreen;

	@FXML
	private ImageView btn_BackToTitle;

	@FXML
	private TextField txtF_NumPets;

	@FXML
	private TextField txtF_NumFights;

	@FXML
	private TextField txtF_MaxHp;

	@FXML
	private Button btn_StartBattle;

	@FXML
	private ScrollPane scrl_petSetup;

	@FXML
	private VBox lstV_petSetup;

	@FXML
	private TextField txtF_seed;

	@FXML
	private Button btn_MakePets;

	ArrayList<Node> componentList = new ArrayList<Node>();

	/**
	 * Handler for events in the text field
	 * 
	 * @param event
	 */
	@FXML
	private void handleTextFieldAction(KeyEvent event) {
		keyPressed = event.getCode();
		target = event.getTarget();
		System.out.println("GLOBAL TEXTBOX KEY PRESSED: " + keyPressed);
		if (event.getCode() == KeyCode.ENTER) {
			if (target.equals(txtF_NumPets)) {
				if (!txtF_NumPets.getText().isEmpty()) {
					addPlayersToList();
				}

			} else if (target == txtF_NumFights) {
				int numPets = Integer.parseInt(txtF_NumFights.getText());
			} else if (target == txtF_MaxHp) {
				maxHp = Double.parseDouble(txtF_MaxHp.getText());
			}
		}
	}

	/**
	 * adds players to the cell list to create pets
	 */
	private void addPlayersToList() {
		numPets = Integer.parseInt(txtF_NumPets.getText());
		if (numPets > MAX_PETS_ALLOWED) {
			error("Please Don't Enter That Many Pets, The Max Value Is 1100 Pets.");
		} else {
			lstV_petSetup.getChildren().clear();
			cellList.clear();

			PetCreationListCell petCell;
			for (int i = 0; i < numPets; i++) {
				petCell = new PetCreationListCell(PET_CELL_SIZE, lstV_petSetup.getMinWidth());
				lstV_petSetup.getChildren().add(petCell);
				cellList.add(petCell);

			}
		}

	}

	/*
	 * Used as a debugging tool for obtaining the information from the view
	 */
	private void getAllPetData() {
		for (int i = 0; i < numPets; i++) {
			currrentPetCellData = cellList.get(i).getData();

			System.out.println(currrentPetCellData.get(0) + " " + currrentPetCellData.get(1) + " "
					+ currrentPetCellData.get(2) + " " + currrentPetCellData.get(3));
			System.out.println();
		}
	}

	/**
	 * Handler for events in text fields where only integers are entered.
	 * 
	 * @param event
	 */
	@FXML
	private void handleIntegerTextInput(KeyEvent event) {
		keyPressed = event.getCode();
		if (event.getCode() == KeyCode.ENTER) {
			((TextField) event.getTarget()).setText("");
		}
	}

	/**
	 * Handles events to bring the intro screen back to the front
	 * 
	 * @param event
	 */
	@FXML
	private void handleImageButtonAction(MouseEvent event) {
		target = event.getTarget();

		if (target == btn_BackToTitle) {
			panel_SetupScreen.toBack();
			panel_IntroScreen.toFront();
		}

	}

	/**
	 * Handles most button events, except for skill selection
	 * 
	 * @param event
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) {
		buttonId = event.getTarget();

		if (buttonId == btn_ExitGame) {
			System.exit(0);
		} else if (buttonId == btn_ExitMidGame) {
			System.exit(0);
		} else if (buttonId == btn_NewGame) {
			resetEverything();
			panel_fightScreen.toBack();
			panel_IntroScreen.toFront();
		} else if (buttonId == btn_StartGame) {
			panel_IntroScreen.toBack();
			panel_SetupScreen.toFront();
		} else if (buttonId.equals(btn_StartBattle)) {
			textArea_output.clear();
			startBattle();
		} else if (buttonId.equals(btn_MakePets)) {
			if (!txtF_NumPets.getText().isEmpty()) {
				addPlayersToList();
			}
		}

	}

	/**
	 * Displays an alert
	 * 
	 * @param title
	 * @param header
	 * @param Content
	 */
	private void alert(String title, String header, String Content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(Content);
		alert.showAndWait();
	}

	/**
	 * Displays error messages
	 * 
	 * @param message
	 */
	private void error(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	/**
	 * Will start a battle if all prerequisite fields are filled
	 */
	private void startBattle() {
		if (lstV_petSetup.getChildren().size() < 2) {
			error("Please Make Sure Pets Are Made First");
		} else if (txtF_NumFights.getText().isEmpty()) {
			error("Please Enter Number Of Pets");
		} else if (txtF_seed.getText().isEmpty()) {
			error("Please Enter Seed For Fights");
		} else if (txtF_MaxHp.getText().isEmpty()) {
			error("Please Enter Max Hp For Fight");
		} else {
			// getAllPetData();
			setPetCreationData();

			panel_fightScreen.toFront();
			panel_SetupScreen.toBack();

			battle = new Battle(pets, fightNum, this, ref);
			battle.startGuiBattle();
		}

	}

	/**
	 * Handles button events when making skill choices
	 * 
	 * @param event
	 */
	@FXML
	private void handleMove(ActionEvent event) {
		buttonId = event.getTarget();
		if (buttonId == btn_rockThrow) {
			pets.get(skillChoiceIndex).guiChooseSkill(Skills.ROCK_THROW);
		} else if (buttonId == btn_scissorPoke) {
			pets.get(skillChoiceIndex).guiChooseSkill(Skills.SCISSORS_POKE);
		} else if (buttonId == btn_paperCut) {
			pets.get(skillChoiceIndex).guiChooseSkill(Skills.PAPER_CUT);
		} else if (buttonId == btn_shootTheMoon) {
			pets.get(skillChoiceIndex).guiChooseSkill(Skills.SHOOT_THE_MOON);
			pets.get(skillChoiceIndex).setGuessedSkill(shootMoonPrompt.popUp(0));
		} else if (buttonId == btn_RoF) {
			pets.get(skillChoiceIndex).guiChooseSkill(Skills.REVERSAL_OF_FORTUNE);
		}

		showNextAction();
	}

	/**
	 * Initializes masks for text fields and sets scroll policies
	 */
	@Override
	public void initialize(URL url, ResourceBundle arg1) {

		TextFieldMasker.addMask(txtF_MaxHp, regex_int_long);
		TextFieldMasker.addMask(txtF_NumFights, regex_int_long);
		TextFieldMasker.addMask(txtF_NumPets, regex_int_long);
		TextFieldMasker.addMask(txtF_seed, regex_int_long);

		scrl_petSetup.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrl_petSetup.setVbarPolicy(ScrollBarPolicy.NEVER);

		defaultCell = new PetCreationListCell(PET_CELL_SIZE, lstV_petSetup.getMinWidth());
		lstV_petSetup.getChildren().add(defaultCell);
		cellList.add(defaultCell);
		defaultCell = new PetCreationListCell(PET_CELL_SIZE, lstV_petSetup.getMinWidth());
		lstV_petSetup.getChildren().add(defaultCell);
		cellList.add(defaultCell);
		numPets = 2;
	}

	/*
	 * Sets the data for the creation of pets
	 */
	// TODO Make the indexes a CONST so we don't have magic numbers
	public void setPetCreationData() {
		for (int i = 0; i < numPets; i++) {
			ArrayList<String> currentPetStrings = cellList.get(i).getData();
			petNames.add(currentPetStrings.get(0));
			masterNames.add(currentPetStrings.get(1));

			if (currentPetStrings.get(2).equals("Power")) {
				petTypes.add(PetTypes.POWER);
			} else if (currentPetStrings.get(2).equals("Intelligence")) {
				petTypes.add(PetTypes.INTELLIGENCE);
			} else if (currentPetStrings.get(2).equals("Speed")) {
				petTypes.add(PetTypes.SPEED);
			}
			if (currentPetStrings.get(3).equals("Human")) {
				playerTypes.add(PlayerTypes.TOPOFTHEFOODCHAIN);
			} else {
				playerTypes.add(PlayerTypes.IAMNOTAROBOT);
			}
		}

		fightNum = Integer.parseInt(txtF_NumFights.getText());
		maxHp = Integer.parseInt(txtF_MaxHp.getText());
		long seed = Long.parseLong(txtF_seed.getText());
		createPets();
		ref = new Referee(pets, seed, this);
	}

	private void clearPetInfo() {
		pets.clear();
		petNames.clear();
		petTypes.clear();
		masterNames.clear();
		playerTypes.clear();
		cellList.clear();
	}

	/**
	 * Creates pets and returns a list of playables
	 * 
	 * @return
	 */
	private void createPets() {
		for (int i = 0; i < numPets; i++) {
			if (playerTypes.get(i) == PlayerTypes.TOPOFTHEFOODCHAIN) {
				pets.add(new Pet(maxHp, petTypes.get(i), petNames.get(i), masterNames.get(i), i));
			} else {
				pets.add(new AIPet(maxHp, petTypes.get(i), petNames.get(i), masterNames.get(i), i));
			}
		}
	}

	/**
	 * Goes through the turns of each playable
	 */
	// TODO This function breaks when there is 3 or more pets involved. I also think
	// we should break
	// this function up by having one function for displaying the pet info on
	// screen, and one for actually
	// choosing skills.
	public void showNextAction() {
		skillChoiceIndex++;

		if (skillChoiceIndex < (pets.size())) {
			if (pets.get(skillChoiceIndex).getPlayerType() == PlayerTypes.TOPOFTHEFOODCHAIN) {
				nextPlayer(skillChoiceIndex);
			} else {
				pets.get(skillChoiceIndex).chooseSkill();
				nextPlayer(skillChoiceIndex);
				showNextAction();
			}
		} else {
			skillChoiceIndex = -1;
			ref.guiSingleDamagePhase();
			fight.nextRound();
		}
	}

	/**
	 * Sets up the GUI for the next human player to pick a skill
	 * 
	 * @param current
	 */
	private void nextPlayer(int current) {
		label_currentPlayerName.setText(pets.get(current).getPlayerName());
		label_currentPetName.setText(pets.get(current).getPetName());
		pBar_playerHealth.setProgress(pets.get(current).getCurrentHp() / maxHp);

		resetAllowedSkills();
		setAllowedSkills(current);

		if (pets.get(current).getPetType() == PetTypes.POWER) {
			image_currentPet.setImage(imageHandler.getImage("power1.gif"));

		} else if (pets.get(current).getPetType() == PetTypes.INTELLIGENCE) {
			image_currentPet.setImage(imageHandler.getImage("intel2.gif"));
		} else {
			image_currentPet.setImage(imageHandler.getImage("speed1.gif"));
		}

		int enemy;

		if ((current + 1) == pets.size()) {
			enemy = 0;
		} else {
			enemy = current + 1;
		}

		label_enemyPlayerName.setText(pets.get(enemy).getPlayerName());
		label_enemyPetName.setText(pets.get(enemy).getPetName());
		pBar_enemyHealth.setProgress(pets.get(enemy).getCurrentHp() / maxHp);
		if (pets.get(enemy).getPetType() == PetTypes.POWER) {
			image_enemyPet.setImage(imageHandler.getImage("power1.gif"));
		} else if (pets.get(enemy).getPetType() == PetTypes.INTELLIGENCE) {
			image_enemyPet.setImage(imageHandler.getImage("intel2.gif"));
		} else {
			image_enemyPet.setImage(imageHandler.getImage("speed1.gif"));
		}
	}

	/**
	 * Reenables disabled skill buttons
	 */
	private void resetAllowedSkills() {
		btn_rockThrow.setDisable(false);
		btn_paperCut.setDisable(false);
		btn_scissorPoke.setDisable(false);
		btn_shootTheMoon.setDisable(false);
		btn_RoF.setDisable(false);
	}

	/**
	 * Disables buttons if they recharging
	 * 
	 * @param i
	 */
	private void setAllowedSkills(int skillChoiceIndex) {
		if (pets.get(skillChoiceIndex).checkRecharge(Skills.ROCK_THROW)) {
			btn_rockThrow.setDisable(true);
		}

		if (pets.get(skillChoiceIndex).checkRecharge(Skills.PAPER_CUT)) {
			btn_paperCut.setDisable(true);
		}

		if (pets.get(skillChoiceIndex).checkRecharge(Skills.SCISSORS_POKE)) {
			btn_scissorPoke.setDisable(true);
		}

		if (pets.get(skillChoiceIndex).checkRecharge(Skills.SHOOT_THE_MOON)) {
			btn_shootTheMoon.setDisable(true);
		}

		if (pets.get(skillChoiceIndex).checkRecharge(Skills.REVERSAL_OF_FORTUNE)) {
			btn_RoF.setDisable(true);
		}
	}

	/**
	 * If the battle is not tied, a winner is chosen and displayed in the textbox.
	 * Also displays the standing of all other pets
	 * 
	 * @param winner
	 */
	public void displayTieBattleResultsGUI(List<Playable> sortedPets) {
		textArea_output.clear();
		textArea_output.appendText("The battle has ended in a tie... How anticlimatic...\n");
		for (int i = 0; i < sortedPets.size(); i++) {
			textArea_output.appendText(
					sortedPets.get(i).getPetName() + " has " + sortedPets.get(i).getFightsWon() + " total wins!\n");
		}
		promptAnotherBattleGUI();

	}

	/**
	 * If the battle is tied, it will display the standings of all pets and note a
	 * tie.
	 * 
	 * @param sortedPets
	 */
	public void displayWinBattleResultsGUI(List<Playable> petList, Playable winner) {
		textArea_output.clear();
		for (int i = 0; i < petList.size(); i++) {
			textArea_output.appendText(
					petList.get(i).getPetName() + " has " + petList.get(i).getFightsWon() + " total wins!\n");
		}
		textArea_output.appendText("\n\nThe battle has ended! " + winner.getPetName()
				+ " has won all the fame and glory in " + "the world!!!");
		promptAnotherBattleGUI();
	}

	/**
	 * Displays the round info for the GUI
	 * 
	 * @param roundNum
	 */
	public void displayRoundInfoGUI(int roundNum) {
		if (roundNum > 1) {
			displayDamageAndHPGUI(pets);
		}
		textArea_output.appendText("Round " + roundNum + ". FIGHT!\n");
	}

	/**
	 * Displays the winner of the fight
	 * 
	 * @param winner
	 */
	public void displayWinFightResultsGUI(Playable winner) {
		textArea_output.clear();
		textArea_output.appendText(winner.getPetName() + " has won the fight!\n");
		textArea_output.appendText(winner.getPetName() + " now has " + winner.getFightsWon() + " wins!\n");
	}

	/**
	 * Displays the fight number.
	 * 
	 * @param fightNum
	 */
	public void displayFightInfoGUI(int fightNum) {
		textArea_output.appendText("Fight " + fightNum + " starts now! BEGIN!\n");
	}

	/**
	 * Asks the user if they want to play again in the texArea.
	 */
	public void promptAnotherBattleGUI() {
		textArea_output.appendText("Would you like to play again?");
	}

	/**
	 * Displays a tie if the fight ends in one.
	 * 
	 * @param sortedPets
	 */
	public void displayTieFightResultsGUI(List<Playable> sortedPets) {
		textArea_output.clear();
		textArea_output.appendText("The fight has ended in a tie! How rare (O.o)\n");
		for (int i = 0; i < sortedPets.size(); i++) {
			sortedPets.get(i).incrementFightsWon();
			textArea_output.appendText(
					sortedPets.get(i).getPetName() + " now has " + sortedPets.get(i).getFightsWon() + " wins!\n");
		}
	}

	/**
	 * Displays damage and HP for the GUI version.
	 *
	 * @param pets
	 */
	public void displayDamageAndHPGUI(List<Playable> pets) {
		textArea_output.appendText("\n\n");
		if (pets.get(0).getSkill() != null) {
			for (int i = 0; i < pets.size(); i++) {
				String skill = pets.get(i).getSkill().toString();
				textArea_output.appendText(pets.get(i).getPetName() + " took " + pets.get(i).getFullDamageTaken()
						+ "damage." + "\n" + pets.get(i).getPetName() + " has " + pets.get(i).getCurrentHp()
						+ " health left and used " + skill + "\n");
			}
		}

	}

	public void setFight(Fight f) {
		fight = f;
	}

	/**
	 * Ends the battle and clears out all relevant information.
	 */
	public void endBattle() {
		this.whoWon();
		ref.resetPets();
	}

	/**
	 * Finds the winner of the battle.
	 */
	public void whoWon() {
		if (gameWon.startAnotherGame(battle.findWinner().getPetName())) {
			panel_IntroScreen.toFront();
			resetEverything();
		} else {
			System.exit(0);
		}
	}

	public void placeLabel(Label label, ImageView image) {
		image.getX();
		label.getBoundsInParent().getMinY();
		label.getText().length();

	}

	private void resetEverything() {
		skillChoiceIndex = -1;
		petNames = new ArrayList<String>();
		masterNames = new ArrayList<String>();
		petTypes = new ArrayList<PetTypes>();
		playerTypes = new ArrayList<PlayerTypes>();
		pets = new ArrayList<Playable>();
	}
}
