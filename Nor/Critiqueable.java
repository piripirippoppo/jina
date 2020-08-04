package studentCode;
/**
 * Objects that implement this interface can be put into a CritiqueDeck.
 */
public interface Critiqueable 
{	
	/**
	 * Getter for the title of the media item.
	 * @return title of the media item
	 */
	public String getTitle();
		
	/**
	 * Getter for the amount of star power of the media item.
	 * @return amount of star power of the media item
	 */
	public int getStarPower();
	
	/**
	 * Getter for the number of fans the media item currently has.
	 * @return number of fans the media item currently has
	 */
	public int getFans();
		
	/**
	 * Setter for the number of fans the media item currently has.
	 * @param newFanCount the number of fans the media item currently has
	 */
	public void setFans(int newFanCount);
	
	/**
	 * Method to create an independent copy of the media item.
	 * @return independent copy of the media item
	 */
	public Critiqueable returnClone();
	
	/**
	 * Method that takes the outcome of a critique and increments the star
	 *   ratings by 1 if the number of critique wins has passed the threshold.
	 * @param outcome the outcome of the critique in which this media item was 
	 *   involved
	 * @return true if the outcome conveyed caused the star rating to go up
	 */
	public boolean inform(Universe.Outcomes outcome);	
	
	/**
	 * String generator for the media item.
	 * @return String representing the media item
	 */
	public String toString();
}
