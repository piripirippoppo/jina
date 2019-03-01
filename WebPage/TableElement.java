package model;

public class TableElement extends TagElement{

	String tableList, getTable, trTag;
	Element[][] tableElement;
	double tableUtil, totalCells=0, numOfCellinUse=0;
	
	//Defines the array and initializes the attributes.
	public TableElement(int rows, int cols, String attributes)
	{
		super("table", true, null, attributes);
		tableElement = new Element[rows][cols];	
		getTable="";
		trTag="";
	}
	
	public void addItem(int rowIndex, int colIndex, Element item)
	{	
		tableElement[rowIndex][colIndex]=item;
	}
	@Override
	public String genHTML(int indentation)
	{		
		String indent = Utilities.indentation(indentation);
		
		for(int i=0; i<tableElement.length; i++)
		{
			for(int j=0; j<tableElement[i].length; j++)
			{					
				if(tableElement[i][j]!=null)
				{
					tableList = tableElement[i][j].genHTML(0);
					getTable += "<td>"+ tableList + "</td>";
				}
				else
					getTable += "<td>" + "</td>";
			}
				trTag += indent+indent+"<tr>" + getTable + "</tr>\n";
				getTable="";
		}
		return indent + super.getStartTag() + "\n" + 
			trTag + indent+super.getEndTag();
	}
	
	/*Returns the percentage of table cells currently in used (those storing references to objects).*/
	public double getTableUtilization()
	{		
		for(int i=0; i<tableElement.length; i++)
			for(int j=0; j<tableElement[i].length; j++)
			{
				totalCells++;
				if(tableElement[i][j]!=null)
					numOfCellinUse++;
			}	
		tableUtil = (numOfCellinUse/totalCells)*100;
		
		return tableUtil;
	}
}
