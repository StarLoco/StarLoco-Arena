/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from acD
 */
public class acd_0 {
    private static final apx ckz = new tq_0();
    private final cp_2 ckA = new cp_2(300);
    private final ArrayList ckB = new ArrayList(300);
    private static float ckC = Float.MIN_VALUE;
    int aG;
    int aH;

    boolean F(int n2, int n3) {
        return n2 >= this.aG && n2 < this.aG + 18 && n3 >= this.aH && n3 < this.aH + 18;
    }

    adb_0 n(int n2, int n3, int n4) {
        return this.a(n2, n3, n4, this.ckA);
    }

    private adb_0 a(int n2, int n3, int n4, cp_2 cp_22) {
        return (adb_0)cp_22.t(acd_0.H(n2, n3, n4));
    }

    public static long H(int n2, int n3, int n4) {
        return (long)(n2 + 32768 & 0xFFFF) << 48 | (long)(n3 + 32768 & 0xFFFF) << 32 | (long)n4;
    }

    void b(acf acf2) {
        ckC = Float.MIN_VALUE;
        this.aG = acf2.readShort() * 18;
        this.aH = acf2.readShort() * 18;
        int n2 = acf2.readShort() & 0xFFFF;
        for (int j = 0; j < n2; ++j) {
            long l2 = acf2.readLong();
            boolean bl2 = acf2.aqE();
            int n3 = acf2.readInt();
            int n4 = acf2.readInt();
            int n5 = acf2.readInt();
            adb_0 adb_02 = new adb_0(n3, n4, n5, bl2);
            this.ckA.a(l2, adb_02);
            this.ckB.add(adb_02);
        }
        this.ckA.compact();
    }

    boolean s(short s, short s2) {
        int n2 = hy_2.aO(this.aG);
        if (n2 != s) {
            return false;
        }
        int n3 = hy_2.aP(this.aH);
        return s2 == n3;
    }

    void a(float f) {
        ckC = f;
        int n2 = this.ckB.size();
        for (int j = 0; j < n2; ++j) {
            ((adb_0)this.ckB.get(j)).a(f);
        }
    }

    static /* synthetic */ float arp() {
        return ckC;
    }
}

