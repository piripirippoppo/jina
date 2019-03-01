package blackjack;
import java.util.*;

public class Blackjack implements BlackjackEngine {
	
	/**
	 * Constructor you must provide.  Initializes the player's account 
	 * to 200 and the initial bet to 5.  Feel free to initialize any other
	 * fields. Keep in mind that the constructor does not define the 
	 * deck(s) of cards.
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	List <Card> gameCard, pCard, dCard;
	Random rdmGen;
	int numOfDecks, bet, pAccount, status;
	
	public Blackjack(Random randomGenerator, int numberOfDecks) 
	{
	    this.rdmGen = randomGenerator;
	    this.numOfDecks = numberOfDecks;
	    
	    gameCard = new ArrayList <Card>();
		for(CardSuit suit:CardSuit.values())
			for(CardValue value:CardValue.values())
				gameCard.add(new Card(value, suit));
			
			bet=5;
			pAccount=200;	
			status=GAME_IN_PROGRESS;
	}
	public int getNumberOfDecks() 
	{
		return numOfDecks;
	}
	/**
	 * Creates and shuffles the card deck(s) using a random number generator.
	 */
	public void createAndShuffleGameDeck() 
	{
		List<Card>shuffled = new ArrayList<Card>();
		for(CardSuit suit:CardSuit.values())
			for(CardValue value:CardValue.values())
				shuffled.add(new Card(value, suit));
	
		Collections.shuffle(shuffled, rdmGen); //shuffle the cards with the method that takes two parameters
		gameCard = shuffled;
	}
	/**
	 * Returns the current deck of cards.
	 * @return Card array representing deck of cards.
	 */
	public Card[] getGameDeck() 
	{
		Card [] gCard = new Card[gameCard.size()];
		for(int i=0; i<gameCard.size(); i++)
			gCard[i] = gameCard.get(i);
		
		return gCard;
	}
	/**
	 * Creates a new deck of cards, and assigns cards to the dealer and player.
	 * A total of four cards are dealt in the following order:
	 * Player (face up), Dealer (face down), Player (face up), Dealer (face  up).
	 * Once the cards have been dealt, the game's status will be GAME_IN_PROGRESS.
	 * Delete the bet amount from the account.
	 */
	public void deal() 
	{	//Creates a new deck of cards
			createAndShuffleGameDeck();
			
			pCard = new ArrayList<Card>();
			dCard = new ArrayList<Card>();
			
		//and assigns cards to the dealer and player.
		//Player (face up), Dealer (face down), Player (face up), Dealer (face  up).
		pCard.add(gameCard.get(0));	pCard.get(0); gameCard.remove(0);
		dCard.add(gameCard.get(0)); dCard.get(0).setFaceDown();	gameCard.remove(0);
		pCard.add(gameCard.get(0));	pCard.get(1); gameCard.remove(0);
		dCard.add(gameCard.get(0));	dCard.get(1); gameCard.remove(0);
		
		//Once the cards have been dealt, the game's status will be GAME_IN_PROGRESS.
			status=GAME_IN_PROGRESS;
			
		//Delete the bet amount from the account.
			pAccount -= bet;
	}
		
	public Card[] getDealerCards() 
	{
		Card [] dealerCard = new Card[dCard.size()];
		for(int i=0; i<dCard.size(); i++)
			dealerCard[i] = dCard.get(i);
			
		return dealerCard;
	}
	/**
	 * Returns an array representing the possible value(s) associated with the 
	 * dealer's cards if the cards represent a value less than or equal to 21.
	 * @return Integer array representing the possible value(s) or null if cards
	 * represent a value higher than 21.  The array will have a size of 1 if only
	 * one value is associated with the set of cards, and a size of two if two
	 * values are possible.  For the case of an array of size two, the smaller value
	 * must appear in the first array entry.
	 */
	public int[] getDealerCardsTotal() 
	{
		int dTotal [] = new int[0];
		int total=0, numOfAce=0;
		
		for(int i=0; i<dCard.size(); i++)
		{
			total += dCard.get(i).getValue().getIntValue(); //total value of the cards
			
			if(dCard.get(i).getValue()==CardValue.Ace) //check hand w ace
				numOfAce++; // if at least one ace exist, true.
		}
		if(numOfAce==0) //A hand with no Ace 
		{
			if(total<=21) //If the total <=21, store the value as it is
			{
				dTotal = new int[1];
				dTotal[0] = total;
				return dTotal;
			}
			else
				return null;
		}
		else //If there is at least one ace exists
		{
			if(total<=11) //convert ace as 11.
			{
				dTotal = new int[2];
				dTotal[0] = total;
				dTotal[1] = total+10;
				return dTotal;
			}
			else //if the total is more than 11, use Ace as 1.
			{
				if(total<=21) //no bust = store the value as it is
				{
					dTotal = new int[1];
					dTotal[0] = total;
					return dTotal;
				}
			}
				return null;
		}
	}
	
