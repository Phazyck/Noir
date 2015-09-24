package noir.graphics;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import noir.math2d.Rectangle2D;

public abstract class Drawable
{
	protected Panel panel;
	protected boolean yIsUp = false;
	protected double padding = 0.0;
	protected boolean centerHorizontally = true;
	protected boolean centerVertically = true;
	protected Color backgroundColor = Color.WHITE;
	protected long totalTimeMillis;
	protected KeyListener keyListener = null;
	protected MouseListener mouseListener = null;
	
	public void setPanel(Panel panel)
	{
		this.panel = panel;
	}
	
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}
	
	public void redraw()
	{
		panel.repaint();
	}
	
	public void update(long elapsedTimeMillis)
	{
		totalTimeMillis += elapsedTimeMillis;
	}
	
	public KeyListener getKeyListener()
	{
		return keyListener;
	}
	
	public abstract void draw(GraphicsBatch graphicsBatch);
	public abstract Rectangle2D getViewport();
	
	public boolean isYUp()
	{
		return yIsUp;
	}
	
	public double getPadding()
	{
		return padding;
	}
	
	public boolean centerHorizontally()
	{
		return centerHorizontally;
	}
	
	public boolean centerVertically()
	{
		return centerVertically;
	}

	public MouseListener getMouseListener() {
		return mouseListener;
	}
}