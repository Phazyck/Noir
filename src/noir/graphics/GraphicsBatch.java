package noir.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;

import noir.math2d.Rectangle2D;
import noir.math2d.Vector2D;

public class GraphicsBatch implements AutoCloseable
{
    private Graphics2D graphics;
	private Rectangle2D viewport;
	
	private double xOffset;
	private double xScale;
	
	private double yOffset;
	private double yScale;
	
	public Graphics2D getGraphics2D()
	{
		return graphics;
	}
	
	public GraphicsBatch(Graphics2D graphics, Rectangle screen, Drawable provider)
	{
		this.graphics = graphics;
		this.viewport = provider.getViewport();
		
		double padding = provider.getPadding();
		
		double worldW = viewport.getWidth();
		double outerW = screen.getWidth();
		double innerW = outerW - 2*padding;
		
		double worldH = viewport.getHeight();
		double outerH = screen.getHeight();
		double innerH = outerH - 2*padding;
		
		double scale = 
			Math.min(
				innerW / worldW, 
				innerH / worldH
			);
		
		xOffset = padding - scale * viewport.getX();
		
		if(provider.centerHorizontally())
		{
			xOffset -= (scale * worldW - innerW) / 2.0;
		}
		
		yOffset = padding - scale * viewport.getY();
		
		if(provider.centerVertically())
		{
			yOffset -= (scale * worldH - innerH) / 2.0;
		}
		
		xScale = yScale = scale;
		
		if(provider.isYUp())
		{
			yOffset = screen.height - yOffset;
			yScale *= -1;
		}
	}

	@Override
	public void close() throws Exception 
	{
		graphics.dispose();
        Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawLineTransformed(int x1, int y1, int x2, int y2, Color color)
	{
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.drawLine(x1, y1, x2, y2);
		graphics.setColor(c);
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, Color color)
	{
		drawLineTransformed(
			transformX(x1),
			transformY(y1),
			transformX(x2),
			transformY(y2),
			color
		);
	}
	
	public void drawLine(Vector2D v1, Vector2D v2, Color color)
	{
		drawLine(v1.x, v1.y, v2.x, v2.y, color);
	}
	
	private int transformX(double x)
	{
		double result = xOffset + x * xScale;
		return (int)result;
	}
	
	private int transformY(double y)
	{
		double result = yOffset + y * yScale;

		return (int)result;
	}
	
	private void fillRectTransformed(int x1, int y1, int x2, int y2, Color color)
	{
		int x = x2;
		int width = x1 - x2;
		
		if(x1 < x2)
		{
			x = x1;
			width *= -1;
		}
		
		int y = y2;
		int height = y1 - y2;
		if(y1 < y2)
		{
			y = y1;
			height *= -1;
		}
		
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.fillRect(x, y, width, height);
		graphics.setColor(c);
	}
	
	public void fillRect(double x1, double y1, double x2, double y2, Color color)
	{
		fillRectTransformed(
			transformX(x1),
			transformY(y1),
			transformX(x2),
			transformY(y2),
			color
		);
	}
	
	public void fillRect(Vector2D v1, Vector2D v2, Color color)
	{
		fillRect(v1.x, v1.y, v2.x, v2.y, color);
	}
	
	public void fillRect(Rectangle2D rectangle, Color color)
	{
		fillRect(
			rectangle.getMinX(),
			rectangle.getMinY(),
			rectangle.getMaxX(),
			rectangle.getMaxY(),
			color
		);
	}
    
    public void clear(Color color)
    {
    	fillRect(viewport, color);
    }
}