package studentCode;

public class Universe {
	//The possible outcomes of critiques are 
	//   FRESH (which means you won)
	//   ROTTEN (which means you lost)
	//   NEUTRAL (which means a tie)
	public static enum Outcomes { FRESH, ROTTEN, NEUTRAL }
	
	public static int THRESHOLD = 12;
	
	public static Critiqueable tomatoToss(Critiqueable critic1, Critiqueable critic2) {
		int result = 
				critic1.getStarPower() * (int)Math.log(critic1.getFans()) 
				   -
				critic2.getStarPower() * (int)Math.log(critic2.getFans());
		
		if (result < 0) {
			critic1.inform(Outcomes.ROTTEN);
			critic2.inform(Outcomes.FRESH);
			return critic2;
		}
		
		if (result > 0) {
			critic1.inform(Outcomes.FRESH);
			critic2.inform(Outcomes.ROTTEN);
			return critic1;
		}
		
		critic1.inform(Outcomes.NEUTRAL);
		critic2.inform(Outcomes.NEUTRAL);
		
		return null;
	}	
}
