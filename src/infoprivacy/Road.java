package infoprivacy;

/**
 * Implements a road type.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class Road
{
	private static int roadcount = 0; // the number of roads created
	
	public static Road getInstance(int fromnode, int tonode)
	{
		Intersection from = Intersection.getInstance(fromnode);
		
		for(Road r : from.getRoads())
		{
			if(r.m_toNode == tonode)
			{
				return r;
			}
		}
		
		roadcount++;
		return new Road(fromnode, tonode);
	}
	
	public static final String UNKNOWN = "unknown";
	public static final String MOTORWAY = "motorway";
	public static final String MOTORWAY_LINK = "motorway_link";
	public static final String PRIMARY = "primary";
	public static final String PRIMARY_LINK = "primary_link";
	public static final String RESIDENTIAL = "residential";
	public static final String ROADTYPE = "roadtype";
	public static final String SECONDARY = "secondary";
	public static final String SECONDARY_LINK = "secondary_link";
	public static final String TERTIARY = "tertiary";
	public static final String TERTIARY_LINK = "tertiary_link";
	public static final String TRUNK = "trunk";
	public static final String TRUNK_LINK = "trunk_link";
	
	private String m_roadType = UNKNOWN;
	
	private final int m_fromNode;
	private final int m_toNode;
	private float m_length;
		
	private Road(int fromNode, int toNode)
	{
		m_fromNode = fromNode;
		m_toNode = toNode;
		
		getFrom().addRoad(this);
	}
	
	public Intersection getFrom()
	{
		return Intersection.getInstance(m_fromNode);
	}
	
	public Intersection getTo()
	{
		return Intersection.getInstance(m_toNode);
	}
	
	public String getRoadType()
	{
		return m_roadType;
	}
	
	@Override
	public String toString()
	{
		return "[Road from=" + m_fromNode + " to=" + m_toNode + " type=" + m_roadType + "]";
	}
	
	public void setRoadType(String roadType)
	{
		// we do this so we don't keep around five hundred thousand strings in
		// memory
		switch(roadType)
		{
		case MOTORWAY:
			m_roadType = MOTORWAY;
			break;
		case MOTORWAY_LINK:
			m_roadType = MOTORWAY_LINK;
			break;
		case PRIMARY:
			m_roadType = PRIMARY;
			break;
		case PRIMARY_LINK:
			m_roadType = PRIMARY_LINK;
			break;
		case RESIDENTIAL:
			m_roadType = RESIDENTIAL;
			break;
		case ROADTYPE:
			m_roadType = ROADTYPE;
			break;
		case SECONDARY:
			m_roadType = SECONDARY;
			break;
		case SECONDARY_LINK:
			m_roadType = SECONDARY_LINK;
			break;
		case TERTIARY:
			m_roadType = TERTIARY;
			break;
		case TERTIARY_LINK:
			m_roadType = TERTIARY_LINK;
			break;
		case TRUNK:
			m_roadType = TRUNK;
			break;
		case TRUNK_LINK:
			m_roadType = TRUNK_LINK;
			break;
		default:
			m_roadType = UNKNOWN;
		} 
	}
	
	/**
	Returns the expected speed limit of this road based on its road type
	returns -1 is the road type is unknown 
	also returns -1 if the road type is "ROADTYPE" (because I'm not sure what that means :P)
	 * 
	 * @return
	 */
	
	//TODO verify these speed limits
	public int getSpeedLimit()
	{
	
		int speedLimit;
		switch(this.getRoadType())
		{
		case MOTORWAY:
			speedLimit = 60;
			break;
		case MOTORWAY_LINK:
			speedLimit = 40;
			break;
		case PRIMARY:
			speedLimit = 50;
			break;
		case PRIMARY_LINK:
			speedLimit = 40;
			break;
		case RESIDENTIAL:
			speedLimit = 25;
			break;
		case ROADTYPE:
			speedLimit = -1;
			break;
		case SECONDARY:
			speedLimit = 40;
			break;
		case SECONDARY_LINK:
			speedLimit = 35;
			break;
		case TERTIARY:
			speedLimit = 30;
			break;
		case TERTIARY_LINK:
			speedLimit = 25;
			break;
		case TRUNK:
			speedLimit = 40;
			break;
		case TRUNK_LINK:
			speedLimit = 35;
			break;
		default:
			speedLimit = -1;
		}
		return speedLimit;
	}

	public void setLength(float length) {
		m_length = length;
	}
	
	/**
	 * Returns the road length in miles
	 * @return
	 */
	public float getLength()
	{
		return m_length;
	}

	public static int count() {
		return roadcount;
	}
}
