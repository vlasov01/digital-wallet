package sv.antifraud.depricated;

import java.util.BitSet;

public class SmallGraph {

    private int numVertex;
    private int numEdges;
    private BitSet adj;

    public SmallGraph(int numVertex, int numEdges) {
        this.numVertex = numVertex;
        this.numEdges = numEdges;
        this.adj = new BitSet(numVertex * numVertex);
        for (int i=0;i<numVertex;i++)
            addEdge(i,i);
    }

    public void addEdge(int start, int end) {
        int index = start * numVertex + end;
        if (!adj.get(index)) {
            adj.set(index);
            index = end * numVertex + start;
            adj.set(index);
            numEdges++;
        }
    }

    public static final void main(String[] args){
        SmallGraph g = new SmallGraph(3,0);
        System.out.println("Initial graph:");
        System.out.println(g);
        g.addEdge(0,2);
        System.out.println("1 edge graph:");
        System.out.println(g);
        g.addEdge(1,2);
        System.out.println("2 edges graph:");
        System.out.println(g);

        final long startTime = System.currentTimeMillis();
        SmallGraph g2 = new SmallGraph(469180,0);
        final long endTime = System.currentTimeMillis();
        System.out.println("Time:"+(endTime-startTime));

    }

    public String toString() {
        StringBuffer sb = new StringBuffer(this.adj.length()+this.numEdges);
        int index=0;
        for(int i=0;i<this.numVertex;i++){
            for(int j=0;j<this.numVertex;j++){
                sb.append(adj.get(index++)?"X":"O");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}