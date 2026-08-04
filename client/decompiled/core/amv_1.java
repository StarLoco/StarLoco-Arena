/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from amv
 */
public class amv_1
extends ZT {
    private static final acl_0 aU = new ym_0(new aNT());

    public amv_1 aBK() {
        amv_1 amv_12;
        try {
            amv_12 = (amv_1)aU.adr();
            amv_12.uG = aU;
        }
        catch (Exception exception) {
            amv_12 = new amv_1();
            amv_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un " + amv_1.class.getSimpleName() + " : " + exception.getMessage()));
        }
        return amv_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (xb_22 != null && (xb_22 instanceof ig_1 && xb_22.mi() != null && xb_22.mi().iP() == 13 && mh_2.YJ().cq(xb_22.getId()) != null || xb_22 instanceof ig_1 && ((ig_1)xb_22).Uc()) && 0 < this.r && (!xb_22.aI() || xb_22.ajQ() != null)) {
            xb_22.h(xb_22.ajQ());
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        float[] fArray;
        float f = this.bWj != null && (fArray = ((xj_0)this.bWj).Tb()) != null && fArray.length == 1 ? Math.min(100.0f, fArray[0]) : 100.0f;
        this.r = (float)ou_1.he(100) <= f ? 1 : 0;
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

