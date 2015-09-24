package noir.math2d;
public class Line2D 
{
	public final double x1;
	public final double y1;
	public final double x2;
	public final double y2;
	
	public Line2D(double x1, double y1, double x2, double y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Line2D(Vector2D v1, Vector2D v2)
	{
		this(v1.x, v1.y, v2.x, v2.y);
	}
}
