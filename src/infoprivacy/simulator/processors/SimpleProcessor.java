package infoprivacy.simulator.processors;

import infoprivacy.simulator.Processor;

import java.util.Date;

/**
 * SimpleProcessor combines all of the functionality that is done ad-hoc in the
 * other classes.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public abstract class SimpleProcessor implements Processor 
{
	protected Date m_lastTime;
	protected double m_lastSpeed;
	protected boolean isFirst = true;
	private final String m_processorName;
	
	/**
	 * A simple class that handles all of the difficult processor stuff.
	 * @param processorName
	 */
	public SimpleProcessor(String processorName)
	{
		m_processorName = processorName;
	}
	
	
	@Override
	public void process(Date time, double speedMPH) 
	{
		// if the speed is below zero it means the trip is over
		if(speedMPH < 0)
		{
			tripFinished();
			isFirst = true;
			return;
		}
		
		if(isFirst)
		{
			initPlugin();
			isFirst = false;
		}
		else
		{
			processEvent(time, speedMPH);
		}
		
		m_lastTime = time;
		m_lastSpeed = speedMPH;
	}
	
	/**
	 * Called each time the plugin is expected to handle new road data.
	 */
	public abstract void initPlugin();
	
	/**
	 * Called each time the plugin is expected to handle data.
	 * @param time - the time of the log
	 * @param speedMPH - the speed the vehicle was going when logged
	 */
	public abstract void processEvent(Date time, double speedMPH);
	
	/**
	 * Called when the trip is finished.
	 */
	public abstract void tripFinished();
	
	@Override
	public String getName() 
	{
		return m_processorName;
	}

}
