package p6Coding;

import java.util.ArrayList;

public class HandEvaluatorPatrickjack {
	// Each of these is passed a reference to an ArrayList<Card> object
	// with "UNKNOWN" length (so you'll need to "ask" it).

	/*
	 * If you have only two cards in your hand and they are a 4 and a 2 (order
	 * doesn't matter) you have Patrickjack! class needs to evaluate the value of
	 * this hand as a 22. If you have only two cards in your hand and they are a 6
	 * and a 9 (order doesn't matter) you have a Margiejack, which has the value 21.
	 * If any of your cards are a 5, each can be treated as either a 5 or a 15
	 * depending on which is better for you. Note that if you had two cards that
	 * were a 5, you wouldn't want BOTH treated as 15 since then you'd be over and
	 * would lose.
	 */

	public static int eval(ArrayList<Card> hand) // value==number, suit==shape
	{
		int total = 0, numOfFive = 0;
		int totalOfFive = 0, totalWithoutFive = 0;

		for (int i = 0; i < hand.size(); i++) 
		{
			total += hand.get(i).getValue(); // total of all values

			if (hand.get(i).getValue() == 5) // count the value that is a 5
			{
				numOfFive++; // count how many 5s I have (for converting 5 or 15)
				totalOfFive += hand.get(i).getValue(); // need to know the total values without values with 5s in order
														// to
			} // convert to make close to 21
		}
		totalWithoutFive = total - totalOfFive;
		
		if (hand.size()==2) 
			{
				if ((hand.get(0).getValue()==4 && hand.get(1).getValue()==2) || (hand.get(1).getValue()==4 && hand.get(0).getValue()==2))
					return 22; // PatrickJack
				else if ((hand.get(0).getValue()==6 && hand.get(1).getValue()==9) || (hand.get(1).getValue()==6 && hand.get(0).getValue()==9))
					return 21; // MargieJack
				else if (hand.get(1).getValue()==5 && hand.get(0).getValue()==5) // converting one 5 to value of 15
																						// to
					return 20; // make bigger number
				else if (hand.get(0).getValue()==5 || hand.get(1).getValue()==5) 
				{
					if (totalWithoutFive <= 6)				//if one of the cards is 5 and the other card value 6 or less
						return (totalWithoutFive + 15);      //then add 15 to make larger value
					else if (totalWithoutFive > 6 && totalWithoutFive <= 16) //if more the value is btwn 6 and 16, then use 5 as 5
						return total;
				} 
				else     //total of two cards deosnt exceed 21 so return total
						return total;
			}
		else 
			{
				for (int i = 0; i < hand.size(); i++) 
				{

				if (numOfFive == 0 || numOfFive > 2) // if I have none or more than 2 cards of 5s, I dont need to convert any 5s
					{
					if (total <= 21) // if total is 21 or smaller, then I need the value as it is
						return total;
					else // over 21 goes to 0
						return 0;
					}
				else if (numOfFive == 1) // when I have one card that is a 5
				{
					if (totalWithoutFive <= 6) // any cards sum less than 7, I need to convert 5 to 15 so it gets close
												// to 21
						return (totalWithoutFive + 15);
					else if (totalWithoutFive > 6 && totalWithoutFive <= 16) // any cards from 7 to 16(16+5=21) need the
																				// value
						return total;
					else // if total sum of cards without five is over 16, then it goes over 21
						return 0;
				}
				else if (numOfFive == 2) // two of 5s
				{
					if (totalWithoutFive == 1) // when I have an ace only besides 5s, convert one of 5s to 15 and make
												// total 21
						return (totalWithoutFive + 20);
					else if (totalWithoutFive > 1 && totalWithoutFive <= 11) // if sum of cards are btwn 2 to 11, both
																				// 5s are 5
						return (totalWithoutFive + 10); // (5+15+2=22)
					else
						return 0; // any sum more 12 besides 5s will be more than 21 after adding two 5s(value of 10)
				}
			}
		}
				return 0;
	}

	/*
	 * houseWins method, the winning hand is the higher hand that has not exceeded
	 * 21 points. Patrickjack is considered as being higher than a 21 total using
	 * more than two cards. In the case of a tie, the player wins. So, for example,
	 * a Patrickjack against a Margiejack has the Patrickjack win and a Margiejack
	 * against a 9+4+8 is a tie.
	 */

	public static boolean houseWins(ArrayList<Card> player, ArrayList<Card> dealer) 
	{
		if(eval(dealer)>eval(player) || eval(player)>22 && eval(dealer)<22)
			return true;
		else
			return false;		
	}
}
