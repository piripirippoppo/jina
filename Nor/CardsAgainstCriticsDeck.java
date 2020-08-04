package studentCode;

import java.awt.List;
import java.util.ArrayList;


/** 
 * A data structure class that stores deep copies of any critiqueable items 
 * that are sent to be critiqued.  By having deep copies, the player's
 * critiqueable item does not get permanently altered in their personal 
 * library, only in this playing of the game.
 * <br><br>
 * A CardsAgainstCriticsDeck object has a list of Critiqueable items
 * held in an ArrayList object.  It cannot be used to hold anything that
 * doesn't implement the Critiqueable interface.
 */
public class CardsAgainstCriticsDeck<T extends Critiqueable> 
{
	private ArrayList<Critiqueable> arr;
	int pos=0;  // to be used in addItem instead of the arr.size() <-- could be changed if used in other methods

	/**
	 * Standard constructor.  It needs to initialize the ArrayList object 
	 * and do any other setup that you deem necessary for this class object.
	 */
	public CardsAgainstCriticsDeck() 
	{	
		arr = new ArrayList<Critiqueable>(0);	
	}

	/**
	 * Adds an item to the CardsAgainstCriticsDeck in a very special way.  
	 * The structure is double-ended; this means that the "side" to which
	 * objects are added alternates with every other item added.  If things
	 * are added in the order 1,2,3,4,5 then the CardsAgainstCriticsDeck 
	 * would grow as the following:<br>
	 * &nbsp;&nbsp;&nbsp;1<br>
	 * &nbsp;&nbsp;&nbsp;1,2<br>
	 * &nbsp;&nbsp;&nbsp;3,1,2<br>
	 * &nbsp;&nbsp;&nbsp;3,1,2,4<br>
	 * &nbsp;&nbsp;&nbsp;5,3,1,2,4<br>
	 * It is your job to determine a good way to ensure this alternation.
	 * 
	 * You can add an instance field to the class if needed in making this
	 * method work correctly.
	 * 
	 * @param newItem refers to a critiqueable item to be added to this CardsAgainstCriticsDeck
	 */
	public void add(T newItem) 
	{
		if(pos%2==0)
			this.arr.add(0,newItem.returnClone());  //when the number of element is even, position is 0 
		else
			this.arr.add(newItem.returnClone());	//when the number of element is odd, store them to the end
		pos++;								//iterate every time newItem is added		
	}
	
	/**
	 * Goes through each item in the CardsAgainstCriticsDeck and adds the 
	 * specified number of fans to every critiqueable item it contains.
	 * 
	 * @param fanGain the value to add to the number of fans
	 */
	public void freshenUp(int fanGain) 
	{
		for(Critiqueable a:arr)
			a.setFans(a.getFans()+fanGain);
	}
	
	/**
	 * Goes through each item in the CardsAgainstCriticsDeck and deducts the 
	 * specified number of fans from every critiqueable item it contains.
	 * 
	 * @param fanLoss the value to deduct from the number of fans
	 */
	public void rottenDown(int fanLoss) 
	{
		for(Critiqueable a:arr)
			a.setFans(a.getFans()-fanLoss);
	}
	
	/**
	 * Removes any critiqueable item currently in the critique deck that have
	 * no more fans left.
	 */
	public void sweepDeck() 
	{
		ArrayList <Critiqueable> newArr = new ArrayList <Critiqueable>(arr); //shallow copy
		for(Critiqueable a:newArr)		//iterate
			if(a.getFans()<=0) 
				arr.remove(a);  		//remove the shallow copy ArrayList (referencing the same elements)			
	}	
	
	/**
	 * Shuffles the contents of the deck in the way described here. 
	 * The deck will be divided into two "packets" (we will call them 
	 * the top half and the bottom half).  The shuffled CardsAgainstCriticsDeck
	 * will consist of the first card from the top packet, 
	 *    followed by the first card from the bottom packet, 
	 *    followed by the second card from the top packet, 
	 *    followed by the second card from the bottom packet, etc. 
	 * 
	 * Important: If there are an odd number of cards, the top packet 
	 * should have one more card than the bottom packet. 
	 * 
	 * Remember that the top of the deck is considered to be the front 
	 * of the ArrayList. 
	 * 
	 */
	public void shuffle() 
	{
		ArrayList <Critiqueable> newArr = new ArrayList <Critiqueable>(pos);
			
		if(arr.size()%2==1) //when it's odd one more card is in top packet
			{
				for(int i=0, j=arr.size()/2+1; j<arr.size(); i++, j++) // i=top cards starting point
				{					   // j=bottom cards starting point +1 gives one less card than top card packet		
					newArr.add(arr.get(i)); 							
					newArr.add(arr.get(j));  //add top card and bottom card each time
				}
				newArr.add(arr.get(arr.size()/2)); //since iteration doesn't go to the middle point, add it later 
			}									//should be after for loop (b/c at the end) 
			else //when it's even cards
			{
				for(int i=0, j=arr.size()/2; j<arr.size(); i++, j++) //top and bottom gets the same amount of cards
				{
					newArr.add(arr.get(i));  
					newArr.add(arr.get(j));
				}	
			} 
			arr = newArr;
	}
	
