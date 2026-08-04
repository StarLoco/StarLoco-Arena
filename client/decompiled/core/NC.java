/*
 * Decompiled with CFR 0.152.
 */
public class NC
extends ZT {
    private static final acl_0 aU = new ym_0(new aks_1());
    private Lr bR;
    private fv_1 bS;
    private boolean bT = false;
    protected int bU;

    private NC() {
    }

    public NC(fv_1 fv_12, Lr lr) {
        this.bS = fv_12;
        this.bR = lr;
        this.aG();
    }

    public NC aaN() {
        NC nC;
        try {
            nC = (NC)aU.adr();
            nC.uG = aU;
        }
        catch (Exception exception) {
            nC = new NC();
            nC.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un CharacGain : " + exception.getMessage()));
        }
        nC.bR = this.bR;
        nC.bS = this.bS;
        nC.bT = false;
        nC.bU = 0;
        return nC;
    }

    public void aG() {
        super.aG();
        if (this.bR == null) {
            return;
        }
        switch (this.bR) {
            case bqx: {
                this.bWt.set(1);
                break;
            }
            case bqy: {
                this.bWt.set(51);
                break;
            }
            case bqz: {
                this.bWt.set(61);
                break;
            }
            case bqA: {
                break;
            }
            case bqB: {
                this.bWt.set(111);
                break;
            }
            case bqC: {
                this.bWt.set(81);
                break;
            }
            case bqD: {
                this.bWt.set(91);
                break;
            }
            case bqE: {
                this.bWt.set(101);
                break;
            }
            case bqF: {
                this.bWt.set(161);
                break;
            }
            case bqG: {
                this.bWt.set(131);
                break;
            }
            case bqH: {
                this.bWt.set(141);
                break;
            }
            case bqI: {
                this.bWt.set(151);
                break;
            }
            case bqJ: {
                this.bWt.set(71);
                break;
            }
            case bqK: {
                this.bWt.set(111);
                break;
            }
            case bqL: {
                this.bWt.set(81);
                break;
            }
            case bqM: {
                this.bWt.set(91);
                break;
            }
            case bqN: {
                this.bWt.set(101);
                break;
            }
            case bqO: {
                this.bWt.set(121);
                break;
            }
            case bqP: {
                this.bWt.set(161);
                break;
            }
            case bqQ: {
                this.bWt.set(131);
                break;
            }
            case bqR: {
                this.bWt.set(141);
                break;
            }
            case bqS: {
                this.bWt.set(151);
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
                this.bWt.set(181);
                break;
            }
            case bqY: {
                this.bWt.set(57);
                break;
            }
            case bqZ: {
                this.bWt.set(66);
                break;
            }
            case bra: {
                this.bWt.set(71);
                break;
            }
            case brb: {
                this.bWt.set(121);
                break;
            }
            case brd: {
                this.bWt.set(201);
                break;
            }
            case bre: {
                this.bWt.set(211);
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
                if (n2 != this.bU - 1) {
                    bl3 = false;
                }
            }
            if (bl3) {
                this.bWm.a(this.bR).jZ(this.r);
                if (this.bWm.d(this.bR) > this.bWm.a(this.bR).max()) {
                    this.bWm.a(this.bR).set(this.bWm.a(this.bR).max());
                }
            }
            this.bT = true;
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un CharacGain : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
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

    public void aK() {
        super.aK();
    }

    public fv_1 aL() {
        return this.bS;
    }

    public Lr aM() {
        return this.bR;
    }

    /* synthetic */ NC(aks_1 aks_12) {
        this();
    }
}

