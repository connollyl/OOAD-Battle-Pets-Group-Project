package UI;

import interfaces.Playable;
import java.util.*;
import Pets.Skills;
import Pets.PetTypes;
import UI.Display.INCLUSIVITY;

/**
 * This class deals with the user interface portion of the
 * Rounds.
 * Author: Logan Garza & Eric Weber
 */
public class RoundUI extends genericUI
{

    private Display disp;

    /**
     * Constructor for the ui
     */
    public RoundUI()
    {
        PROPERTY_FILE_NAME = "RoundUI";
        constStrings = getConstStrings(PROPERTY_FILE_NAME);
        disp = Display.getDisplay();
    }

    /**
     * Displays the round and number of it
     *
     * @param roundNumber
     */
    public void showInitialRound(int roundNumber)
    {
        disp.println(prop("roundNumber") + roundNumber);

    }

    /**
     * Prompts for and obtains the skills for the pets.
     *
     * @param currentPet
     */
    public void skillSelect(Playable currentPet)
    {
        displayPetInfo(currentPet.getPetName(), currentPet.getPetType());
        Skills[] skillList = Skills.getNumSkills();
        disp.println(prop("chooseSkill"));
        int chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
        if(currentPet.checkRecharge(skillList[chosenSkill - 1]))
        {
            while(currentPet.checkRecharge(skillList[chosenSkill - 1]))
            {
                cooldownCheck(skillList, currentPet, chosenSkill);
            }
        }
        currentPet.setSkill(skillList[chosenSkill - 1]);
        disp.println(prop("skillConfirmation") + skillList[chosenSkill - 1].toString());
        currentPet.setRechargeTime(skillList[chosenSkill - 1]);
        if(skillList[chosenSkill - 1] == Skills.SHOOT_THE_MOON)
        {
            promptPredictSkill(currentPet);
        }
    }

    /**
     * Displays the pet info.
     *
     * @param name
     * @param type
     */
    public void displayPetInfo(String name, PetTypes type)
    {
        disp.println("Pet: " + name + " Of Type: " + type.toString());
    }

    /**
     * Displays damage and HP.
     *
     * @param pets
     */
    public void displayDamageAndHP(List<Playable> pets)
    {
        for(int i = 0; i < pets.size(); i++)
        {
            String skill = pets.get(i).getSkill().toString();

            disp.println(pets.get(i).getPetName() + " took "
                    + pets.get(i).getFullDamageTaken() + " , has "
                    + pets.get(i).getCurrentHp() + prop("healthLeft") + " and used " + skill);
        }
    }

    /**
     * Prompts the prediction that is done for shoot the moon.
     *
     * @param currentPet current Playable object
     *
     * @see interfaces.Playable
     */
    public void promptPredictSkill(Playable currentPet)
    {
        Skills[] skillList = Skills.getNumSkills();
        disp.println(prop("predictSkill"));
        int chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
        disp.println(prop("skillPredictConfirm") + skillList[chosenSkill - 1].toString());
        currentPet.setGuessedSkill(skillList[chosenSkill - 1]);
    }

    /**
     * Checks that the chosen skill is not on cooldown for the current pet,
     * True - re-prompts
     * False - continue
     *
     * @param skillList   list of skills for the current pet
     * @param currentPet  current playable object
     * @param chosenSkill integer representing a skill from the skills list
     */
    private void cooldownCheck(Skills[] skillList, Playable currentPet, int chosenSkill)
    {
        disp.println(skillList[chosenSkill - 1].toString() + prop("cooldown")
                + currentPet.getSkillRechargeTime(skillList[chosenSkill - 1]));

        chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
    }

}
