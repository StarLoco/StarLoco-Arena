/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nR
 */
public class nr_0 {
    public static int Pm = 0;
    public static int Pn = 1;
    public static int Po = 60;
    public static int Pp = 0;
    public static byte Pq = (byte)100;
    public static final int Pr = 12;
    public static final int Ps = 6;
    public static byte Pt = (byte)100;
    public static final int Pu = 2;
    public static int Pv = 100000;
    public static int Pw = 12;
    public static int Px = 50;
    public static final int Py = 10;
    public static final int Pz = 3;
    public static final int PA = 3;
    public static final long PB = 300L;
    public static final int PC = 112;
    public static final int PD = 5;
    public static final int PE = 7;
    public static final int PF = 9;
    public static final int PG = 10;
    public static final byte PH = -2;
    public static final int PI = 50000;
    public static final int PJ = 100;
    public static final int PK = 20;
    public static final int PL = 20;
    public static final int PM = 2;
    public static final int PN = 6;
    public static final int PO = 6;
    public static final int[] PP = new int[]{860, 4000, 10000, 20000, 40000};
    public static final int[] PQ = new int[]{3, 3, 4, 7, 12, 20};

    public static short cs(int n2) {
        for (int j = 0; j < PP.length; ++j) {
            if (n2 > PP[j]) continue;
            return (short)(j + 1);
        }
        return 6;
    }

    public static short a(int n2, int n3, int n4, short s) {
        return s;
    }

    public static short b(et_2[] et_2Array) {
        int n2 = 0;
        int n3 = 0;
        short s = 0;
        for (int j = et_2Array.length - 1; 0 <= j; --j) {
            int n4 = et_2Array[j].Ny();
            n2 += n4;
            short s2 = nr_0.cs(n4);
            n3 += s2;
            if (s2 <= s) continue;
            s = s2;
        }
        return nr_0.a(n2, n3, et_2Array.length, s);
    }

    public static int ct(int n2) {
        return n2 == 1 ? 0 : n2 * n2 * 10;
    }
}

