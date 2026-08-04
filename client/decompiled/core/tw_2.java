/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tW
 */
public class tw_2
extends ZT {
    private static final acl_0 aU = new ym_0(new aT());
    private Lr bR;
    private boolean bT = false;
    protected fv_1 bS;
    protected int bU;

    private tw_2() {
    }

    public tw_2(fv_1 fv_12, Lr lr) {
        this.bS = fv_12;
        this.bR = lr;
        this.aG();
    }

    public tw_2 zQ() {
        tw_2 tw_22;
        try {
            tw_22 = (tw_2)aU.adr();
            tw_22.uG = aU;
        }
        catch (Exception exception) {
            tw_22 = new tw_2();
            tw_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un CharacBuff : " + exception.getMessage()));
        }
        tw_22.bR = this.bR;
        tw_22.bT = false;
        tw_22.bS = this.bS;
        tw_22.bU = 0;
        return tw_22;
    }

    public void aG() {
        super.aG();
    }

    public void a(xb_2 xb_22, boolean bl2) {
        kc_2 kc_22 = ((mv_1)this.bdv.gT()).q(this.bWn);
        if (kc_22 != null && this.bWl != null && !this.bWl.PR() && !this.bWl.PT() && this.bWl.b(this.bR)) {
            boolean bl3 = true;
            if (this.bU > 0) {
                int n2 = 0;
                int n3 = ((xj_0)this.ajO()).ST();
                for (xb_2 xb_23 : this.bWm.PJ()) {
                    if (xb_23.ajO() == null || xb_23.ajO().ST() != n3) continue;
                    ++n2;
                }
                if (n2 != this.bU - 1) {
                    bl3 = false;
                }
            }
            if (bl3) {
                this.bWl.a(this.bR).kb(this.r);
                this.bWl.a(this.bR).jZ(this.r);
            }
            this.bT = true;
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            case 2: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                this.bU = (int)((xj_0)this.bWj).Tb()[1];
                break;
            }
            case 3: {
                this.r = ou_1.aE((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1)) + (int)((xj_0)this.bWj).iY(2);
                break;
            }
            case 4: {
                this.r = ou_1.aE((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1)) + (int)((xj_0)this.bWj).iY(2);
                this.bU = (int)((xj_0)this.bWj).Tb()[3];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un CharacBuff : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
    }

    public void aK() {
        if (this.bT && this.bWl != null && this.bWl.b(this.bR)) {
            boolean bl2 = true;
            if (this.bU > 0) {
                int n2 = 0;
                int n3 = ((xj_0)this.ajO()).ST();
                for (xb_2 xb_22 : this.bWm.PJ()) {
                    if (xb_22.ajO() == null || xb_22.ajO().ST() != n3) continue;
                    ++n2;
                }
                if (n2 != this.bU) {
                    bl2 = false;
                }
            }
            if (bl2) {
                this.bWl.a(this.bR).kb(-this.r);
                this.bWl.a(this.bR).ka(this.r);
            }
            if (this.bWk != null && this.bWk.iP() != 12) {
                switch (this.bR) {
                    case bqB: 
                    case bqE: 
                    case bqD: 
                    case bqC: {
                        ((gn_0)this.bWm).LQ().m(or_0.VR.tI(), (short)(this.bWm.d(this.bR) + this.bWm.d(Lr.bra)));
                        break;
                    }
                    case bra: {
                        ((gn_0)this.bWm).LQ().m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqB) + this.bWm.d(Lr.bra)));
                        ((gn_0)this.bWm).LQ().m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqE) + this.bWm.d(Lr.bra)));
                        ((gn_0)this.bWm).LQ().m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqC) + this.bWm.d(Lr.bra)));
                        ((gn_0)this.bWm).LQ().m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqD) + this.bWm.d(Lr.bra)));
                    }
                }
            }
        }
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

    public fv_1 aL() {
        return this.bS;
    }

    public Lr aM() {
        return this.bR;
    }

    /* synthetic */ tw_2(aT aT2) {
        this();
    }
}

