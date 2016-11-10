package sv.antifraud;

/**
 * a program that detects suspicious transactions
 * fraud detection algorithm
 * Created by Sergey Vlasov on 11/4/2016.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class FeatureThree extends AntifraudEngine {

    private static final File outputFile = new File(baseOutputFilename+"output3.txt");

    public static void main(String[] args){
        FeatureThree engine = new FeatureThree();
        engine.execute(outputFile);
    }

    public void runValidator(final BufferedWriter bw) throws IOException {
        //final Set<Long> set = new HashSet<Long>(4000000); //Improve speed to bootstrap initial state
        //final Set<Long> cache = new HashSet<Long>(4000000); //Improve speed of graph
        Graph graph = new FastGraph(700000);

        readFile(batchPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {
                graph.addEdge(new Integer(x), new Integer(y));
                //set.add(Transformer.toOrderedLong(x,y));
            }
        });

        //max 77372 min 0 median 38456
        final long startTime = System.currentTimeMillis();
        readFile(streamPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {
                BreadthFirstIterator bfi = new BreadthFirstIterator(graph, new Integer(x),4);
                try {
                    /*
                    if (set.contains(Transformer.toOrderedLong(x,y))) {
                        bw.write("trusted\n");
                        trusted++;
                    }
                    else if (cache.contains(Transformer.toOrderedLong(x,y))) {
                        bw.write("trusted\n");
                        trusted++;
                        gchr++;
                    }
*/
                    //else {
                        int d = bfi.distanceTo(new Integer(y));
                        if (d != -1) {
                            //cache.add(Transformer.toOrderedLong(x, y));
                            bw.write("trusted\n");
                            trusted++;
                            if (d==1)
                                g1++;
                            else if (d==2)
                                g2++;
                            else if (d==3)
                                g3++;
                            else if (d==4)
                                g4++;
                            if ((g2+g3+g4) % 1000 == 0) {
                                System.out.println("Time to process g1 " + g1 + " g2 " + g2 + " g3 " + g3 + " g4 " + g4 + " t="+(System.currentTimeMillis() - startTime) + " GCHR=" + gchr);
                            }
                        } else {
                            bw.write("unverified\n");
                        }
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //System.out.println("set   size="+set.size());
        //System.out.println("cache size="+cache.size());
    }
}
