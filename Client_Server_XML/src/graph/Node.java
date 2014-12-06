package graph;
import java.util.ArrayList;
import java.util.HashMap;;

/**
 * Node class that supports deep cloning
 * 
 * @author Scott LaChance
 *
 */
public class Node
{
	/**
	 * Constructor
	 * @param data
	 */
	public Node(int data)
	{
		m_childNodes = new ArrayList<Node>();
		m_data = data;
	}
	
	/**
	 * Add a child to this node
	 * @param n
	 */
	public void addChild(Node n)
	{
		m_childNodes.add(n);
	}
	
	/**
	 * Makes a deep copy of this node
	 * walking all its children and establishing 
	 * the appropriate relationships
	 */
	public Node clone()
	{
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		return walk(this, map);
	}
	
	/**
	 * Getter for the child nodes of this node
	 * @return
	 */
	public ArrayList<Node> getChildNodes()
	{
		return m_childNodes;
	}
	
	/**
	 * Helper method to walk the graph.
	 * It doesn't handle cycles
	 * It uses recursion to walk the graph
	 * @param n Starting node
	 * @param map Map of found nodes 
	 * @return A copy of the starting node and all it's children
	 */
	private Node walk(Node n, HashMap<Node, Node> map)
	{
		Node newNode = null;
		
		// in the case where multiple parents point to the same
		// child, we need to detect that the child has already
		// been created and then associate
		// We do this by mapping the original node to its clone.
		// When we encounter an original node that has already been cloned,
		// we simply assign it here. 
		if(map.containsKey(n))
		{
			newNode = map.get(n); // use existing
		}
		else
		{
			// First time encountering this original node, make a copy
			newNode = new Node(n.m_data);
			map.put(n,  newNode);
		}
		
		for(Node node : n.getChildNodes())
		{
			newNode.addChild(walk(node, map));
		}
		
		return newNode;
	}
	
	/**
	 * Helper method for debugging
	 */
	public void dump()
	{
		dumpWalk(this, 0);
	}
	
	/**
	 * Simply walks the node graph and generates a formatted display
	 * @param n
	 * @param indent
	 */
	private void dumpWalk(Node n, int indent)
	{
		String space = "";
		for(int i=0; i < indent;i++)
		{
			space += " ";
		}
		String msg = String.format("%sNode %d - %s", space, n.m_data, n.toString());
		System.out.println(msg);
		for(Node node : n.getChildNodes())
		{
			dumpWalk(node, indent + 1);
		}		
	}
	
	private ArrayList<Node> m_childNodes;
	private int m_data;
}
