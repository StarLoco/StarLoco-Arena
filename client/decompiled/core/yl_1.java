/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.BitSet;

/*
 * Renamed from yL
 */
public abstract class yl_1
extends ack_1 {
    public static final int aDL = 0;
    public static final int aDM = 1;
    public static final int aDN = 1;
    public static final int aDO = 2;
    public static final int aDP = 3;
    protected int aDQ;
    protected boolean aDR;
    private final long[] aDS = new long[1];

    protected yl_1() {
    }

    public yl_1(int n2, agf_2 agf_22, BitSet bitSet, BitSet bitSet2, int n3, int n4, float[] fArray, int n5, boolean bl2) {
        super(n2, agf_22, bitSet, bitSet2, n3, n4, fArray, null, bl2);
        this.aDQ = n5;
    }

    public void b() {
        this.aDR = false;
        super.b();
    }

    public int getType() {
        return 0;
    }

    public boolean FF() {
        return this.ahI >= 63 || this.ahI < 0;
    }

    public byte FG() {
        return 1;
    }

    public boolean c(aOf aOf2) {
        return true;
    }

    public boolean d(aOf aOf2) {
        switch (this.aDQ) {
            case 1: {
                return this.cjA.size() == 0;
            }
            case 2: {
                if (aOf2 != null && aOf2 instanceof gn_0) {
                    yg_0 yg_02 = ((gn_0)aOf2).PH();
                    ArrayList arrayList = this.cjA;
                    for (aOf aOf3 : arrayList) {
                        if (!(aOf3 instanceof gn_0) || ((gn_0)aOf3).PH() != yg_02) continue;
                        return false;
                    }
                    return true;
                }
                return false;
            }
            case 3: {
                return !this.cjA.contains(aOf2);
            }
        }
        return true;
    }

    public long[] e(aOf aOf2) {
        if (aOf2 != null) {
            try {
                this.aDS[0] = wi_2.u(aOf2.gn(), aOf2.go(), aOf2.gp());
                return this.aDS;
            }
            catch (Exception exception) {
                a.error((Object)"Erreur sur la recherche de cible pour une SetEffectArea", (Throwable)exception);
            }
        }
        return null;
    }

    public ArrayList f(aOf aOf2) {
        ArrayList<aOf> arrayList = new ArrayList<aOf>();
        if (aOf2 != null) {
            arrayList.add(aOf2);
        }
        return arrayList;
    }

    public boolean FH() {
        if (this.cjB == null) {
            return false;
        }
        if (this.cjB.length != 2) {
            return false;
        }
        return this.cjB[0] > 0.0f || this.cjB[1] > 0.0f;
    }

    public void g(aOf aOf2) {
        if (this.bdv != null && this.bdv.gU() != null) {
            cn_0 cn_02 = this.bdv.gU();
            int n2 = (int)(this.cjB[0] * this.cjB[1]);
            int n3 = (int)(this.cjB[2] * this.cjB[3]);
            arm_0 arm_02 = arm_0.lQ((short)n2).dS(n3 > 0);
            cn_02.a(aup_0.a(this, aOf2.getId()), arm_02.bS((short)1));
        }
    }

    public boolean FI() {
        return this.aDR;
    }

    public void aX(boolean bl2) {
        this.aDR = bl2;
    }

    public byte[] cd() {
        return ug_2.EMPTY_BYTE_ARRAY;
    }

    public void b(byte[] byArray) {
    }

    private static String k(ry ry2) {
        return ry2 == null ? "null" : "(" + ry2.getX() + ", " + ry2.getY() + ", " + ry2.wk() + ")";
    }

    public String toString() {
        return "(" + this.nD + ", " + yl_1.k(this.baN) + ")";
    }
}

