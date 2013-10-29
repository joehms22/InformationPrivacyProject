package infoprivacy.simulator.gui;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.ProcessorSupervisor;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class creates panels that enable/disable a given processor.
 * @author Joseph Lewis <joehms22@gmail.com>
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
