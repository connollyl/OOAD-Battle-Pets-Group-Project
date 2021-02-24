package Pets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

import Events.AttackEvent;
import Events.FightStartEvent;
import Events.PlayerEventInfo;
import Events.PlayerEventInfo.PlayerEventInfoBuilder;
import Events.RoundStartEvent;
import interfaces.Playable;

public class AnotherCupOfJavaAI implements Playable
{
	private final int ROF_DAMAGE_CHECKER = 5;
	private final int RANDOM_BOUND_TWO = 2;
	private final int CD_BASIC_SKILLS = 2; // Sets cool down to 2
	private final int CD_ADVANCED_SKILLS = 7; // Number of rounds that must go by before use of skills 4 and 5.
	private final double SEVENTY_FIVE_PERCENT = 0.75;
	private final double TWENTY_FIVE_PERCENT = 0.25;
	private final int indexPC  = 0;
	private final int indexRT  = 1;
	private final int indexSP  = 2;
	private final int indexSTM = 3;
	private final int indexRF  = 4;
	private int meIndex;
	private int victimIndex;
	private int fightsWon;
	private String petName;
	private String playerName;
	private double differenceSumDamage;
	private double maxHP;
	private double HP;
	private double damageTaken;
	private double randomDamageRecieved;
	private double randomDamageDealt;
	private Skills skillToUse;
	private Skills guessedSkill;
	private PetTypes petType;
	private PlayerTypes playerType;
	private List<Skills> skillsList = new ArrayList<Skills>();
	private Map<Skills, Skill> skillMap = new HashMap<Skills, Skill>();
	private PlayerEventInfo pEvent;
	private AttackEvent aEvent;
	private FightStartEvent fEvent;
	private RoundStartEvent rEvent;
	private Random RNGesus = new Random();
	private final int SKILL_BOUND = 5;
	private final int NORMAL_SKILL_BOUND = 3;
	private List<PlayerEventInfo> petInfoList = new ArrayList<PlayerEventInfo>();
	private List<Playable> petList = new ArrayList<Playable>();
	
	
	public AnotherCupOfJavaAI(PlayerEventInfo info)
	{
		petName = "Another Cup Of Java";
		playerName = "Team_13";
		maxHP = info.getStartingHp();
		HP = maxHP;
		damageTaken = 0;
		fightsWon = 0;
		skillToUse = null;
		guessedSkill = null;
		petType = PetTypes.INTELLIGENCE;
		playerType = PlayerTypes.AnotherCupOfJava;
		skillsList.addAll(info.getSkillSet());
		this.skillMap.put(Skills.ROCK_THROW, new Skill(Skills.ROCK_THROW));
		this.skillMap.put(Skills.SCISSORS_POKE, new Skill(Skills.SCISSORS_POKE));
		this.skillMap.put(Skills.PAPER_CUT, new Skill(Skills.PAPER_CUT));
		this.skillMap.put(Skills.SHOOT_THE_MOON, new Skill(Skills.SHOOT_THE_MOON));
		this.skillMap.put(Skills.REVERSAL_OF_FORTUNE, new Skill(Skills.REVERSAL_OF_FORTUNE));
	}
	
	//This constructor is solely used for our UI version as we create the pets before sending them to Ref.
	public AnotherCupOfJavaAI(double battleMaxHp, PetTypes type, String pName, String pMasterName, int index) 
	{
		meIndex = index;
		petName = pName;
		playerName = pMasterName;
		maxHP = battleMaxHp;
		HP = maxHP;
		fightsWon = 0;
		skillToUse = null;
		guessedSkill = null;
		petType = type;
		playerType = PlayerTypes.AnotherCupOfJava;
		this.skillMap.put(Skills.ROCK_THROW, new Skill(Skills.ROCK_THROW));
		this.skillMap.put(Skills.SCISSORS_POKE, new Skill(Skills.SCISSORS_POKE));
		this.skillMap.put(Skills.PAPER_CUT, new Skill(Skills.PAPER_CUT));
		this.skillMap.put(Skills.SHOOT_THE_MOON, new Skill(Skills.SHOOT_THE_MOON));
		this.skillMap.put(Skills.REVERSAL_OF_FORTUNE, new Skill(Skills.REVERSAL_OF_FORTUNE));
		Skills[] skillArray = Skills.values();
		skillsList = new ArrayList<Skills>();
		for(int i = 0; i < skillArray.length; i++)
			skillsList.add(skillArray[i]);
	}

