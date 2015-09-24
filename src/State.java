import java.awt.Color;

public class State 
{
	public final double x;
	public final double y;
	public final double orientation;
	public final Color penColor;
	
	public State(double x, double y, double orientation, Color penColor)
	{
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.penColor = penColor;
	}
}
