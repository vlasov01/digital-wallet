package sv.antifraud;

/**
 * Created by admin on 11/5/2016.
 */

public class Transformer {
    public static long pack(int c1, int c2, int c3, int c4)
    {
        return ((c1 << 24) | (c2 << 16) | (c3 << 8) | (c4)) & 0xffffffffL;
    }
}
