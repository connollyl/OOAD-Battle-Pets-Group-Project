package GUI_Components;

import java.util.Optional;

import Pets.Skills;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class SkillPredictPrompt extends Alert {
	ButtonType rockThrow, scissorPoke, paperCut, shootTheMoon, reversalOfFortune,cancel;
	Optional<ButtonType> result; 
	int numCalled = 0;
	public SkillPredictPrompt() {
		
		super(AlertType.CONFIRMATION);
		this.setTitle("You Chose Shoot the Moon!");
		this.setHeaderText("Please Select A Move To Predict");
		rockThrow = new ButtonType("Rock Throw");
		scissorPoke = new ButtonType("Scissor Poke");
		paperCut = new ButtonType("Paper Cut");
		shootTheMoon = new ButtonType("Shoot The Moon");
		reversalOfFortune = new ButtonType("Reversal Of Fortune");
		cancel = new ButtonType("Cancel");
		this.getButtonTypes().setAll(rockThrow, scissorPoke, paperCut, shootTheMoon, reversalOfFortune,cancel);
		//this.initStyle(StageStyle.TRANSPARENT);
		
	}

	public Skills popUp(int numReset) {
		
		if(numReset == 1) {
			this.setContentText("Hey Listen!");
		}else if(numReset == 2){
			this.setContentText("Really, You Need To Pick One Of These Here Moves...");
		}else if(numReset == 3) {
			this.setContentText("Are You Not Entertained?!?!");
		}else if(numReset >= 4) {
			this.setContentText("One Cannot Simply Not Predict A Skill");
		}
		
		result = this.showAndWait();
		if (result.get() == rockThrow) {
			numCalled = 0;
			return Skills.ROCK_THROW;
		} else if (result.get() == scissorPoke) {
			numCalled = 0;
			return Skills.SCISSORS_POKE;
		} else if (result.get() == paperCut) {
			numCalled = 0;
			return Skills.PAPER_CUT;
		} else if (result.get() == shootTheMoon) {
			numCalled = 0;
			return Skills.SHOOT_THE_MOON;
		} else if (result.get() == reversalOfFortune) {
			numCalled = 0;
			return Skills.REVERSAL_OF_FORTUNE;
		} else {
			popUp(++numCalled);
		}
		return null;
	}

}
