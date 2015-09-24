package noir.turtle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.Timer;

import noir.graphics.Drawable;
import noir.graphics.GraphicsBatch;
import noir.graphics.Panel;
import noir.lsystem.LSystem;
import noir.math2d.ColorLine2D;
import noir.math2d.Rectangle2D;
import noir.math2d.Vector2D;

public class Turtle extends Drawable implements KeyListener
{
	public static void main(String[] args)
	{
		LSystem lSystem = noir.lsystem.Examples.sierpinski(false);
		String string = lSystem.getString(6);
		char[] characters = string.toCharArray(); 
		
		Turtle turtle = new Turtle(lSystem.angle, StartingDirection.LEFT);
		new Panel(
			lSystem.title, 
			800, 600, 
			turtle
		);
		
		double targetTime = 2000;
		double runs = characters.length;
		double instructionsPrRun = 1;
		while(runs > targetTime)
		{
			++instructionsPrRun;
			runs = characters.length / instructionsPrRun;
		}
		
		final int instructionsPerRun = (int)instructionsPrRun;
		
		int delay = (int)(targetTime / runs);
		
		if(delay > 100)
		{
			delay = 100;
		}
		
		
		Timer timer = new Timer(delay, null);
		
		timer.setRepeats(true);
		
		timer.addActionListener(new ActionListener() {
			
			int i = 0;
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int j = 0; j < instructionsPerRun; ++j)
				{
					char c = characters[i];
					turtle.feed(c);
					++i;
					
					if(i >= characters.length)
					{
						break;
					}
				}
				turtle.redraw();
				
				if(i >= characters.length)
				{
					if(timer != null)
					{
						timer.stop();
					}
				}
			}
		});
		
		timer.start();
	}
	
	private double xMin, xMax, yMin, yMax;
	private double worldPaddingPct;
	
	private State state;
	private double turningAngle;
	private LinkedList<ColorLine2D> lines;
	
	public Turtle(double turningAngle)
	{
		this(turningAngle, false, StartingDirection.UP);
	}
	
	public Turtle(double turningAngle, StartingDirection startingDirection)
	{
		this(turningAngle, false, startingDirection);
	}
	
	private double getOrientation(StartingDirection startingDirection)
	{
		switch(startingDirection)
		{
			case DOWN  : return Math.PI * 0.5;
			case LEFT  : return Math.PI * 1.0;
			case UP    : return Math.PI * 1.5;
			case RIGHT : return Math.PI * 2.0;
			default    : return 0.0;
		}		
	}
	
	public Turtle(double turningAngle, boolean interactive, StartingDirection startingDirection)
	{
		super.backgroundColor = Color.GRAY;
		super.padding = 0;
		xMin = yMin = xMax = yMax = 0;
		worldPaddingPct = 0.1;
		
		double orientation = getOrientation(startingDirection);
				
		state = new State(Vector2D.ZERO, orientation);
		this.turningAngle = turningAngle;
		lines = new LinkedList<ColorLine2D>();
		
		if(interactive)
		{
			super.keyListener = this;
		}
	}
	
	private void move(boolean draw)
	{
		Vector2D a = state.position;
		state = state.move();
		Vector2D b = state.position;
		
		if(draw)
		{
			lines.add(new ColorLine2D(a,b, state.color));
		}
		
		double x = b.x;
		
		if(x < xMin)
		{
			xMin = x;
		}
		
		if(x > xMax)
		{
			xMax = x;
		}
		
		double y = b.y;
		
		if(y < yMin)
		{
			yMin = y;
		}
		
		if(y > yMax)
		{
			yMax = y;
		} 
	}
	
	private void turnLeft()
	{
		state = state.turnDegrees(-turningAngle);
	}
	
	private void turnRight()
	{
		state = state.turnDegrees(turningAngle);
	}
	
	public void feed(char c)
	{
		switch(c)
		{
			case 'F':
			{
				move(true);
			} break;
			case 'f':
			{
				move(false);
			} break;
			case '+':
			{
				turnRight();
			} break;
			case '-':
			{
				turnLeft();
			} break;
			case '[':
			{
				state = state.push();
			} break;
			case ']':
			{
				state = state.pop();
			} break;
			case 'R':
			{
				state = state.color(Color.RED);
			} break;
			case 'G':
			{
				state = state.color(Color.GREEN);
			} break;
			case 'B':
			{
				state = state.color(Color.BLUE);
			} break;
			case 'Y':
			{
				state = state.color(Color.YELLOW);
			} break;
			case 'C':
			{
				state = state.color(Color.CYAN);
			} break;
			case 'M':
			{
				state = state.color(Color.MAGENTA);
			} break;
			default:
			{
				String message = String.format("Unknown character: %c", c);
				throw new RuntimeException(message);
			}
		}
	}
	
	public void feed(char[] cs)
	{
		for(char c : cs)
		{
			feed(c);
		}
	}
	
	public void feed(String s)
	{
		feed(s.toCharArray());
	}

	@Override
	public void draw(GraphicsBatch graphicsBatch) {
		graphicsBatch.clear(Color.WHITE);

		for(ColorLine2D line : lines)
		{
			graphicsBatch.drawLine(line.x1, line.y1, line.x2, line.y2, line.color);
		}
		
		
		Rectangle2D bounds = getViewport();
		
		double s = Math.max(bounds.width, bounds.height)*0.02;
		
		double r = state.orientation;
		
		
		double x = state.position.x + Math.cos(r)*s;
		
		double y = state.position.y + Math.sin(r)*s;
		
		double d = Math.PI * 0.8;
		
		double xa = x + Math.cos(r+d)*s;
		double ya = y + Math.sin(r+d)*s;
		double xb = x + Math.cos(r-d)*s;
		double yb = y + Math.sin(r-d)*s;
		
		graphicsBatch.drawLine(x, y, xa, ya, state.color);
		graphicsBatch.drawLine(x, y, xb, yb, state.color);
		
		
		
	}
	
	private double getWorldPadding()
	{
		double w = xMax-xMin;
		double h = yMax-yMin;
		double s = Math.max(w, h);
		return worldPaddingPct * s;
	}

	@Override
	public Rectangle2D getViewport() {
		
		double wp = getWorldPadding();
		double x = xMin - wp;
		double y = yMin - wp;
		double width = (xMax - xMin) + 2 * wp;
		double height = (yMax - yMin) + 2 * wp;
		
		Rectangle2D result = new Rectangle2D(x, y, width, height);
		return(result);
	}

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(c == KeyEvent.CHAR_UNDEFINED)
		{
			return;
		}
		try
		{
			feed(c);
		} 
		catch(Exception exn)
		{
			System.out.println(exn.getMessage());
		}
		
		redraw();		
	}

	@Override
	public void keyTyped(KeyEvent e) { }
}