	public void makePetList()
	{
		
	}
	
	@Override
	public String getPlayerName() 
	{
		return playerName;
	}
	
	@Override
	public String getPetName() 
	{
		return petName;
	}
	
	@Override
	public PlayerTypes getPlayerType() 
	{
		return playerType;
	}
	
	@Override
	public PetTypes getPetType() 
	{
		return petType;
	}
	
	@Override
	public double getCurrentHp() 
	{
		return HP;
	}
	
	@Override
	public void setCurrentHp(double currentHp) 
	{
		HP = currentHp;
		if(skillToUse == Skills.REVERSAL_OF_FORTUNE)
		{
			differenceSumDamage = 0;
		}
	}
	
	@Override
	public Skills chooseSkill() 
	{
		Skills skill = skillCalculator();
		setSkill(skill);
		return skill;
	}

	@Override
	public void updateHp(double hp) 
	{
		HP = HP - hp;
	}
	
	@Override
	public void resetHp() {
		HP = maxHP;
		
	}
	
	@Override
	public boolean isAwake() 
	{
		if(HP > 0)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public Skills getSkillPrediction() 
	{
		return guessedSkill;
	}
	
	@Override
	public int getSkillRechargeTime(Skills skill) 
	{
		return this.skillMap.get(skill).getRechargeTime();
	}
	
	@Override
	public double calculateHpPercent() 
	{
		return HP/maxHP;
	}
	
	@Override
	public void reset() 
	{
		HP = maxHP;
		skillToUse = null;
		guessedSkill = null;
		resetRecharge();
	}
	
	@Override
	public void decrementRechargeTimes() 
	{
		Iterator<Map.Entry<Skills, Skill>> iter = skillMap.entrySet().iterator();

		while (iter.hasNext()) 
		{
			Map.Entry<Skills, Skill> skillCooldown = (Map.Entry<Skills, Skill>)iter.next();
			((Skill) skillCooldown.getValue()).decrementRecharge();
		}

		for(int i = 0; i < petList.size(); i++)
		{
			petList.get(i).decrementRechargeTimes();
		}
	}
	
	@Override
	public void setRechargeTime(Skills skill, int rechargeTime) 
	{
		this.skillMap.get(skill).setRechargeTime(rechargeTime);
	}
	
	private Skills skillCalculator() 
	{
		
		if(petList.get(victimIndex).getPetType() == PetTypes.POWER)
		{
			return predictAttackOnPower();
		}
		else if(petList.get(victimIndex).getPetType() == PetTypes.SPEED)
		{
			return predictAttackOnSpeed();
		}
		else
		{
			return predictAttackOnIntelligence();
		}
	}
	
	private int findSkill (Skills skill)
	{
		for (int i = 0; i < skillsList.size(); i++)
		{
			if (skillsList.get(i) == skill)
			{
				return i;
			}
		}
		return -1; //Should never return this
	}
	
	private Skills predictAttackOnPower() 
	{
		int randChoice = RNGesus.nextInt(2);
		//Try using STM if possible
		if(rEvent.getRoundNumber() > 1 && !checkRecharge(skillsList.get(indexSTM)))
		{
			if(petList.get(victimIndex).checkRecharge(Skills.ROCK_THROW))
			{
				if(checkRecharge(skillsList.get(indexSP)))
					setGuessedSkill(skillsList.get(indexPC));
				else if(checkRecharge(skillsList.get(indexRT)))
				{
					if(randChoice == 0)
						setGuessedSkill(skillsList.get(indexPC));
					else
						setGuessedSkill(skillsList.get(indexSP));
				}
				else
					setGuessedSkill(skillsList.get(indexSP));
			}
			else if(petList.get(victimIndex).checkRecharge(Skills.SCISSORS_POKE))
			{
				if(checkRecharge(skillsList.get(indexSP)))
				{
					if(randChoice == 0)
						setGuessedSkill(skillsList.get(indexPC));
					else
						setGuessedSkill(skillsList.get(indexRT));
				}
				else if(checkRecharge(skillsList.get(indexPC)))
					setGuessedSkill(skillsList.get(indexPC));
				else
					setGuessedSkill(skillsList.get(indexRT));
			}
			else if(petList.get(victimIndex).checkRecharge(Skills.PAPER_CUT))
			{
				if(checkRecharge(skillsList.get(indexPC)))
					if(randChoice == 0)
						setGuessedSkill(skillsList.get(indexRT));
					else
						setGuessedSkill(skillsList.get(indexSP));
				else if(checkRecharge(skillsList.get(indexRT)))
					setGuessedSkill(skillsList.get(indexPC));
				else
					setGuessedSkill(skillsList.get(indexSP));
			}
			else
			{
				setGuessedSkill(skillsList.get(RNGesus.nextInt(NORMAL_SKILL_BOUND)));
			}
			return skillsList.get(indexSTM);
		}
		//checks to see if we should use reversal
		else if (differenceSumDamage > ROF_DAMAGE_CHECKER && 
				petList.get(victimIndex).getCurrentHp() > petList.get(meIndex).getCurrentHp())
		{
			return skillsList.get(indexRF);
		}
		//enemy Rock Throw is recharging
		else if(petList.get(victimIndex).checkRecharge(Skills.ROCK_THROW))
		{
			if (!checkRecharge(skillsList.get(indexRT)))
			{
				return skillsList.get(indexRT);
			}		
			else if(!checkRecharge(skillsList.get(indexPC)) && randChoice == 0)
			{
				return skillsList.get(indexPC);
			}
			else 
			{
				return skillsList.get(indexSP);
			}
		}
		//enemy Scissors Poke is recharging
		else if(petList.get(victimIndex).checkRecharge(Skills.SCISSORS_POKE))
		{
			if (!checkRecharge(skillsList.get(indexSP)))
			{
				return skillsList.get(indexSP);
			}
			else if(!checkRecharge(skillsList.get(indexRT)) && randChoice == 0)
			{
				return skillsList.get(indexRT);
			}
			else
			{
				return skillsList.get(indexPC);
			}
		}
		//enemy Paper Cut is recharging
		else if(petList.get(victimIndex).checkRecharge(Skills.PAPER_CUT))
		{
			if (!checkRecharge(skillsList.get(indexPC)))
			{
				return skillsList.get(indexPC);
			}
			else if(!checkRecharge(skillsList.get(indexSP)) && randChoice == 0)
			{
				return skillsList.get(indexSP);
			}
			else
			{
				return skillsList.get(indexRT);
			}
		}
		//if none of the conditions are true, choose a random valid skill.
		else
		{
			int randomIndex = RNGesus.nextInt(SKILL_BOUND);
			while(checkRecharge(skillsList.get(randomIndex)))
			{
				randomIndex = RNGesus.nextInt(SKILL_BOUND); 
			}
					return skillsList.get(randomIndex);
		}
	}

	private Skills predictAttackOnSpeed() 
	{
		Skills skill = null;
		
		if(this.calculateHpPercent() >= SEVENTY_FIVE_PERCENT)
		{
			if(petList.get(victimIndex).checkRecharge(Skills.ROCK_THROW))
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 0)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 0)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 1)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 1)
				{
					skill = Skills.PAPER_CUT;
				}
			}
			else if(!checkRecharge(skillsList.get(indexRT)))
			{
				skill = Skills.ROCK_THROW;
			}
			else if(!checkRecharge(skillsList.get(indexSTM)))
			{
				skill = Skills.SHOOT_THE_MOON;
				predictSTMSpeedSkill();
			}
			else if(!checkRecharge(skillsList.get(indexRF)))
			{
				skill = Skills.REVERSAL_OF_FORTUNE;
			}
			else
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 0)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 0)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 1)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 1)
				{
					skill = Skills.SCISSORS_POKE;
				}
			}
		}
		else if((this.calculateHpPercent() < SEVENTY_FIVE_PERCENT) && 
				(this.calculateHpPercent() >= TWENTY_FIVE_PERCENT))
		{
			if(petList.get(victimIndex).checkRecharge(Skills.SCISSORS_POKE))
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 0)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 0)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 1)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 1)
				{
					skill = Skills.PAPER_CUT;
				}
			}
			else if(!checkRecharge(skillsList.get(indexSP)))
			{
				skill = Skills.SCISSORS_POKE;
			}
			else if(!checkRecharge(skillsList.get(indexSTM)))
			{
				skill = Skills.SHOOT_THE_MOON;
				predictSTMSpeedSkill();
			}
			else if(!checkRecharge(skillsList.get(indexRF)))
			{
				skill = Skills.REVERSAL_OF_FORTUNE;
			}
			else
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 0)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 0)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexPC)) && randTemp == 1)
				{
					skill = Skills.PAPER_CUT;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 1)
				{
					skill = Skills.ROCK_THROW;
				}
			}
		}
		else
		{
			if(petList.get(victimIndex).checkRecharge(Skills.PAPER_CUT))
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 0)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 0)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 1)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 1)
				{
					skill = Skills.SCISSORS_POKE;
				}
			}
			else if(!checkRecharge(skillsList.get(indexPC)))
			{
				skill = Skills.PAPER_CUT;
			}
			else if(!checkRecharge(skillsList.get(indexSTM)))
			{
				skill = Skills.SHOOT_THE_MOON;
				predictSTMSpeedSkill();
			}
			else if(!checkRecharge(skillsList.get(indexRF)))
			{
				skill = Skills.REVERSAL_OF_FORTUNE;
			}
			else
			{
				double randTemp = RNGesus.nextInt(2);
				if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 0)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 1)
				{
					skill = Skills.ROCK_THROW;
				}
				else if(!checkRecharge(skillsList.get(indexSP)) && randTemp == 0)
				{
					skill = Skills.SCISSORS_POKE;
				}
				else if(!checkRecharge(skillsList.get(indexRT)) && randTemp == 1)
				{
					skill = Skills.ROCK_THROW;
				}
			}
		}
		return skill;
	}
	
	
	private void predictSTMSpeedSkill() 
	{
		if(this.calculateHpPercent() >= SEVENTY_FIVE_PERCENT)
		{
			if(petList.get(victimIndex).checkRecharge(Skills.ROCK_THROW))
			{
				guessedSkill = skillsList.get(RNGesus.nextInt(2) + 2);
			}
			else 
			{
				guessedSkill = Skills.ROCK_THROW;
			}
		}
		else if((this.calculateHpPercent() < SEVENTY_FIVE_PERCENT) && 
				(this.calculateHpPercent() >= TWENTY_FIVE_PERCENT))
		{
			if(petList.get(victimIndex).checkRecharge(Skills.SCISSORS_POKE))
			{
				int skillToChoose = (RNGesus.nextInt(2));
				if(skillToChoose == 1)
				{
					guessedSkill = Skills.ROCK_THROW;
				}
				else
				{
					guessedSkill = Skills.PAPER_CUT;
				}
			}
			else
			{
				guessedSkill = Skills.SCISSORS_POKE;
			}

		}
		else
		{
			if(petList.get(victimIndex).checkRecharge(Skills.PAPER_CUT))
			{
				guessedSkill = skillsList.get(RNGesus.nextInt(2) + 1);
			}
			else
			{
				guessedSkill = Skills.PAPER_CUT;
			}
		}
	}
	

	/**
	 * Chooses the best attack against INT pets.
	 * @return skillchoice
	 */
	//Might be better to have it choose the skill that will do +3 damage every time, 
	// or to choose STM if the enemy is consistently doing +3 Cond DMG every time.
	private Skills predictAttackOnIntelligence() 
	{
		int skillChoice = RNGesus.nextInt(2);
		if(petList.get(victimIndex).checkRecharge(Skills.ROCK_THROW))
		{
			if(skillChoice == 0)
			{
				if(!checkRecharge(skillsList.get(indexPC)))
				{
					return skillsList.get(indexPC);
				}
				else
				{
					return skillsList.get(indexRT);
				}
			}
			if(skillChoice == 1)
			{
				if(!checkRecharge(skillsList.get(indexRT)))
				{
					return skillsList.get(indexRT);
				}
				else
				{
					return skillsList.get(indexPC);
				}
			}
		}
		else if(petList.get(victimIndex).checkRecharge(Skills.SCISSORS_POKE))
		{
			if(skillChoice == 0)
			{
				if(!checkRecharge(skillsList.get(indexRT)))
				{
					return skillsList.get(indexRT);
				}
				else
				{
					return skillsList.get(indexSP);
				}
			}
			if(skillChoice == 1)
			{
				if(!checkRecharge(skillsList.get(indexSP)))
				{
					return skillsList.get(indexSP);
				}
				else
				{
					return skillsList.get(indexRT);
				}
			}
		}
		else if(petList.get(victimIndex).checkRecharge(Skills.PAPER_CUT))
		{
			if(skillChoice == 0)
			{
				if(!checkRecharge(skillsList.get(indexSP)))
				{
					return skillsList.get(indexSP);
				}
				else
				{
					return skillsList.get(indexPC);
				}
			}
			if(skillChoice == 1)
			{
				if(!checkRecharge(skillsList.get(indexPC)))
				{
					return skillsList.get(indexPC);
				}
				else
				{
					return skillsList.get(indexSP);
				}
			}
		}
		else if (differenceSumDamage > ROF_DAMAGE_CHECKER /*&& enemy health is > our health*/)
		{ 
			return skillsList.get(indexRF);
		}
		else
		{
			int randomIndex = RNGesus.nextInt(SKILL_BOUND);
			if(checkRecharge(skillsList.get(randomIndex)) && skillsList.get(randomIndex) != Skills.SHOOT_THE_MOON)
			{
				randomIndex = RNGesus.nextInt(SKILL_BOUND);
			}
			return skillsList.get(randomIndex);
		}
		return predictAttackOnIntelligence();
	}

	public void resetRecharge() 
	{
		Iterator<Map.Entry<Skills, Skill>> iter = skillMap.entrySet().iterator();
		
		while (iter.hasNext()) 
		{
			Map.Entry<Skills, Skill> skillCooldown = (Map.Entry<Skills, Skill>)iter.next();
			((Skill) skillCooldown.getValue()).resetRecharge();
		}
	}
	
	@Override
	public void update(Observable playable, Object o) 
	{
		if(o instanceof AttackEvent)
		{
			AttackEvent ae = (AttackEvent)o;
			aEvent = ae;
			setIndexes();
			setPetInfo();
		}
		//Shouldn't be used, but just in case somehow it gets sent here.
		else if(o instanceof PlayerEventInfo)
		{
			PlayerEventInfo pe = (PlayerEventInfo)o;
			pEvent = pe;
		}
		else if(o instanceof RoundStartEvent)
		{
			RoundStartEvent re = (RoundStartEvent)o;
			rEvent = re;
		}
		else if(o instanceof FightStartEvent)
		{
			FightStartEvent fe = (FightStartEvent)o;
			fEvent = fe;
			petList.clear();
			petInfoList = fe.getPlayerEventInfo();
			createPetList();
		}
	}
	
	private void setPetInfo() 
	{
		petList.get(aEvent.getAttackingPlayableIndex()).setSkill(aEvent.getAttackingSkillChoice());
		petList.get(aEvent.getAttackingPlayableIndex()).setGuessedSkill(aEvent.getPredictedSkillEnum());
		petList.get(aEvent.getVictimPlayableIndex())
		.setCurrentHp(petList.get(aEvent.getAttackingPlayableIndex()).getCurrentHp() - 
				(aEvent.getRandomDamage() + aEvent.getConditionalDamage()));
	}

	private void createPetList() 
	{
		for(int i = 0; i < petInfoList.size(); i++)
		{
			if(petInfoList.get(i).getPlayerType().equals(PlayerTypes.AnotherCupOfJava))
			{
				petList.add(new AnotherCupOfJavaAI(petInfoList.get(i).getStartingHp(), 
						petInfoList.get(i).getPetType(), petInfoList.get(i).getPetName(), 
						petInfoList.get(i).getPetName(), i));
				if(i == petInfoList.size() - 1)
				{
					victimIndex = 0;
					meIndex = i;
				}
				else
				{
					meIndex = i;
					victimIndex = i + 1;
				}
			}
			petList.add(new AIPet(petInfoList.get(i).getStartingHp(), 
					petInfoList.get(i).getPetType(), petInfoList.get(i).getPetName(), 
					petInfoList.get(i).getPetName(), i));
		}
	}
	
	private void setIndexes() 
	{
		damageTaken = 0;
		if(petList.get(aEvent.getAttackingPlayableIndex()).getPetName().equals(this.getPetName())
				|| petInfoList.get(meIndex).getPlayerType().equals(PlayerTypes.AnotherCupOfJava))
		{
			meIndex = aEvent.getAttackingPlayableIndex();
			setRandomDamageDealt(aEvent.getRandomDamage());
			victimIndex = aEvent.getVictimPlayableIndex();
		}
		if(petList.get(aEvent.getVictimPlayableIndex()).getPetName().equals(this.getPetName()))
		{
			setRandomDamageTaken(aEvent.getRandomDamage());
			damageTaken = aEvent.getRandomDamage() + aEvent.getConditionalDamage();
		}
		if(aEvent.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE)
			differenceSumDamage = 0;
	}
	
	@Override
	public void setSkill(Skills skills) 
	{
		skillToUse = skills;
		if(skills == Skills.SHOOT_THE_MOON || skills == Skills.REVERSAL_OF_FORTUNE)
			setRechargeTime(skills, CD_ADVANCED_SKILLS);
		else
			setRechargeTime(skills, CD_BASIC_SKILLS);
	}

	
	
	@Override
	public Skills getSkill() {
		return skillToUse;
	}

	@Override
	public void setPlayerType(PlayerTypes type) 
	{
		playerType = type;
	}

	@Override
	public int getFightsWon() {
		return fightsWon;
	}

	@Override
	public boolean checkRecharge(Skills skill) {
		if (skillMap.get(skill).isOnCD())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public double getMaxHp() {
		return maxHP;
	}

	@Override
	public double getDamageDifference() {
		return differenceSumDamage;
	}

	@Override
	public void incrementFightsWon() 
	{
		fightsWon++;
	}

	@Override
	public void setRandomDamageDealt(double randomDamage) {
		randomDamageDealt = randomDamage;
	}

	@Override
	public void setRandomDamageTaken(double randomDamage) {
		randomDamageRecieved = randomDamage;
	}

	@Override
	public int getPlace() {
		return meIndex;
	}

	@Override
	public void calculateDamageDifference() {
		differenceSumDamage = differenceSumDamage + (randomDamageRecieved - randomDamageDealt);
	}

	@Override
	public void setGuessedSkill(Skills skills) 
	{
		guessedSkill = skills;
	}

	@Override
	public double getFullDamageTaken() 
	{
		return damageTaken;
	}

	@Override
	public void guiChooseSkill(Skills skills) {
		skillToUse = skills;
		
	}

}
