package noir.math2d;

import java.awt.Rectangle;

public class Rectangle2D 
{
	public final double x;
	public final double y;
	public final double width;
	public final double height;
	
	public Rectangle2D(Vector2D origin, Vector2D dimensions)
	{
		this(
			origin.x, 
			origin.y, 
			dimensions.x, 
			dimensions.y
		);
	}
	
	public Rectangle2D(Rectangle awtRectangle)
	{
		this(
			awtRectangle.getX(), 
			awtRectangle.getY(), 
			awtRectangle.getWidth(), 
			awtRectangle.getHeight()
		);
	}
	
	public Rectangle2D(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getMinX()
	{
		return x;
	}
	
	public double getMaxX()
	{
		return x + width;
	}
	
	public double getMinY()
	{
		return y;
	}
	
	public double getMaxY()
	{
		return y + height;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
}
