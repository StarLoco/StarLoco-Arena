/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ME
 */
public class me_1
extends ZT {
    private static final acl_0 aU = new ym_0(new dp_2());

    public me_1() {
        this.aG();
    }

    public me_1 YI() {
        me_1 me_12;
        try {
            me_12 = (me_1)aU.adr();
            me_12.uG = aU;
        }
        catch (Exception exception) {
            me_12 = new me_1();
            me_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un HPDebuff : " + exception.getMessage()));
        }
        return me_12;
    }

    public void aG() {
        super.aG();
        this.bWt.set(4);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm != null && this.bWm.b(Lr.bqx)) {
            this.bWm.a(Lr.bqx).kb(-this.r);
            if (this.bWm.a(Lr.bqx).atR() > this.bWm.a(Lr.bqx).max()) {
                this.bWm.a(Lr.bqx).set(this.bWm.a(Lr.bqx).max());
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
            case 3: {
                this.r = ou_1.aE((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1)) + (int)((xj_0)this.bWj).iY(2);
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un HPDebuff : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
    }

    public void aK() {
        if (this.bWm != null && this.bWm.b(Lr.bqx)) {
            this.bWm.a(Lr.bqx).kb(this.r);
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
}

