package sv.antifraud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 11/6/2016.
 */

public class Graph {

    // symbol table: key = string vertex, value = set of neighboring vertices
    private Map<Integer, Set<Integer>> st;

    // number of edges
    private int numEdges;

    /**
     * Initializes an empty graph with no vertices or edges.
     */
    public Graph() {
        st = new HashMap<Integer, Set<Integer>>();
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return st.size();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return numEdges;
    }

    // throw an exception if v is not a vertex
    private void validateVertex(Integer v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

    /**
     * Returns the degree of vertex v in this graph.
     *
     * @param  v the vertex
     * @return the degree of {@code v} in this graph
     * @throws IllegalArgumentException if {@code v} is not a vertex in this graph
     */
    public int degree(Integer v) {
        validateVertex(v);
        return st.get(v).size();
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
        if (!hasEdge(v, w)) numEdges++;
        st.get(v).add(w);
        st.get(w).add(v);
    }

    /**
     * Adds vertex v to this graph (if it is not already a vertex).
     *
     * @param  v the vertex
     */
    public void addVertex(Integer v) {
        if (!hasVertex(v)) st.put(v, new HashSet<Integer>());
    }


    /**
     * Returns the vertices in this graph.
     *
     * @return the set of vertices in this graph
     */
    public Iterable<Integer> vertices() {
        return st.keySet();
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
        return st.get(v);
    }

    /**
     * Returns true if v is a vertex in this graph.
     *
     * @param  v the vertex
     * @return {@code true} if {@code v} is a vertex in this graph,
     *         {@code false} otherwise
     */
    public boolean hasVertex(Integer v) {
        return st.keySet().contains(v);
    }

    /**
     * Returns true if v-w is an edge in this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @return {@code true} if {@code v-w} is a vertex in this graph,
     *         {@code false} otherwise
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *         is not a vertex in this graph
     */
    public boolean hasEdge(Integer v, Integer w) {
        validateVertex(v);
        validateVertex(w);
        return st.get(v).contains(w);
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return string representation of this graph
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Integer v : st.keySet()) {
            s.append(v + ": ");
            for (Integer w : st.get(v)) {
                s.append(w + " ");
            }
            s.append('\n');
        }
        return s.toString();
    }

    /**
     * Unit tests the {@code Graph} data type.
     */
    public static void main(String[] args) {

        // create graph
        Graph graph = new Graph();
        graph.addEdge(new Integer(0), new Integer(2));
        graph.addEdge(new Integer(1), new Integer(2));
        System.out.println(graph);

        Graph graph2 = new Graph();
        graph2.addEdge(new Integer(0), new Integer(1));
        graph2.addEdge(new Integer(1), new Integer(2));
        graph2.addEdge(new Integer(2), new Integer(3));
        graph2.addEdge(new Integer(3), new Integer(4));
        graph2.addEdge(new Integer(4), new Integer(5));
        System.out.println(graph2);

        /*
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            graph.addEdge(v, w);
        }

        // print out graph
        StdOut.println(graph);

        // print out graph again by iterating over vertices and edges
        for (String v : graph.vertices()) {
            StdOut.print(v + ": ");
            for (String w : graph.adjacentTo(v)) {
                StdOut.print(w + " ");
            }
            StdOut.println();
        }
*/
    }

}