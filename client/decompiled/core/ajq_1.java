/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajq
 */
public class ajq_1
extends ZT {
    private static final acl_0 aU = new ym_0(new alh_0());

    public ajq_1() {
        this.aG();
    }

    public ajq_1 azd() {
        ajq_1 ajq_12;
        try {
            ajq_12 = (ajq_1)aU.adr();
            ajq_12.uG = aU;
        }
        catch (Exception exception) {
            ajq_12 = new ajq_1();
            ajq_12.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un APUse : " + exception.getMessage()));
        }
        return ajq_12;
    }

    public static ajq_1 d(ea_0 ea_02, int n2, kc_2 kc_22) {
        ajq_1 ajq_12;
        try {
            ajq_12 = (ajq_1)aU.adr();
            ajq_12.uG = aU;
        }
        catch (Exception exception) {
            ajq_12 = new ajq_1();
            ajq_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un APUse : " + exception.getMessage()));
        }
        ajq_12.aW = mh_2.bvZ.getId();
        ajq_12.bWr = ((ZT)mh_2.bvZ.getObject()).Oz();
        ajq_12.aG();
        ajq_12.bWm = kc_22;
        ajq_12.r = n2;
        ajq_12.ahI = -1;
        ajq_12.bdv = ea_02;
        return ajq_12;
    }

    public void aG() {
        super.aG();
        this.bWt.set(55);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqy)) {
            this.bWm.a(Lr.bqy).ka(this.r);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

