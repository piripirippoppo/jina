package model;

import java.util.ArrayList;

public class ListElement extends TagElement
{
	String liGen="";
	String indent="";
	boolean lineOrder;
	ArrayList<Element> lElement = new ArrayList<Element>(); 
	
	public ListElement(boolean ordered, String attributes)
	{
		super((ordered==false)?"ul":"ol", true, null, attributes);
		lineOrder=ordered;
	}
	//Adds a list item to the end of the list.
	public void addItem(Element item)
	{
		lElement.add(item);	
	}
	@Override
	public String genHTML(int indentation)
	{	
			indent = Utilities.indentation(indentation);
		
		for(int i=0; i<lElement.size(); i++) 
			if(lineOrder==false)
				liGen += indent+indent+"<li>\n"+indent+indent+lElement.get(i).genHTML(indentation) + "\n" + indent+indent+"</li>\n";		
				
			else
				liGen += indent+"   "+"<li>\n"+indent+lElement.get(i).genHTML(indentation) + "\n" +indent+"   "+"</li>\n";
		
		return indent+super.getStartTag()+"\n"+liGen+indent+super.getEndTag();
	}
}
