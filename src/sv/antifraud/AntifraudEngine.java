package sv.antifraud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * a program that detects suspicious transactions
 * fraud detection algorithm
 * AntifraudEngine - executor
 * Created by Sergey Vlasov on 11/4/2016.
 */

public abstract class AntifraudEngine {
    public static final String baseInputFilename = "paymo_input/";
    public static final String baseOutputFilename = "paymo_output/";
    public static File outputFile;
    public static File batchPaymentFile = new File(baseInputFilename+"batch_payment.csv");
    public static File streamPaymentFile = new File(baseInputFilename+"stream_payment.csv");

    public int trusted = 0;
    //TODO remove
    public int g1 = 0;
    public int g2 = 0;
    public int g3 = 0;
    public int g4 = 0;
    public int gchr = 0;

    public abstract void runValidator(BufferedWriter bw) throws IOException;
    public static void configIO(String[] args) {
        if (args.length==3){
            batchPaymentFile = new File(args[0]);
            streamPaymentFile = new File(args[1]);
            outputFile = new File(args[2]);
        }
    }
    public void execute(File featureOutputFile) {
        try {
            validateInputFiles();
            //System.in.read(); //VisualVM start point
            final BufferedWriter bw = getFeatureWriter(featureOutputFile);
            runValidator(bw);
            bw.close();
            //System.out.println("trusted="+trusted);
            //max
            //3938360+(

        } catch (final IOException ex) {
            System.out.println("Exception: "+ex);
            ex.printStackTrace();
        }
    }

    public static void validateInputFiles() {
        if (!batchPaymentFile.exists() || !streamPaymentFile.exists()) {
            System.err.println("Batch and/or stream payment file(s) not found");
            System.exit(1);
        }
    }

    public static BufferedWriter getFeatureWriter(File featureOneFile) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(featureOneFile)));
    }

    public static void readFile(final File file, Strategy strategy) throws FileNotFoundException, IOException {

        //System.out.println("\nReading "+file);
        final FileInputStream stream = new FileInputStream(file);
        final BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        final String skipped = br.readLine();

        final long startTime = System.currentTimeMillis();
        long validRecordCounter = 0;
        String line;
        String[] tokens;
        while((line = br.readLine()) != null) {
            try {
                tokens = line.substring(21,33).split(",");
                final int x = Integer.parseInt(tokens[0]);              // source ID
                final int y = Integer.parseInt(tokens[1].substring(1)); // destination ID
                strategy.performTask(x,y);
                validRecordCounter++;
            }
            // Ignoring invalid strings
            catch (NumberFormatException nfe){}
            catch (StringIndexOutOfBoundsException siobe) {}
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Time to process:"+(endTime-startTime));
        //System.out.println("Valid records count:"+validRecordCounter);
        br.close();
    }
}
