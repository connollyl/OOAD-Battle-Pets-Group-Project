package interfaces;

import java.util.Observer;

import Game.Referee;
import Pets.*;

/**
 * This interface defines the behavior of playable objects.
 * Author: garzal
 */
public interface Playable extends Observer
{
	/**
	 * Returns a chosen skill
	 * @return skill
	 */
	public Skills chooseSkill();
	
	/**
	 * Reutrns Player Name
	 * @return
	 */
	public String getPlayerName();
	
	/**
	 * Sets Player type as either AI or human
	 * @param type
	 */
	public void setPlayerType(PlayerTypes type);
	
	/**
	 * Returns the playerType
	 * @return
	 */
	public PlayerTypes getPlayerType();
	
	/**
	 * Returns petName
	 * @return
	 */
	public String getPetName();
	
	/**
	 * Gets the pet type
	 * @return
	 */
	public PetTypes getPetType();
	
	/**
	 * gets the current hp
	 * @return
	 */
	public double getCurrentHp();
	
	/**
	 * updates the hp when damage is taken
	 * @param hp
	 */
	public void updateHp(double hp);
	
	/**
	 * resets hp to the max hp
	 */
	public void resetHp();
	
	/**
	 * sets the hp to selected value
	 * @param currentHp
	 */
	public void setCurrentHp(double currentHp);
	
	/**
	 * checks if a pet's hp > 0
	 * @return
	 */
	public boolean isAwake();
	
	/**
	 * Gets the predicted skill when STM is chosen
	 * @return
	 */
	public Skills getSkillPrediction();
	
	/**
	 * Gets the skills cooldown time
	 * @param skill
	 * @return
	 */
	public int getSkillRechargeTime(Skills skill);
	
	/**
	 * Gets the percent of hp remaining
	 * @return
	 */
	public double calculateHpPercent();
	
	/**
	 * Resets pets to full hp and resets wins.
	 */
	public void reset();
	
	/**
	 * Reduces the cooldown time by 1
	 */
	public void decrementRechargeTimes();
	
	/**
	 * Sets the cooldown for a skill
	 * @param skill
	 */
	public void setRechargeTime(Skills skill);
	
	/**
	 * Gets the amount of fights won for the playable
	 * @return
	 */
	public int getFightsWon();
	
	/**
	 * Checks the cooldown of a skill
	 * @param skill
	 * @return
	 */
	public boolean checkRecharge(Skills skill);
	
	/**
	 * gets max hp
	 * @return
	 */
	public double getMaxHp();
	
	/**
	 * Gets the damage difference between playables
	 * @return
	 */
	public double getDamageDifference();
	
	/**
	 * Increases fights won by 1
	 */
	public void incrementFightsWon();
	
	/**
	 * Sets the rand damage delt to a playable
	 * @param randomDamage
	 */
	public void setRandomDamageDealt(double randomDamage);
	
	/**
	 * Sets the rand damage taken to a playable
	 * @param randomDamage
	 */
	public void setRandomDamageTaken(double randomDamage);
	
	/**
	 * Gets the index of a playable in a list
	 * @return
	 */
	public int getPlace();
	
	/**
	 * Returns the chosen skill
	 * @return
	 */
	public Skills getSkill();
	
	/**
	 * Calculates the difference in damage between playables
	 */
	public void calculateDamageDifference();
	
	/**
	 *  Resets the cooldown to 0 for all skills
	 */
	public void resetRecharge();
	
	/**
	 * Sets the skill for a playable
	 * @param skills
	 */
	public void setSkill(Skills skills);
	
	/**
	 * Sets the guessed skill when using STM
	 * @param skills
	 */
	public void setGuessedSkill(Skills skills);
	
	/**
	 * Gets the rand dmg + cond dmg taken
	 * @return
	 */
	public double getFullDamageTaken();
}


