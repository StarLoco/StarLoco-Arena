/*
 * Decompiled with CFR 0.152.
 */
public class avJ
extends qs_0 {
    private static aLN Hv = new aLN();
    private static long dfO = 100L;
    private final ry dfP;
    private final qc_0 dfQ;

    public avJ(int n2, int n3, int n4, long l2, ry ry2, qc_0 qc_02) {
        super(n2, n3, n4);
        this.dfP = ry2;
        this.dfQ = qc_02;
        this.bC(l2);
    }

    public long oS() {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            ee_2 ee_22 = (ee_2)adu_02.eg(this.mS());
            ee_22.PL().c(avx_0.deu);
            ee_22.m(this.dfP);
            ee_22.b(this.dfQ);
        }
        return dfO;
    }

    protected void ax() {
    }
}

