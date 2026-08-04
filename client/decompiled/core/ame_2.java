/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ame
 */
public class ame_2
extends ZT {
    private static final acl_0 aU = new ym_0(new asq_0());
    private Lr bR;
    private boolean bT = false;
    protected fv_1 bS;
    protected int bU;

    private ame_2() {
    }

    public ame_2(fv_1 fv_12, Lr lr) {
        this.bS = fv_12;
        this.bR = lr;
        this.aG();
    }

    public ame_2 aBw() {
        ame_2 ame_22;
        try {
            ame_22 = (ame_2)aU.adr();
            ame_22.uG = aU;
        }
        catch (Exception exception) {
            ame_22 = new ame_2();
            ame_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un CharacBuff : " + exception.getMessage()));
        }
        ame_22.bR = this.bR;
        ame_22.bT = false;
        ame_22.bS = this.bS;
        ame_22.bU = 0;
        return ame_22;
    }

    public void aG() {
        super.aG();
        if (this.bR == null) {
            return;
        }
        switch (this.bR) {
            case bqx: {
                this.bWt.set(3);
                break;
            }
            case bqy: {
                this.bWt.set(53);
                break;
            }
            case bqz: {
                this.bWt.set(63);
                break;
            }
            case bqA: {
                this.bWt.set(171);
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
            case brb: {
                break;
            }
        }
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm != null && this.bWm.b(this.bR)) {
            boolean bl3 = true;
            if (this.bU > 0) {
                int n2 = 0;
                int n3 = ((xj_0)this.ajO()).ST();
                for (xb_2 xb_23 : this.bWm.PJ()) {
                    if (xb_23.ajO() == null || xb_23.ajO().ST() != n3) continue;
                    ++n2;
                }
                if (n2 != this.bU) {
                    bl3 = false;
                }
            }
            if (bl3) {
                this.bWm.a(this.bR).kb(this.r);
                this.bWm.a(this.bR).jZ(this.r);
            }
            this.bT = true;
            if (this.bWk == null || this.bWk.iP() != 12 && ((gn_0)this.bWm).LQ() != null) {
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
        if (this.bT) {
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
            if (bl2 && this.bWm != null && this.bWm.b(this.bR)) {
                this.bWm.a(this.bR).kb(-this.r);
                this.bWm.a(this.bR).ka(this.r);
            }
        }
        super.aK();
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

    public fv_1 aL() {
        return this.bS;
    }

    public Lr aM() {
        return this.bR;
    }

    /* synthetic */ ame_2(asq_0 asq_02) {
        this();
    }
}

