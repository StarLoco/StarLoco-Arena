/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sQ
 */
public class sq_2
extends ZT {
    private static final acl_0 aU = new ym_0(new PB());

    public sq_2() {
        this.aG();
    }

    public sq_2 yZ() {
        sq_2 sq_22;
        try {
            sq_22 = (sq_2)aU.adr();
            sq_22.uG = aU;
        }
        catch (Exception exception) {
            sq_22 = new sq_2();
            sq_22.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un MPUse : " + exception.getMessage()));
        }
        return sq_22;
    }

    public static sq_2 a(ea_0 ea_02, int n2, kc_2 kc_22) {
        sq_2 sq_22;
        if (kc_22 == null) {
            return null;
        }
        try {
            sq_22 = (sq_2)aU.adr();
            sq_22.uG = aU;
        }
        catch (Exception exception) {
            sq_22 = new sq_2();
            sq_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un MPUse : " + exception.getMessage()));
        }
        sq_22.aW = mh_2.bwa.getId();
        sq_22.bWr = ((ZT)mh_2.bwa.getObject()).Oz();
        sq_22.aG();
        sq_22.bWm = kc_22;
        sq_22.bWl = kc_22;
        sq_22.r = n2;
        sq_22.ahI = -1;
        sq_22.bdv = ea_02;
        return sq_22;
    }

    public void aG() {
        super.aG();
        this.bWt.set(65);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqz)) {
            this.bWm.a(Lr.bqz).ka(this.r);
            if (this.bWm instanceof gn_0 && ((gn_0)this.bWm).LQ() != null && !((gn_0)this.bWm).Dk()) {
                ((gn_0)this.bWm).LQ().l(or_0.aag.tI(), (short)this.r);
                ((gn_0)this.bWm).LQ().l(or_0.XL.tI(), (short)this.r);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
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

