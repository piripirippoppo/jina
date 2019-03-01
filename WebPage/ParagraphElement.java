package model;

import java.util.ArrayList;

public class ParagraphElement extends TagElement
{
	ArrayList<Element> paraElement; 
	String paraGen="", paraAttributes;
	
	public ParagraphElement(String attributes)
	{
		super("p", true, null, attributes);
		paraElement = new ArrayList<Element>();
		paraAttributes=attributes;
	}
	
	public static void enableId(boolean choice)
	{
		myChoice=choice;
	}

	public void addItem(Element item) 
	{//Adds an element to the paragraph.
		paraElement.add(item);
	}
	@Override
	public String genHTML(int indentation)
	{	
		String indent = Utilities.indentation(indentation);
		if(paraAttributes==null)
		{
			for(int i=0; i<paraElement.size(); i++)
				paraGen += "\n" + indent+indent+paraElement.get(i).genHTML(0);
		}
		else
			for(int i=0; i<paraElement.size(); i++)
				paraGen += "\n"+indent+"   "+paraElement.get(i).genHTML(0);
		return indent+super.getStartTag()+ paraGen + "\n"+indent+super.getEndTag();
		
	}
}