	/**
	 * The method is meant to allow the next two critiqueable items to be critiqued
	 * against each other, and for the winner to be returned.
	 * <br><br>
	 * If there are no critiqueable items in the structure, returns null.<br>
	 * If there is only one critiqueable item in the structure, it is removed
	 * and declared the winner<br>
	 * Otherwise, there is a series of events that takes place.
	 * The following presents the events and the order in which 
	 * the events must take place:<br>
	 * 1. One critiqueable item is removed from each end of the structure
	 *       and they will later be critiqued.<br>
	 * 2. All critiqueable items remaining in the CardsAgainstCriticsDeck have their fan
	 *       counts reduced by the following rules:<br>
	 *         * If they have Star Power of more than 100, they lose 10 fans.<br>
	 *         * Otherwise, if they have Star Power of more than 50, they lose 5 fans.<br>
	 *         * Otherwise, they lose 1 fan.<br>
	 * 3. The CardsAgainstCriticsDeck is cleared of any critiqueable items who no longer
	 *       have any fans.<br>
	 * 4. Use the freshenUp method to make it so that each critiqueable item still in 
	 *       the CardsAgainstCriticsDeck gets one new fan. <br>
	 * 5. The winner in solitaire critiques between the two critiqueable items removed
	 *    in the first step above are determined in by two-step process; 
	 *       (a) if they have different star power values, the one with the higher 
	 *           star power value wins but if they have the same star power values then 
	 *       (b) the one with the larger number of fans wins.  If they have the
	 *           same star power values and the same number of fans, then whichever 
	 *           of the two critiqueable items came from the front of the deck wins.
	 * <br><br>
	 * NOTE: The @SuppressWarnings("unchecked") indicator is to inform
	 * the compiler that even though we don't test to make sure the cast
	 * to T is valid, we are sure of our logic.
	 * 
	 * @return reference to the winning critiqueable item
	 */
	@SuppressWarnings("unchecked")
	public T solitaireCritique() 
	{
		if(arr.size()==0) //If there are no critiqueable items in the structure, returns null.
			return null;
		else if(arr.size()==1)  //If there is only one critiqueable item in the structure, it is removed
			return (T)arr.remove(0); //and declared the winner
		else   //Otherwise, there is a series of events that takes place.
		{	
			Critiqueable first = arr.remove(0);  //One critiqueable item is removed from each end of the structure
			Critiqueable last = arr.remove(arr.size()-1);
			
			for(Critiqueable a:arr)
			{
				if(a.getStarPower()>100) //If they have Star Power of more than 100, they lose 10 fans.
					a.setFans(a.getFans()-10); 
				else if(a.getStarPower()>50) //if they have Star Power of more than 50, they lose 5 fans.
					a.setFans(a.getFans()-5);
				else						//they lose 1 fan.
					a.setFans(a.getFans()-1);
			}
			
			ArrayList <Critiqueable> newArr = new ArrayList<Critiqueable>(arr);
			for(Critiqueable a:newArr) //The CardsAgainstCriticsDeck is cleared of any critiqueable items who
				if(a.getFans()<=0)     //no longer have any fans. (includes less than 0)
					arr.remove(a);

				freshenUp(1); //Use the freshenUp method to make it so that each critiqueable item still in
								//the CardsAgainstCriticsDeck gets one new fan. 
				if(first.getStarPower()==last.getStarPower())  //if they have the same star power values
				{  //the one with the larger number of fans wins or if the number of fans are the same, first wins
					if(first.getFans()>=last.getFans())
						return (T)first;
					else  //if the other way around, then last critique wins
						return (T)last;
				}
				else
				{ //if they have different star power values, the one with the higher star power value wins
					if(first.getStarPower()>last.getStarPower())
						return (T)first;
				}		
						return (T)last;			//cast Critiqueable to T because return type doesn't match but since
		}										// T is extended, ok to use as long as casted.
	}
	
