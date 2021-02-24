package interfaces;

public interface Startable {
	
	public enum startType
	{
		CONSOLE(0),
		GUI(1);
		
		private int type;
		
		/**
		 * Returns the type of startable
		 * @return
		 */
		public int getType()
		{
			return this.type;
		}
		
		/**
		 * Constructor for a startType
		 * @param t
		 */
		private startType(int t)
		{
			this.type = t;
		}
		
	}
	
	/**
	 * Returns the game type as a int
	 * @return
	 */
	public static int getGameType()
	{
		return -1;
	}
	
	/**
	 * Overwriteable start method for each game type
	 */
	public void start();

}
