package sv.antifraud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * FastGraph.java
 * Graph implementation to store Integer values backed by an array for a faster access
 * Created by Sergey Vlasov on 11/4/2016.
 */

public final class FastGraph extends Graph {
    // symbol table: key = string vertex, value = set of neighboring vertices
    //private Map<Integer, Set<Integer>> adjacencyList;
    private Set<Integer>[] adjacencyList ;

    /**
     * Initializes an empty graph with no vertices or edges.
     */
    public FastGraph() {
        this(10);
    }

    /**
     * Initializes an empty graph with no vertices or edges.
     * @param initialSize size of the array
     */
    public FastGraph(int initialSize) {
        adjacencyList = new Set[initialSize+1];//<Integer>
    }

    // throw an exception if v is not a vertex
    private void validateVertex(Integer v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

    /**
     * Adds the edge v-w to this graph (if it is not already an edge).
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void addEdge(Integer v, Integer w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        //if (!hasEdge(v, w)) numEdges++;
        adjacencyList[v.intValue()].add(w);
        adjacencyList[w.intValue()].add(v);
    }

    /**
     * Adds vertex v to this graph (if it is not already a vertex).
     *
     * @param  v the vertex
     * TODO Set initial size of the HashSet based on average size in the dataset
     */
    private void addVertex(Integer v) {
        adjacencyList[v.intValue()] = new HashSet<Integer>();
    }

    /**
     * Returns the set of vertices adjacent to v in this graph.
     *
     * @param  v the vertex
     * @return the set of vertices adjacent to vertex {@code v} in this graph
     * @throws IllegalArgumentException if {@code v} is not a vertex in this graph
     */
    public Iterable<Integer> adjacentTo(Integer v) {
        validateVertex(v);
        return adjacencyList[v.intValue()];
    }

    /**
     * Returns true if v is a vertex in this graph.
     *
     * @param  v the vertex
     * @return {@code true} if {@code v} is a vertex in this graph,
     *         {@code false} otherwise
     */
    public boolean hasVertex(Integer v) {
        //System.out.println("v="+v.intValue()+" size="+adjacencyList.length);
        return adjacencyList[v.intValue()]!=null;
    }

    /**
     * Unit tests the {@code Graph} data type.
     */
    public static void main(String[] args) {

        // create graph
        Graph graph = new FastGraph(2);
        graph.addEdge(new Integer(1), new Integer(2));
        graph.addEdge(new Integer(0), new Integer(2));
        System.out.println(graph);

        Graph graph1 = new FastGraph(494670);
        graph1.addEdge(new Integer(49466), new Integer(6989));
        graph1.addEdge(new Integer(6989), new Integer(11302));
        System.out.println(graph1);

        Graph graph2 = new FastGraph(5);
        graph2.addEdge(new Integer(0), new Integer(1));
        graph2.addEdge(new Integer(1), new Integer(2));
        graph2.addEdge(new Integer(2), new Integer(3));
        graph2.addEdge(new Integer(3), new Integer(4));
        graph2.addEdge(new Integer(4), new Integer(5));
        System.out.println(graph2);
    }

}
