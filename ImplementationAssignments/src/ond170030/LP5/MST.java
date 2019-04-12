// Starter code for LP5

package ond170030.LP5;

import ond170030.LP5.Graph.Vertex;
import ond170030.LP5.Graph.Edge;
import ond170030.LP5.Graph.GraphAlgorithm;
import ond170030.LP5.Graph.Factory;
import ond170030.LP5.Graph.Timer;

import ond170030.LP5.BinaryHeap.Index;
import ond170030.LP5.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;
    
    MST(Graph g) {
	super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
	MSTVertex(Vertex u) {
	}

	MSTVertex(MSTVertex u) {  // for prim2
	}

	public MSTVertex make(Vertex u) { return new MSTVertex(u); }

	public void putIndex(int index) { }

	public int getIndex() { return 0; }

	public int compareTo(MSTVertex other) {
	    return 0;
	}
    }

    public long kruskal() {
	algorithm = "Kruskal";
	Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }

    public long prim3(Vertex s) {
	algorithm = "indexed heaps";
        mst = new LinkedList<>();
	wmst = 0;
	IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
	return wmst;
    }

    public long prim2(Vertex s) {
	algorithm = "PriorityQueue<Vertex>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<MSTVertex> q = new PriorityQueue<>();
	return wmst;
    }

    public long prim1(Vertex s) {
	algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<Edge> q = new PriorityQueue<>();
	return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
	MST m = new MST(g);
	switch(choice) {
	case 0:
	    m.kruskal();
	    break;
	case 1:
	    m.prim1(s);
	    break;
	case 2:
	    m.prim2(s);
	    break;
	default:
	    m.prim3(s);
	    break;
	}
	return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	int choice = 0;  // Kruskal
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

	if (args.length > 1) { choice = Integer.parseInt(args[1]); }

	Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

	Timer timer = new Timer();
	MST m = mst(g, s, choice);
	System.out.println(m.algorithm + "\n" + m.wmst);
	System.out.println(timer.end());
    }
}