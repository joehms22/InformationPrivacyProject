package infoprivacy.simulator.graph;

import infoprivacy.DelimitedFileReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reads a RoadNetwork file and parses its roads and intersections out.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 * @license BSD 3 Clause License
 */
public class RoadNetworkReader
{
	private static final String LINE_DELIMITER = " ";

	private final Path m_roadNetworkBaseFolder;
	private String m_roadNetworkBaseName;
	
	public RoadNetworkReader(Path roadNetworkBaseFolder) throws IOException
	{
		m_roadNetworkBaseFolder = roadNetworkBaseFolder;
		
		// Get the base name for this network e.g. "denver" from "denver.roadnetwork"
		m_roadNetworkBaseName = roadNetworkBaseFolder.getFileName().toString();
		int endIndex = m_roadNetworkBaseName.indexOf(".roadnetwork");
		if(endIndex != 0)
		{
			m_roadNetworkBaseName = m_roadNetworkBaseName.substring(0,endIndex);
		}
		
		// no specific order is needed here, but this might be easier
		// than others as it is probably the way we'd logically do it.
		readNodes();
		readAdjacency();
		readEdgeLength();
		readEdgeType();
		
	}
	
	private void readNodes() throws IOException
	{
		Path f = Paths.get(m_roadNetworkBaseFolder.toString(), m_roadNetworkBaseName + ".nodes.dat");
		
		for(String[] line : new DelimitedFileReader(f, LINE_DELIMITER, 1))
		{
			int id = parseInt(line[0]);
			float lat = Float.parseFloat(line[1]);
			float lon = Float.parseFloat(line[2]);
			
			Intersection sect = Intersection.getInstance(id);
			sect.setLatLon(lat, lon);
		}
		
	}
	
	private void readAdjacency() throws IOException
	{
		Path f = Paths.get(m_roadNetworkBaseFolder.toString(), m_roadNetworkBaseName + ".adjacency.dat");
		
		for(String[] line : new DelimitedFileReader(f, LINE_DELIMITER, 1))
		{
			int origId = parseInt(line[0]);
			
			for(int i = 2; i < line.length; i++)
			{
				int toId = parseInt(line[i]);
				Road.getInstance(origId, toId);
			}
		}
	}
	
	private int parseInt(String intStr)
	{
		try
		{
			return Integer.parseInt(intStr);
		}
		catch(NumberFormatException ex)
		{
			String[] parts = intStr.toLowerCase().split("e");
			
			return (int)(Integer.parseInt(parts[0]) * Math.pow(10, Integer.parseInt(parts[1])));
		}
	}
	
	private void readEdgeLength() throws IOException
	{
		Path f = Paths.get(m_roadNetworkBaseFolder.toString(), m_roadNetworkBaseName + ".edgelength.dat");
		
		for(String[] line : new DelimitedFileReader(f, LINE_DELIMITER, 1))
		{
			int from = parseInt(line[0]);
			int to = parseInt(line[1]);
			float length = Float.parseFloat(line[2]);
			
			
			Road.getInstance(from, to).setLength(length);
		}
	}
	
	private void readEdgeType() throws IOException
	{
		Path f = Paths.get(m_roadNetworkBaseFolder.toString(), m_roadNetworkBaseName + ".edgetype.dat");
		
		for(String[] line : new DelimitedFileReader(f, LINE_DELIMITER, 1))
		{
			int from = parseInt(line[0]);
			int to = parseInt(line[1]);
			
			
			Road.getInstance(from, to).setRoadType(line[2]);
		}
	}
}