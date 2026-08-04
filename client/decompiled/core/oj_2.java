/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Oj
 */
public abstract class oj_2 {
    private static final Logger a = Logger.getLogger(oj_2.class);
    private final int fb;
    private final int fc;

    protected oj_2(int n2, int n3) {
        assert (n2 > 0 && n3 > 0);
        this.fb = n2;
        this.fc = n3;
    }

    public int getHeight() {
        return this.fc;
    }

    public int getWidth() {
        return this.fb;
    }

    public abstract int abp();

    public abstract byte abq();

    public final int a(auo_0 auo_02) {
        return this.au(auo_02.getX(), auo_02.getY());
    }

    public abstract int au(int var1, int var2);

    public final auo_0 gY(int n2) {
        return new auo_0(this.gZ(n2), this.ha(n2));
    }

    public abstract int gZ(int var1);

    public abstract int ha(int var1);

    public final auo_0 b(auo_0 auo_02) {
        return this.av(auo_02.getX(), auo_02.getY());
    }

    public auo_0 av(int n2, int n3) {
        int n4 = this.aw(n2, n3);
        int n5 = this.ax(n2, n3);
        return new auo_0(n4, n5);
    }

    public abstract int aw(int var1, int var2);

    public abstract int ax(int var1, int var2);

    public final auo_0 c(auo_0 auo_02) {
        return this.ay(auo_02.getX(), auo_02.getY());
    }

    public abstract auo_0 ay(int var1, int var2);

    public abstract int az(int var1, int var2);

    public abstract int aA(int var1, int var2);

    public final auo_0 w(int n2, int n3, int n4) {
        return new auo_0(this.x(n2, n3, n4), this.y(n2, n3, n4));
    }

    public final int x(int n2, int n3, int n4) {
        return this.az(n2, n3) + this.gZ(n4);
    }

    public int y(int n2, int n3, int n4) {
        return this.aA(n2, n3) + this.ha(n4);
    }

    public static long z(int n2, int n3, int n4) {
        int n5 = 0x100000;
        int n6 = 0x100000;
        assert (Math.abs(n3) <= n6 || Math.abs(n4) <= n6 || n2 <= n5 || n2 >= 0) : "L'id de la map (" + n3 + "," + n4 + ") - monde " + n2 + " n'est pas bon.";
        long l2 = n2 & 0xFFFFF;
        long l3 = Math.abs(n3) & 0xFFFFF;
        if (Integer.signum(n3) == -1) {
            l3 |= 0x100000L;
        }
        long l4 = Math.abs(n4) & 0xFFFFF;
        if (Integer.signum(n4) == -1) {
            l4 |= 0x100000L;
        }
        return l2 << 42 | l3 << 21 | l4;
    }

    public static int[] cg(long l2) {
        int[] nArray = new int[]{oj_2.ch(l2), oj_2.ci(l2), oj_2.cj(l2)};
        return nArray;
    }

    public static int ch(long l2) {
        return (int)(l2 >>> 42 & 0xFFFFFL);
    }

    public static int ci(long l2) {
        long l3 = l2 >>> 21 & 0x1FFFFFL;
        if ((l3 & 0x100000L) == 0x100000L) {
            l3 = -(l3 & 0xFFFFFL);
        }
        return (int)l3;
    }

    public static int cj(long l2) {
        long l3 = l2 & 0x1FFFFFL;
        if ((l3 & 0x100000L) == 0x100000L) {
            l3 = -(l3 & 0xFFFFFL);
        }
        return (int)l3;
    }
}

