package noir.math2d;

public class Vector2D 
{
	public final double x;
	public final double y;
	public final static Vector2D ZERO = new Vector2D(0,0); 
	
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2D add(Vector2D v)
	{
		Vector2D result = new Vector2D(
			this.x + v.x,
			this.y + v.y
		);
				
		return(result);
	}
	
	public Vector2D scale(double s)
	{
		Vector2D result = new Vector2D(
			s * this.x,
			s * this.y
		);
					
		return(result);
	}
}
