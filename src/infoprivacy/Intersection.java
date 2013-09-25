package infoprivacy;

import java.util.ArrayList;
import java.util.Collection;
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
	private static final ArrayList<Intersection> INTERSECTIONS = new ArrayList<>(400000);
	
	public static Intersection getInstance(int nodeId)
	{
		synchronized(INTERSECTIONS)
		{
			INTERSECTIONS.ensureCapacity(nodeId);
			Intersection section = INTERSECTIONS.get(nodeId);
			
			if(section == null)
			{
				section = new Intersection(nodeId);
				INTERSECTIONS.set(nodeId, section);
			}
			
			return section;
		}
	}
	
	
	
	private final int m_nodeId;
	private final LinkedList<Road> m_edges = new LinkedList<>();
	
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
}
