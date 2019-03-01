package tests;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;
import blackjack.*;


/**
 * Please put your own test cases into this file, so they can be tested
 * on the server.
*/

public class StudentTests 
{
	@Test
	public void dealerBust()
	{
		Blackjack blackjack = setBlackJack();
		blackjack.deal();
		int [] dt = blackjack.getDealerCardsTotal();
		
		String results = getCardsString(blackjack.getDealerCards()) + "\n";
		results += "Dealer's Cards: " + "\n";

		int[] dealerValues =  blackjack.getDealerCardsTotal();
		
		
		System.out.print(results);

	}
	
	public static boolean correctResults(String filename, String results) {
        String officialResults="";
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filename));
            
            String line;
            while ((line = fin.readLine()) != null) {
                officialResults += line + "\n";
            }
            
        }catch (IOException e) {
            System.out.println("File opening failed.");
            return false;
        } 
        
        results = removeBlanks(results);
        officialResults = removeBlanks(officialResults);
        
        if (results.equals(officialResults)) {
            return true;
        }
        
        return false;
    }
	
	public static String removeBlanks(String src) {
		StringBuffer resultsBuf = new StringBuffer();
		
		char curr;
		for (int i=0; i<src.length(); i++) {
			curr = src.charAt(i);
			if (curr != ' ' && curr != '\n')
				resultsBuf.append(curr);
		}
		return resultsBuf.toString();
	}
	
	/**** Private methods ****/
	private static Blackjack setBlackJack() {
		Random randomGenerator = new Random(1234567L);
		int numberOfDecks = 1;
		Blackjack blackjack = new Blackjack(randomGenerator, numberOfDecks);
	    return blackjack;
	}
	
	private static String getCardsString(Card[] array) {
		String result = "";
		for (int i=0; i<array.length; i++) {
			result += array[i] + "\n";
		}
		return result;
	}
	
	private static String mapIntString(int value) {
		String result;
		switch (value) {
			case Blackjack.DRAW:
				result = "DRAW";
				break;
			case Blackjack.LESS_THAN_21:
				result = "LESS_THAN_21";
				break;
			case Blackjack.BUST:
				result = "BUST";
				break;
			case Blackjack.BLACKJACK:
				result = "BLACKJACK";
				break;
			case Blackjack.HAS_21:
				result = "HAS_21";
				break;
			case Blackjack.DEALER_WON:
				result = "DEALER_WON";
				break;
			case Blackjack.PLAYER_WON:
				result = "PLAYER_WON";
				break;
			case Blackjack.GAME_IN_PROGRESS:
				result = "GAME_IN_PROGRESS";
				break;
			default:
				result = "INVALID";
			    break;
		}
		return result;
	}
}
