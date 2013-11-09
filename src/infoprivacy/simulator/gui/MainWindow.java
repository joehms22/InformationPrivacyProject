package infoprivacy.simulator.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main window of the application.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @author Andy Brunner <andybrunner91@gmail.com>
 * @author Jacob Bellatti <jake.bellatti@gmail.com>
 * @license BSD 3 Clause License
 */
public class MainWindow extends JFrame
{

	private static final long serialVersionUID = -5327894513395293006L;
	
	private static MainWindow INSTANCE;
	
	private final JTabbedPane m_tabs;
	
	public static MainWindow getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new MainWindow();
		}
		
		return INSTANCE;
	}

	private MainWindow()
	{
		// Make us look pretty on the target platform
		try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		m_tabs = new JTabbedPane();
		
		m_tabs.addTab("Setup", new JScrollPane(new SetupManager(),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		m_tabs.addTab("Output", OutputManager.getInstance());
		setLayout(new BorderLayout());
		add(m_tabs, BorderLayout.CENTER);
		
		setTitle("Driving Simulator");
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	
	
	/**
	 * Switches the current tab to the results tab.
	 */
	public void showResults() 
	{
		m_tabs.setSelectedIndex(1); // change to the data tab
	}
	
	public static void main(String args[])
	{
		MainWindow.getInstance();
	}


}
