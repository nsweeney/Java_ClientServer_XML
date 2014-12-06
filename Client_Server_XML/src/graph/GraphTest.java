package graph;

/**
 * Test harness for the Node class
 * 
 * @author Scott LaChance
 *
 */
public class GraphTest
{

	/**
	 * Main entry point
	 * @param args
	 */
	public static void main(String[] args)
	{
		log("Starting test ...");
		
		buildGraph();
		
		log("  cloning original node graph");
		Node clone = m_root.clone();
		
		log("  dumping original and cloned nodes");
		log(" Original");
		m_root.dump();
		log(" ======== ");
		log(" Cloned");
		clone.dump();
		log(" ======== ");
		
		if(!check(clone))
		{
			System.out.println("check failed");
		}
		
		log("Test complete");
	}
	
	/**
	 * This checks the two node graphs, specifically looking to verify
	 * that Node 2 and Node 4 point to the same Node 3 child instance.
	 * @param clone
	 * @return
	 */
	static boolean check(Node clone)
	{
		log("  verifying cloned node graph points to same child 3");
		boolean result = true;
		Node root = clone;
		Node child2 = root.getChildNodes().get(0);
		Node child3 = child2.getChildNodes().get(0);
		Node child4 = root.getChildNodes().get(1);
		Node child43 = child4.getChildNodes().get(0);
		
		if( child43 != child3 ) // verifies the object instance IDs are the same.
		{
			log("  Child 3 is different; should be same reference");
			result = false;
		}	
		else
		{
			log("  Child 3 is same reference");
		}
		
		return result;
	}
	
	/**
	 * Build the original node graph to clone
	 */
	static void buildGraph()
	{
		log("  building node graph...");
		m_root = new Node(1); 
		Node child2 = new Node(2);
		Node child3 = new Node(3);
		child2.addChild(child3);
		Node child4 = new Node(4);
		child4.addChild(child3);
		m_root.addChild(child2);
		m_root.addChild(child4);
	}
	
	private static void log(String msg)
	{
		System.out.println(msg);
	}
	static Node m_root;

}
