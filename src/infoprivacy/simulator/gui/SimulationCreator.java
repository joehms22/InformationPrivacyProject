package infoprivacy.simulator.gui;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import infoprivacy.simulator.ProcessorSupervisor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import net.josephlewis.java.gui.Dialogs;

/**
 * A window that allows the user to create new simulation files.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class SimulationCreator extends JDialog
{
	// the simulation data
	private final TreeMap<Date, Double> m_speeds = new TreeMap<Date, Double>(); 
	private  Date m_lastTime = new Date();
	private  int m_lastSpeed = 0;
	
	
	private static final long serialVersionUID = 1L;
    private final Chart2D m_chart = new Chart2D(); // the main chart of the app
    private final ITrace2D m_trace = new Trace2DSimple(); // the main plot
    
    // spinnerNumberModel args are <initial value, min, max, step>
    private final JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(0, 30, 100, 1));
    private final JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(30,1, 1000, 1));
	private final JSpinner deltaSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 30, 1));

    
	public SimulationCreator()
	{		
		setModal(true);
		setTitle("Simulation Creator");
		setLayout(new BorderLayout());
		add(m_chart, BorderLayout.CENTER);
		m_chart.addTrace(m_trace);
		
		JToolBar buttons = new JToolBar();
		add(buttons, BorderLayout.SOUTH);

		
		// setup the main interaction area
		buttons.add(new JLabel("Drive"));
		buttons.add(speedSpinner);
		buttons.add(new JLabel(" MPH for "));
		
		buttons.add(timeSpinner);
		buttons.add(new JLabel(" seconds raising/falling "));
		buttons.add(deltaSpinner);
		buttons.add(new JLabel(" MPH/S to target."));		
		
		JButton addButton = new JButton("<html><b>Add Data</b></html>");
		buttons.add(addButton);
		
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generateData((Integer)speedSpinner.getValue(), (Integer) timeSpinner.getValue(), (Integer) deltaSpinner.getValue() );
			}
		});
		
		
		// Add the save button to the top of the panel for exporting data
		JButton saveButton = new JButton("Save");
		add(saveButton, BorderLayout.NORTH);
		
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveSimulation();
			}
			
		});
		
		

		// submit the data as the window closes
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				runSimulation();
			}
		});
		
		
		setSize(800, 600);
		setVisible(true);
		
		// add the initial point where the trip begins.
		addPoint(0);
	}
	
	/**
	 * Generates the driving needed to get to the given speed from the last one
	 * 
	 * @param speed - the speed to get to
	 * @param time - the time to maintain that speed
	 * @param delta - the change in speeds we can use to get there.
	 */
	private void generateData(int speed, int time, int delta) 
	{
		if(speed > m_lastSpeed)
		{
			for(int i = m_lastSpeed + delta; i < speed; i += delta)
			{
				addPoint(i);
			}
		}
		else
		{
			for(int i = m_lastSpeed - delta; i > speed; i -= delta)
			{
				addPoint(i);
			}
		}
		
		// add the maintain speed data
		for(int i = 0; i < time; i++)
		{
			addPoint(speed);
		}
		
		updateGraph();
	}

	/**
	 * Adds a point of data one second from the last one.
	 * @param speed - the speed the driver is going at the given time.
	 */
	public void addPoint(int speed)
	{
		m_lastTime = new Date(m_lastTime.getTime() + 1);
		m_lastSpeed = speed;
		
		m_speeds.put(m_lastTime, (double)speed);
	}
	
	/**
	 * Displays all of the drive so far on the graph.
	 */
	public void updateGraph()
	{	    
		m_trace.removeAllPoints();
	    // Add all points, as it is static: 
	    for(Entry<Date, Double> ent : m_speeds.entrySet())
	    {
	      m_trace.addPoint(ent.getKey().getTime(), ent.getValue());
	    }
	    
	    m_trace.setPhysicalUnits("Seconds", "MPH");
	}
	
	/**
	 * Prompts the user to save the simulation.
	 */
	public void saveSimulation()
	{
		String savepath = Dialogs.showSaveDialog(new String[]{"Simulation File",".dat"}, "", false, true, false);
		
		
		if(savepath != null)
		{
			Path p = Paths.get(savepath + ".dat");
			
			Charset charset = Charset.forName("UTF-8");
			
			try (BufferedWriter writer = Files.newBufferedWriter(p, charset)) {
				
				for(Entry<Date, Double> e : m_speeds.entrySet())
				{
					String line = e.getKey().getTime() + ",0,"+e.getValue() + ",0,0\n";
					writer.write(line, 0, line.length());
				}
			} catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
		}
	}

	/**
	 * Runs the currently displayed simulation.
	 */
	private void runSimulation() {
		ProcessorSupervisor ps = ProcessorSupervisor.getInstance();

		for(Entry<Date, Double> e : m_speeds.entrySet())
		{
			ps.process(e.getKey(), e.getValue());
		}

		// notify that we are done
		ps.process(-1, -1);				
	}
}
