package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;
import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Discretizes all speeds that the driver was at and reports them.
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class SpeedBucket implements Processor
{
	// these are the bucket upper cutoffs.
	public static int[] BUCKET_MAX_VALUES = new int[]{30,45,55,75,Integer.MAX_VALUE};
	public int[] m_buckets = new int[BUCKET_MAX_VALUES.length];
	public int m_count;
	
	public void initialize()
	{
		m_buckets = new int[BUCKET_MAX_VALUES.length];
		m_count = 0;
	}

	@Override
	public void process(Date time, double speedMPH) 
	{
		if(speedMPH < 0)
		{
			report();
			initialize();
		}
		
		for(int i = 0; i < m_buckets.length; i++)
		{
			if(speedMPH < BUCKET_MAX_VALUES[i])
			{
				m_buckets[i]++;
				break;
			}
		}
		
		m_count++;
	}

	/**
	 * Report the speeds in the bucket.
	 */
	private void report() 
	{
		int lastMPH = 0;
		for(int i = 0; i < m_buckets.length; i++)
		{
			
			Reporter.getInstance().logValue("Percent of trip " + 
										lastMPH + " -> " + 
										BUCKET_MAX_VALUES[i] + " mph", (100.0 * m_buckets[i]) / m_count);
			
			
			lastMPH = BUCKET_MAX_VALUES[i];
		}
	}

	@Override
	public String getName() {
		return "Speed Discretization";
	}

}
