package infoprivacy.simulator.gui;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import infoprivacy.simulator.LogHandler;
import infoprivacy.simulator.Reporter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

/**
 * Listens to the Reporter and displays the output in a nice GUI.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class OutputManager extends JPanel implements LogHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2311586918899707512L;

	// This is the panel where everything is going to be added, it is within
	// a jscrollpane so we don't overflow the main window.
	private final JPanel m_innerPanel = new JPanel();

	
	private static final Dimension CHART_DIMENSION = new Dimension(600,300);
	private static OutputManager INSTANCE;

	
	/**
	 * Singleton constructor/getter for OutputManager
	 * @return
	 */
	public static OutputManager getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new OutputManager();
		}
		
		return INSTANCE;
	}
	
	/**
	 * The OutputManager logs data in a human-readable format.
	 */
	private OutputManager()
	{
		setLayout(new BorderLayout());
		
		m_innerPanel.setBackground(Color.WHITE);

		m_innerPanel.setLayout(new BoxLayout(m_innerPanel, BoxLayout.PAGE_AXIS));
		add(new JScrollPane(m_innerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		
		
		// Set this up as a Reporter Handler
		Reporter.getInstance().onLog(this);
	}

	@Override
	public void handleLogEvent(String eventName, Map<Date, Double> data) 
	{
		// Create a chart:  
	    Chart2D chart = new Chart2D();
	    // Create an ITrace: 
	    ITrace2D trace = new Trace2DSimple(); 
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace);    
	    
	    // Make sure our map is *sorted* so the data doens't come out wonky.
	    data = new TreeMap<>(data);
	    
	    // Add all points, as it is static: 
	    for(Entry<Date, Double> ent : data.entrySet())
	    {
	      trace.addPoint(ent.getKey().getTime(), ent.getValue());
	    }
	    
	    trace.setName(eventName);
	    trace.setPhysicalUnits("Seconds", "MPH");
	   
	    chart.setSize(CHART_DIMENSION);
	    chart.setMaximumSize(CHART_DIMENSION);
	    chart.setPreferredSize(CHART_DIMENSION);
	    chart.setMinimumSize(CHART_DIMENSION);
	    addNewComponent(chart);
	}

	@Override
	public void handleLogValue(String valueName, double value) 
	{
		addNewComponent(new JLabel("<html><h2>" + valueName + ": <span color='blue'>"+value+"</span></h2></html>"));
	}
	
	private void addNewComponent(JComponent cmp)
	{
		cmp.setAlignmentX( Component.LEFT_ALIGNMENT );//0.0

		m_innerPanel.add(cmp);
		m_innerPanel.add(new JSeparator());
	}
	
	
	/**
	 * Clears the output panel and prepares it for a new incoming set of data
	 */
	public void clearOutput()
	{
		m_innerPanel.removeAll();
	}
}
