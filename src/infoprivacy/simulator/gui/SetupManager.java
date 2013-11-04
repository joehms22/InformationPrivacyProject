package infoprivacy.simulator.gui;

import infoprivacy.RouteData;
import infoprivacy.simulator.Processor;
import infoprivacy.simulator.ProcessorSupervisor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.josephlewis.java.gui.Dialogs;

/**
 * This class handles the tab for the whole project setup.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class SetupManager extends JPanel 
{

	private static final long serialVersionUID = 8579533159259385634L;

	public SetupManager()
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(new JLabel("<html><h1>Data Generators</h1></html>"));
		
		
		
		// Add buttons/forms for each input source
		JButton andyDataGenerator = new JButton("Use a saved Trip Data File");
		andyDataGenerator.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				FileFilter filter = new FileNameExtensionFilter("Trip Data File", "dat");
				Path output = Dialogs.showFileOpenDialog(new FileFilter[]{filter}, false);
				
				if(output != null)
				{
					new RouteData(output);
				}
			}
			
		});
		
		add(andyDataGenerator);
		
		
		JButton fakeDataGenerator = new JButton("Generate Bad Random Route Data");
		add(fakeDataGenerator);
		fakeDataGenerator.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				double lastValue = 30;
				
				for(int i = 0; i < 100; i++)
				{
					Date d = new Date(i);
					if(Math.random() < .33)
					{
						lastValue -= 5;
						if(lastValue < 0)
						{
							lastValue = 0;
						}
						ProcessorSupervisor.getInstance().process(d, lastValue);
						continue;
					}
					if(Math.random() > .33)
					{
						lastValue += 10;
						ProcessorSupervisor.getInstance().process(d, lastValue);
						continue;
					}
					ProcessorSupervisor.getInstance().process(d, lastValue);
				}
				
				ProcessorSupervisor.getInstance().process(new Date(), -1);

			}
			
		});
		
		JButton clearData = new JButton("Clear Output");
		add(clearData);
		clearData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OutputManager.getInstance().clearOutput();
			}
		});
		
		// Add enable/disable buttons for each processor
		add(new JLabel("<html><h1>Data Processors</h1></html>"));
		for(Processor proc : ProcessorSupervisor.getInstance().getProcessors())
		{
			add(new ProcessorPanel(proc));
		}
	}
	
	public JComponent add(JComponent cmp)
	{
		cmp.setAlignmentX( Component.LEFT_ALIGNMENT );
		super.add(cmp);
		return cmp;
	}
}
