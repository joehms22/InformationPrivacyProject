package infoprivacy;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException
	{
		// Load the road network
		new RoadNetworkReader(Paths.get("denver.roadnetwork"));
		
		// Give stats about the system
		System.out.println("Number of Roads: " + Road.count());
		System.out.println("Number of Intersections: " + Intersection.count());
		
		new RoadDFS(150, Intersection.getInstance(100000));
	}
}
