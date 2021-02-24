package Game;

import java.util.ArrayList;
import java.util.List;

import GUI.FXMLPresenter;
import GUI.Main;

import UI.StartEndUI;
import interfaces.Playable;
import interfaces.Startable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

/**
 * Runs the GUI version of the game. Has most of the same functionality as Game,
 * but uses GUI controlling classes.
 * @author garzal
 *
 */
public class GUIGame implements Startable
{
	private static final startType gameType = startType.GUI;
	private boolean gameRunning = false;
	MainGame mGame;
	
	/**
	 * Starts a GUI Game
	 */
	public void start()
	{
		if(!checkGameRunning())
		{
			newGame();
		}
	}
	
	/**
	 * Returns the game type
	 * @return
	 */
	public static int getGameType()
	{
		return gameType.getType();
	}

	/**
	 * Returns true if the game is running
	 * @return
	 */
	public boolean checkGameRunning() 
	{
		return this.gameRunning;
	}

	/**
	 * Creates a new game and sets gameRunning to true
	 */
	public void newGame() 
	{
		gameRunning = true;
		Main.main(null);
	}
}
