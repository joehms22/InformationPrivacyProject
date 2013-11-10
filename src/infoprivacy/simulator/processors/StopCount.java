package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Counts the number of stops made by this vehicle.
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class StopCount implements Processor
{
	int stopCount = 0;
	double lastSpeed = 10;
	
	@Override
	public void process(Date time, double speedMPH) {
		
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Number of Stops", stopCount);
			stopCount = 0;
		}
		
		if(lastSpeed != 0 && speedMPH == 0)
		{
			stopCount++;
		}
		
		lastSpeed = speedMPH;		
	}

	@Override
	public String getName() {
		return "Stop Count";
	}

}
