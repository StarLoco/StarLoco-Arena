/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XJ
 */
public class xj_0
extends ahv_2 {
    private final float[] beD;
    private final int[] bZn;
    private final String bZo;
    private final boolean bZp;
    private final boolean beB;

    public xj_0(int n2, int n3, String string, agf_2 agf_22, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4, long l2, ahl_2 ahl_22, boolean bl2, float[] fArray, int[] nArray5, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        super(n2, n3, agf_22, nArray, nArray2, nArray4, nArray3, ug_2.bQd, ug_2.bQd, ug_2.bQd, l2, ahl_22, bl2, false, (short)-1, bl3, false, false, false, bl6, bl7);
        this.beD = fArray;
        this.bZn = nArray5;
        this.bZo = string;
        this.bZp = bl4;
        this.beB = bl5;
    }

    public float[] Tb() {
        return this.beD;
    }

    public int[] aln() {
        return this.bZn;
    }

    public boolean alo() {
        return this.bZp;
    }

    public boolean Ta() {
        return this.beB;
    }

    public int[] alp() {
        return null;
    }

    public float iY(int n2) {
        if (n2 >= 0 && n2 < this.beD.length) {
            return this.beD[n2];
        }
        return -1.0f;
    }

    public String alq() {
        return this.bZo;
    }

    public byte alr() {
        return 0;
    }
}

