package graphs;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties 
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated 
 * with a vertex. 
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> 
{
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph()
	{
		adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
		dataMap = new HashMap<String, E>();
	}
	/**Adds a vertex to the graph by adding to the adjacency map an entry for the vertex. 
	 * This entry will be an empty map. An entry in the dataMap will store the provided data.
	 */
	//IllegalArgumentException - If the vertex already exists in the graph. Use any error message.
	public void addVertex(String vertexName, E data)
	{
		if(adjacencyMap.containsKey(vertexName)) throw new IllegalArgumentException();
		adjacencyMap.put(vertexName, new HashMap<String, Integer>());
		dataMap.put(vertexName, data);
	}
	/**Adds or updates a directed edge with the specified cost.**/
	//IllegalArgumentException - If any of the vertices are not part of the graph. Use any error message.
	public void addDirectedEdge(String startVertexName, String endVertexName, int cost)
	{
		if(!dataMap.containsKey(startVertexName) || !dataMap.containsKey(endVertexName))
			throw new IllegalArgumentException();
		adjacencyMap.get(startVertexName).put(endVertexName, cost);
		//HashMap<String, HashMap<String, Integer>
	}
	/**
	 * Returns a string with information about the Graph. 
	 * Notice that vertices are printed in sorted order and information 
	 * about adjacent edges is printed in sorted order (by vertex name). 
	 * You may not use Collections.sort or Arrays.sort in order to implement this method. 
	 * See the sample output for formatting details. return string with graph information
	 * 
	 * An example of such a class is PrintCallBack (which you will find in the graph package). 
	 * This class is used to generate the string that represents the path we follow when performing 
	 * a breadth-first search or a depth-first search. 
	 */
	//Vertex: ST, Vertex Data: 1000.5
	public String toString()
	{	
		Set<String> ans1 = new TreeSet<String>();
        Set<String> ans2 = new TreeSet<String>();
       
        ans2.addAll(adjacencyMap.keySet());
        
        String answer = "Vertices: " + ans2.toString();
        	   answer += "\nEdges:\n";
        
        for(String a : adjacencyMap.keySet()) 
            ans1.add("Vertex(" + a + ")--->" + adjacencyMap.get(a).toString() + "\n");
        
        for(String res : ans1) 
            answer += res;
       
        return answer;
	}
	/**Returns a map with information about vertices adjacent to vertexName. 
	 * If the vertex has no adjacent, an empty map is returned.
	 */
	public Map<String, Integer> getAdjacentVertices(String vertexName)
	{
		return this.adjacencyMap.get(vertexName);
	}
	/**Returns the cost associated with the specified edge.**/
	//IllegalArgumentException - If any of the vertices are not part of the graph. Use any error message.
	public int getCost(String startVertexName, String endVertexName)
	{
		if(!dataMap.containsKey(startVertexName) || !dataMap.containsKey(endVertexName))
			throw new IllegalArgumentException();
		return adjacencyMap.get(startVertexName).get(endVertexName);
	}
	/**Returns a Set with all the graph vertices.**/
	public Set<String> getVertices()
	{
		return dataMap.keySet();
	}
	//IllegalArgumentException - If the vertex is not part of the graph. Use any error message.
	/**Returns the data component associated with the specified vertex.**/
	public E getData(String vertex)
	{
		if(!dataMap.containsKey(vertex))
			throw new IllegalArgumentException();
		return dataMap.get(vertex);
	}
	//IllegalArgumentException - If the vertex is not part of the graph. Use any error message.
	/**Computes Depth-First Search of the specified graph.
	 * Each time we reach a vertex, the implementation of DFS and BFS 
	 * is expected to call the processVertex method to apply whatever processing needs to be done to the vertex. 
	 * We have implemented PrintCallBack for you (don't modify it).
	 */
	public void doDepthFirstSearch(String startVertexName, CallBack<E> callback)
	{ // DeapthFirst = Stack
		if(!dataMap.containsKey(startVertexName)) throw new IllegalArgumentException();

		ArrayDeque<String> stack = new ArrayDeque<String>(); //FILO
		ArrayList<String> visit = new ArrayList<String>(); 
        
        stack.push(startVertexName);
        
       while(!stack.isEmpty())
       {
    	   String vertex = stack.pop(); // remove the object on top of the stack
    	   if(!visit.contains(vertex))
    	   { //call the processVertex method to apply whatever processing needs to be done to the vertex
    		   callback.processVertex(vertex, dataMap.get(vertex));
               for(String a:adjacencyMap.get(vertex).keySet()) 
                   stack.push(a);
               
               visit.add(vertex);
    	   }
       }
	}
	//IllegalArgumentException - If the vertex is not part of the graph. Use any error message.
	/**Computes Breadth-First Search of the specified graph.**/
	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callback)
	{ // BreadthFirst = Queue
		if(!dataMap.containsKey(startVertexName)) throw new IllegalArgumentException();

		ArrayDeque<String> queue = new ArrayDeque<String>(); //FIFO
		ArrayList<String> visit = new ArrayList<String>();
		
		queue.add(startVertexName); 

		while(!queue.isEmpty())
		{
			String vertex = queue.poll(); // poll - retrieve and remove the first element of the queue
			if(!visit.contains(vertex))
			{
				callback.processVertex(vertex, dataMap.get(vertex));
				for(String a:adjacencyMap.get(vertex).keySet())
					queue.add(a);
				
				visit.add(vertex);
			}
		}	
	}
	//IllegalArgumentException - If any of the vertices are not part of the graph. Use any error message.
	/**Computes the shortest path and shortest path cost using Dijkstras's algorithm. 
	 * It initializes shortestPath with the names of the vertices corresponding to the shortest path. 
	 * If there is no shortest path, shortestPath will be have entry "None".
	
	 *If no path is found while executing Dijkstra's algorithm, 
	 *the ArrayList representing the path will have the entry "None" and return -1 in this case.
	 */
	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath)
	{ //Dijkstra = Priority Queue
		if(!dataMap.containsKey(startVertexName) || !dataMap.containsKey(endVertexName))
			throw new IllegalArgumentException();

	     ArrayList<String> visit = new ArrayList<String>();
	     final Map<String, Integer> cost = new TreeMap<>();
	     Map<String, String> root = new TreeMap<>();
	       
	     for(String vertex : dataMap.keySet()) 
	     {	
	         cost.put(vertex, -1); // set it to -1 to find the vertex with no path (better than having 0)
	         root.put(vertex, "None"); // path = none to start with
	     }  
	     //anonymous class. yes!!
	     	Comparator<String> comparator = new Comparator<String>()
	     	{
	     		public int compare(String x, String y) 
	     		{
	     			return cost.get(x).compareTo(cost.get(y));
	     		}    
	     	}; // need to have in order to have the items in sorted order!!
	     	PriorityQueue<String> prior = new PriorityQueue<String>(comparator);	
	     	
	     	cost.put(startVertexName, 0); // set the cost 0 to start the algorithm with   
	        prior.add(startVertexName); 
	       
	        while(!visit.contains(endVertexName) && prior.size() != 0) 
	        {
	             String minPath = prior.poll(); //poll - retrieve and remove the first element            
	             visit.add(minPath); 

	            for(String vertex : adjacencyMap.get(minPath).keySet()) 
	            {
	                if(!visit.contains(vertex))
	                {
	                        int initCost = cost.get(vertex);
	                        int newCost = getCost(minPath, vertex) + cost.get(minPath);
	                        //getCost(String startVertexName, String endVertexName)
	                        if(initCost == -1 || initCost > newCost)
	                        {
	                            cost.put(vertex, newCost); // update the cost (curr cost being minimum) -int
	                            root.put(vertex, minPath); // update the path (all path saved) -string
	                           
	                            prior.add(vertex); // adding adjacency vertices
	                        }
	                }
	            }
	        }
	       
	        Stack<String> path = new Stack<>();
	        String currVertex = path.push(endVertexName);
	       
	        if(cost.get(currVertex) == -1) 
	        { //root.get(endVertexName).equals("None") doesn't work for 'ST'
	            shortestPath.add("None");
	            return -1;
	        }
	        else
	        {
	        	while(!root.get(currVertex).equals("None")) 
	        	{
	        		currVertex = root.get(currVertex);
	        		path.push(currVertex);      		
	        	}
	        	while(path.size() != 0)
	        		shortestPath.add(path.pop());
	        }     
	        return cost.get(endVertexName);
	}
}
