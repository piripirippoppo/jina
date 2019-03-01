package model;

public class TextElement implements Element{
	
	String tText, indent;
	
	public TextElement(String text)
	{
		tText=text;
		indent="";
	}
	//Returns a string that represents the HTML associated with the element.
	public String genHTML(int indentation)
	{
		for(int i=0; i<indentation; i++)
			indent += " ";
		
		return indent+tText;
	}
}
