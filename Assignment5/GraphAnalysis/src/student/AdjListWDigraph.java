package student;

import java.io.*;		
import java.util.*;

/**
 * Adjacency list implementation of a weighted directed graph.
 * Parallel edges NOT allowed.
 * 
 * @author konstantinos
 *
 */
public class AdjListWDigraph {
	int numVertices;
	ArrayList<Hashtable<Integer,Double>> edges;
	boolean[] reach = new boolean[numVertices];
	
	public AdjListWDigraph(int n) {
		numVertices = n;
		edges = new ArrayList<Hashtable<Integer,Double>>(n);
		for (int i=0; i<n; i++)
			edges.add(new Hashtable<Integer,Double>());
	}
	
	public int getNumVertices() {
		return numVertices;
	}

	public boolean isThereEdge(int u, int v) {
		return edges.get(u).containsKey(v);
	}
	
	public boolean addEdge(int u, int v) {
		return addEdge(u,v,1.0);
	}

	public boolean addEdge(int u, int v, double weight) {
		if (edges.get(u).containsKey(v)) return false;
		edges.get(u).put(v,weight);
		return true;
	}
	
	public double getEdgeWeight(int u, int v) {
		if (!isThereEdge(u,v)) return -1;
		return edges.get(u).get(v);
	}
	
	// Checks if the graph is symmetric.
	public boolean isSymmetric() {
		for (int i=0; i<numVertices; i++) {
			Set<Map.Entry<Integer,Double>> successors = edges.get(i).entrySet();
			for (Map.Entry<Integer,Double> entry: successors) {
				int j = entry.getKey();
				double w = entry.getValue();
				if (!isThereEdge(j,i)) return false;
				if (getEdgeWeight(j,i) != w) return false;
			}
		}
		
		return true;
	}
	
	private class Triple implements Comparable<Triple> {
		public int vertex;
		public double d;
		public int prev;
		
		public Triple (int v, double d, int prev) {
			vertex = v;
			this.d = d;
			this.prev = prev;
		}
		
		public int compareTo(Triple o) {
			if (d < o.d)
				return -1;
			else if (d == o.d)
				return (vertex - o.vertex);
			else // d > o.d
				return 1;
		}
		
		public String toString() {
			return "(" + vertex + "," + d + "," + prev + ")";
		}
	}
	
	public boolean[] reachable(int u) {
		Hashtable<Integer, Double> temp = edges.get(u);
		for (Enumeration<Integer> e = temp.keys() ; e.hasMoreElements() ;) {
			int next = e.nextElement();
	        reach[next] = true;
	        reachable(next);
	     }
		return reach;
	}
	
	public int connectedComponents() {
		int start = 0;
		int count = 0;
		int num = 0;
		while (start <= numVertices) {
			boolean[] ct = reachable(start);
			for (int i = 0; i <= ct.length; i++) {
				if (ct[i] == true)
					count++;
			}
			if (count >= num)
				num = count;
			start++;
			count = 0;
		}
		return num;
	}
	
	public double shortestPath(int source, int destination) {
		if (edges.get(source) == null || edges.get(source) == null)
			return -1.0;
		ArrayList<Integer> tobe = new ArrayList<Integer>();
		int[] visited = new int[numVertices];
		double[] dist = new double[numVertices];
		Arrays.fill(dist, Double.POSITIVE_INFINITY);
		dist[source] = 0;
		int num = source;
		boolean stop = false;
		while (stop == false) {
			Hashtable<Integer, Double> temp = edges.get(num);
			for (Enumeration<Integer> e = temp.keys() ; e.hasMoreElements() ;) {
				int next = e.nextElement();
				visited[next]++;
				tobe.add(next);
				if (dist[next] > dist[num] + temp.get(next))
					dist[next] = dist[num] + temp.get(next);
				if (next == destination || visited[next] >= numVertices - 1)
					stop = true;
			}
			num = tobe.get(0);
		}
		return dist[destination];
	}

	public LinkedList<Integer> tour(LinkedList<Integer> toVisit) {
		boolean[] visited = new boolean[toVisit.size()];
		double[][] distances = new double[toVisit.size() - 1][toVisit.size() - 1];
		LinkedList<Integer> tourmap = new LinkedList<Integer>();
		int place = 0;
		double distance = 0; 
		while (place <= toVisit.size()) {
			for (int i = 0; i <= toVisit.size(); i++) {
				if (i != place) {
					distance = shortestPath(place, i);
					if (distance == Double.POSITIVE_INFINITY)
						return null;
					distances[place][i] = distance;
				}
			}
			place++;
		}
		place = 0;
		visited[place] = true;
		tourmap.add(place);
		double count = 0;
		int y = 0;
		while (place <= toVisit.size()) {
			for (int j = 0; j <= toVisit.size(); j++) {
				if (count >= distances[place][j] && visited[j] == false) {
					count = distances[place][j]; 
					y = j;
				}
			}
			tourmap.add(y);
			visited[y] = true;
			place = y;
		}
		return tourmap;
	}
	
