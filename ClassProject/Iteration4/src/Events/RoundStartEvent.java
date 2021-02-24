package Events;
/**
 * This class handles all of the objects and Builder for 
 * RoundStartEvent.
 * Author: Aidan, Andrew
 */
public class RoundStartEvent extends BaseEvent
{
	private int roundNumber;
	private static final int INT_DEFAULT = -1;
	public RoundStartEvent(RoundStartEventBuilder builder)
	{
		super(EventTypes.ROUND_START);
		if(builder.roundNumber == 0) {
			this.roundNumber = INT_DEFAULT;
		}else {
			this.roundNumber = builder.roundNumber;
		}
	}
	/**
     * Returns roundNumber
	 * @return roundNumber
	 */
	public int getRoundNumber()
	{
		return roundNumber;
	}
	/**
	 * Builder Class For roundEvent Objects
	 */
	public static class RoundStartEventBuilder
	{
		public int roundNumber;
		
		/**
		 * Default contstructor for roundEvent Builder
		 */
		public RoundStartEventBuilder()
		{
		}
		/**
		 * Initializes roundNumber for roundEvent
		 */
		public RoundStartEventBuilder withRoundNumber(int roundNum) {
			this.roundNumber = roundNum;
			return this;
		}
		/**
		 * Builds class.
		 */
		public RoundStartEvent build() {
			return new RoundStartEvent(this);
		}

		
		/**
		 * Rerturns string representation for roundStartEvent
		 * @return string value
		 */
		@Override
		public String toString() {
			return "RoundStartEventBuilder [roundNumber=" + roundNumber + "]";
		}
		/**
		 * Auto-Generated Hashcode
		 * @return hashcode
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + roundNumber;
			return result;
		}
		/**
		 * Determines if two RoundStartEvent objects are equal.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RoundStartEventBuilder other = (RoundStartEventBuilder) obj;
			if (roundNumber != other.roundNumber)
				return false;
			return true;
		}
		
	}

}
