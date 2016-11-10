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
    private Set<Integer> visited = new HashSet<Integer>();
    private Queue<Integer> queue = new LinkedList<Integer>();
    private Graph graph;
    private int distance = 0;
    private int cutOffDistance;

    public BreadthFirstIterator(Graph g, Integer startingVertex, int cutOffDistance) {
        this.graph = g;
        this.queue.add(startingVertex);
        this.visited.add(startingVertex);
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
        return !this.queue.isEmpty();
    }

    @Override
    public Integer next() {
        //removes from front of queue
        Integer next = queue.remove();

        for (Integer neighbor : this.graph.adjacentTo(next)) {
            if (!this.visited.contains(neighbor)) {
                this.queue.add(neighbor);
                this.visited.add(neighbor);
            }
        }
        return next;
    }

    public int distanceTo(Integer endVertex){
        Queue<Integer> nextLevel = new LinkedList<Integer>();
        try {
            while (distance < cutOffDistance && this.hasNext()) {
                while (this.hasNext()) {
                    Integer next = queue.remove();
                    //System.out.println(next);
                    if (next.equals(endVertex)) return distance;
                    for (Integer neighbor : this.graph.adjacentTo(next)) {
                        if (!this.visited.contains(neighbor)) {
                            if (neighbor.equals(endVertex)) return distance + 1;
                            nextLevel.add(neighbor);
                            this.visited.add(neighbor);
                        }
                    }
                }
                this.queue = nextLevel;
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
