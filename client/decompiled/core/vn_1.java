/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Vn
 */
public class vn_1
extends ZT {
    private static final acl_0 aU = new ym_0(new alw_1());
    protected ArrayList byd = new ArrayList();

    public vn_1() {
        this.aG();
    }

    public vn_1 aij() {
        vn_1 vn_12;
        try {
            vn_12 = (vn_1)aU.adr();
            vn_12.uG = aU;
        }
        catch (Exception exception) {
            vn_12 = new vn_1();
            vn_12.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + vn_1.class.getSimpleName() + " : " + exception.getMessage()));
        }
        vn_12.g(this);
        return vn_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (bl2 && this.bWl != null && 0 < this.byd.size()) {
            for (int j = this.byd.size() - 1; 0 <= j; --j) {
                kc_2 kc_22 = (kc_2)this.byd.get(j);
                if (kc_22 == null) {
                    a.error((Object)("Impossible de lancer l'effet " + vn_1.class.getSimpleName() + " : EffectUser \u00e9gal \u00e0 null."));
                    continue;
                }
                yk_0 yk_02 = yk_0.b(this.bdv, this.r, this.bWl);
                yk_02.akd();
                yk_02.g(this.bWl);
                yk_02.i(kc_22);
                yk_02.release();
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
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + vn_1.class.getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length));
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
}

