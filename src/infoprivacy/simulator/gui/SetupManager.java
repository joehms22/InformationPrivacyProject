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
 * Copyright (c) 2013
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @author Andy Brunner <andybrunner91@gmail.com>
 * @author Jacob Bellatti <jake.bellatti@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * * Neither the name of the University of Denver nor the names of its 
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * This class handles the tab for the whole project setup.
 *
 */
public class SetupManager extends JPanel 
{

	private static final long serialVersionUID = 8579533159259385634L;

	public SetupManager()
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(new JLabel("<html><h1>Generate Data</h1></html>"));
		JButton simulationCreator = new JButton("Create a simulation file");
		simulationCreator.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SimulationCreator();
			}
		
		});
		
		add(simulationCreator);
		
		add(new JLabel("<html><h1>Data Sources</h1></html>"));
		
		
		
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
