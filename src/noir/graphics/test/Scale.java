package noir.graphics.test;

import java.awt.Color;

import noir.graphics.Drawable;
import noir.graphics.GraphicsBatch;
import noir.graphics.Panel;
import noir.math2d.Rectangle2D;
import noir.math2d.Vector2D;
import noir.mathf.MathF;

public class Scale extends Drawable
{
	public static void main(String[] args)
	{
		new Panel(
			null, 
			800, 
			600, 
			new Scale(), 
			60.0
		);
	}
	
	private double progress = 0.0;
	private double speed = 0.002;
	private double size;
	private Rectangle2D worldBounds;
	private Color color;
	
	public Scale()
	{
		super.yIsUp = true;
		Vector2D origin = new Vector2D(-4.0, -3.0);
		Vector2D dimensions = new Vector2D(8.0, 6.0);
		worldBounds = new Rectangle2D(origin, dimensions);
	}
	
	private void updateColor()
	{
		double r = Math.sin(progress+Math.PI*2*(1.0/3.0));
		double g = Math.sin(progress+Math.PI*2*(2.0/3.0));
		double b = Math.sin(progress+Math.PI*2*(3.0/3.0));
				
		float R = MathF.clamp01((float)r);
		float G = MathF.clamp01((float)g);
		float B = MathF.clamp01((float)b);
		color = new Color(R,G,B);
	}

	@Override
	public void update(long elapsedTimeMillis) 
	{
		double elapsed = elapsedTimeMillis;
		progress += elapsed*speed;
		size = (Math.sin(progress)+1)*0.5;
		padding = (-Math.sin(progress)+1)*32.0;
		
		String title = String.format("padding = %.2f", padding);
		panel.setFrameTitle(title);
		
		updateColor();
		
		panel.setBackground(Color.BLACK);
	}

	@Override
	public void draw(GraphicsBatch graphicsBatch) 
	{
		graphicsBatch.clear(Color.WHITE);
		
		double x1 = worldBounds.getMinX();
		double y1 = worldBounds.getMinY();
		double x2 = worldBounds.getMaxX();
		double y2 = worldBounds.getMaxY();
		
		// Boxes
		for(double x = x1; x < x2; ++x)
		{
			graphicsBatch.fillRect(new Vector2D(x,y1), new Vector2D(x+1,y1+1), color);
		}
		
		for(double y = y1; y < y2; ++y)
		{
			graphicsBatch.fillRect(new Vector2D(x1,y), new Vector2D(x1+1,y+1), color);
		}
		
		// Lines
		for(double x = x1; x <= x2; ++x)
		{
			graphicsBatch.drawLine(new Vector2D(x,y1), new Vector2D(x,y2), Color.BLACK);
		}
		
		for(double y = y1; y <= y2; ++y)
		{
			graphicsBatch.drawLine(new Vector2D(x1,y), new Vector2D(x2,y), Color.BLACK);
		}
		
		// Center box
		graphicsBatch.fillRect(new Vector2D(size,size), new Vector2D(-size,-size), color);
		
		{
			Vector2D TL = new Vector2D(-size,-size);
			Vector2D BL = new Vector2D(-size,+size);
			Vector2D TR = new Vector2D(+size,-size);
			Vector2D BR = new Vector2D(+size,+size);
			Color black = Color.BLACK;
			
			// Top side
			graphicsBatch.drawLine(TL, TR, black);
			// Bottom side
			graphicsBatch.drawLine(BL, BR, black);
			// Left side
			graphicsBatch.drawLine(BL, TL, black);
			// Right side
			graphicsBatch.drawLine(BR, TR, black);
			
			// Across
			graphicsBatch.drawLine(BL, TR, black);
			graphicsBatch.drawLine(BR, TL, black);
		}
	}
		
	@Override
	public Rectangle2D getViewport() {
		return worldBounds;
	}
}
