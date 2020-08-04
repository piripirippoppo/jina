package studentCode;

/** 
 * A mutable class that represents a Novel media item that might be subject
 * to critique in our game.  As such, it implements the Critiqueable interface.
 * <br><br>
 * A Novel object has a title (String) and a number of fans (int) as well
 * as a number of stars (int) above its base and an internal counter
 * for how many victories it has had since the last time its number of
 * stars increased (int).
 */
public class Novel implements Critiqueable 
{
	
	private String title;
	private int numberOfFans;
	private int stars;
	private int winsSinceStarBump;

	/**
	 * Standard constructor.  
	 * 
	 * @param titleIn desired title for this Novel
	 * @param numberOfFansIn starting number of fans for this Novel
	 */
	public Novel(String titleIn, int numberOfFansIn) 
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
	public Novel(Novel other) 
	{
		this.title = other.title;
		this.numberOfFans = other.numberOfFans;
		this.stars = other.stars;
		this.winsSinceStarBump = other.winsSinceStarBump;
	}
	
	/**
	 * Getter for star power of the Novel, which is always 2 more than
	 * the number of actual stars they have.
	 * 
	 * @return star power of the Novel
	 */
	@Override
	public int getStarPower() 
	{
		return (stars+2);
	}
		
	/**
	 * Getter for name of the Novel.
	 * 
	 * @return reference to the title of the Novel
	 */
	@Override
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Getter for the number of fans of the Novel.
	 * 
	 * @return number of fans of the Novel
	 */
	@Override
	public int getFans() 
	{
		return numberOfFans;
	}
	
	/**
	 * Setter for the number of fans of the Novel.
	 * @param newNumberOfFans the new number of fans value for the Novel
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
	 * However, it reduces the numberOfFans by 1 if the outcome was a loss.
	 *   
	 * A critique victory is defined as an outcome of FRESH.
	 * A critique lose is defined as an outcome of ROTTEN.  
	 *    
	 * @param outcome the outcome of the critique in which this Novel was 
	 *   involved
	 * @return true if the outcome conveyed caused the number of stars to go up
	 */
	@Override
	public boolean inform(Universe.Outcomes outcome)
	{	
		if(outcome==Universe.Outcomes.FRESH)
		{
			winsSinceStarBump++;
			if(Universe.THRESHOLD<=winsSinceStarBump)
			{
				stars++;
				winsSinceStarBump=0;
				return true;
			}
		}
			else if(outcome==Universe.Outcomes.ROTTEN)
				numberOfFans--;
				
				return false;		
	}
	
	/**
	 * Method to create an independent copy of the Novel.
	 * @return independent copy of the Novel
	 */
	@Override
	public Critiqueable returnClone()
	{
		Novel newNovel = new Novel(title, numberOfFans);
		newNovel.stars=this.stars;
		newNovel.winsSinceStarBump = this.winsSinceStarBump;
		
		return newNovel;
	}
	
	/**
	 * The "usual suspect" toString method.
	 * @return a String describing the Novel
	 */
	@Override
	public String toString() {
		return "Novel<Title: " + getTitle() + 
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
			Novel casted = (Novel)other;
			return 
					this.getTitle().equals(casted.getTitle()) 
					&& 
					this.getFans() == casted.getFans() 
					&& 
					this.getStarPower() == casted.getStarPower();
		}
	}	
}
