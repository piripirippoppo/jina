package listClasses;

import java.util.Comparator;

public class SortedLinkedList<T> extends BasicLinkedList<T>
{
	Comparator<T> comparator;
	//Creates an empty list that is associated with the specified comparator. 
	//It must call the super class constructor to initialize the appropriate variables.
	public SortedLinkedList(Comparator<T> comparator)
	{
		super();
		this.comparator = comparator;
	}
	//Inserts the specified element at the correct position in the sorted list.
	public SortedLinkedList<T> add(T element)
	{	
		if(element==null) return this;
		
		Node curr=head, prev=null;
		Node newNode = new Node(element);
		
		if(curr==null) //empty list
		{
			newNode.next = head;
			head = tail = newNode;
			size++;
		}
		else // at least one element in the list
		{
			if(comparator.compare(element, head.data)<=0) //new element comes before the head
			{
				newNode.next = head;
				head = newNode;
				size++;
			}
			else if(comparator.compare(element, tail.data)>=0) //new element comes to the tail
			{
				tail.next = newNode; //never null
				tail = newNode;
				size++;
			}
			else //new element comes after the head
			{
				while(comparator.compare(element, curr.data)>0) // new element comes in the middle
				{
					prev = curr;
					curr = curr.next;
				}	
					newNode.next = curr;
					prev.next = newNode;
					size++;
			}
		}
		return this;
	}
	/*This operation is invalid for a sorted list.
	This operation is invalid for a sorted list. 
	An UnsupportedOperationException will be generated using the message "Invalid operation for sorted list."*/
	@Override
	public BasicLinkedList<T> addToEnd(T data)
	{
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}
	//This operation is invalid for a sorted list.
	public BasicLinkedList<T> addToFront(T data)
	{
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}
	//Implements the remove operation by calling the super class remove method.
	public SortedLinkedList<T> remove(T targetData)
	{
		return (SortedLinkedList<T>) super.remove(targetData, comparator);
	}
}
