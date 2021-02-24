package Game;
import UI.RoundUI;
import java.util.ArrayList;
import java.util.List;
import Pets.Pet;
/**
 * This class stores info needed to conduct a round of combat. It will invoke
 * the ui to print out the number info and invoke the ref to calculate damage.
 * Author:  Aidan Gengler, Andrew Cummings, Logan Garza
 */
public class Round {
	private List <Pet> pets;
	private int roundNum;
	private RoundUI ui;
	private Referee ref;
	/**
	 * Creates a round using a list of currently awake pets, the round number,
	 * and a referee
	 * @param currPetList
	 * @param round
	 * @param refer
	 */
	public Round(List<Pet>currPetList, int round, Referee refer) 
	{
		this.pets = currPetList;
		this.ui = new RoundUI();
		this.roundNum = round;
		this.ref = refer;
	}
	
	/**
	 * Lets pets pick there skill and tells the ref to calculate damage.
	 */
	public void startRound()
	{
		ui.showInitialRound(roundNum);
		for(int i = 0; i < pets.size(); i++)
		{
			ui.skillSelect(pets.get(i));
		}
		ref.singleDamagePhase();
	}
}
