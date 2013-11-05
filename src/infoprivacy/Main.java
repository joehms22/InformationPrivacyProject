package infoprivacy;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException
	{
		// Load the road network
		new RoadNetworkReader(Paths.get("denver.roadnetwork"));
		
		// Give stats about the system
		System.out.println("Number of Roads: " + Road.count());
		System.out.println("Number of Intersections: " + Intersection.count());
		
		//test route data
		int[] dummy = {1,2,3,4,5};
		RouteData theData = new RouteData(dummy);
		System.out.println("The distance of this route is" + theData.getTripDistance());
		
		for(LinkedList<Intersection> path : new RoadDFS(150, Intersection.getInstance(100000)))
		{
			System.out.println("------------------------------------------------------");
			
			StringBuilder sb = new StringBuilder();
			for(Intersection i : path)
			{
				sb.append(i);
			}
			
			System.out.println(sb);
		}
	}
}
