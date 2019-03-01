package listClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BasicLinkedList<T> implements Iterable<T>
{
	protected class Node
	{
		protected T data;
		protected Node next;
		
		Node(T data)
		{
			this.data = data;
			next = null;
		}
	}
		protected Node head, tail;
		protected int size;
	
	//Defines an empty linked list.
	public BasicLinkedList()
	{
		head = tail = null;
		size = 0;
	}
	//Adds element to the end of the list.
	public BasicLinkedList<T> addToEnd(T data) //use the tail reference
	{
		if(data==null) return this;
		
		Node newNode = new Node(data);
		if(head==null)
		{
			head = tail = newNode;
			size++;
		}
		else
		{
			newNode.next = tail.next; // to connect the nodes
			tail.next = newNode;
			tail = newNode;
			size++;
		}		
		return this;
	}
	//Adds element to the front of the list.
	public BasicLinkedList<T> addToFront(T data)
	{
		if(data==null) return this;
		
		Node newNode = new Node(data);
		if(head==null)
		{
			//newNode.next = head;
			head = tail = newNode;
			size++;
		}
		else
		{
			newNode.next = head;
			head = newNode;
			size++;
		}
		return this;
	}
	//Returns but does not remove the first element from the list.
	public T getFirst()
	{
		return (head == null || size==0)? null:head.data; 
	}
	//Returns but does not remove the last element from the list.
	public T getLast() //use the tail reference
	{
		return (tail == null || size==0)? null:tail.data;
	}
	
	//Returns an arrayList with the elements of the current list in reverse order.
	public ArrayList<T> getReverseArrayList() //gets linkedList and return arrayList
	{
		ArrayList<T> answer = new ArrayList<T>();
		getArrayReversedAux(head, answer);

		return answer;	
	}
	private void getArrayReversedAux(Node headAux, ArrayList<T> answer)
	{		
		if(headAux!= null)
		{
			answer.add(0, headAux.data);
			getArrayReversedAux(headAux.next, answer);
		}
	}
	//Returns a new list with the element of the linked list in reverse order.
	/** add an auxiliary function no instance variables, no static variables**/
	public BasicLinkedList<T> getReverseList() //recursion
	{
		BasicLinkedList<T> answer = new BasicLinkedList<T>();
		getReversedAux(head, answer);
		return answer;	
	}
	private void getReversedAux(Node headAux, BasicLinkedList<T> answer)
	{
		if(headAux != null)
		{
			answer.addToFront(headAux.data);	
			getReversedAux(headAux.next, answer);
		}
	}
	//Notice you must not traverse the list to compute the size.
	public int getSize()
	{
		return size;
	}
	//This method must be implemented using an anonymous inner class that defines the iterator.
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{	
			Node curr;
			public boolean hasNext()
			{
				return curr != tail || curr == null;
			}
			public T next()
			{
				if(curr == null)
				{
					curr = head;
					return curr.data;
				}
				if(curr.next == null)
					throw new NoSuchElementException();
		
				curr = curr.next;
				//T temp = curr.data;
				return curr.data;
			}
		};
	}
	//Removes ALL instances of targetData from the list.
	public BasicLinkedList<T> remove(T targetData, Comparator<T> comparator)
	{
		Node prev = null, curr = head;
		
		while(curr!=null)
		{
			if(comparator.compare(targetData,curr.data)==0) //when the element matches the target
			{
				if(curr==head) // when the target element is at the first position
				{
					head = head.next;
					size--;
				}
				
				else if(curr==tail) // when the target element is at the last position
				{
					tail = prev;
					size--;
				}
				
				else // the target is neither at the first nor last position
				{
					prev.next = curr.next;
					size--;
				}		
			}
			else //element doesn't match the target, then go to the next element
				prev = curr;
			
			curr = curr.next; // iterate
		}
			return this;
	}
	//Removes and returns the first element from the list.
	public T retrieveFirstElement()
	{
		T temp = null;
		
		if(head != null)
		{
			temp = head.data;
			head = head.next;
			size--;
			
			if(size==0)
				head = tail = null;
		}
		return temp;	
	}
	//Removes and returns the last element from the list.
	public T retrieveLastElement() //use the tail reference
	{
		 T temp = null;
		 Node prev = null, curr = head;
		
		 if(tail != null)
		 {
			 while(curr != null)
			 {
				 if(curr == tail)
				 {
					 temp = curr.data;
					 tail = prev;
					 curr = null;
					 size--;
					 
					 if(size==0)
						 head = tail = null;
				 }
				 else
				 {
					 prev = curr;
					 curr = curr.next;
				 }
			 }
		 }
		 return temp;
	}

	public String toString()
	{
		String answer="";
		
		for(T a:this)
			answer += a + " ";
		return answer;
	}
}
