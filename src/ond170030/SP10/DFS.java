/** Starter code for SP8
 *  @author
 */

// change to your netid
package ond170030.SP10;

import ond170030.SP10.Graph.Vertex;
import ond170030.SP10.Graph.Edge;
import ond170030.SP10.Graph.GraphAlgorithm;
import ond170030.SP10.Graph.Factory;
import ond170030.SP10.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    int topNum;
    int concomp;				//variable for storing the number of connected components
    boolean isCyclic;		// boolean variable to check if a particular vertex has cycles
    LinkedList<Graph.Vertex> finishList; // List to store the vertices visited

    public static class DFSVertex implements Factory {
        int cno;
        boolean seen;			//keeps track of vertices whether they are seen or not
        Vertex parent;
        boolean status;		//boolean variable to check the cycles in the graph

        public DFSVertex(Vertex u) {
            this.cno = 0;
        }

        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    /**
     * Static function to run depthFirstSearch.
     *
     * @param g of type Graph
     * @return DFS
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        return d;
    }


    public void dfs(Iterable<Vertex> iterable){
        finishList = new LinkedList<Vertex>();
        topNum = g.size();
        isCyclic = false;

        for(Vertex u: iterable){
            get(u).seen = false;
            get(u).status = false;
            get(u).parent = null;
        }
        //set number of connected components to 0
        concomp = 0;

        //visiting each vertex of the graph if not seen
        for(Vertex u : iterable){
            if(!get(u).seen){
                concomp++;
                dfsVisit(u);
            }
        }
    }


    /*
     * Helper function to update the cycle
     *
     */
    private void dfsVisit(Vertex u) {
        get(u).seen = true;
        get(u).status = true;
        get(u).cno = concomp;

        //checking if the adjacent vertices of u are visited
        for(Edge e: g.incident(u)){
            Vertex v = e.otherEnd(u);
            if(!isCyclic){
                // if parentseen is true the graph has cycles
                if(get(v).status) isCyclic = true;
            }
            if(!get(v).seen){
                get(v).parent = u;
                dfsVisit(v);
            }
        }

        finishList.addFirst(u);              // add each vertex visited to the starting of the list
        get(u).status = false;
    }


    public List<Vertex> topologicalOrder1() {
        //check if graph is directed, if not return null
        if(!g.isDirected())
            return null;
        this.dfs(g);

        //checks if there is a cycle in the graph and returns null, else returns the list
        if(isCyclic)
            return null;
        else
            return finishList;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }


    public static DFS stronglyConnectedComponents(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        List<Vertex> list = d.finishList;
        g.reverseGraph();
        d.dfs(list);
        g.reverseGraph();
        return d;
    }

    public int connectedComponents() {
        dfs(g);
        return concomp;
    }


    public int cno(Vertex u) {
        return get(u).cno;
    }

    public static void main(String[] args) throws Exception {
        String string = "11 16  1 11 1  11 4 1  4 9 1  9 11 1  4 1 1  11 3 1  3 10 1  10 6 1  6 3 1  2 3 1  2 7 1  7 8 1  8 2 1  5 4 1  5 8 1  5 7 1";
        //String string ="7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1";
        Scanner in;
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph
        Graph g = Graph.readGraph(in,true);
        g.printGraph(false);

        DFS d = stronglyConnectedComponents(g);
        System.out.println("Number of components: " + d.concomp + "\nu\tconnectedComponents");
        for(Vertex u: g) {
            System.out.println(u + "\t" + d.cno(u));
        }

    }
}