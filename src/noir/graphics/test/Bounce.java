package noir.graphics.test;

import java.awt.Color;

import noir.graphics.Drawable;
import noir.graphics.GraphicsBatch;
import noir.graphics.Panel;
import noir.math2d.Rectangle2D;
import noir.mathf.MathF;

public class Bounce extends Drawable
{
	public static void main(String[] args)
	{
		new Panel(
			null, 
			800, 
			600, 
			new Bounce(), 
			60.0
		);
	}
	
    private double x = 0;
    private double y = 0;
    
    private double dx = 0.5;
    private double dy = 0.5;
    
    private double w = 64;
    private double h = 64;
    
    private Color color;
    private Color invert;
    
    private float progress = 0.0f;
    private final float speed = 0.0015f;
	
	@Override
	public Rectangle2D getViewport() 
	{
		return new Rectangle2D(panel.getBounds());
	}
	
	private void updateColor(double elapsed)
	{
		progress += elapsed*speed;
		
		double r = Math.sin(progress+Math.PI*2*(1.0/3.0));
		double g = Math.sin(progress+Math.PI*2*(2.0/3.0));
		double b = Math.sin(progress+Math.PI*2*(3.0/3.0));
				
		float R = MathF.clamp01((float)r);
		float G = MathF.clamp01((float)g);
		float B = MathF.clamp01((float)b);
		color = new Color(R,G,B);
		invert = new Color(1f-R, 1f-G, 1f-B);
	}
	
	private void updatePosition(double elapsed)
	{
		x += dx * elapsed;
    	y += dy * elapsed;
    	
    	
    	if(x < 0)
    	{
    		x = -x;
    		dx = -dx;
    	}
    	
    	if(y < 0)
    	{
    		y = -y;
    		dy = -dy;
    	}
    	
    	if(x + w > getViewport().width)
    	{
    		x -= (x + w) - getViewport().width;
    		dx = -dx;
    	}
    	
    	if(y + h > getViewport().height)
    	{
    		y -= (y + h) - getViewport().height;
    		dy = -dy;
    	}
	}
	
	private void updateTitle()
	{
    	Rectangle2D bounds = getViewport();
    	String s = String.format("x: %4.0f, y: %4.0f, w: %4.0f, h: %4.0f", 
    			x, 
    			y, 
    			bounds.width, 
    			bounds.height);
    	
    	panel.setFrameTitle(s);
	}
	
	@Override
	public void update(long elapsedTimeMillis) {
		double elapsed = elapsedTimeMillis;
		
		updateColor(elapsed);
    	updatePosition(elapsed);
		updateTitle();
	}
	
	@Override
	public void draw(GraphicsBatch graphicsBatch) {

		graphicsBatch.clear(Color.black);
		
		double t = 4;
		graphicsBatch.fillRect(new Rectangle2D(x-t,y-t,w+2*t,h+2*t), invert);
		graphicsBatch.fillRect(new Rectangle2D(x,y,w,h), color);
	}
}