	/**
	 * The method will return a ragged 2D structure using the Java array.
	 * It will have references to deep copies of the critiqueable items 
	 * currently stored in the CardsAgainstCriticsDeck.  
	 * 
	 * The 2D structure will have one row for each single-digit star power
	 *  value (0 through 9) and then a row for all with higher star power
	 *  values.  
	 *  
	 * Within each row the order will be based on the "front to back" order 
	 * of them in the deck's single-dimensional structure.
	 * 
	 * NOTE: To build the ragged 2D structure, you'll need to read
	 * through the list of critiqueable items once to determine how big each 
	 * row will need to be and then another time to populate the 
	 * ragged structure with the references to the copies of the 
	 * critiqueable items.
	 * 
	 * @return reference to a ragged 2D structure using the java array
	 */
	public Critiqueable[][] export2Darray() 
	{
		Critiqueable[][] retVal = new Critiqueable[11][];
		for(int i=0; i<retVal.length; i++)   //give all the array(column) size of 0
			retVal[i] = new Critiqueable[0]; //in case, if there is no elements, then size still be set to 0
		
		int [] size = new int[11]; 			 //for declaring the size of the array
		
		for(int i=0; i<arr.size(); i++) //for making second arrays' sizes (iterate arr.size() much)
		{		
			int count = arr.get(i).getStarPower();  //get the StarPower Value
			
			if(count>10)  //StarPower 10 or more is going to the last row  		
				retVal[10] = new Critiqueable[++size[10]]; //iterate the element of size[10] which will be the size of the array 	
			else  											//any number of StarPower below 10 will be stored in each row
				retVal[count] = new Critiqueable[++size[count]]; //StarPower matches with row.			
		}
		
		int [] iterator = new int[11];  //to iterate the position where the elements will be stored
		for(int j=0; j<arr.size(); j++) //for storing the array
		{
			int count = arr.get(j).getStarPower(); //get the value of StarPower
			
			if(count>10)    											//[iterator[10]++] will only increase 
				retVal[10][iterator[10]++] = arr.get(j).returnClone();  // when it get hit. 
			else
				retVal[count][iterator[count]++] = arr.get(j).returnClone(); // row is the same as count
		}																     //iterate only when it gets hit
		return retVal;
	}

	/**
	 * The method will return a ragged 2D structure using the ArrayList
	 * data type - it will have references to deep copies of the critiqueable 
	 * items currently stored in the CardsAgainstCriticsDeck.  
	 * 
	 * The 2D structure will have one row for each single-digit star power
	 *  value (0 through 9) and then a row for all with higher star power
	 *  values.  
	 *  
	 * Within each row the order will be based on the "front to back" order 
	 * of them in the deck's single-dimensional structure.
	 * 
	 * NOTE: To build this ragged 2D structure, you should only need
	 * to go through the deck once!
	 * 
	 * @return reference to a ragged 2D structure using ArrayLists
	 */
	public ArrayList<ArrayList<Critiqueable>> export2Darraylist() 
	{
		ArrayList<ArrayList<Critiqueable>> retVal = new ArrayList<ArrayList<Critiqueable>>();
		
		for(int i=0; i<11; i++)		//initialize in case if there is no elements, doesn't go null
			retVal.add(new ArrayList<Critiqueable>(0));
		
		for(int i=0; i<arr.size(); i++)  //since I don't have to give the initial size to second arrayList, 
		{									//store the elements as I get 
			int count = arr.get(i).getStarPower();
			
			if(count>10)
				retVal.get(10).add(arr.get(i).returnClone());  // to the 10th row
			else
				retVal.get(count).add(arr.get(i).returnClone()); //row is same as count
		}	
		return retVal;
	}	
	
	/**
	 * The method will return a String object containing a representation
	 * of the critiqueable items currently held in the CardsAgainstCriticsDeck, 
	 * shown in order from "front to back" of the ArrayList holding the references.
	 * 
	 * @return String representing the CardsAgainstCriticsDeck
	 */
	@Override
	public String toString() {
		StringBuffer retVal = new StringBuffer("Contents: ");
		retVal.append("[ ");
		for (Critiqueable val : arr) {
			retVal.append(val + ", ");
		}
		if (retVal.lastIndexOf(", ") == retVal.length()-2) {
			retVal.delete(retVal.length()-2, retVal.length());
		}
		retVal.append(" ]");

		return new String(retVal);
	}
}
