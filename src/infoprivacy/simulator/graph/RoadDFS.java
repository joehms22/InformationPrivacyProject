package infoprivacy.simulator.graph;

import java.util.Iterator;
import java.util.LinkedList;

import net.josephlewis.java.collections.DefaultMap;
import net.josephlewis.java.collections.DefaultMap.ValueGenerator;

/**
 * A DFS algorithm derived from Wikipedia's DFS pseudocode to operate on our
 * road network.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class RoadDFS implements Iterable<LinkedList<Intersection>>
{
	enum NODESTATE
	{
		discovered,
		unexplored,
		explored
	};
	
	enum ROADSTATE
	{
		unexplored,
		discovered,
		back
	};
	
	private final double m_neededDistance;
	private final DefaultMap<Intersection, NODESTATE> m_states;
	private final DefaultMap<Road, ROADSTATE> m_roadStates;
	private final LinkedList<LinkedList<Intersection>> m_pathsFound = new LinkedList<>();
	
	public RoadDFS(double distance, Intersection startNode)
	{
		m_neededDistance = distance;
		m_states = new DefaultMap<Intersection, NODESTATE>(new ValueGenerator<Intersection, NODESTATE>(){
			@Override
			public NODESTATE getValue(Intersection key) {
				return NODESTATE.unexplored;
			}
		});
		
		m_roadStates = new DefaultMap<Road, ROADSTATE>(new ValueGenerator<Road, ROADSTATE>(){
			@Override
			public ROADSTATE getValue(Road key) {
				return ROADSTATE.unexplored;
			}
		});
		
		dfs(distance, startNode, new LinkedList<Intersection>());
	}
	
	/**
	 * Appends a found path to the list of known paths.
	 * @param path
	 */
	public void yield(LinkedList<Intersection> path)
	{
		m_pathsFound.add(path);
	}
	
	/**
	 * Performs depth first search on the graph until no more roads can be taken
	 * with the distance remaining.
	 * 
	 * @param distanceLeft - the distance this road can take.
	 * @param intersection - the current intersection
	 * @param pathSoFar - all of the points on the path until now.
	 */
	private void dfs(double distanceLeft, Intersection intersection, LinkedList<Intersection> pathSoFar)
	{
		pathSoFar.push(intersection);
		
		if(distanceLeft < 0)
		{
			yield(new LinkedList<Intersection>(pathSoFar));
			pathSoFar.pop();
			return;
		}
		
		
		m_states.put(intersection, NODESTATE.discovered);
		for(Road edge : intersection.getRoads())
		{
			if(m_roadStates.get(edge) == ROADSTATE.unexplored)
			{
				Intersection nextIntersection = edge.getTo();
				if(m_states.get(nextIntersection) == NODESTATE.unexplored)
				{
					m_roadStates.put(edge, ROADSTATE.discovered);
					dfs(distanceLeft - edge.getLength(), nextIntersection, pathSoFar);
		
				}
				else
				{
					m_roadStates.put(edge, ROADSTATE.back);
				}
			}
		}
		
		pathSoFar.pop();
		m_states.put(intersection, NODESTATE.explored);
	}
	

	@Override
	public Iterator<LinkedList<Intersection>> iterator() 
	{
		return m_pathsFound.iterator();
	}
}
