package model;

public class AnchorElement extends TagElement
{
	String anchorUrl, anchorLinkText;
		
	public AnchorElement(String url, String linkText, String attributes)
	{
		super("a", true, null, "href=\""+url+"\""+Utilities.getAttributes(attributes));
		anchorLinkText=linkText;
		anchorUrl=url;
	}
	public String getLinkText()
	{
		return anchorLinkText;
	}
	public String getUrlText()
	{
		return anchorUrl;
	}
	@Override
	public String genHTML(int indentation)
	{				
		return Utilities.indentation(indentation) + super.getStartTag() + anchorLinkText + super.getEndTag();
	}
}
