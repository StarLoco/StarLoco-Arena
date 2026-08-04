/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aeZ
 */
public class aez_1
extends ZT {
    private static final acl_0 aU = new ym_0(new pX());
    protected fv_1 bS;
    protected boolean bgq = false;
    protected ArrayList byd = new ArrayList();

    protected aez_1() {
    }

    public aez_1(fv_1 fv_12) {
        this.bS = fv_12;
        this.aG();
    }

    public aez_1(fv_1 fv_12, boolean bl2) {
        this.bS = fv_12;
        this.bgq = bl2;
        this.aG();
    }

    public aez_1 auM() {
        aez_1 aez_12;
        try {
            aez_12 = (aez_1)aU.adr();
            aez_12.uG = aU;
        }
        catch (Exception exception) {
            aez_12 = new aez_1();
            aez_12.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + aez_1.class.getSimpleName() + " : " + exception.getMessage()));
        }
        aez_12.a(this);
        return aez_12;
    }

    public void a(aez_1 aez_12) {
        super.g(aez_12);
        this.bS = aez_12.bS;
        this.bgq = aez_12.bgq;
    }

    public void a(int n2, float f, boolean bl2) {
        super.a(n2, f, bl2);
        switch (n2) {
            case 0: {
                if (bl2) break;
                this.r += us_0.U((float)this.r * f / 100.0f);
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
        this.r = Math.max(0, this.r);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (bl2 && this.bWl != null && 0 < this.byd.size()) {
            for (int j = this.byd.size() - 1; 0 <= j; --j) {
                kc_2 kc_22 = (kc_2)this.byd.get(j);
                if (kc_22 == null) {
                    a.error((Object)("Impossible de lancer l'effet " + aez_1.class.getSimpleName() + " : EffectUser \u00e9gal \u00e0 null."));
                    continue;
                }
                int n2 = ig_1.a(this.r, this.bWl, kc_22, ((gn_0)this.bWl).gg(), this.bS, false, ((xj_0)this.ajO()).alM().fj().aoa());
                if (0 >= n2) continue;
                ig_1 ig_12 = ig_1.b(this.bdv, this.bS, n2, this.bWl);
                ig_12.akd();
                ig_12.g(this.bWl);
                ig_12.i(kc_22);
                ig_12.release();
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
                this.r = ou_1.A((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1), (int)((xj_0)this.bWj).iY(2));
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + aez_1.class.getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        this.byd.clear();
        for (kc_2 kc_22 : vv_0.aiq().a(this.bWl, this.bdv.gT(), ((xj_0)this.bWj).alM(), this.bWl.gn(), this.bWl.go(), this.bWl.gp())) {
            if (kc_22 == this.bWl) continue;
            this.byd.add(kc_22);
        }
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }

    public boolean aH() {
        return false;
    }

    public fv_1 aL() {
        return this.bS;
    }
}

