package Game;
import UI.RoundUI;
import interfaces.Playable;

import java.util.ArrayList;
import java.util.List;

import GUI.FXMLPresenter;
import Pets.Pet;
/**
 * This class stores info needed to conduct a round of combat. It will invoke
 * the ui to print out the number info and invoke the ref to calculate damage.
 * Author:  Aidan Gengler, Andrew Cummings, Logan Garza
 */
public class Round 
{
	private List <Playable> pets;
	private int roundNum;
	private RoundUI ui;
	private Referee ref;
	private FXMLPresenter presenter;
	
	/**
	 * Creates a round using a list of currently awake pets, the round number,
	 * and a referee
	 * @param currentPets
	 * @param round
	 * @param refer
	 */
	public Round(List<Playable> currentPets, int round, Referee refer) 
	{
		this.pets = currentPets;
		this.ui = new RoundUI();
		this.roundNum = round;
		this.ref = refer;
	}
	
	/**
	 * Constructor for round, for use with GUI
	 * @param currentPets
	 * @param round
	 * @param pres
	 */
	public Round(List<Playable> currentPets, int round, FXMLPresenter pres) 
	{
		this.pets = currentPets;
		this.roundNum = round;
		presenter = pres;
	}
	public Round(int round)
	{
		this.roundNum = round;
	}
	
	/**
	 * Lets pets pick there skill and tells the ref to calculate damage.
	 */
	public void startRound()
	{
		ui.showInitialRound(roundNum);
		for(int i = 0; i < pets.size(); i++)
		{
			if(pets.get(i) instanceof Pet) {
				ui.skillSelect(pets.get(i));
			}
			else
			{
				pets.get(i).chooseSkill();
			}
		}
		ref.singleDamagePhase();
	}
	
	/**
	 * Starts a round of combat in the GUI
	 */
	public void startGuiRound()
	{
		presenter.showNextAction();
	}
	
	public int getRoundNum()
	{
		return this.roundNum;
	}

}
