package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Calculates the amount of night driving a person did. Should be more accurate
 * based on the season etc, but this works for now.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class NightDriving implements Processor {

	private final int DAWN_TIME_S = 7 * 60 * 60;
	private final int DUSK_TIME_S = 19 * 60 * 60;
	
	private int m_points;
	private int m_nightPoints;
	
	public void init()
	{
		m_points = 0;
		m_nightPoints = 0;
	}
	
	@Override
	public void process(Date time, double speedMPH) {
		
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Night Driving (%)", 100.0 * m_nightPoints / m_points);
			
			m_nightPoints = 0;
			m_points = 0;
			return;
		}
		
		long secondsPastMidnight = time.getTime() % 86400;
		
		if(secondsPastMidnight < DAWN_TIME_S || secondsPastMidnight > DUSK_TIME_S)
		{
			m_nightPoints++;
		}
		
		m_points++;
	}

	@Override
	public String getName() {
		return "Night Driving Checks";
	}

}
