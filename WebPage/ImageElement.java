package model;

public class ImageElement extends TagElement
{
	String imageUrl;
	/**Represents an <img> tag. For this project you can 
	*  assume we will not update any of the attributes associated with this tag.
	*/
	public ImageElement(String imageURL, int width, int height, String alt, String attributes)
	{
		super("img", false, null, "src=\"" + imageURL + "\" " +
				"width=\"" + width + "\" height=\"" + height + "\" alt=\"" + alt +"\""+
														Utilities.getAttributes(attributes));
		imageUrl=imageURL;
	}

	@Override
	public String genHTML(int indentation)
	{
		return Utilities.indentation(indentation) + super.getStartTag();
	}
	public String getImageURL()
	{
		return imageUrl;
	}
}
