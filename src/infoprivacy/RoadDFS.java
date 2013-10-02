package infoprivacy;

import java.util.LinkedList;

import net.josephlewis.java.collections.DefaultMap;
import net.josephlewis.java.collections.DefaultMap.ValueGenerator;

/**
 * A DFS algorithm derived from Wikipedia's DFS pseudocode to operate on our
 * road network.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class RoadDFS 
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
	
	public void yield(LinkedList<Intersection> path)
	{
		System.out.println("Path found!");
	}
	
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
	
	public LinkedList<Intersection> getNext()
	{
		return null;
	}
}
