package infoprivacy;

import java.util.Map.Entry;
import java.util.TreeMap;

public class RouteData 
{
	// In the format Speed in mph->Time in ms that you were going that velocity
	private final TreeMap<Integer, Entry<Float, Float>> m_routeData = new TreeMap<>();
	
	public RouteData()
	{
		// TODO create a new constructor for Rinku's data
	}
	
	public float getTripDistance()
	{
		float total = 0;
		
		for(Entry<Integer, Entry<Float, Float>> ent : m_routeData)
		{
			// convert ms to hours
			total += ent.getKey() * (ent.getValue() / (1000 * 60 * 60));
		}
		
		return total;
	}
	
	
}
