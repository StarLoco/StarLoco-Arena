/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ay
 */
public class ay_2
extends ig_1 {
    private static final acl_0 aU = new ym_0(new jv_0());

    private ay_2() {
    }

    public ay_2(fv_1 fv_12) {
        super(fv_12, true);
    }

    public ay_2 Hk() {
        ay_2 ay_22;
        try {
            ay_22 = (ay_2)aU.adr();
            ay_22.uG = aU;
        }
        catch (Exception exception) {
            ay_22 = new ay_2();
            ay_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage() + "."));
        }
        ay_22.a(this);
        return ay_22;
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

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                if (this.bWm == null || !this.bWm.b(Lr.bqx)) {
                    this.r = 0;
                    break;
                }
                int n2 = (int)((xj_0)this.bWj).Tb()[0];
                int n3 = this.bWm.a(Lr.bqx).atR();
                this.r = us_0.U(n3 * n2 / 100);
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + this.getClass().getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length + "."));
                this.r = 0;
            }
        }
        if (this.bWm.b(Lr.brc)) {
            this.bgm = us_0.U((float)(this.r * this.bWm.d(Lr.brc)) / 100.0f);
        }
    }

    /* synthetic */ ay_2(jv_0 jv_02) {
        this();
    }
}