	public AdjListWDigraph MST() {
		AdjListWDigraph spanningtree = new AdjListWDigraph(numVertices);
		boolean cont = true;
		int num = 0;
		int v = 0;
		double count = 0;
		while (cont) {
			Hashtable<Integer, Double> temp = edges.get(num);
			for (Enumeration<Integer> e = temp.keys(); e.hasMoreElements() ;) {
				if (count >= temp.get(e.nextElement())) {
					count = temp.get(e.nextElement());
					v = e.nextElement();
				}
			}
			spanningtree.addEdge(num, v);
			num = v;
		}
		return null;
	}
	
	public String toString() {
		String str = "n = " + numVertices + "\n";
		for (int k=0; k<numVertices; k++) {
			str += "vtx " + k + ": [";
			Map.Entry<Integer,Double>[] succ = new Map.Entry[0];
			succ = edges.get(k).entrySet().toArray(succ);
			if (succ.length > 0) {
				int v = succ[0].getKey();
				double weight = succ[0].getValue();
				str += v + ":" + weight;
			}
			for (int i=1; i<succ.length; i++) {
				int v = succ[i].getKey();
				double weight = succ[i].getValue();
				str += ", " + v + ":" + weight;
		    }
			str += "]\n";
		}
		return str;
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String inputFile = null;
		if (args.length < 1){
			System.out.println("Provide path to txtfile for the graph:");
			inputFile = console.readLine().trim();
		} else {
			inputFile = args[0];
		}
		
		Scanner graphScanner = null;
		try {
			graphScanner= new Scanner(new File(inputFile));
		} catch(FileNotFoundException e){
			e.printStackTrace();
			return;
		}
		
		int numberOfVertices = 0;
		if(graphScanner.hasNext()){
			String nv = graphScanner.nextLine();
			if(!nv.equals(""))
				numberOfVertices = Integer.parseInt(nv);
		}
		
		AdjListWDigraph g1 = new AdjListWDigraph(numberOfVertices);
		while(graphScanner.hasNextLine()){
			String line = graphScanner.nextLine();
			String[] terms = line.split("\t");
			if (terms.length < 2) continue;
			int fromNode = Integer.parseInt(terms[0]);
			int toNode = Integer.parseInt(terms[1]);
			double weight = -1.0;
			if (terms.length > 2){
				weight = Double.parseDouble(terms[2]);
			}
			if (weight>0.0)	g1.addEdge(fromNode, toNode, weight);
			else g1.addEdge(fromNode, toNode);
		}
		
		//System.out.println(g1);

		while (true) {
			System.out.println("\nInput your command:\n"
					+ "\treachable <node ID>          - Finds the nodes reachable from this node.\n"
					+ "\tconnected                	- Prints number of connected components in graph.\n"
					+ "\tshortestpath <source ID> <destination ID>       - Prints length of shortest path from source to dest.\n"
					+ "\tmst      					- Creates the minimum spanning tree and prints it.\n"
					+ "\ttour <node1> <node2> ...       - Prints tour going through node1, node2 etc.\n"
					+ "\texit                           - Exits the program."
					+ "");
			String temp = console.readLine().trim();
			String[] splitted = temp.split(" ");
			
			if (splitted[0].equals("reachable")) {
				if (splitted.length <2) continue;
				int index = Integer.parseInt(splitted[1]);
				boolean[] reached = g1.reachable(index);
				System.out.println(Arrays.toString(reached));
			} else if (splitted[0].equals("connected")) {
				int numComponents = g1.connectedComponents();
				System.out.println("Number of connected components:"+numComponents);
			} else if (splitted[0].equals("shortestpath")) {
				if (splitted.length <3) continue;
				int source = Integer.parseInt(splitted[1]);
				int dest = Integer.parseInt(splitted[2]);
				double shortestDist = g1.shortestPath(source, dest);
				System.out.println("Shortest path from "+source+" to "+dest+" is "+shortestDist);
			} else if (splitted[0].equals("mst")) {
				AdjListWDigraph mst = g1.MST();
				System.out.println(mst.toString());
			} else if (splitted[0].equals("tour")) {
				LinkedList<Integer> inputList = new LinkedList<Integer>();
				for(int i=1; i<splitted.length;i++){
					int nextNode = Integer.parseInt(splitted[i]);
					inputList.add(nextNode);
				}
				LinkedList<Integer> outputList = g1.tour(inputList);
				System.out.println(outputList.toString());
			} else if (splitted[0].equals("exit")) {
					break;
			} else {
				System.out.println("Incorrect input. Please try again.");
			}

		}
	}
	
}
