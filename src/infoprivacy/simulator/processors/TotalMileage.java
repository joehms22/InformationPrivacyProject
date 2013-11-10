package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Counts the total miles driven on the trip. Note that this is not wildly
 * accurate, because it assumes the driver was driving at the current speed 
 * since the last point was reported rather than interpolating.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class TotalMileage implements Processor {

	private Date m_lastTime = null;
	double m_milesDriven = 0.0;
	
	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Total Distance Driven (mi)", m_milesDriven);
			return;
		}
		
		if(m_lastTime != null)
		{
			long timeDelta = time.getTime() - m_lastTime.getTime();
			
			speedMPH /= 3600.0; // get speed per second
			
			m_milesDriven += speedMPH * timeDelta;
		}
		
		m_lastTime = time;
	}

	@Override
	public String getName() {
		return "Trip Mileage";
	}

}
