package GUI_Components;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import interfaces.Playable;

public class GameWonPrompt extends Alert {

	ButtonType yeet, nah;
	Optional<ButtonType> result;

	public GameWonPrompt() {
		super(AlertType.CONFIRMATION);
		this.setTitle("The Battle Has Come To A Conclusion!");

		this.setContentText("Would You Like To Play Again?");
		this.yeet = new ButtonType("Yeet!");
		this.nah = new ButtonType("Nah.");
		this.getButtonTypes().setAll(yeet, nah);

	}

	public boolean startAnotherGame(String winner) 
	{

		this.setHeaderText("The Winner Is: " + winner);

		if (this.showAndWait().get() == yeet) {
			return true;
		} else {

			return false;
		}
	}
}
