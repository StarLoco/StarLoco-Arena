/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eO
 */
public class eo_1
extends ZT {
    private static final acl_0 aU = new ym_0(new ahc_1());

    public eo_1 hV() {
        eo_1 eo_12;
        try {
            eo_12 = (eo_1)aU.adr();
            eo_12.uG = aU;
        }
        catch (Exception exception) {
            eo_12 = new eo_1();
            eo_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Petrified : " + exception.getMessage()));
        }
        return eo_12;
    }

    public void a(xb_2 xb_22) {
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return false;
    }

    public boolean aJ() {
        return true;
    }
}

