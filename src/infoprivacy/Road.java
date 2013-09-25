package infoprivacy;

/**
 * Implements a road type.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class Road
{
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
	
	private final String roadType = UNKNOWN;
	
	private final int m_fromNode;
	private final int m_toNode;
		
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
		return roadType;
	}
	
	public void setRoadType(String roadType)
	{
		// we do this so we don't keep around five hundred thousand strings in
		// memory
		switch(roadType)
		{
		case MOTORWAY:
			roadType = MOTORWAY;
			break;
		case MOTORWAY_LINK:
			roadType = MOTORWAY_LINK;
			break;
		case PRIMARY:
			roadType = PRIMARY;
			break;
		case PRIMARY_LINK:
			roadType = PRIMARY_LINK;
			break;
		case RESIDENTIAL:
			roadType = RESIDENTIAL;
			break;
		case ROADTYPE:
			roadType = ROADTYPE;
			break;
		case SECONDARY:
			roadType = SECONDARY;
			break;
		case SECONDARY_LINK:
			roadType = SECONDARY_LINK;
			break;
		case TERTIARY:
			roadType = TERTIARY;
			break;
		case TERTIARY_LINK:
			roadType = TERTIARY_LINK;
			break;
		case TRUNK:
			roadType = TRUNK;
			break;
		case TRUNK_LINK:
			roadType = TRUNK_LINK;
			break;
		default:
			roadType = UNKNOWN;
		} 
	}
}
