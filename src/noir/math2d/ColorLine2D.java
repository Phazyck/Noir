package noir.math2d;

import java.awt.Color;

public class ColorLine2D extends Line2D 
{
	public final Color color;
	
	public ColorLine2D(double x1, double y1, double x2, double y2, Color color)
	{
		super(x1, y1, x2, y2);
		this.color = color;
	}
	
	public ColorLine2D(Vector2D v1, Vector2D v2, Color color)
	{
		super(v1,v2);
		this.color = color;
	}
}
