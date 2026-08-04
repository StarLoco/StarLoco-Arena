/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aaS
 */
public class aas_1
extends ZT {
    private static final acl_0 aU = new ym_0(new aam_2());

    public aas_1 apw() {
        aas_1 aas_12;
        try {
            aas_12 = (aas_1)aU.adr();
            aas_12.uG = aU;
        }
        catch (Exception exception) {
            aas_12 = new aas_1();
            aas_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return aas_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl instanceof gn_0 && !this.bWl.PR() && !this.bWl.PT()) {
            for (ack_1 ack_12 : this.bdv.gX().Sz()) {
                if (!ack_12.i(this.ajS())) continue;
                ((yl_1)ack_12).aX(true);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void aK() {
        for (ack_1 ack_12 : this.bdv.gX().Sz()) {
            if (!ack_12.i(this.ajS())) continue;
            ((yl_1)ack_12).aX(false);
        }
        super.aK();
    }

    public void a(xb_2 xb_22) {
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return false;
    }

    public boolean aJ() {
        return true;
    }
}

