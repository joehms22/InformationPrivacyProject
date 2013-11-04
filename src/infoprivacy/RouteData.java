package infoprivacy;

import java.util.Map.Entry;
import java.util.TreeMap;

//public class RouteData 
//{
//	// In the format Speed in mph->Time in ms that you were going that velocity
//	private final TreeMap<Integer, Entry<Float, Float>> m_routeData = new TreeMap<>();
//	
//	public RouteData(int[] nodes)
//	{
//		// TODO create a new constructor for Rinku's data
//		for(int i = 0; i < nodes.length; i++)
//		{
//			Road thisRoad = Road.getInstance(nodes[i], nodes[i+1]);
//			m_routeData.put()
//		}
//		
//	}
//	
//	public float getTripDistance()
//	{
//		float total = 0;
//		
//		for(Entry<Integer, Entry<Float, Float>> ent : m_routeData.entrySet())
//		{
//			// convert ms to hours
//			total += ent.getKey() * (ent.getValue().getValue() / (1000 * 60 * 60));
//		}
//		
//		return total;
//	}
	
	public class RouteData 
	{
		// The value (the Integer) is speed in mph and the key (the float) is amount of time (in ms) that you
		//were going that speed
		private final TreeMap<Float, Integer> m_routeData = new TreeMap<>();
		
		//takes in the node ids (in order) of the route and populates the TreeMap with the expected speed and time
		//to drive each edge of the route
		public RouteData(int[] nodes)
		{
			// TODO create a new constructor for Rinku's data
			
			float time; 
			for(int i = 0; i < nodes.length - 1; i++)
			{
				Road thisRoad = Road.getInstance(nodes[i], nodes[i+1]);				
				time = thisRoad.getLength() * (float)(60*60*1000) / (float)thisRoad.getSpeedLimit() ;
				m_routeData.put(time, thisRoad.getSpeedLimit());
			}
			
		}
		
		//TODO Fix Roundoff error in this function
		public float getTripDistance()
		{
			float total = 0;
			
			for(Entry<Float, Integer> ent : m_routeData.entrySet())
			{
				// convert ms to hours
				total += ent.getKey() * (ent.getValue() / (1000 * 60 * 60));
			}
			
			return total;
		}
	
	
}
