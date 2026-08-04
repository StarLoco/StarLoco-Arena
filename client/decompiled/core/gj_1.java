/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;

/*
 * Renamed from gj
 */
public class gj_1 {
    public static final long sm = 0L;
    private static final String sn = "";
    public static final long so = 0L;
    private static final String sp = "";
    private static final int sq = 128;
    private static final String sr = "";
    private static final long ss = 18273645L;
    private static final String st = Long.toHexString(18273645L);
    private static final String su = "0";
    public static final String sv = "";
    private static final String sw = "0x";
    public static final String sx = "-1|";
    public static final String sy = System.getProperty("line.separator");
    private static long sz = 0L;
    private static String sA = "";
    private static long sB = 0L;
    private static String sC = "";
    private static short[] sD = new short[128];
    private static String sE = "";

    private static long O(long l2) {
        return (l2 & 0xFF00FF00FF00FFL) << 8 | (l2 & 0xFF00FF00FF00FF00L) >> 8;
    }

    public static long aj(String string) {
        return gj_1.O(string.length() < 16 ? 0L : Long.decode(sw + string.substring(0, 16)));
    }

    public static long ak(String string) {
        return gj_1.O(string.length() < 32 ? 0L : Long.decode(sw + string.substring(16, 32)));
    }

    public static void init() {
        sz = 0L;
        sA = "";
        sB = 0L;
        sC = "";
        for (int j = sD.length - 1; 0 <= j; --j) {
            gj_1.sD[j] = 0;
        }
        sE = "";
    }

    private static String P(long l2) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = Long.toHexString(l2);
        for (int j = 15 - string.length(); 0 <= j; --j) {
            stringBuilder.append(su);
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private static String x(short s) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = Long.toHexString(s);
        for (int j = 3 - string.length(); 0 <= j; --j) {
            stringBuilder.append(su);
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private static void add(String string) {
        for (int j = string.length() - 1; 0 <= j; --j) {
            int n2 = j % sD.length;
            short s = (short)(sD[n2] + string.charAt(j) & 0xFFFF);
            gj_1.sD[n2] = (short)((s & 0x5555) << 1 | (s & 0xAAAA) >> 1);
        }
    }

    private static void b(long l2, long l3) {
        sz = gj_1.O(l2);
        sA = gj_1.P(sz);
        sB = gj_1.O(l3);
        sC = gj_1.P(sB);
        gj_1.add(st);
        gj_1.add(sA);
        gj_1.add(sC);
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < sD.length; ++j) {
            stringBuilder.append(gj_1.x(sD[j]));
        }
        sE = stringBuilder.toString();
    }

    private static String getKey() {
        return sA + sC + sE;
    }

    public static boolean al(String string) {
        return string.equals(gj_1.getKey());
    }

    public static void a(String string, long l2, long l3) {
        gj_1.init();
        try {
            String string2;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(string));
            while ((string2 = bufferedReader.readLine()) != null) {
                if (string2.startsWith(sx)) continue;
                gj_1.add(string2);
            }
            gj_1.b(l2, l3);
            bufferedReader.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public static void am(String string) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(string, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeBytes(sx + gj_1.getKey() + sy);
            randomAccessFile.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

