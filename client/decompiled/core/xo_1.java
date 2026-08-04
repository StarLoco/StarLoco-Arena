/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Xo
 */
public class xo_1
extends qs_0 {
    private static aLN Hv = new aLN();
    private static String bXA = "AnimTacle";
    private static long bXB = 1200L;

    public xo_1(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public long oS() {
        ee_2 ee_22;
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && (ee_22 = (ee_2)adu_02.eg(this.mS())) != null) {
            Hv.info(aon_0.aYc().getString("fight.tackled", ee_22.getName()));
            ee_22.NW().aY(bXA);
            return bXB;
        }
        return 0L;
    }

    protected void ax() {
    }
}

