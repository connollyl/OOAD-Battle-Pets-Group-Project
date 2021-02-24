package Pets;
import Pets.Skills;

public class PetSkill
{
	private Skills skill;
	private int currentRechargeTime;
	
	public PetSkill(Skills skill)
	{
		this.skill = skill;
		this.currentRechargeTime = 0;
	}

}
