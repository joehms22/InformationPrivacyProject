package infoprivacy;

import java.util.LinkedList;

public abstract class Plugin 
{
	private final String m_name;
	
	public Plugin(String name)
	{
		m_name = name;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * Returns a value that ranks how likely the given path is to the data
	 * @return
	 */
	public abstract float rankPath(LinkedList<Intersection> path, RouteData route);
	
	/**
	 * Modifies a route to make it more private.
	 * @param route
	 */
	public abstract void modifyRoute(RouteData route);

}
