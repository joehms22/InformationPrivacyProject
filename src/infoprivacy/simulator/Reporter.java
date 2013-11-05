package infoprivacy.simulator;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * The reporter handles the recording of interesting data that the processors
 * see.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class Reporter 
{
	private static Reporter INSTANCE;
	private final LinkedList<LogHandler> m_handlers = new LinkedList<LogHandler>();
	
	private Reporter()
	{
		// Nothing goes here, but we force private for singleton purposes.
	}
	
	/**
	 * Gets the single reporter for this project.
	 * 
	 * @return
	 */
	public static Reporter getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Reporter();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Logs an event to files/the GUI/anything else.
	 * 
	 * @param eventName - the name of the event
	 * @param lastSpeedDates - A map of Date > Speed events for the area of interest.
	 */
	public void logEvent(String eventName, Map<Date, Double> lastSpeedDates)
	{
		for(LogHandler lh : m_handlers)
		{
			lh.handleLogEvent(eventName, lastSpeedDates);
		}
	}
	
	/**
	 * Logs some kind of variable to the reporter.
	 * 
	 * @param valueName - the name of the value e.g. (Mean speed)
	 * @param speedMPH - the value for the variable
	 */
	public void logValue(String valueName, double speedMPH)
	{
		for(LogHandler lh : m_handlers)
		{
			lh.handleLogValue(valueName, speedMPH);
		}
	}
	
	/**
	 * Adds a handler to be called when a value is logged.
	 * @param lh - the handler to add.
	 */
	public void onLog(LogHandler lh)
	{
		m_handlers.add(lh);
	}
}
