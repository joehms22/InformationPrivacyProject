package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

public class TripTime implements Processor {

	private Date m_firstDate = null;
	private  Date m_runningDate = null;
	
	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Trip Time (sec)", m_runningDate.getTime() - m_firstDate.getTime());
			return;
		}
		
		if(m_firstDate == null)
		{
			m_firstDate = time;
		}
		
		m_runningDate = time;
	}

	@Override
	public String getName() {
		return "Trip Time";
	}

}
