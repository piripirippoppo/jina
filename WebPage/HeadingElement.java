package model;

public class HeadingElement extends TagElement
{
	//Level can assume values from 1 up to including 6.
	//Includes the content as part of the heading.
	public HeadingElement(Element content, int level, String attributes)
	{	
		super("h"+level, true, content, attributes);
	}
}
