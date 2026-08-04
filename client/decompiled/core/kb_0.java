/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Kb
 */
public class kb_0
extends ZT {
    private boolean bnh = false;
    private static final acl_0 aU = new ym_0(new all_0());

    public kb_0() {
        this.aG();
    }

    public kb_0 Wz() {
        kb_0 kb_02;
        try {
            kb_02 = (kb_0)aU.adr();
            kb_02.uG = aU;
        }
        catch (Exception exception) {
            kb_02 = new kb_0();
            kb_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un HPGain : " + exception.getMessage()));
        }
        return kb_02;
    }

    public void aG() {
        super.aG();
        this.bWt.set(1);
    }

    public void a(int n2, float f, boolean bl2) {
        super.a(n2, f, bl2);
        switch (n2) {
            case 0: {
                if (bl2) break;
                this.r = (int)((float)this.r + (float)this.r * f / 100.0f);
                break;
            }
            case 1: {
                if (!bl2) {
                    this.r = (int)((float)this.r + f);
                    break;
                }
                this.r = us_0.U(f);
                break;
            }
        }
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqx)) {
            alm_0 alm_02 = this.bWm.a(Lr.bqx);
            int n2 = alm_02.atR();
            alm_02.jZ(this.r);
            if (this.bnh && alm_02.atR() > alm_02.max()) {
                alm_02.set(alm_02.max());
            }
            this.r = alm_02.atR() - n2;
            if (this.bWl instanceof gn_0) {
                ((gn_0)this.bWl).LQ().l(or_0.Xy.tI(), (short)this.r);
                ((gn_0)this.bWl).LQ().l(or_0.aac.tI(), (short)this.r);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.bnh = true;
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            case 3: {
                this.r = ou_1.A((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1), (int)((xj_0)this.bWj).iY(2));
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un HPGain : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        float f = this.r;
        if (this.bWl != null && this.bWl.b(Lr.bqX)) {
            f *= (float)(100 + this.bWl.d(Lr.bqX)) / 100.0f;
        }
        this.r = Math.max(0, us_0.U(f));
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

