package infoprivacy.grapher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SpeedGrapher extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MouseHandler mouseHandler = new MouseHandler();
	static PrintWriter writer = null;
	
	static int maxSpeed;
	static int timeLength;

    static GeneralPath path = null;
    private boolean drawing = false;
    private int lastX = 0;
    private static int height = 200;
    private static int width = 320;

    public SpeedGrapher() {
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(8,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        if (path!=null) {
            g2d.draw(path);
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            if(e.getX() > lastX)
            {
            	lastX = e.getX();
            	System.out.println("The time is " + (float)p.x*(float)timeLength/(float)width + " and speed is " + (float)(height - p.y)*(float)maxSpeed/(float)height);
	           
            	path.lineTo(p.x, p.y);
            }

            repaint();
        }
    }

    public void display() {
        JFrame f = new JFrame("Speed Grapher");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
    	
    	path = new GeneralPath();
        path.moveTo(0, height);
        System.out.println("What should be the max speed on the graph (in mph)?");
        Scanner theScanner = new Scanner(System.in);
        maxSpeed = theScanner.nextInt();
        System.out.println("Over what period of time (in seconds)");
        timeLength = theScanner.nextInt();
        theScanner.close();
        SpeedGrapher rc = new SpeedGrapher();
        rc.display();
        }
}