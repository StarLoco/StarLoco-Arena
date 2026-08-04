/*
 * Decompiled with CFR 0.152.
 */
public class ah
extends ZT {
    private static final acl_0 aU = new ym_0(new gv_0());
    private Lr bR;
    private fv_1 bS;
    private boolean bT = false;
    protected int bU;

    private ah() {
    }

    public ah(fv_1 fv_12, Lr lr) {
        this.bS = fv_12;
        this.bR = lr;
        this.aG();
    }

    public ah aF() {
        ah ah2;
        try {
            ah2 = (ah)aU.adr();
            ah2.uG = aU;
        }
        catch (Exception exception) {
            ah2 = new ah();
            ah2.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        ah2.bR = this.bR;
        ah2.bS = this.bS;
        ah2.bT = false;
        return ah2;
    }

    public void aG() {
        super.aG();
        if (this.bR != null) {
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
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm != null && this.bWm.b(this.bR)) {
            boolean bl3 = true;
            if (0 < this.bU) {
                int n2 = ((xj_0)this.ajO()).ST();
                int n3 = 0;
                for (xb_2 xb_23 : this.bWm.PJ()) {
                    XV xV = xb_23.ajO();
                    if (xV == null || xV.ST() != n2) continue;
                    ++n3;
                }
                if (n3 != this.bU - 1) {
                    bl3 = false;
                }
            }
            if (bl3) {
                alm_0 alm_02 = this.bWm.a(this.bR);
                alm_02.jZ(this.r);
                alm_02.kb(this.r);
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
                        cl_1 cl_12 = ((gn_0)this.bWm).LQ();
                        cl_12.m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqB) + this.bWm.d(Lr.bra)));
                        cl_12.m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqE) + this.bWm.d(Lr.bra)));
                        cl_12.m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqC) + this.bWm.d(Lr.bra)));
                        cl_12.m(or_0.VR.tI(), (short)(this.bWm.d(Lr.bqD) + this.bWm.d(Lr.bra)));
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + this.getClass().getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length + "."));
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
        if (this.bT) {
            boolean bl2 = true;
            if (0 < this.bU) {
                int n2 = ((xj_0)this.ajO()).ST();
                int n3 = 0;
                for (xb_2 xb_22 : this.bWm.PJ()) {
                    XV xV = xb_22.ajO();
                    if (xV == null || xV.ST() != n2) continue;
                    ++n3;
                }
                if (n3 != this.bU) {
                    bl2 = false;
                }
            }
            if (bl2) {
                alm_0 alm_02 = this.bWm.a(this.bR);
                alm_02.kb(-this.r);
                if (alm_02.max() < alm_02.atR()) {
                    alm_02.set(alm_02.max());
                }
            }
        }
        super.aK();
    }

    public fv_1 aL() {
        return this.bS;
    }

    public Lr aM() {
        return this.bR;
    }

    /* synthetic */ ah(gv_0 gv_02) {
        this();
    }
}

