package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;
import java.util.TreeMap;

/**
 * Shows driving habits above 80MPH
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class Over80MPHProcessor implements Processor
{
	private final TreeMap<Date, Double> m_recorder = new TreeMap<>();

	@Override
	public void process(Date time, double speedMPH) 
	{
		// report results, the trip is over
		if(speedMPH < 0) {
			reportResults();
			return;
		}
		
		if(speedMPH >= 80.0)
		{
			m_recorder.put(time, speedMPH);
		}
		else
		{
			reportResults();
		}
	}

	private void reportResults() {
		if(!m_recorder.isEmpty())
		{
			Reporter.getInstance().logEvent("Over 80 MPH", m_recorder);
			m_recorder.clear();
		}		
	}

	@Override
	public String getName() {
		return "Over 80 MPH";
	}

}
