package infoprivacy.simulator.processors;

import infoprivacy.simulator.LogHandler;
import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

/**
 * This will count all of the reported points once the project finishes, this
 * is useful for showing that our method actually cuts down on the data 
 * breached.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class ReportedPointCounter implements Processor, LogHandler 
{
	
	private int allTimes = 0;
	private final HashSet<Date> reportedTimes = new HashSet<Date>();

	public ReportedPointCounter()
	{
		Reporter.getInstance().onLog(this);
	}
	
	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			System.out.println("Reporting");

			float allpts = 100 * (((float) reportedTimes.size()) / allTimes);
			Reporter.getInstance().logValue("Percent of Points Reported", allpts);
			
			allTimes = 0;
			reportedTimes.clear();
			return;
		}
		
		allTimes++;
	}

	@Override
	public String getName() {
		return "Reported Point Counter";
	}

	@Override
	public void handleLogEvent(String eventName,
			Map<Date, Double> lastSpeedDates) 
	{
		for(Date d : lastSpeedDates.keySet())
		{
			reportedTimes.add(d);
		}
	}

	@Override
	public void handleLogValue(String valueName, double speedMPH) 
	{
		// we don't care about these.
	}

}
