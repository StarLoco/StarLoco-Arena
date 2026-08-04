/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZD
 */
public class zd_1
extends ZT {
    private static final acl_0 aU = new ym_0(new pi_2());
    protected Lr bR;
    protected boolean ccI;
    private fv_1 bS;
    private boolean bT = false;
    protected int bU;

    protected zd_1() {
    }

    public zd_1(fv_1 fv_12, Lr lr) {
        this.bS = fv_12;
        this.ccI = true;
        this.bR = lr;
        this.aG();
    }

    public zd_1 anZ() {
        zd_1 zd_12;
        try {
            zd_12 = (zd_1)aU.adr();
            zd_12.uG = aU;
        }
        catch (Exception exception) {
            zd_12 = new zd_1();
            zd_12.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        zd_12.bR = this.bR;
        zd_12.ccI = this.ccI;
        zd_12.bS = this.bS;
        zd_12.bT = false;
        zd_12.bU = 0;
        return zd_12;
    }

    public void aG() {
        super.aG();
        if (this.bR != null) {
            switch (this.bR) {
                case bqx: {
                    this.bWt.set(4);
                    break;
                }
                case bqy: {
                    this.bWt.set(54);
                    break;
                }
                case bqz: {
                    this.bWt.set(64);
                    break;
                }
                case bqA: {
                    this.bWt.set(172);
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
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.ccI) {
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
                    if (n3 != this.bU) {
                        bl3 = false;
                    }
                }
                if (bl3) {
                    alm_0 alm_02 = this.bWm.a(this.bR);
                    alm_02.kb(-this.r);
                    alm_02.ka(this.r);
                }
                this.bT = true;
            } else {
                this.aoy();
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
        this.ccI = true;
        int n2 = 0;
        switch (this.bR) {
            case bqz: {
                if (!this.bWm.b(Lr.bqZ)) break;
                n2 = this.bWm.d(Lr.bqZ);
                break;
            }
            case bqy: {
                if (!this.bWm.b(Lr.bqY)) break;
                n2 = this.bWm.d(Lr.bqY);
                break;
            }
            default: {
                n2 = 0;
            }
        }
        if (ou_1.he(100) <= n2) {
            this.ccI = false;
        }
    }

    public void aK() {
        if (this.bT) {
            if (this.bWm != null && this.bWm.b(this.bR)) {
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
                    this.bWm.a(this.bR).kb(this.r);
                }
            }
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
}

