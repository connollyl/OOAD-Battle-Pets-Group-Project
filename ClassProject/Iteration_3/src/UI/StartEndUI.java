package UI;

import java.util.*;
import interfaces.Playable;

/**
 * Handles display and input operations for the start and end
 * scenarios of the game
 *
 * @Author Eric Weber
 * @Version 2.1
 */
public class StartEndUI extends genericUI
{
    
    private Display disp;

    /**
     * Constructor for the startEndUI class
     */
    public StartEndUI()
    {
        PROPERTY_FILE_NAME = "StartEndUI";
        constStrings = getConstStrings(PROPERTY_FILE_NAME);
        disp = Display.getDisplay();
    }

    /**
     * Clears screen and displays start of current fight information.
     *
     * @param currentFightNum is current fight.
     */
    public void showInitFight(int currentFightNum)
    {
        disp.blankln(2);
        disp.println(prop("startFight") + (currentFightNum) + "!");
    }

    /**
     * Displays the results for given fight for given pet.
     *
     * @param winner is the winning pet
     */
    public void printWinFightResults(Playable winner)
    {

        disp.println(winner.getPetName() + prop("winFight"));
        disp.println(winner.getPetName() + prop("totalWon1") + winner.getFightsWon() + prop("totalWon2"));
    }

    /**
     * Prints the results if fight is tied, and displays the list of tied pets.
     *
     * @param sortedPets is list of tied pets for given pets.
     */
    public void printTieFightResults(List<Playable> sortedPets)
    {
        disp.println(prop("fightTie"));
        for(int i = 0; i < sortedPets.size(); i++)
        {
            sortedPets.get(i).incrementFightsWon();
            disp.println(sortedPets.get(i).getPetName() + prop("totalWon1") + sortedPets.get(i).getFightsWon() + prop("totalWon2"));
        }
    }

    /**
     * Prints results of tied battle.
     *
     * @param sortedPets is tied pets for battle.
     */
    public void printTieBattleResults(List<Playable> sortedPets)
    {
        disp.println(prop("battleTie"));
        for(int i = 0; i < sortedPets.size(); i++)
        {
            disp.println(sortedPets.get(i).getPetName() + " has " + sortedPets.get(i).getFightsWon() + prop("totalWins"));
        }
    }

    /**
     * Displays damage numbers for each round.
     *
     * @param petName         is pet that took damage.
     * @param damageTaken     is number representation of damage taken.
     * @param healthRemaining is number representation of health remaing for given pet.
     */
    public void displayDamageTaken(String petName, double damageTaken, double healthRemaining)
    {
        disp.println(petName + " recieved " + damageTaken + " damage.");
        disp.println(petName + " has " + healthRemaining + " remaining.");
        disp.blankln(2);
    }

    /**
     * Display text for when pet goes to sleep.
     *
     * @param petName is name of pet that went to sleep.
     */
    public void petWentToSleep(String petName)
    {
        disp.println(prop("sleepyPet1") + petName + prop("sleepyPet2"));
        disp.blankln();
        disp.println(petName + prop("sleepyPet3"));
        disp.blankln();
    }

    /**
     * Displays the result for given battle.
     *
     * @param petList is list of pets.
     * @param p       is winner.
     */
    public void printWinBattleResults(List<Playable> petList, Playable p)
    {
        for(int i = 0; i < petList.size(); i++)
        {
            disp.println(petList.get(i).getPetName() + " has " + petList.get(i).getFightsWon() + prop("totalWins"));
        }
        disp.println(prop("endBattle") + " " + p.getPetName() + prop("fameAndGlory"));

    }

    /**
     * Prompt user for the start of another new battle.
     *
     * @return if the player wants to initiate another battle.
     */
    public boolean promptAnotherBattle()
    {
        disp.println(prop("playAgain"));

        boolean playAgain = disp.readBoolean();

        if(!playAgain)
        {
            disp.clearScreen();
            disp.println(prop("thx4plyn"));
        }
        disp.clearScreen();
        return playAgain;
    }

    //---------------------------------------------------------------------
    /* public static void main(String[] args)
     * {
     *
     * } */
}
