package sv.antifraud;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by admin on 11/6/2016.
 */

public class BreadthFirstIterator implements Iterator<Integer> {
    final private Set<Integer> visited = new HashSet<Integer>();
    private Queue<Integer> queue = new LinkedList<Integer>();
    private Graph graph;
    private int distance = 0;
    private int cutOffDistance;

    public BreadthFirstIterator(Graph g, Integer startingVertex, int cutOffDistance) {
        graph = g;
        queue.add(startingVertex);
        visited.add(startingVertex);
        this.cutOffDistance = cutOffDistance;
    }

    public BreadthFirstIterator(Graph g, Integer startingVertex) {
        this(g,startingVertex,Integer.MAX_VALUE); //No cut off for distance calculation
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public Integer next() {
        //removes from front of queue
        Integer next = queue.remove();
        for (Integer neighbor : graph.adjacentTo(next)) {
            if (!visited.contains(neighbor)) {
                queue.add(neighbor);
                visited.add(neighbor);
            }
        }
        return next;
    }

    /**
     * Returns distance from a root to a vertex in this graph.
     *
     * @param  endVertex the vertex
     * @return {@code distance} if {@code endVertex} is a vertex in this graph accessible from
     * startingVertex in less then cut-off steps defined as cutOffDistance,
     *         {@code -1} otherwise
     */

    public int distanceTo(Integer endVertex){
        Queue<Integer> nextLevel = new LinkedList<Integer>();
        try {
            while (distance < cutOffDistance && hasNext()) {
                while (hasNext()) {
                    Integer next = queue.remove();
                    if (next.equals(endVertex)) return distance;
                    for (Integer neighbor : graph.adjacentTo(next)) {
                        if (!visited.contains(neighbor)) {
                            //Are we there yet?
                            if (neighbor.equals(endVertex)) return distance + 1;

                            nextLevel.add(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
                queue = nextLevel;
                nextLevel = new LinkedList<Integer>();
                distance++;
            }
        }
        catch (IllegalArgumentException iae) {
            // not a vertex
        }
        return -1;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(new Integer(0), new Integer(1));
        graph.addEdge(new Integer(1), new Integer(2));
        graph.addEdge(new Integer(1), new Integer(5));
        graph.addEdge(new Integer(2), new Integer(3));
        graph.addEdge(new Integer(3), new Integer(4));
        graph.addEdge(new Integer(4), new Integer(5));
        graph.addEdge(new Integer(5), new Integer(6));
        System.out.println(graph);
        BreadthFirstIterator bfi;

        bfi = new BreadthFirstIterator(graph, new Integer(0));
        System.out.println("Distance 0 to 0 = 0 "+bfi.distanceTo(new Integer(0)));

        bfi = new BreadthFirstIterator(graph, new Integer(0));
        System.out.println("Distance 0 to 1 = 1 "+bfi.distanceTo(new Integer(1)));

        bfi = new BreadthFirstIterator(graph, new Integer(0));
        System.out.println("Distance 0 to 2 = 2 "+bfi.distanceTo(new Integer(2)));

        bfi = new BreadthFirstIterator(graph, new Integer(0),2);
        System.out.println("Distance 0 to 3 = 3 "+bfi.distanceTo(new Integer(3)));

        bfi = new BreadthFirstIterator(graph, new Integer(0),2);
        System.out.println("Distance 0 to 4 = 3 "+bfi.distanceTo(new Integer(4)));

        bfi = new BreadthFirstIterator(graph, new Integer(0));
        System.out.println("Distance 0 to 5 = 2 "+bfi.distanceTo(new Integer(5)));

        bfi = new BreadthFirstIterator(graph, new Integer(5));
        System.out.println("Distance 5 to 0 = 2 "+bfi.distanceTo(new Integer(0)));
    }
}
