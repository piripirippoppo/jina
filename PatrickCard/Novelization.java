package studentCode;

/** 
 * A mutable class that represents a Novelization media item that might be subject
 * to critique in our game.  As such, it implements the Critiqueable interface.
 * <br><br>
 * A Novelization object has a title (String) and a number of fans (int) as well
 * as a number of stars (int) above its base and an internal counter
 * for how many victories it has had since the last time its number of
 * stars increased (int).
 */
public class Novelization implements Critiqueable 
{
	
	private String title;
	private int numberOfFans;
	private int stars;
	private int winsSinceStarBump;

	/**
	 * Standard constructor.  
	 * 
	 * @param titleIn desired title for this Novelization
	 * @param numberOfFansIn starting number of fans for this Novelization
	 */
	public Novelization(String titleIn, int numberOfFansIn) 
	{
		title = titleIn;
		numberOfFans = numberOfFansIn;
		stars = 0;
		winsSinceStarBump = 0;
	}

	/**
	 * Copy constructor.  
	 * 
	 * @param other reference to the existing object which is the basis of the new one
	 */
	public Novelization(Novelization other) 
	{
		this.title = other.title;
		this.numberOfFans = other.numberOfFans;
		this.stars = other.stars;
		this.winsSinceStarBump = other.winsSinceStarBump;
	}
	
	/**
	 * Getter for star power of the Novelization, which is based on the
	 * first character of its title and the number of stars it has.
	 * Specifically, the UNICODE value of the first character in its name
	 * taken %10 plus the number of stars;
	 * 
	 * @return star power of the Novelization
	 */
	@Override
	public int getStarPower() 
	{
		int a = title.charAt(0)%10;
		return (a+stars);
	}
	
	/**
	 * Getter for name of the Novelization.
	 * 
	 * @return reference to the title of the Novelization
	 */
	@Override
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Getter for the number of fans of the Novelization.
	 * 
	 * @return number of fans of the Novelization
	 */
	@Override
	public int getFans()
	{
		return numberOfFans;
	}
	
	/**
	 * Setter for the number of fans of the Novelization.
	 * @param newNumberOfFans the new number of fans value for the Novelization
	 */
	@Override
	public void setFans(int newNumberOfFans) 
	{
		numberOfFans = newNumberOfFans;
	}
	
	/**
	 * Method that takes the outcome of a critique and increments the stars
	 *   if the number of critique victories since the last time the number
	 *   of stars was increased has passed the Universe's threshold.
	 *    
	 * However, it sets the numberOfFans to 0 if the outcome was a loss
	 *   
	 * A critique victory is defined as an outcome of FRESH.
	 * A critique lose is defined as an outcome of ROTTEN.  
	 *   
	 *  
	 * @param outcome the outcome of the critique in which this Novelization was 
	 *   involved
	 * @return true if the outcome conveyed caused the number of stars to go up
	 */
	@Override
	public boolean inform(Universe.Outcomes outcome) 
	{
		if(outcome == Universe.Outcomes.FRESH)
		{
			winsSinceStarBump++;		
			if(Universe.THRESHOLD<=winsSinceStarBump)
			{
				stars++;
				winsSinceStarBump=0;
				return true;
			}
		}
		else if(outcome == Universe.Outcomes.ROTTEN)
			numberOfFans=0;
		
			return false;		
	}
	
	/**
	 * Method to create an independent copy of the Novelization.
	 * @return independent copy of the Novelization
	 */
	@Override
	public Critiqueable returnClone() 
	{
		Novelization newNovelization = new Novelization(title, numberOfFans);
		newNovelization.stars = this.stars;
		newNovelization.winsSinceStarBump = this.winsSinceStarBump;
		
		return newNovelization;
	}
	
	/**
	 * The "usual suspect" toString method.
	 * @return a String describing the Novelization
	 */
	@Override
	public String toString() {
		return "Novelization<Title: " + getTitle() + 
				"  Fans: " + getFans() +
				"  Stars: " + getStarPower() +
				">";
	}
	
	/**
	 * The "usual suspect" equals method.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		else if (this.getClass()!=other.getClass()) {
			return false;
		}
		else {
			Novelization casted = (Novelization)other;
			return 
					this.getTitle().equals(casted.getTitle()) 
					&& 
					this.getFans() == casted.getFans() 
					&& 
					this.getStarPower() == casted.getStarPower();
		}
	}	
}
