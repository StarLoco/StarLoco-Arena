/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Va
 */
public class va_1
extends ZT {
    private static final acl_0 aU = new ym_0(new ahq_2());

    public va_1() {
        this.aG();
        this.ahI = 1;
    }

    public va_1 aia() {
        va_1 va_12;
        try {
            va_12 = (va_1)aU.adr();
            va_12.uG = aU;
        }
        catch (Exception exception) {
            va_12 = new va_1();
            va_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un RE:Death : " + exception.getMessage()));
        }
        this.ahI = 1;
        return va_12;
    }

    public void aG() {
        super.aG();
        this.bWt.set(2);
    }

    public void a(xj_0 xj_02, Pi pi, ea_0 ea_02, kc_2 kc_22, kc_2 kc_23, int n2, int n3, short s, avz_0 avz_02) {
        super.a((XV)xj_02, pi, ea_02, kc_22, kc_23, n2, n3, s, avz_02);
        this.ahI = 1;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqx) && !this.bWm.b(avx_0.deE)) {
            this.bWm.a(Lr.bqx).aAG();
            if (this.bWm instanceof gn_0 && !((gn_0)this.bWm).Dk() && this.mi() != null && this.mi().iP() == 3) {
                if (((ack_1)this.mi()).Nq() != null) {
                    ((gn_0)((ack_1)this.mi()).Nq()).LQ().l(or_0.VQ.tI(), (short)1);
                    for (int j = 0; j < 4; ++j) {
                        ((gn_0)((ack_1)this.mi()).Nq()).LQ().m((short)(or_0.WG.tI() + j), (short)-1);
                    }
                    ((gn_0)((ack_1)this.mi()).Nq()).LQ().l(or_0.aae.tI(), (short)1);
                    if (((gn_0)this.bWm).NY() == xq.axQ) {
                        ((gn_0)this.bWm).LQ().l(or_0.Xt.tI(), (short)-1);
                    }
                } else {
                    ((gn_0)this.bWm).LQ().l(or_0.VP.tI(), (short)1);
                }
            }
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

