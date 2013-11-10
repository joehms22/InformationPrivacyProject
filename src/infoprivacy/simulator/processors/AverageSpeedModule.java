package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Calculates the average speed for the drive.
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class AverageSpeedModule implements Processor
{

	private static ArrayList<Double> speeds;
	private static double avg;
	
	public AverageSpeedModule()
	{
		initialize();
	}

	public static void initialize(){
		speeds = new ArrayList<Double>();
		avg = 0;
	}

	public static double addValue(double d){
		speeds.add(d);
		for(double s : speeds){
			avg += s;
		}
		avg /= speeds.size();
		return avg;
	}

	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			Reporter.getInstance().logValue("Average Speed", avg);
			initialize();
		}
		
		addValue(speedMPH);
	}

	@Override
	public String getName() {
		return "Average Speed";
	}

}
