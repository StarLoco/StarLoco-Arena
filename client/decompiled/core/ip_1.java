/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ip
 */
public class ip_1
extends ZT {
    private static final acl_0 aU = new ym_0(new aiy_0());

    public ip_1 lx() {
        ip_1 ip_12;
        try {
            ip_12 = (ip_1)aU.adr();
            ip_12.uG = aU;
        }
        catch (Exception exception) {
            ip_12 = new ip_1();
            ip_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un DamageTransfer : " + exception.getMessage()));
        }
        return ip_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (xb_22 != null && bl2 && (xb_22 instanceof ig_1 || xb_22 instanceof ib_1)) {
            xb_22.h(this.bWl);
            if (xb_22 instanceof ig_1) {
                ((ig_1)xb_22).bE(false);
            }
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        super.aK();
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

