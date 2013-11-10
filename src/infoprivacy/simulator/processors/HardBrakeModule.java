package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Warns about hard brakes encountered on the drive.
 * @author Joseph Lewis <joehms22@gmail.com>
 * @author Jacob Bellatti <jake.bellatti@gmail.com>
 * @license BSD 3 Clause License
 */
public class HardBrakeModule implements Processor
{
	private static double decelThresh;
	
	public HardBrakeModule()
	{
		initialize();
	}
	
	
	public void initialize(){
		m_lastSpeed = Double.NaN;
		m_accel.clear();
		setDecelThresh(3.5); // mph/s
	}
	
	public static void setDecelThresh(double d){
		decelThresh = d;
	}
	
	
	private final TreeMap<Date, Double> m_accel = new TreeMap<>();
	private double m_lastSpeed = Double.NaN;
	
	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			report();
			initialize();
		}
		
		if(m_lastSpeed == Double.NaN)
		{
			m_lastSpeed = speedMPH;
			return;
		}
		
		if(speedMPH < m_lastSpeed)
		{
			
			m_accel.put(time, speedMPH);
		}
		else
		{
			report();
		}
		
		m_lastSpeed = speedMPH;
		
	}
	
	private void report() {
		
		if(m_accel.size() < 2) // need at least 2 events for a hard brake.
		{
			m_accel.clear();
			return;
		}
				
		Entry<Date, Double> lastEntry = null;
		for(Entry<Date,Double> ent : m_accel.entrySet())
		{
			if(lastEntry == null)
			{
				lastEntry = ent;
				continue;
			}
			
			long dateDiff = ent.getKey().getTime() - lastEntry.getKey().getTime();
			double speedDiff = lastEntry.getValue() - ent.getValue();
			
			// acceleration / time to accelerate in seconds e.g. 5mph/s
			if(speedDiff / dateDiff > decelThresh)
			{
				Reporter.getInstance().logEvent(getName(), m_accel);
				break;
			}
		}
		
		m_accel.clear();
		
	}

	@Override
	public String getName() 
	{
		return "Hard Brake";
	}

}
