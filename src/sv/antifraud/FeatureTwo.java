package sv.antifraud;

/**
 * a program that detects suspicious transactions
 * fraud detection algorithm
 * Feature 2
 * Created by Sergey Vlasov on 11/4/2016.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class FeatureTwo extends AntifraudEngine {
    public static void main(String[] args){
        outputFile = new File(baseOutputFilename+"output2.txt");
        configIO(args);
        FeatureTwo engine = new FeatureTwo();
        engine.execute(outputFile);
    }

    public void runValidator(final BufferedWriter bw) throws IOException {

        Graph graph = new Graph();

        readFile(batchPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {graph.addEdge(new Integer(x), new Integer(y));}
        });

        //max 77372 min 0 median 38456
        readFile(streamPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {
                BreadthFirstIterator bfi = new BreadthFirstIterator(graph, new Integer(x),2);
                try {
                    if (bfi.distanceTo(new Integer(y))!=-1) {
                        bw.write("trusted\n");
                        trusted++;
                    }
                    else {
                        bw.write("unverified\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //System.out.println("set size="+set.size());
    }
}
