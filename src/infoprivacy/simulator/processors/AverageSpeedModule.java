package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Calculates the average speed for the drive.
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class AverageSpeedModule implements Processor
{

	private static double avg;
	private static int count;
	
	public AverageSpeedModule()
	{
		initialize();
	}

	public static void initialize(){
		avg = 0;
		count = 0;
	}


	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Average Speed", avg / count);
			initialize();
		}
		avg += speedMPH;
		count++;
	}

	@Override
	public String getName() {
		return "Average Speed";
	}

}
