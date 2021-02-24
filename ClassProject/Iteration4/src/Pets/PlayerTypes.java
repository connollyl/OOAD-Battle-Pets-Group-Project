package Pets;

public enum PlayerTypes {
	IAMNOTAROBOT,
	TOPOFTHEFOODCHAIN;
	
	/**
	 * Returns an array of PlayerTypes
	 * @return
	 */
	public static PlayerTypes[] getPlayerType()
	{
		return PlayerTypes.values();
	}
	
}

