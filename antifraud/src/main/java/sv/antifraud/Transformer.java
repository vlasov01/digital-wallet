package sv.antifraud;

/**
 * Created by admin on 11/5/2016.
 */

public class Transformer {
    // ordering values so we always process them in the same order and
    // packing them to reduce memory footprint
    public static Long toOrderedLong(int x, int y) {
        return new Long( x > y ? pack(x, y) : pack(y, x) );
    }

    public static long pack(int x, int y) {
        long xPacked = ((long)x) << 32;
        long yPacked = y & 0xFFFFFFFFL;
        return xPacked | yPacked;
    }

    public static int unpackX(long packed) {
        return (int) (packed >> 32);
    }

    public static int unpackY(long packed) {
        return (int) (packed & 0xFFFFFFFFL);
    }



    public static void main(String[] args) {
        int x = 49466;
        int y = 6989;

        System.out.println("x=    "+Integer.toHexString(x));
        System.out.println("y=    "+Integer.toHexString(y));

        long pack = pack(x,y);
        System.out.println("pack= "+Long.toHexString(pack));

        int x2 = unpackX(pack);
        System.out.println("x2=   "+Integer.toHexString(x2));

        int y2 = unpackY(pack);
        System.out.println("y2=   "+Integer.toHexString(y2));

        testAll();
    }

    private static void testAll() {
        long pack, iteration = 0;
        int x = 49466, y = 6989;

        pack = pack(x,y);
        if (!((x == unpackX(pack)) && (y == unpackY(pack))))
                System.out.println("Failed");
        System.out.println("x=    "+x);
        System.out.println("y=    "+y);

    }
}
