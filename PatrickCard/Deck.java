package p6Coding;

import java.util.ArrayList;

public class Deck {

	//You need to use this ArrayList<Card> structure to hold the deck
	//  
	//Your cannot use regular arrays in this class other than in the
	//  deal method, which needs to return an array
	private ArrayList<Card> cards;	

	public Deck() 
	{
		cards = new ArrayList <Card> (45);
		
			  for(int suit=0; suit<=4; suit++)
				  for(int value=1; value<=9; value++)
					  cards.add(new Card(value, suit)); 
	}

	public Deck(Deck other) 
	{
		this.cards = new ArrayList <Card>(other.cards);
		/*other.cards has Deck and cards and ArrayList, but this.cards has only empty cards arrayList.
		 * I need to make a duplicate of other.cards(existing cards) then make this.cards to point that duplicate.
		 */
	}

	public Card getCardAt(int position) 
	{
		return cards.get(position);		
	}

	public int getNumCards() 
	{
		return cards.size();
	}

	public Card[] deal(int numCards) 
	{
		Card [] dealCard = new Card [numCards];   //new card array with the length of numCards
		
		for(int i=0; i<numCards; i++) 
		{
			dealCard[i] = cards.get(0); // the first card will go into the first list of the array (then the card will be removed)
			cards.remove(0);			// as I remove the first arrayList, all arrayList will be pushed to the front
		}								// therefore, remove and get both only 0 position. (numCards of time) 
			return dealCard;
	}
	
	public void cut(int position) throws StarDeckException 
	{
		ArrayList <Card> top = new ArrayList <Card>();   	//new empty cards that will keep the cards from left side of the position(card pick)
		ArrayList <Card> btm = new ArrayList <Card>(cards);     // should be the same as cards and will take the card from right side of the position
		ArrayList <Card> cut= new ArrayList <Card> (cards);       // same as the cards then add the topcards at the end of the arrayList
		
		int endPoint=cards.size()-3;
		if(position < 3 || position > endPoint) 
			throw new StarDeckException("Too few cards in one part of the cut.");
		
		else
		{
				for(int i=0; i<position; i++)
				{
					top.add(cards.get(i));
					cut.remove(0);    //again 0 position only
				}
				for(int i=position; i>cards.size(); i++)   //selected cards to the end of the cards.
					btm.add(cards.get(i));    
				
				for(Card c:top)			// add topCards to the end of cutCard arrayList
					cut.add(c);
				this.cards=cut;			//make the card same as cutCard
		}
	}
		
	public void shuffle() 
	{
		ArrayList<Card> shuffled = new ArrayList<Card>();
		
		if(cards.size()%2==1)
		{
			for(int i=0, j=cards.size()/2+1; j<cards.size(); i++, j++)
			{
				shuffled.add(cards.get(i));
				shuffled.add(cards.get(j));
			}
				shuffled.add(cards.get(cards.size()/2));
		}
		else
			for(int i=0, j=cards.size()/2; j<cards.size(); i++, j++)
			{
				shuffled.add(cards.get(i));
				shuffled.add(cards.get(j));
			}
		this.cards=shuffled;
		
		/*//If there are an odd number of cards, the top packet should have one more card than the bottom packet.		
		ArrayList <Card>cut = new ArrayList <Card>();  //store the first half of the cards.
		ArrayList <Card>sin = new ArrayList <Card>();  //eventually will store all the new orders of the cards.
	
		if(cards.size()%2==0)
		{
			for(int i=0; i<cards.size(); i++)
			{
				Card a = cards.get(0);  //since Im removing the card at the end, the position will be always 0
				cut.add(a);             // add the card to the cut arrayList one by one
				cards.remove(0);	    // remove first half cards
			}
			for(int i=0; i<cards.size(); i++)
			{
				sin.add(cut.get(i));	   //iterate cut (top half) and left of the this.cards (bottom half) 
				sin.add(cards.get(i));	   //so the cards appears one and one each time			
			}
		}
		else  //same codes for odd cards 
		{
			for(int i=0; i<cards.size(); i++)
			{
				Card a = cards.get(0);
				cut.add(a);
				cards.remove(0);
			}
			for(int i=0; i<cards.size(); i++)
			{
				sin.add(cut.get(i));
				sin.add(cards.get(i));

			}
		}	
			this.cards=sin;   //Since I created new "sin" card to make new shuffle cards, point that to this.cards.*/
}							  //so it will show in the scheen 
}
