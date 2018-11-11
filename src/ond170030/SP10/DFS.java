package ond170030.SP10;

import ond170030.SP10.Graph.Edge;
import ond170030.SP10.Graph.Factory;
import ond170030.SP10.Graph.GraphAlgorithm;
import ond170030.SP10.Graph.Vertex;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	boolean iscyclic;
    public static class DFSVertex implements Factory {
        int cno;
        boolean seen; 
        boolean status;
        Vertex parent;
        public DFSVertex(Vertex u) {
            this.seen = false;
            this.status = false;
        }
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        return d.dfs();
    }

    private DFS dfs() {
        LinkedList<Vertex> finishedList = new LinkedList<>();
        for(Vertex u: g){
            get(u).seen = false;
            get(u).parent = null;
        }
        for(Vertex u: g){
            if(get(u).seen){

            }
        }
        return null;
    }

    // Member function to find topological order

    public List<Vertex> topologicalOrder1() {
        if(!g.isDirected()){
            return null;
        }

        LinkedList<Vertex> list = new LinkedList<>();
        Set<Vertex> set = new HashSet<Vertex>();

        for(Vertex u : g){
        	if(!get(u).seen) {
        		this.topohelper(u, list);
        	}
        }
        if(list.size() != g.size()) {
        	return null;
        }
        return list;
    }

    public void topohelper(Vertex v, LinkedList<Vertex> list){
        if(get(v).status) {
        	iscyclic = true;
        	list = null;
        	return;
        }
        if(!get(v).seen) {
        	get(v).seen = true;
        	get(v).status = true;
        	for(Edge e : g.incident(v)){
                Vertex toVertex = e.otherEnd(v);
                topohelper(toVertex, list);

        }
        	if(!iscyclic) {
        	list.addFirst(v);
        	}
        	get(v).status = false;
        }

    }

    public static DFS stronglyConnectedComponents(Graph g) {
        return null;
    }



    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
    	String string = "10 12   1 3 2   1 8 3   3 2 5   8 2 4   2 4 1   4 7 7   8 5 1   5 4 1   5 10 1   6 8 4   6 10 9   10 9 0"; //DAG
//      String string = "4 6   1 2 2   2 3 3   3 1 1   1 3 3   3 4 4   4 4 4";                                                    //Not a Dag
//      String string = "5 5   1 3 3    1 2 3   2 4 3   4 5 5   5 2 1";                                                           //Not a Dag
//      String string = "8 8   1 3 1   2 3 1   3 4 1   2 5 1   5 7 1   4 6 1   4 7 1   7 8 1";                                                  //Not a Dag
//        String string = "8 8   1 3 1   2 3 1   3 4 1   2 5 1   5 7 1   4 6 1   4 7 1   7 8 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        DFS d = new DFS(g);
        System.out.println(d.topologicalOrder1(g));
        /*int numcc = d.connectedComponents();
        System.out.println("Number of components: " + numcc + "\nu\tcno");
        for(Vertex u: g) {
            System.out.println(u + "\t" + d.cno(u));
        }*/
    }
}