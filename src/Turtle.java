import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import noir.math2d.Line2D;

@SuppressWarnings("serial")
public class Turtle extends Canvas implements Runnable, KeyListener
{
	private boolean computing = true;
	private int padding = 32;
	private Color penColor = Color.black; 
	
	private Scalar x;
	private Scalar y;
	private double orientation;
	
	private LinkedList<Line2D> lines;
	private LinkedList<Color> colors; 
	private Stack<State> stack;
	
	private BufferStrategy strategy;
	
	private boolean running = true;
	
	private LSystem system;
	private int iteration = 0;
	
	private JFrame container;
	
	public Turtle(LSystem system)
	{
		this.system = system;
		int width = 800;
		int height = 600;
		
		container = new JFrame(system.title);
		
		JPanel panel = (JPanel)container.getContentPane();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
		
		setBounds(0, 0, width, height);
		panel.add(this);
		
		setIgnoreRepaint(true);
		
		container.pack();
		container.setResizable(true);
		container.setVisible(true);
		
		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				System.exit(0);;
			}
		});
		
		this.addKeyListener(this);
		this.setFocusable(true);
		
		
		
		createBufferStrategy(3);
		strategy = getBufferStrategy();
		
		compute();
	}
	
	public void Move()
	{
		double x1 = x.getValue();
		double y1 = y.getValue();
		double dx = Math.cos(orientation);
		double dy = Math.sin(orientation);
		double x2 = x1 + dx;
		double y2 = y1 + dy;
		x.setValue(x2);
		y.setValue(y2);
	}
	
	public void MoveAndDraw()
	{
		double x1 = x.getValue();
		double y1 = y.getValue();
		double dx = Math.cos(orientation);
		double dy = Math.sin(orientation);
		double x2 = x1 + dx;
		double y2 = y1 + dy;
		x.setValue(x2);
		y.setValue(y2);
		
		lines.add(new Line2D(x1,y1,x2,y2));
		colors.add(penColor);
	}
	
	public void turnRadians(double radians)
	{
		orientation += radians;
	}
			
	
	public void turnDegrees(double degrees)
	{
		double radians = Math.toRadians(degrees);
		turnRadians(radians);
	}
	
	public void compute()
	{
		lineCount = 0;
		computing = true;
		String program = system.getString(iteration);
		String title = String.format("%s - Iteration %d", system.title, iteration);
		container.setTitle(title);
		lines = new LinkedList<Line2D>();
		colors = new LinkedList<Color>();
		stack = new Stack<State>();
		x = new Scalar(0.0);
		y = new Scalar(0.0);
		orientation = system.orientation;
		
		double degrees = system.angle;
		char[] instructions = program.toCharArray();
		
		
		for(char instruction : instructions)
		{
			switch(instruction)
			{
				case 'R':
				{
					penColor = Color.red;
				} break;
				case 'G':
				{
					penColor = Color.green;
				} break;
				case 'B':
				{
					penColor = Color.blue;
				} break;
				case 'Y':
				{
					penColor = Color.yellow;
				} break;
				case 'M':
				{
					penColor = Color.magenta;
				} break;
				case 'F':
				{
					MoveAndDraw();
				} break;
				case '+':
				{
					turnDegrees(degrees);
				} break;
				case '-': 
				{
					turnDegrees(-degrees);
				} break;
				case '[':
				{
					stack.push(new State(
							x.getValue(),
							y.getValue(),
							orientation,
							penColor));
				} break;
				case ']':
				{
					State s = stack.pop();
					x.setValue(s.x);
					y.setValue(s.y);
					orientation = s.orientation;
					penColor = s.penColor;
				} break;
				default:
					continue;
			}
		}
		
		computing = false;
		
		timePassed = 0;
	}
	
	private double timePassed = 0.0;
	private double drawTime = 1.0;
	private int lineCount = 0;
	
	private void update(double deltaTime)
	{
		timePassed += deltaTime;
		
		int totalLines = lines.size();
		
		double lineSpeed = totalLines / drawTime;
		
		if(lineSpeed < 16)
		{
			lineSpeed = 16;
		}
				
		lineCount = (int)(lineSpeed * timePassed);
	}

	private void draw(Graphics g)
	{
		if(computing)
		{
			return;
		}
		
		int screenWidth = getParent().getWidth();
		int screenHeight = getParent().getHeight();
		setBounds(0, 0, screenWidth, screenHeight);
		Bounds bounds = new Bounds(padding, screenWidth, screenHeight, x, y);
		g.setColor(Color.black);
		
		for(int i = 0; i < lines.size(); ++i)
		{
			if(i >= lineCount)
			{
				break;
			}
			Line2D line = lines.get(i);
			Color color = colors.get(i);
			bounds.drawLine(g, line, color);
		
		}
	}
	
	@Override
	public void run()
	{
		long lastLoopTime = System.currentTimeMillis();
		
		while(running)
		{
			long currentLoopTime = System.currentTimeMillis();
			double deltaTimeMs = currentLoopTime - lastLoopTime;
			double deltaTimeS = deltaTimeMs / 1000.0;
			lastLoopTime = currentLoopTime;
			update(deltaTimeS);
			
			do {
				do {
					Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
					g.setColor(Color.white);
					g.fillRect(0,0,getWidth(),getHeight());
					draw(g);
					g.dispose();
					
				} while(strategy.contentsRestored());
				strategy.show();
			} while(strategy.contentsLost());
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
			{
				--iteration;
				if(iteration < 0)
				{
					iteration = 0;
				}
				compute();
			} break;
			case KeyEvent.VK_RIGHT:
			{
				++iteration;
				compute();
			} break;
			
			default: break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
