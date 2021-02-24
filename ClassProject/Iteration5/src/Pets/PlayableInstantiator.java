package Pets;

import Pets.AIPet;
import Pets.AnotherCupOfJavaAI;
import Pets.Pet;
import Pets.PetTypes;

public class PlayableInstantiator {

	public PlayableInstantiator() {
	}

	public AnotherCupOfJavaAI createSmartAi(double maxHealth) {
		return new AnotherCupOfJavaAI(maxHealth, PetTypes.INTELLIGENCE, "AnotherCupOfJava", "CupOfJava", 0);
	}

	public AIPet createAiPet(double maxHealth, PetTypes type, String petName, String pMasterName, int index) {

		return new AIPet(maxHealth, type, pMasterName, pMasterName, index);
	}

	public Pet createHumanPet(double maxHealth, PetTypes type, String petName, String pMasterName, int index) {
		return new Pet(maxHealth, type, pMasterName, pMasterName, index);
	}

}