	public int getDealerCardsEvaluation()        
	{
		int numOfAce=0, numOfTen=0, totalWithoutAce=0, total=0, totalOfAce=0;

		for(int i=0; i<dCard.size(); i++)
		{
			total += dCard.get(i).getValue().getIntValue();
		
			if(dCard.get(i).getValue()==CardValue.Ace) //check if there is ace(s)
				numOfAce++; // count ace(s);
			
			if(dCard.get(i).getValue()==CardValue.Jack || dCard.get(i).getValue()==CardValue.King ||
				dCard.get(i).getValue()==CardValue.Queen)
				numOfTen++;
		}
			totalWithoutAce = total - totalOfAce; //to use to count has_21 with ace(s)
			
			if(getDealerCardsTotal()==null) //handle all hand total value more than 21
				return BUST;
			else // when the values are less or equals to 21
			{
				if(dCard.size()==2) //to handle BLACKJACK
				{
					if(numOfAce==1) //if an ace exists
					{
						if(numOfTen==1) //if king, jack or queen exists
							return BLACKJACK; 
						else //ace with no jack/king/queens
							return total==11?HAS_21:LESS_THAN_21;
					} // two cards in hand with no ace
					else
						return total==21?HAS_21:LESS_THAN_21;
				}
					
				else  //more than two cards
				{	
					if(numOfAce==0)
						return total==21?HAS_21:LESS_THAN_21;
					else if(numOfAce==1)  //all else if for find HAS_21 when ace(s) exist(s)
						return totalWithoutAce==10?HAS_21:LESS_THAN_21;
					//total without Ace is 9, convert Ace to 11(Has_21) otherwise, less than 21
					else if(numOfAce==2) 				
						return totalWithoutAce==9?HAS_21:LESS_THAN_21;
					//total without Ace is 8, convert Ace to 11(Has_21) otherwise, less than 21
					else if(numOfAce==3)
						return totalWithoutAce==8?HAS_21:LESS_THAN_21;
					//total without Ace is 7, convert Ace to 11(Has_21) otherwise, less than 21
					else
						return totalWithoutAce==7?HAS_21:LESS_THAN_21;
				}
			} 
	}
	
	public Card[] getPlayerCards() 
	{
		Card [] playerCard = new Card[pCard.size()];
		for(int i=0; i<pCard.size(); i++)
			playerCard[i] = pCard.get(i);
		
		return playerCard;
	}
	/**
	 * Returns an array representing the possible value(s) associated with the 
	 * player's cards if the cards represent a value less than or equal to 21.
	 * @return integer array representing the possible value(s) or null if cards
	 * represent a value higher than 21.  The array will have a size of 1 if only
	 * one value is associated with the set of cards, and a size of two if two
	 * values are possible.  For the case of an array of size two, the smaller value
	 * must appear in the first array entry.
	 */
	public int[] getPlayerCardsTotal() 
	{
		int pTotal [] = new int[0];
		int total=0, numOfAce=0;
		
		
		for(int i=0; i<pCard.size(); i++)
		{
			total += pCard.get(i).getValue().getIntValue();
			
			if(pCard.get(i).getValue()==CardValue.Ace)
				numOfAce++;
		}
		if(numOfAce==0) //A hand with no Ace 
		{
			if(total<=21) //If the total <=21, store the value as it is
			{
				pTotal = new int[1];
				pTotal[0] = total;
				return pTotal;
			}
			else // if the total is more than 21, BUST(null)
				return null;
		}
		else //If there is at least one ace exists
		{
			if(total<=11) // when total is 11 including at least one ace, convert ace as 11.
			{
				pTotal = new int[2]; 
				pTotal[0] = total; //smaller value
				pTotal[1] = total+10; //ace value as 11 
				return pTotal;
			}
			else //ace with total value more than 11 (use ace as 1)
			{
				if(total<=21) //if total 21 or less than 21, store the value
				{
					pTotal = new int[1];
					pTotal[0] = total;
					return pTotal;
				}
			}
		}
			return null;
				
	}
	
