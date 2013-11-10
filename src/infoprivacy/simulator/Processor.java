package infoprivacy.simulator;

import java.util.Date;

/**
 * Something that handles data as it streams in and fires off events in the 
 * Reporter.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public interface Processor 
{
	/**
	 * Processes a point of data in the stream.
	 * @param time - the time the data occurs at.
	 * @param speedMPH - the speed the vehicle was going at the time.
	 */
	public void process(Date time, double speedMPH);
	
	/**
	 * Returns a human-readable name for this processor. e.g. "Hard Brake Checker"
	 * @return - a human readable name for the processor.
	 */
	public String getName();
}
