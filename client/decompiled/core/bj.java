/*
 * Decompiled with CFR 0.152.
 */
public class bj
implements abc_2 {
    private static final int fw = 256;
    private static final int fx = 12;
    final int fy;
    StringBuffer buf;

    public bj(int n2) {
        this.fy = n2;
        this.buf = new StringBuffer(n2);
    }

    public String q(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Class name may not be null");
        }
        int n2 = string.length();
        if (n2 < this.fy) {
            return string;
        }
        if (this.buf.capacity() > 256) {
            this.buf = new StringBuffer(this.fy);
        }
        this.buf.setLength(0);
        int[] nArray = new int[12];
        int[] nArray2 = new int[12];
        int n3 = bj.a(string, nArray);
        if (n3 == 0) {
            return string;
        }
        this.a(string, nArray, nArray2, n3);
        for (int j = 0; j <= n3; ++j) {
            if (j == 0) {
                this.buf.append(string.substring(0, nArray2[j] - 1));
                continue;
            }
            this.buf.append(string.substring(nArray[j - 1], nArray[j - 1] + nArray2[j]));
        }
        return this.buf.toString();
    }

    static int a(String string, int[] nArray) {
        int n2;
        int n3 = 0;
        for (n2 = 0; (n3 = string.indexOf(46, n3)) != -1 && n2 < 12; ++n2) {
            nArray[n2] = n3++;
        }
        return n2;
    }

    void a(String string, int[] nArray, int[] nArray2, int n2) {
        int n3;
        int n4 = string.length() - this.fy;
        for (n3 = 0; n3 < n2; ++n3) {
            int n5;
            int n6;
            int n7 = -1;
            if (n3 > 0) {
                n7 = nArray[n3 - 1];
            }
            int n8 = n6 = (n5 = nArray[n3] - n7 - 1) < 1 ? n5 : 1;
            n6 = n4 > 0 ? (n5 < 1 ? n5 : 1) : n5;
            n4 -= n5 - n6;
            nArray2[n3] = n6 + 1;
        }
        n3 = n2 - 1;
        nArray2[n2] = string.length() - nArray[n3];
    }

    static void b(String string, int[] nArray) {
        System.out.print(string);
        for (int j = 0; j < nArray.length; ++j) {
            if (j == 0) {
                System.out.print(nArray[j]);
                continue;
            }
            System.out.print(", " + nArray[j]);
        }
        System.out.println();
    }
}

