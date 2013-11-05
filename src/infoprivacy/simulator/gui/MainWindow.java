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
 *
 */
public class MainWindow extends JFrame 
{

	private static final long serialVersionUID = -5327894513395293006L;

	public MainWindow()
	{
		  try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		JTabbedPane m_tabs = new JTabbedPane();
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

	public static void main(String args[])
	{
		new MainWindow();
	}
}
