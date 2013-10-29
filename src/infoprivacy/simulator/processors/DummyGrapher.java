package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;
import java.util.TreeMap;

public class DummyGrapher implements Processor {

	TreeMap<Date, Double> lastSpeedDates = new TreeMap<>();
	
	
	@Override
	public void process(Date time, double speedMPH) {

		lastSpeedDates.put(time, speedMPH);
		
		if(Math.random() < .10)
		{
			Reporter.getInstance().logEvent("Dummy Graph Event", lastSpeedDates);
			lastSpeedDates.clear();
		}
		
	}

	@Override
	public String getName() {
		return "Dummy Grapher";
	}

}
