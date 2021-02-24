package Game;

import java.io.IOException;
import UI.*;
import Game.Game;
import interfaces.Startable;
public class MainGame 
{
	private static StartEndUI ui = new StartEndUI();
	private static Startable game;
	private static int choice;
	/**
	 * Main method for the game.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		choice = ui.promptGameType();
		
		if(choice == Game.getGameType())
		{
			game = new Game();
		}
		else if(choice == GUIGame.getGameType())
		{
			game = new GUIGame();
		}
		else
		{
			game = null;
			//TODO: Invalid selection notification
		}
			
		game.start(); //Starts the game, regardless of type
	}
}
