package infoprivacy.simulator;

import java.util.Date;
import java.util.Map;

public interface LogHandler 
{
	/**
	 * Logs an event to files/the GUI/anything else.
	 * 
	 * @param eventName - the name of the event
	 * @param lastSpeedDates - A map of Date > Speed events for the area of interest.
	 */
	public void handleLogEvent(String eventName, Map<Date, Double> lastSpeedDates);
	
	/**
	 * Logs some kind of variable to the reporter.
	 * 
	 * @param valueName - the name of the value e.g. (Mean speed)
	 * @param speedMPH - the value for the variable
	 */
	public void handleLogValue(String valueName, double speedMPH);

}
