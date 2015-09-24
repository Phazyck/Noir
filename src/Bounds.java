import java.awt.Color;
import java.awt.Graphics;

import noir.math2d.Line2D;

public class Bounds 
{
	double scale;
	double padX;
	double padY;
	double minDrawX;
	double minDrawY; 
	
	public Bounds(
			int padding,
			int screenWidth, 
			int screenHeight,
			Scalar scalarX,
			Scalar scalarY
		)
	{
		double drawWidth = scalarX.getSpan();
		double drawHeight = scalarY.getSpan();
		minDrawX = scalarX.getMinimum();
		minDrawY = scalarY.getMinimum();
		double width = screenWidth - 2*padding;
		double height = screenHeight - 2*padding;
		
		scale = Math.min(width / drawWidth, height / drawHeight);
		
		padX = ((double)padding) - ((scale * drawWidth) - width) / 2.0;
		padY = ((double)padding) - ((scale * drawHeight) - height) / 2.0;
	}
	
	public void drawLine(Graphics g, Line2D line, Color color)
	{
		g.setColor(color);
		int x1 = (int)(padX + (line.x1 - minDrawX) * scale);
		int y1 = (int)(padY + (line.y1 - minDrawY) * scale);
		int x2 = (int)(padX + (line.x2 - minDrawX) * scale);
		int y2 = (int)(padY + (line.y2 - minDrawY) * scale);
		g.drawLine(x1, y1, x2, y2);
	}

}
