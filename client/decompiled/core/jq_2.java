/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Jq
 */
public class jq_2
extends zj_1 {
    private static final acl_0 aU = new ym_0(new fx_2());

    private jq_2() {
    }

    public jq_2(fv_1 fv_12, Lr lr) {
        super(fv_12, lr);
        this.aG();
    }

    public jq_2 VC() {
        jq_2 jq_22;
        try {
            jq_22 = (jq_2)aU.adr();
            jq_22.uG = aU;
        }
        catch (Exception exception) {
            jq_22 = new jq_2();
            jq_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un CharacLeech : " + exception.getMessage()));
        }
        jq_22.bR = this.bR;
        return jq_22;
    }

    public void aG() {
        super.aG();
        if (this.bR == null) {
            return;
        }
        switch (this.bR) {
            case bqx: {
                break;
            }
            case bqy: {
                this.bWt.set(56);
                break;
            }
            case bqz: {
                break;
            }
            case bqA: {
                break;
            }
            case bqB: {
                break;
            }
            case bqC: {
                break;
            }
            case bqD: {
                break;
            }
            case bqE: {
                break;
            }
            case bqF: {
                break;
            }
            case bqG: {
                break;
            }
            case bqH: {
                break;
            }
            case bqI: {
                break;
            }
            case bqJ: {
                break;
            }
            case bqK: {
                break;
            }
            case bqL: {
                break;
            }
            case bqM: {
                break;
            }
            case bqN: {
                break;
            }
            case bqO: {
                break;
            }
            case bqP: {
                break;
            }
            case bqQ: {
                break;
            }
            case bqR: {
                break;
            }
            case bqS: {
                break;
            }
            case bqT: {
                break;
            }
            case bqU: {
                break;
            }
            case bqV: {
                break;
            }
            case bqW: {
                break;
            }
            case bqX: {
                break;
            }
            case bqY: {
                break;
            }
            case bqZ: {
                break;
            }
            case bra: {
                break;
            }
        }
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && !this.bWl.PR() && !this.bWl.PT() && this.bWm != null && this.bWm.b(this.bR) && this.bWl.b(this.bR)) {
            this.bWl.a(this.bR).kb(this.r);
            this.bWl.a(this.bR).jZ(this.r);
            this.ccI = true;
            if (this.bWk != null && this.bWk.iP() != 12) {
                switch (this.bR) {
                    case bqB: 
                    case bqC: 
                    case bqD: 
                    case bqE: {
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
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void aK() {
        if (this.bT && this.bWl != null && this.bWl.b(this.bR)) {
            this.bWl.a(this.bR).kb(-this.r);
            this.bWl.a(this.bR).ka(this.r);
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

    /* synthetic */ jq_2(fx_2 fx_22) {
        this();
    }
}

