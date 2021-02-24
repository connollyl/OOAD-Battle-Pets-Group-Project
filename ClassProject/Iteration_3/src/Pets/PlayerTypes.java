package Pets;

public enum PlayerTypes {
	IAMNOTAROBOT,
	TOPOFTHEFOODCHAIN;
	
	public static PlayerTypes[] getPlayerType()
	{
		return PlayerTypes.values();
	}
	
}

