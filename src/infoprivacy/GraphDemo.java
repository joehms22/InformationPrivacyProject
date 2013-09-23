package infoprivacy;

import net.josephlewis.java.graph.Graph;
import net.josephlewis.java.graph.Node;
import net.josephlewis.java.graph.SparseDirectedGraph;

public class GraphDemo 
{
	
	/**
	 * Stores a thing
	 * @author Joseph Lewis <joehms22@gmail.com>
	 *
	 */
	public static class Entity{
		public String id;
		public Entity(String id)
		{
			this.id = id;
		}
	}

	public static void main(String[] args)
	{
		// Create the people
		Entity a = new Entity("Alice");
		Entity b = new Entity("Bob");
		Entity e = new Entity("Eve");
		
		// Create a new sparse directed graph
		Graph<Entity> g = new SparseDirectedGraph<>();
		
		// Add everyone as nodes to the graph
		Node<Entity> alice = g.createNode(a);
		Node<Entity> bob = g.createNode(b);
		Node<Entity> eve = g.createNode(e);
		
		// Edges will represent who can hear whom
		// alice + bob are talking on the phone
		g.addEdge(alice, bob);
		g.addEdge(bob, alice);
		g.addEdge(alice, eve); // eve is quietly listening to alice
		
		g.adjacent(alice, bob); // true
		g.adjacent(bob, eve); // false
		g.adjacent(eve, alice); // false
		
		g.neighbors(alice); // collection containing eve and bob
		
		g.deleteEdge(alice, eve);
		
		g.neighbors(alice); // collection containing only bob
		
		// iterate through all nodes
		for(Node<Entity> node : g)
		{
			System.out.println(node);
		}
		
		// iterate through all the items the nodes represent (entities)
		for(Node<Entity> node : g)
		{
			System.out.println(node.getValue().id);
		}
	}
}
