package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Represents a web page. 
 * Web page elements are stored in an ArrayList of Element objects. 
 * A title is associated with every page. 
 * This class implements the Comparable interface. 
 * Pages will be compared based on the title.
 */
public class WebPage implements Comparable<WebPage>
{	
	String webTitle, webStatus="", webCode="", webList="";

	ArrayList<Element> webElement;
	
	static boolean webChoice;
	//Initializes the object with the specified title and creates the ArrayList.
	public WebPage(String title)
	{
		webTitle=title;
		webElement = new ArrayList<Element>();
	}
	
	//Adds an element to the page by adding the element to the end of the ArrayList.
	//-1 if the element is not a TagElement; otherwise the id associated with the element.
	public int addElement(Element element)
	{
		webElement.add(element);
		
		if(!(element instanceof TagElement))
				return -1;
		
		return	((TagElement)element).getId();	
	}
	
	public int compareTo(WebPage webPage)
	{
		return(this.webTitle.compareTo(webPage.webTitle));
	}
	//Enables the ids associated with tag elements.
	static void enableId(boolean choice)
	{
		webChoice=choice;
	}
	//Returns a reference to a particular element based on the id
	//reference to element if found and null otherwise.
	Element findElem(int id)
	{
		for(int i=0; i<webElement.size(); i++)
		{
			if(id==((TagElement)webElement.get(i)).getId())
				return webElement.get(i);
		}
		return null;
	}
	
	public String getWebPageHTML(int indentation)
	{
		String space=Utilities.indentation(indentation);
		
		for(int i=0; i<webElement.size(); i++)
			webList += webElement.get(i).genHTML(indentation)+"\n";
		
		webCode = "<!doctype html>\n" + "<html>\n" +space+ "<head lang=\"en\">\n" +space+space+ 
				"<meta charset=\"utf-8\"/>\n"+space+space+"<title>"+webTitle+"</title>\n"+space+"</head>\n"+
				space+"<body>\n"+webList+"   "+"</body>\n"+"</html>";
		return webCode;
	}

	//Returns information about the number of lists, paragraphs, and tables present in the page. Also, 
	//it provide table utilization information. See public tests for format.
	public String stats()
	{ /**
		List Count: 1
		Paragraph Count: 0
		Table Count: 2
		TableElement Utilization: 75.0
	 	*/
		int numOfList=0, numOfPara=0, numOfTable=0;
		double tableUtil=0.0;
		for(int i=0; i<webElement.size(); i++)
		{
			if(webElement.get(i) instanceof ListElement)
				numOfList++;
			else if(webElement.get(i) instanceof ParagraphElement)
				numOfPara++;
			else if(webElement.get(i) instanceof TableElement)
			{
				tableUtil += ((TableElement)webElement.get(i)).getTableUtilization();
				numOfTable++;
			}
		}					
		webStatus += "List Count: "+numOfList+
				  "\nParagraph Count: "+numOfPara+
				  "\n"+"Table Count: "+numOfTable+ 
				  "\n"+ "TableElement Utilization: "+tableUtil/numOfTable;
		return webStatus;
	}
	//Writes to the specified file the web page page using the provided indentation.
	public void writeToFile(String filename, int indentation)
	{
		Utilities webFile = new Utilities();
		webFile.writeToFile(filename, getWebPageHTML(indentation));
	}
}
