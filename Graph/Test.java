package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;

public class StudentTests 
{
	@Test
	public void testFristElement() 
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();

		basicList.addToFront("Blue").addToFront("Oragne").addToFront("Red")
		.addToEnd("Purple").addToEnd("Milk");
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		
		System.out.println();
		System.out.println("First: " + basicList.getFirst());
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		
		assertEquals(basicList.getFirst(), "Red");
		
		basicList.addToFront("Silver");
		assertEquals(basicList.getFirst(), "Silver");			
	}
	
	@Test
	public void testLastElement() 
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Blue").addToFront("Oragne").addToEnd("Red")
		.addToFront("Purple").addToFront("Milk");
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		
		System.out.println();
		System.out.println("Last: " + basicList.getLast());
		
		assertEquals(basicList.getLast(), "Red");
		
		basicList.addToEnd("Silver");
		assertEquals(basicList.getLast(), "Silver");
	}
	
	@Test
	public void testNullCheck()
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Blue").addToFront("Oragne").addToEnd("Red").addToFront("Silver");
		
		
		for(String a:basicList)
			System.out.print(a + " ");
		System.out.println();
		basicList.retrieveFirstElement();
		basicList.retrieveFirstElement();
		basicList.retrieveLastElement();
		basicList.retrieveLastElement();
		basicList.retrieveLastElement();
		basicList.retrieveLastElement();
		basicList.retrieveFirstElement();
		basicList.retrieveFirstElement();
		

		System.out.print(basicList.getFirst() + " ");
		System.out.print(basicList.getLast() + " ");
		
		assertEquals(basicList.getFirst(), null);
		assertEquals(basicList.getLast(), null);
		
		basicList.addToEnd("Blue").addToFront("blue").addToEnd("BLUE").addToFront("blue");
		basicList.remove("blue", String.CASE_INSENSITIVE_ORDER); 
		
		System.out.println();
		
		basicList.remove("blue", String.CASE_INSENSITIVE_ORDER);
		
		System.out.print(basicList.getFirst() + " ");
		System.out.print(basicList.getLast() + " ");
		
		assertEquals(basicList.getFirst(), null);
		assertEquals(basicList.getLast(), null);
	}
	@Test
	public void testRemoveTargetElement() 
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();	
		basicList.addToFront("Blue").addToFront("Orange").addToFront("Red")
		.addToEnd("Milk").addToEnd("milk").addToEnd("Magenta").addToFront("Strawberry");
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		System.out.println("Removing Milk"); //one in the middle, multiple 
		basicList.remove("Milk", String.CASE_INSENSITIVE_ORDER);
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		BasicLinkedList<String> milkList = new BasicLinkedList<String>();
		milkList.addToFront("Blue").addToFront("Orange").addToFront("Red").addToEnd("Magenta").addToFront("Strawberry");
		
		assertEquals(basicList.toString(), milkList.toString());
						
		System.out.println("Removing Strawberry"); //one at the beginning
		basicList.remove("strawberry", String.CASE_INSENSITIVE_ORDER);
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		BasicLinkedList<String> strawberryList = new BasicLinkedList<String>();	
		strawberryList.addToFront("Blue").addToFront("Orange").addToFront("Red").addToEnd("Magenta");
		
		assertEquals(basicList.toString(), strawberryList.toString());
		
		System.out.println("Removing Magenta"); //one at the end
		basicList.remove("Magenta", String.CASE_INSENSITIVE_ORDER);
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		BasicLinkedList<String> magentaList = new BasicLinkedList<String>();
		magentaList.addToFront("Blue").addToFront("Orange").addToFront("Red");
		
		assertEquals(basicList.toString(), magentaList.toString());
		
		basicList.remove("RED", String.CASE_INSENSITIVE_ORDER);
		basicList.remove("orange", String.CASE_INSENSITIVE_ORDER);
		basicList.remove("blue", String.CASE_INSENSITIVE_ORDER);
		
		System.out.print(basicList.getFirst());
		System.out.print(basicList.getLast());
		
		assertEquals(basicList.getFirst(), null);
		assertEquals(basicList.getLast(), null);
		
	}
	@Test
	public void testRetrieveFirstElement() 
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToFront("Blue").addToEnd("Oragne").addToFront("Red")
		.addToEnd("Purple").addToFront("Milk").addToFront("Magenta").addToEnd("Strawberry");
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		System.out.println("Retrieve First: " + basicList.retrieveFirstElement());
		
		BasicLinkedList<String> magentaList = new BasicLinkedList<String>();
		magentaList.addToFront("Blue").addToEnd("Oragne").addToFront("Red")
		.addToEnd("Purple").addToFront("Milk").addToEnd("Strawberry");
		
		assertEquals(basicList.toString(), magentaList.toString());
				
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		System.out.println("Retrieve First: " + basicList.retrieveFirstElement());
		
		BasicLinkedList<String> milkList = new BasicLinkedList<String>();
		milkList.addToFront("Blue").addToEnd("Oragne").addToFront("Red")
		.addToEnd("Purple").addToEnd("Strawberry");
		
		assertEquals(basicList.toString(), milkList.toString());
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
	}
	@Test
	public void testRetrieveLastElement() 
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		
		basicList.addToEnd("Blue").addToFront("Orange").addToFront("Red")
		.addToFront("Purple").addToFront("Milk").addToEnd("Magenta").addToFront("Strawberry");
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		System.out.println("Retrieve Last: " + basicList.retrieveLastElement());
		assertTrue(basicList.getLast().equals("Blue"));
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
		
		System.out.println("Retrieve Last: " + basicList.retrieveLastElement());
		assertTrue(basicList.getLast().equals("Orange"));
		
		for (String entry : basicList) 
			System.out.print(entry + " ");
		System.out.println();
	}
	@Test
	public void testSize()
	{
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		
		basicList.addToFront("Blue").addToEnd("Oragne").addToFront("Red")
		.addToEnd("Purple").addToFront("Milk").addToEnd("Magenta").addToFront("Strawberry");
		
		//System.out.println("Size: " + basicList.getSize());
		assertEquals(basicList.getSize(),7);
		
		basicList.retrieveLastElement();
		System.out.println("After delete one Element. Size: " + basicList.getSize());
		assertEquals(basicList.getSize(),6);
		
		basicList.retrieveFirstElement();
		System.out.println("After delete one Element. Size: " + basicList.getSize());
		assertEquals(basicList.getSize(),5);
		
		basicList.addToFront("Blue");
		System.out.println("After add one Element. Size: " + basicList.getSize());
		assertEquals(basicList.getSize(),6);		
	}
	@Test
	public void testSoredList()
	{
		SortedLinkedList<String> sortedList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
	
		sortedList.add("Silver").add("Orange").add("Red").add("Purple").add("Milk")
		.add("Magenta").add("Strawberry").add("Blue").add("RED");
		
		SortedLinkedList<String> compList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
		
		compList.add("Blue").add("Magenta").add("Milk").add("Orange").add("Purple")
		.add("RED").add("Red").add("Silver").add("Strawberry");

		for(String s:sortedList)
			System.out.print(s + " ");	
		System.out.println();
		
		for(String s:compList)
			System.out.print(s + " ");	
		System.out.println();
		
		assertEquals(sortedList.toString(), compList.toString());	
	}
	@Test
	public void testSortedRemove()
	{
		SortedLinkedList<String> sortedList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
		
		sortedList.add("Silver").add("Oragne").add("Red").add("Purple").add("Milk")
		.add("Magenta").add("Strawberry").add("Blue").add("RED").add("blue");
		
		for(String s:sortedList)
			System.out.print(s + " ");
		System.out.println();
				
		sortedList.remove("blue"); //first (and second) element (with upper and lower cases)
		sortedList.remove("Strawberry"); //last element
		sortedList.remove("dummy"); //element not in the list

		SortedLinkedList<String> compList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
		
		compList.add("Silver").add("Oragne").add("Red").add("Purple").add("Milk")
		.add("Magenta").add("RED");
		
		for(String s:sortedList)
			System.out.print(s + " ");
		System.out.println();
		
		assertEquals(sortedList.getSize(), 7);
		assertEquals(sortedList.toString(), compList.toString());
	}
	@Test
	public void testGetReverseList()
	{
		BasicLinkedList<String> originalList = new BasicLinkedList<String>();
		originalList.addToFront("Maryland").addToFront("Kansas").addToFront("Hawaii")
		.addToFront("Georgia").addToFront("Delaware").addToFront("Connecticut").addToFront("Alaska");
		
		BasicLinkedList<String> compList = new BasicLinkedList<String>();
		compList.addToEnd("Maryland").addToEnd("Kansas").addToEnd("Hawaii")
		.addToEnd("Georgia").addToEnd("Delaware").addToEnd("Connecticut").addToEnd("Alaska");
		
		for (String entry : originalList) 
			System.out.print(entry + " ");
		System.out.println();
		
		for (String entry : compList) 
			System.out.print(entry + " ");
		System.out.println();
		
		for (String entry : originalList.getReverseList()) 
			System.out.print(entry + " ");
		System.out.println();
		
		assertEquals(originalList.getReverseList().toString(), compList.toString());
	}
	@Test
	public void testGetReverseArrayList()
	{
		BasicLinkedList<String> basedList = new BasicLinkedList<String>();
		basedList.addToFront("Maryland").addToFront("Kansas").addToFront("Hawaii").addToFront("Georgia")
		.addToFront("Delaware").addToFront("Connecticut").addToFront("Alaska");
		
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("Maryland"); answer.add("Kansas"); answer.add("Hawaii");  answer.add("Georgia");
		answer.add("Delaware"); answer.add("Connecticut"); answer.add("Alaska");
		
		for (String entry : basedList) 
			System.out.print(entry + " ");
		System.out.println();
		
		for (String entry : answer) 
			System.out.print(entry + " ");
		System.out.println();
				
		for (String entry : basedList.getReverseArrayList()) 
			System.out.print(entry + " ");
		System.out.println();
		
		assertEquals(basedList.getReverseArrayList(), answer);
	}
}
