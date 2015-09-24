package noir.turtle;

import java.awt.Color;

import noir.math2d.Vector2D;

public class State 
{
	public final Vector2D position;
	public final double orientation;
	public final Color color;
	public final State previous;

	public State(Vector2D position, double orientation, Color color, State previous)
	{
		this.position = position;
		this.orientation = orientation;
		this.color = color;
		this.previous = previous;
	}
	
	public State(Vector2D position, double orientation, Color color)
	{
		this(position, orientation, color, null);
	}
	
	public State(Vector2D position, double orientation)
	{
		this(position, orientation, Color.BLACK);
	}
	
	public State()
	{
		this(Vector2D.ZERO, 1.5*Math.PI);
	}
	
	public State move()
	{
		double x = position.x + Math.cos(orientation);
		double y = position.y + Math.sin(orientation);
		State result = new State(new Vector2D(x,y), orientation, color, previous);
		return(result);
	}
	
	public State turnRadians(double radians)
	{
		State result = new State(position, orientation+radians, color, previous);
		return(result);
	}
	
	public State turnDegrees(double degrees)
	{
		double radians = Math.toRadians(degrees);
		return(turnRadians(radians));
	}
	
	public State push()
	{
		State result = new State(position, orientation, color, this);
		return(result);
	}
	
	public State color(Color color)
	{
		State result = new State(position, orientation, color, previous);
		return(result);
	}
	
	public State pop()
	{
		if(previous != null)
		{
			return previous;
		}
		else
		{
			throw new RuntimeException("No previous state to pop back to.");
		}
	}
}
