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

public final class FeatureOne extends AntifraudEngine {

    private static int trusted = 0;
    private static final File featureOneFile = new File(baseOutputFilename+"output1.txt");

    public static void main(String[] args){
        try {
            validateInputFiles();
            //System.in.read(); //VisualVM start point
            final BufferedWriter bw = getFeatureWriter(featureOneFile);
            feature1(bw);
            bw.close();
            System.out.println("trusted="+trusted);
            //max
            //3938360+(

        } catch (final IOException ex) {
            System.out.println("Exception: "+ex);
            ex.printStackTrace();
        }
    }

    private static void feature1(final BufferedWriter bw) throws IOException {
        final Set<Long> set =
                //Collections.synchronizedSortedSet(  - not required - single for a thread
                //new TreeSet<Long>();//);
                // 3938360 transactions
                // 469180 unique pairs
                new HashSet<Long>(4000000); //Improve speed to bootstrap initial state
        //new HashSet<Long>();

        readFile(batchPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {set.add(Transformer.toOrderedLong(x,y));}
        });

        //max 77372 min 0 median 38456
        readFile(streamPaymentFile, new Strategy() {
            @Override
            public void performTask(int x, int y) {
                try {
                    if (set.contains(Transformer.toOrderedLong(x,y))) {
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
