/*
 * Decompiled with CFR 0.152.
 */
public class gM
extends ZT {
    private static final acl_0 aU = new ym_0(new amu_2());

    public gM kl() {
        gM gM2;
        try {
            gM2 = (gM)aU.adr();
            gM2.uG = aU;
        }
        catch (Exception exception) {
            gM2 = new gM();
            gM2.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + gM2.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        return gM2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).Qf();
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

