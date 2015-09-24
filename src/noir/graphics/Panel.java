package noir.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel
{
	static {	    
		System.setProperty("sun.java2d.opengl", "true");
		
//	    System.setProperty("sun.java2d.transaccel", "True");
//	    System.setProperty("sun.java2d.d3d", "True");
//	    System.setProperty("sun.java2d.ddforcevram", "True");
	}
    
    private JFrame frame;
    private Drawable drawable;
    
    public Panel(String title, int width, int height, Drawable provider) 
    {
    	this(title, width, height, provider, 0.0);
    }
    
    public Panel(String title, int width, int height, Drawable provider, double fps) 
    {
    	super();
    	
    	frame = new JFrame(title);
        frame.setLayout(new BorderLayout());
        
        
        setPreferredSize(new Dimension(width, height));
        
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIgnoreRepaint(fps > 0.0);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        provider.setPanel(this);
    	this.drawable = provider;
    	
    	KeyListener keyListener = provider.getKeyListener();
    	if(keyListener != null)
    	{
    		frame.addKeyListener(keyListener);
    	}
        
    	MouseListener mouseListener = provider.getMouseListener();
    	if(mouseListener != null)
    	{
    		this.addMouseListener(mouseListener);
    	}
    	
    	if(fps > 0.0)
    	{
    		setTimer(fps);
    	}

    	frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				refresh();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) { }
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
    }
    
    public void setFrameTitle(String title)
    {
    	frame.setTitle(title);
    }
    
    private void refresh()
    {
		try(GraphicsBatch graphicsBatch = makeBatch(getGraphics())) 
    	{
    		drawable.draw(graphicsBatch);
    	} 
    	catch(Exception exn) 
    	{
    		throw new RuntimeException(exn);
//			e.printStackTrace();
		}    	
    }
    
    private void setTimer(double fps)
    {
    	int delay = (int)(1000.0 / fps);
        Timer timer = new Timer(delay, new ActionListener() {
        	
        	long lastTime = System.currentTimeMillis();
        	
			@Override
			public void actionPerformed(ActionEvent evt) 
			{
				refresh();
	        	
				long currentTime = System.currentTimeMillis();
				long elapsedTimeMillis = currentTime - lastTime;
	        	lastTime = currentTime;
	        	
	        	drawable.update(elapsedTimeMillis);
			}
		});
		timer.start();
    }
    
    private GraphicsBatch makeBatch(Graphics graphics)
    {
    	Rectangle bounds = getBounds();
    	setBackground(drawable.getBackgroundColor());
    	graphics.setColor(getBackground());
    	graphics.fillRect(0, 0, bounds.width, bounds.height);
    	graphics.setColor(getForeground());
    	GraphicsBatch graphicsBatch = new GraphicsBatch((Graphics2D)graphics, bounds, drawable);
    	return graphicsBatch;
    }

    @Override
	public void paint(Graphics graphics) 
    {
    	super.paint(graphics);
    	GraphicsBatch graphicsBatch = makeBatch(graphics);
    	drawable.draw(graphicsBatch);
    }
}