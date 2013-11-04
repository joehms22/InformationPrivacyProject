package infoprivacy;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.TreeMap;
import infoprivacy.simulator.*;
	
	public class RouteData 
	{
		// The value (the Integer) is speed in mph and the key (the float) is amount of time (in ms) that you
		//were going that speed
		
		private final TreeMap<Float, Integer> m_routeData = new TreeMap<>();
		
		//takes in the node ids (in order) of the route and populates the TreeMap with the expected speed and time
		//to drive each edge of the route
		//TODO make this take in a string which is the path to the file with the route data
		public RouteData(int[] nodes)
		{
			
			float time; 
			for(int i = 0; i < nodes.length - 1; i++)
			{
				Road thisRoad = Road.getInstance(nodes[i], nodes[i+1]);				
				time = thisRoad.getLength() * (float)(60*60*1000) / (float)thisRoad.getSpeedLimit() ;
				m_routeData.put(time, thisRoad.getSpeedLimit());
			}
			
		}
		
		public RouteData(Path filePath)
		{
			
			for(String[] line : new DelimitedFileReader(filePath, ",", 1))
			{
				int time = Integer.parseInt(line[0]);
				float mph = Float.parseFloat(line[2]);
				ProcessorSupervisor.getInstance().process(time, mph);
			}
			ProcessorSupervisor.getInstance().process(0, -1);

		}
		
		//TODO Fix Roundoff error in this function
		public float getTripDistance()
		{
			float total = 0;
			
			for(Entry<Float, Integer> ent : m_routeData.entrySet())
			{
				// convert ms to hours
				total += ent.getKey() * (float)(ent.getValue() / (float)(1000 * 60 * 60));
			}
			
			return total;
		}
	
	
}
