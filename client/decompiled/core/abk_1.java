/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBk
 */
public class abk_1
extends re_0 {
    private long cKS;
    private boolean cFY;

    public abk_1(int n2, int n3, int n4, boolean bl2, boolean bl3, long l2, long l3, int n5, int n6, short s, boolean bl4) {
        super(n2, n3, n4, bl2, bl3, l2, n5, n6, s);
        this.cKS = l3;
        this.cFY = bl4;
    }

    public long oS() {
        adu_0 adu_02 = apN.aDK().aDL();
        yp_2 yp_22 = (yp_2)je_1.Wa().el(this.cKS);
        this.bG(yp_22.eA());
        ee_2 ee_22 = (ee_2)adu_02.eg(this.Nl());
        if (ee_22 != null) {
            Hv.info(aon_0.aYc().getString("fight.spellCast", ee_22.getName(), yp_22.getName()));
        }
        if (this.cFY) {
            return super.oS();
        }
        this.Nn();
        return -1L;
    }

    protected void ax() {
        super.ax();
    }
}

