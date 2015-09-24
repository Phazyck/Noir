package noir.conway;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import noir.graphics.Drawable;
import noir.graphics.GraphicsBatch;
import noir.graphics.Panel;
import noir.math2d.Rectangle2D;

public class GameOfLife extends Drawable implements MouseListener {
	
	Random random = new Random();
	
	private double shakeX;
	private double shakeY;

	public static void main(String[] args)
	{
		new Panel("Conway's Game of Life", 800, 600, new GameOfLife(), 60.0);
	}
	
	private boolean[][] cells;
	private boolean[][] previous;
	private double tickDuration = 1000;
	private long tickProgress = 0;
	private boolean wrapping = true;
	
	private double shakeDurationPct = 0.2;
	private long shakeProgress = 0;
	private double shakeStrength = 0.5;
	private double worldPadding = 1.0;
	
	private boolean getCell(int row, int column)
	{
		int rows = getRows();
		
		if(row < 0)
		{
			if(wrapping)
			{
				row += rows;
			}
			else
			{
				return false;
			}
		}
		
		if(row >= rows)
		{
			if(wrapping)
			{
				row -= rows;
			}
			else
			{
				return false;
			}
		}

		int columns = getColumns();
		
		if(column < 0)
		{
			if(wrapping)
			{
				column += getColumns();
			}
			else
			{
				return false;
			}
		}
		
		if(column >= columns)
		{
			if(wrapping)
			{
				column -= columns;
			}
			else
			{
				return false;
			}
		}
		
		return(cells[row][column]);
	}
	
	private int liveNeighbors(int row, int column)
	{
		int result = 0;
		
		for(int dRow = -1; dRow <= 1; ++dRow)
		{
			for(int dColumn = -1; dColumn <= 1; ++dColumn)
			{
				if((dRow == 0) && (dColumn == 0))
				{
					continue;
				}
				
				boolean live = getCell(row+dRow, column+dColumn);
				if(live)
				{
					++result;
				}
			}
		}
		
		return(result);
	}
	
	public GameOfLife()
	{
		super.backgroundColor = Color.BLACK;
		super.mouseListener = this;
		int rows = 12;
		int columns = 16;
		cells = new boolean[rows][columns];
		previous = new boolean[rows][columns];
		
		cells[3][3] = true;
		cells[4][3] = true;
		cells[5][3] = true;
		cells[5][2] = true;
		cells[4][1] = true;
		
		/*
		cells[2][2] = true;
		cells[2][3] = true;
		cells[3][2] = true;
		cells[3][3] = true;
		*/
	}
	
	private int getRows()
	{
		return cells.length;
	}
	
	private int getColumns()
	{
		return cells[0].length;
	}
	
	private void tick()
	{
		int rows = getRows();
		int columns = getColumns();
		
		for(int row = 0; row < rows; ++row)
		{
			for(int column = 0; column < columns; ++column)
			{
				int neighbors = liveNeighbors(row, column);
				
				boolean live = cells[row][column];
				
				boolean lives  = neighbors == 3 || (live && (neighbors == 2));
				
				previous[row][column] = lives;
			}
		}
		
		boolean[][] tmp = cells;
		cells = previous;
		previous = tmp;
	}
	
	@Override
	public void update(long elapsedTimeMillis) {
		super.update(elapsedTimeMillis);
		
		tickProgress += elapsedTimeMillis;
		
		if(tickProgress > tickDuration)
		{
			tick();
			tickProgress -= tickDuration;
		}
		
		shakeProgress += elapsedTimeMillis;
		
		if(shakeProgress > tickDuration)
		{
			shakeProgress -= tickDuration;
		}
		
		
		double d = super.totalTimeMillis;
		shakeX = Math.sin(d / 1000);
		shakeY = Math.cos(d / 1000);
		shakeX = 0;
		shakeY = 0;
		
		
		double shakeDuration = shakeDurationPct * tickDuration;
		
		if(shakeProgress < shakeDuration)
		{
			shakeX += (random.nextDouble() - 0.5) * shakeStrength;
			shakeY += (random.nextDouble() - 0.5) * shakeStrength;
		}
	}

	@Override
	public void draw(GraphicsBatch graphicsBatch) {
		
		graphicsBatch.clear(Color.WHITE);
		
		int rows = getRows();
		int columns = getColumns();
		
		for(int row = 0; row <= rows; ++row)
		{
			graphicsBatch.drawLine(0, row, columns, row, Color.BLACK);
		}
		
		for(int column = 0; column <= columns; ++column)
		{
			graphicsBatch.drawLine(column, 0, column, rows, Color.BLACK);
		}
		
		for(int row = 0; row < rows; ++row)
		{
			for(int column = 0; column < columns; ++column)
			{
				double x1 = column;
				double x2 = x1 + 1;
				double y1 = row;
				double y2 = row + 1;
				
				boolean live = cells[row][column];
				if(live)
				{
					graphicsBatch.fillRect(x1, y1, x2, y2, Color.BLACK);
				}
			}
		}
		
		Point p = null;
		try {
			p = panel.getMousePosition();
			
		}
		catch(Exception exn)
		{
//			exn.printStackTrace();
		}
		
		
		if(p != null && drawMouse)
		{
			Graphics2D graphics = graphicsBatch.getGraphics2D();
			Color c = graphics.getColor();
			graphics.setColor(Color.RED);
			graphics.drawLine(mouseXP, mouseYP, p.x, p.y);
			graphics.setColor(c);
		}
	
	
	}

	@Override
	public Rectangle2D getViewport() {
		double rows = getRows();
		double columns = getColumns();
		
		double x = shakeX - worldPadding;
		double y = shakeY - worldPadding;
		
		Point p = null;
		try {
			p = panel.getMousePosition();
			
		}
		catch(Exception exn)
		{
//			exn.printStackTrace();
		}
		
		p = null;
		if(p != null)
		{
			Rectangle b = panel.getBounds();
			double w = b.getWidth();
			double h = b.getHeight();
			double d = 8.0;
			x += (p.x - (w / 2)) / w * d;
			y += (p.y - (h / 2)) / h * d;
		}
		
		double width = columns + 2 * worldPadding;
		double height = rows + 2 * worldPadding;
		
		return new Rectangle2D(x, y, width, height);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private int mouseXP, mouseYP;

	@Override
	public void mousePressed(MouseEvent e) {
		drawMouse = true;
		mouseXP = e.getX();
		mouseYP = e.getY();
	}
	
	private boolean drawMouse = false;

	@Override
	public void mouseReleased(MouseEvent e) {
		drawMouse = false;
	}

}
