package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * This is a dummy processor, and should be removed at a later time.
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class DummyProcessor implements Processor {

	@Override
	public void process(Date time, double speedMPH) {
		// clutter up the output!
		Reporter.getInstance().logValue("Dummy Speed", speedMPH);
		
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Trip Completed", 0);
		}
	}

	@Override
	public String getName() {
		return "Dummy Processor";
	}

}
