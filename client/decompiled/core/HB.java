/*
 * Decompiled with CFR 0.152.
 */
public class HB
extends qs_0 {
    private arh_0 bfh;
    private qc_0 bfi;
    private gn_0 bfj;

    public HB(int n2, int n3, int n4, long l2, arh_0 arh_02) {
        super(n2, n3, n4);
        this.bC(l2);
        this.bfh = arh_02;
    }

    public long oS() {
        vD vD2;
        adu_0 adu_02 = apN.aDK().aDL();
        if (this.bfh == null || adu_02 == null) {
            return 0L;
        }
        ee_2 ee_22 = (ee_2)apN.aDK().aDL().eg(this.mS());
        int[] nArray = this.bfh.aEH();
        ry ry2 = new ry(nArray[0], nArray[1], (short)nArray[2]);
        vD vD3 = ee_22.NW();
        if (ee_22.rD()) {
            this.bfj = ee_22.PZ();
            if (this.bfh.aEF() > 1) {
                this.bfi = this.bfj.L();
                this.bfj.b(this.bfh.lV(1));
            }
            ee_22.PZ().a(ry2, true);
            vD3.Pv();
        }
        if ((vD2 = vD3) != null) {
            ((abm_2)vD2).a(this.bfh, true, true);
        }
        return this.bfh.aEF() * 300 + 300;
    }

    protected void ax() {
        if (this.bfi != null) {
            this.bfj.b(this.bfi);
        }
        this.bfj = null;
        this.bfi = null;
    }
}

