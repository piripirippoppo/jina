package studentCode;


/** 
 * A mutable class that represents a Movie media item that might be subject
 * to critique in our game.  As such, it implements the Critiqueable interface.
 * <br><br>
 * A Movie object has a title (String) and a number of fans (int) as well
 * as a number of stars (int) above its base and an internal counter
 * for how many victories it has had since the last time its number of
 * stars increased (int).
 */

public class Movie implements Critiqueable 
{
	
	private String title;
	private int numberOfFans;
	private int stars;
	private int winsSinceStarBump;

	/**
	 * Standard constructor.  
	 * 
	 * @param titleIn desired title for this Movie
	 * @param numberOfFansIn starting number of fans for this Movie
	 */
	public Movie(String titleIn, int numberOfFansIn) 
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
	public Movie(Movie other) 
	{
		this.title = other.title;
		this.numberOfFans = other.numberOfFans;
		this.stars = other.stars;
		this.winsSinceStarBump = other.winsSinceStarBump;
	}
		
	/**
	 * Getter for star power of the Movie, which is always 7 more than
	 * the number of actual stars they have.
	 * 
	 * @return star power of the Movie
	 */
	@Override
	public int getStarPower() 
	{
		return (stars+7);
	}	
	
	/**
	 * Getter for name of the Movie.
	 * 
	 * @return reference to the title of the Movie
	 */
	@Override
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Getter for the number of fans of the Movie.
	 * 
	 * @return number of fans of the Movie
	 */
	@Override
	public int getFans() 
	{
		return numberOfFans;
	}
	
	/**
	 * Setter for the number of fans of the Movie.
	 * @param newNumberOfFans the new number of fans value for the Movie
	 */
	@Override
	public void setFans(int newNumberOfFans) 
	{
		numberOfFans=newNumberOfFans;
	}
	/**
	 * Method that takes the outcome of a critique and increments the stars
	 *   if the number of critique victories since the last time the number
	 *   of stars was increased has passed the Universe's threshold.
	 *   A critique victory is defined as an outcome of FRESH.
	 * @param outcome the outcome of the critique in which this Movie was 
	 *   involved
	 * @return true if the outcome conveyed caused the number of stars to go up
	 */
	@Override
	public boolean inform(Universe.Outcomes outcome) 
	{
		if(outcome == Universe.Outcomes.FRESH)
		{
			winsSinceStarBump++;
			
			if(winsSinceStarBump>=Universe.THRESHOLD)
			{	
				stars++;
				winsSinceStarBump=0;
				return true;
			}
		}
				return false;
	}

	/**
	 * Method to create an independent copy of the Movie.
	 * @return independent copy of the Movie
	 */
	@Override
	public Critiqueable returnClone() 
	{
		Movie newMovie = new Movie(title, numberOfFans);
		newMovie.stars = this.stars;
		newMovie.winsSinceStarBump = this.winsSinceStarBump;
		
		return newMovie;
	}
	
	/**
	 * The "usual suspect" toString method.
	 * @return a String describing the Movie
	 */
	@Override
	public String toString() {
		return "Movie<Title: " + getTitle() + 
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
			Movie casted = (Movie)other;
			return 
					this.getTitle().equals(casted.getTitle()) 
					&& 
					this.getFans() == casted.getFans() 
					&& 
					this.getStarPower() == casted.getStarPower();
		}
	}
}
