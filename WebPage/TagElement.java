package model;

public class TagElement implements Element 
{
	String tName, tagAttributes, endingTag, strId, startTag;
	boolean finishTag;
	static boolean myChoice=false;
	Element tagContent;
	static int id=1;
	int tagLevel=1, myId=1;
	
	/**
	 * Represents an HTML tag element (<<p>, <ul>, etc.). 
	 * Each tag has an id (ids start at 1). 
	 *  
	 * when the HTML for the tag is generated. 
	 * This can be disabled by using enableId.
	 */
	public TagElement(String tagName, boolean endTag, Element content, String attributes)
	{
		tName=tagName;
		finishTag=endTag;
		tagContent=content;
		//tagAttributes=Utilities.getAttributes(attributes);
		tagAttributes=attributes;
		strId="id=";
		myId=id++;
	}
	
	public String genHTML(int indentation)
	{	 
		return Utilities.indentation(indentation) + getStartTag() + tagContent.genHTML(0) + getEndTag();
	}
	
	public static void enableId(boolean choice)
	{
		myChoice=choice;
	}
	public String getEndTag()
	{
		if(finishTag==true)
			endingTag="</"+tName+">";
		else
			endingTag="<"+tName+">\n";
		return endingTag;
	}
	public int getId()
	{
		return id;
	}
	//By default the start tag will have an id (e.g., <<p id="a1"></p>)
	public String getStartTag()
	{ //can be four different cases. 
		if(myChoice==true) //id==true
		{
			if(tagAttributes==null) //id==true, no attributes
			return startTag = "<" +tName+ " " + strId +"\""+tName+myId+"\""+Utilities.getAttributes(tagAttributes)+">";
			else //id==true, yes attributes
			return startTag = "<" +tName+ " " + strId +"\""+tName+myId+"\" "+Utilities.getAttributes(tagAttributes)+">";
		}
		else //id==false
		{
			if(tagAttributes==null) //id==false, no attributes
				return startTag="<"+tName+Utilities.getAttributes(tagAttributes)+">";
		}  ////id==false, yes attributes
			return startTag="<"+tName+" "+Utilities.getAttributes(tagAttributes)+">";
	}
	public String getStringId()
	{
		return strId;
	}
	public static void resetIds()
	{
		id=1;
	}
	public void setAttributes(String attributes)
	{
		tagAttributes=attributes;
	}
}