	public int getPlayerCardsEvaluation() 
	{
		int numOfAce=0, numOfTen=0, totalWithoutAce=0, total=0, totalOfAce=0;

		for(int i=0; i<pCard.size(); i++)
		{
			total += pCard.get(i).getValue().getIntValue(); // the total values of the cards
		
			if(pCard.get(i).getValue()==CardValue.Ace) // if a hand has(have) ace(s)
				numOfAce++; // count how many ace(s) are in hand
			
			if(pCard.get(i).getValue()==CardValue.Jack || pCard.get(i).getValue()==CardValue.King
				|| pCard.get(i).getValue()==CardValue.Queen) 
				numOfTen++;
		}
			totalWithoutAce = total - totalOfAce; //to use to check a hand of HAS_21	
			
			if(getPlayerCardsTotal()==null) // handle all hand value more than 21
				return BUST;
			
			if(pCard.size()==2) //to handle BLACKJACK
			{
				if(numOfAce==1) //if an ace exists
				{
					if(numOfTen==1) //if king, jack or queen exists
						return BLACKJACK; 
					else // An ace exists, but no king/jack/queen ( if total==21 -> has_21, otherwise less)
						return total==21?HAS_21:LESS_THAN_21;
				}
				else // TWO cards in hand w no ace
					return total==21?HAS_21:LESS_THAN_21;
			}
				
			else  //more than two cards
			{	
				if(numOfAce==0)
					return total==21?HAS_21:LESS_THAN_21;
				else if(numOfAce==1)  //all else if for find HAS_21 when ace(s) exist(s)
					return totalWithoutAce==10?HAS_21:LESS_THAN_21;
				//total without Ace is 9, convert Ace to 11(Has_21) otherwise, less than 21
				else if(numOfAce==2) 				
					return totalWithoutAce==9?HAS_21:LESS_THAN_21;
				//total without Ace is 8, convert Ace to 11(Has_21) otherwise, less than 21
				else if(numOfAce==3)
					return totalWithoutAce==8?HAS_21:LESS_THAN_21;
				//total without Ace is 7, convert Ace to 11(Has_21) otherwise, less than 21
				else
					return totalWithoutAce==7?HAS_21:LESS_THAN_21;
			}					
	}

