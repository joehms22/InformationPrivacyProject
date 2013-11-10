package infoprivacy;

import infoprivacy.simulator.ProcessorSupervisor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.TreeMap;

/**
 * Reads/creates route data from the .dat files we were given.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @author Andy Brunner <andybrunner91@gmail.com>
 * @author Jacob Bellatti <jake.bellatti@gmail.com>
 * @license BSD 3 Clause License
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class RouteData 
{
	// The value (the Integer) is speed in mph and the key (the float) is amount of time (in ms) that you
	//were going that speed

	private final TreeMap<Date, Double> m_routeData = new TreeMap<>();

	
	/**
	 * Constructs the route data and processes it.
	 * 
	 * @param filePath - the path to the route data file.
	 */
	public RouteData(Path filePath)
	{

		try {
			for(String[] line : new DelimitedFileReader(filePath, ",", 1))
			{
				if(line.length <= 3)
				{
					continue;
				}
				long time = Long.parseLong(line[0].trim());
				float mph = Float.parseFloat(line[2].trim());
				ProcessorSupervisor.getInstance().process(time, mph);
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		ProcessorSupervisor.getInstance().process(0, -1);

	}
}
