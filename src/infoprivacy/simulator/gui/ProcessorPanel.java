package infoprivacy.simulator.gui;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.ProcessorSupervisor;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @author Andy Brunner <andybrunner91@gmail.com>
 * @author Jacob Bellatti <jake.bellatti@gmail.com>
 * @license BSD 3 Clause License
 * This class handles the tab for the whole project setup.
 *
 */
public class ProcessorPanel extends JPanel implements ChangeListener 
{
	private static final long serialVersionUID = 1L;

	private final Processor m_processor;
	private final JCheckBox m_box;
	
	public ProcessorPanel(Processor proc)
	{
		m_processor = proc;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		m_box = new JCheckBox(proc.getName(), ProcessorSupervisor.getInstance().isEnabled(proc));
		m_box.addChangeListener(this);
		
		add(m_box);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) 
	{	
		ProcessorSupervisor.getInstance().setEnabled(m_processor, m_box.isSelected());
	}
}