	/**
	 * Retrieves a card from the deck and assigns the card to the player.
	 * The new sets of cards will be evaluated.  If the player busts, the game
	 * is over and the games's status will be updated to DEALER_WON.  Otherwise
	 * the game's status is GAME_IN_PROGRESS.
	 */
	public void playerHit() 
	{	
		//Retrieves a card from the deck and assigns the card to the player.
		pCard.add(gameCard.get(0)); gameCard.remove(0);
		
		//The new sets of cards will be evaluated
	//If the player busts -> game over -> the games's status DEALER_WON	
		if(getPlayerCardsEvaluation()==BUST) 
			status=DEALER_WON;
		else
			status=GAME_IN_PROGRESS; 
	}
	/**
	 * Flips the dealer's card that is currently face down 
	 * and assigns cards to the dealer as long as the dealer 
	 * doesn't bust and the cards have a value less than 16.  Once the dealer
	 * has a hand with a value greater than or equal to 16, and less than or equal to 21, 
	 * the hand will be compared against the player's hand and whoever has the
	 * hand with a highest value will win the game. If both have the same value 
	 * we have a draw.  The game's status will be updated to one of
	 * the following values: DEALER_WON, PLAYER_WON, or DRAW.  The player's
	 * account will be updated with a value corresponding to twice the bet amount if 
	 * the player wins.  If there is a draw the player's account will be updated
	 * with the only the bet amount. 
	 */
	public void playerStand() 
	{
		dCard.get(0).setFaceUp(); //Flips the dealer's card that is currently face down
		
		while(evalDHand()<16 && getDealerCardsEvaluation()!=BUST)
		{	
			dCard.add(gameCard.get(0)); //assigns cards to the dealer as long as the dealer 
			gameCard.remove(0);			//doesn't bust and the cards have a value less than 16.	
		}
		
		if(evalDHand()>=16 && evalDHand()<=21) // more than 16, less than 21 gets evaluated 
		{	
			if(getDealerCardsEvaluation()==HAS_21) // dealer = has_21
			{
				if(getPlayerCardsEvaluation()==LESS_THAN_21) // d.has_21 vs p.less_than_21
					status=DEALER_WON;
				else // d.has_21 vs (p.has_21 or p.blackjack)
				{
					status=DRAW;
					pAccount += bet;
				}
			}
			else if(getDealerCardsEvaluation()==LESS_THAN_21) //dealer = less_than_21
			{
				if(getPlayerCardsEvaluation()==LESS_THAN_21) // d.less_than_21 vs p.less_than_21
				{
					if(evalDHand()>evalPHand()) // d.cardValue > p.CardValue
						status=DEALER_WON;
					else if(evalDHand()<evalPHand()) // d.cardValue < p.CardValue
					{
						status=PLAYER_WON;
						pAccount += (bet*2);
					}
					else //d.cardValue = p.CardValue
					{
						status=DRAW;
						pAccount += bet;
					}
				}
				else // d.less_than_21 vs (p.has_21 or p.blackjack)
				{
					status=PLAYER_WON;
					pAccount += (bet*2);
				}
			}
			else if(getDealerCardsEvaluation()==BLACKJACK) // dealer = blackjack
			{	//d.blackjack vs (p.blackjack or p.has_21)
				if(getPlayerCardsEvaluation()==BLACKJACK || getPlayerCardsEvaluation()==HAS_21) 
				{ 
					status=DRAW;
					pAccount += bet;
				}
				else // d.blackjack vs p.less_than_21
					status=DEALER_WON;
			}
		}
		else if(getDealerCardsEvaluation()==BUST)  //dealer = bust
			{
				status=PLAYER_WON;
				pAccount += (bet*2);
			}	
	}
	
	public int getGameStatus() 
	{
		return status;
	}
		
	public void setBetAmount(int amount) 
	{
		bet = amount;
	}
	
	public int getBetAmount() 
	{
		return bet;
	}
	
	public void setAccountAmount(int amount) 
	{	
		pAccount = amount;
	}
	
	public int getAccountAmount() 
	{
		return pAccount;
	}

	/* Feel Free to add any private methods you might need */
	
	public int evalPHand()
	{
		int total=0, numOfAce=0;
		
		for(int i=0; i<pCard.size(); i++)
		{
			total += pCard.get(i).getValue().getIntValue();
			
			if(pCard.get(i).getValue()==CardValue.Ace)
				numOfAce++; //count Ace(s)
		}
		if(numOfAce==0) //A hand with no Ace 
		{
			if(total<=21) //If the total <=21, store
				return total;
		}
		
		else //If there is at least one ace exists
		{
			if(total<=11)
			{
				total += 10;
				return total;
			}
			else if(total>11 && total<=21)
				return total;
		}
				return total;
	}
	
	public int evalDHand()
	{
		int total=0, numOfAce=0;
		
		for(int i=0; i<dCard.size(); i++)
		{
			total += dCard.get(i).getValue().getIntValue();
			
			if(dCard.get(i).getValue()==CardValue.Ace)
				numOfAce++; // count Ace(s)
		}
		
		if(numOfAce==0) //A hand with no Ace 
		{
			if(total<=21) //If the total <=21, store
				return total;
		}
		
		else //If there is at least one ace exists
		{
			if(total<=11) // when the total is 11, convert Ace as 11.
			{
				total += 10; 
				return total;
			}
			else if(total>11 && total<=21) // total btwn 11 to 21, store as it is.
				return total;
		}
				return total; 
	}
}
