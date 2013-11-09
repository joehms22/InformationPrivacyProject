package infoprivacy.simulator.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements an intersection, the points at which roads are joined.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class Intersection
{
	// Keeps a list of all the intersections that exist.
	// set the initial capacity high so we don't have to resize
	private static final HashMap<Integer, Intersection> INTERSECTIONS = new HashMap<>(400000);
	
	public static Intersection getInstance(int nodeId)
	{
		synchronized(INTERSECTIONS)
		{
			Intersection section = INTERSECTIONS.get(nodeId);
			
			if(section == null)
			{
				section = new Intersection(nodeId);
				INTERSECTIONS.put(nodeId, section);	
			}
			
			return section;
		}
	}
	
	
	
	private final int m_nodeId;
	private final LinkedList<Road> m_edges = new LinkedList<>();
	private float m_lat; // the latitude of the intersection
	private float m_lon; // the longitude of the intersection
	
	private Intersection(int nodeId)
	{
		m_nodeId = nodeId;
	}
	
	public int getNodeId()
	{
		return m_nodeId;
	}
	
	public Collection<Road> getRoads()
	{
		return m_edges;
	}

	public void addRoad(Road road) {
		m_edges.add(road);
	}

	public void setLatLon(float lat, float lon) {
		m_lat = lat;
		m_lon = lon;
	}
	
	@Override
	public String toString()
	{
		return "[Intersection lat=" + m_lat + " lon=" + m_lon + "]";
	}

	public static int count() {
		synchronized(INTERSECTIONS)
		{
			return INTERSECTIONS.size();
		}
	}
}
