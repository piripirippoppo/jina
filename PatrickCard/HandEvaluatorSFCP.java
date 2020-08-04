package p6Coding;

public class HandEvaluatorSFCP 
{
	//ALL OF THESE ARE PASSED AN ARRAY OF LENGTH 5

	//Cluster 1: Think about how a helper might be useful for these...
	public static boolean hasPair(Card[] cards) 
	{
		int count=0;
		for(int i=0; i<cards.length; i++)
			for(int j=i+1; j<cards.length; j++) 
			{
				if(cards[i].getValue()==cards[j].getValue())  //check if two cards' values are the same
					count++;								//if it is, then increment
			}
			if(count>0)								//if count is greater than 0, it means, there pair(s) exists. 
				return true;
			else
				return false;
	}

	public static boolean hasThreeOfAKind(Card[] cards) //value = numbers, suit = shape
	{
		int count=0;
		for(int i=0; i<cards.length; i++)
			for(int j=i+1; j<cards.length; j++)   //two loop to compare each card to each card 
			{
				if(cards[i].getValue()==cards[j].getValue()) //if values are the same then increment the count
					count++;
				
				if(j==cards.length-1 && count!=2) // when it hits the end and if the count is not 2, that means there are no 
					count=0;					//two same cards as first card, or just a pair so count set 0 to count again. 
	
				else if(j==cards.length-1 && count>=2) // when it hits the end and if the count is more than 2,
				{
					if(count==2 && hasFourOfAKind(cards)==true && hasFullHouse(cards)==true)
						return false;						//means there are at least three cards that are the same
					else if(count==2)
						return true;
				}
			}
				return false;
	}

	public static boolean hasFourOfAKind(Card[] cards) //value = numbers, suit = shape
	{
		int count=0;
		for(int i=0; i<cards.length; i++)
			for(int j=1+i; j<cards.length; j++)
			{
				if(cards[i].getValue()==cards[j].getValue())  //same as three kinds method but with count of 3
					count++;
				
				if(j==cards.length-1 && count!=3)
					count=0;
		
				else if(j==cards.length-1 && count>=3)
					return true;
			}
					return false;
	}

	public static boolean hasFiveOfAKind(Card[] cards) 
	{
		for(int j=1; j<cards.length; j++)  
			{
				if(cards[0].getValue()!=cards[j].getValue())// all the same values. if any cards(value)are different, return false
					return false;
			}
					return true;
	}
	//Cluster 2
	public static boolean hasRainbow(Card[] cards) 
	{
		for(int i=0; i<cards.length; i++)
			for(int j=i+1; j<cards.length; j++)
			{
				if(cards[i].getSuit()==cards[j].getSuit()) //all different suits. if any cards(suit) are matching, then return false
					return false;			
			}
					return true;
	}

	public static boolean hasStraight(Card [] cards) 
	{
		int min=cards[0].getValue(), total=0;
		for(int i=0; i<cards.length; i++)
		{
			total += cards[i].getValue();  //get the cards' total of value
			
			if(cards[i].getValue()<min)
				min=cards[i].getValue();  //find the minimum value of the card to compare  
		}
		if(hasPair(cards)==true)  //to check if there is any same value of card
			return false;
		
		if(min==1)    //when min is 1, the only two cases could be straight are 1-5 and 1 and 6-9 
		{
				if(total==31 || total==15)  //when the cards are 1-5, total is 15 and when the cards are 1 and 6-9, the total is 31
					return true;			// with the card value 1, only two cases are true
				else
					return false;
		}
		else			//besides 1, all possible minimum values are 2 to 5 (5,6,7,8,9) 
		{
			if(min==2 && total==20)	//when min is 2 and the cards are 2,3,4,5,6 then to total is 20 
				return true;
			else if(min==3 && total==25) // min 3 if ->(3,4,5,6,7) satisfied, then total is 25
				return true;
			else if(min==4 && total==30) // min 4 -> (4,5,6,7,8) the total is 30
				return true;
			else if(min==5 && total==35) // min 5 -> (5,6,7,8,9) the total is 35
				return true;
		}
			return false;	
	}

	public static boolean hasFlush(Card[] cards) 
	{
		for(int i=0; i<cards.length; i++)
			for(int j=1+i; j<cards.length; j++)
			{
				if(cards[i].getSuit()!=cards[j].getSuit()) //all the same suits. if any cards value(suit) are not the same
					return false;							//return false
			}
					return true;					
	}

	//Cluster 3: Think about how to make use of existing methods to
	//           make the following ones easier to write...
	public static boolean hasStraightRainbow(Card[] cards)
	{
		if(hasRainbow(cards)==true && hasStraight(cards)==true)  //if rainbow and straight both methods are satisfied 
			return true;										//then return true
		else
			return false;
	}

	public static boolean hasStraightFlush(Card[] cards) 
	{
		if(hasFlush(cards)==true && hasStraight(cards)==true)  
			return true;	
		else
			return false;
	}

	public static boolean hasTwoPair(Card[] cards) 
	{
		int count=0, saveCount=0;
		for(int i=0; i<cards.length; i++)
			for(int j=i+1; j<cards.length; j++)
			{
				if(cards[i].getValue()==cards[j].getValue()) //find if there is any same value and if there is,
					count++;								//then increment the count
				if(j==cards.length-1 && count==1)//check if the count is only 1 (for two cards pair)
				{											// this is to check every cycle (if count is 1, that means there is a pair of cards)
					saveCount++;	 						//this is for counting "how many pairs I have" 
					count=0;								//for next cycle, set the count 0 and recount the cards[1] to the end.
				}
				else if(j==cards.length-1 && count>=2 ||       //if im comparing from the middle of the card to the end, then count
							hasFourOfAKind(cards)==true || hasFiveOfAKind(cards)==true)//could be 2, so give those two extra condition
					return false;													//not to make Four/Five ofKind to be true			
				else if(j==cards.length-1 && count==0)
					continue;
			}
			if(saveCount==2)  //out of all those conditions, if saveCount is 2, that means there are two pairs of cards
				return true;
			else
				return false;
	}
	//Challenge
	public static boolean hasFullHouse(Card[] cards) 
	{		
		int count=0;
		
		for(int i=0; i<cards.length; i++)				//only one circle check
			if(cards[0].getValue()!=cards[i].getValue())  //get how many cards are not matching with the first card 	
				count++;      //count should be only 2 or 3 (when the hand is fullHouse)
		
		Card[] com = new Card[count];      //make a new card with the length of the count
	
		for(int i=0, k=0; i<cards.length; i++)
			if(cards[0].getValue()!=cards[i].getValue())
			{
				com[k]=cards[i];                      //store that un-matching cards to a new array 
				k++;
			}
		
			if(count==2)   //if count is 2, that means my original cards has threeOfKinds
			{
				if(com[0].getValue()==com[1].getValue())  //then check if rest of the cards are the same
					return true;							//if it is, then fullHouse
				else
					return false;
			}
			else if(count==3)  //if count is 3, that means my original cards has a pair of cards
			{
				if(com[0].getValue()==com[1].getValue() && com[1].getValue()==com[2].getValue()) //then check if rest are threeOfKind
					return true;	//if it is, then fullHouse!!!!!!!!!
				else
					return false;
			}
					return false;	
		
	
		//return hasTwoPair(cards)&&hasThreeOfAKind(cards);  //has to modify hasTwoPair(TwoPair can be fullhouse)
	}
}