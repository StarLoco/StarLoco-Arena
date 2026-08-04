/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from acw
 */
public class acw_0
extends ZT {
    private static final acl_0 aU = new ym_0(new bn_0());
    protected fv_1 bS;
    protected boolean bgq = false;
    protected ArrayList byd = new ArrayList();

    protected acw_0() {
    }

    public acw_0(fv_1 fv_12) {
        this.bS = fv_12;
        this.aG();
    }

    public acw_0(fv_1 fv_12, boolean bl2) {
        this.bS = fv_12;
        this.bgq = bl2;
        this.aG();
    }

    public acw_0 arl() {
        acw_0 acw_02;
        try {
            acw_02 = (acw_0)aU.adr();
            acw_02.uG = aU;
        }
        catch (Exception exception) {
            acw_02 = new acw_0();
            acw_02.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        acw_02.a(this);
        return acw_02;
    }

    public void a(acw_0 acw_02) {
        super.g(acw_02);
        this.bS = acw_02.bS;
        this.bgq = acw_02.bgq;
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
        if (this.bWl != null && 0 < this.byd.size()) {
            for (int j = this.byd.size() - 1; 0 <= j; --j) {
                kc_2 kc_22 = (kc_2)this.byd.get(j);
                if (kc_22 == null) {
                    a.error((Object)("Impossible de lancer l'effet " + acw_0.class.getSimpleName() + " : EffectUser \u00e9gal \u00e0 null."));
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + acw_0.class.getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        this.byd.clear();
        if (this.bWl == null) {
            a.error((Object)("Impossible de calculer l'effet " + this.getClass().getSimpleName() + " : Caster \u00e9gal \u00e0 null."));
        } else if (this.bWm == null) {
            a.error((Object)("Impossible de calculer l'effet " + this.getClass().getSimpleName() + " : Target \u00e9gal \u00e0 null."));
        } else if (this.bdv == null) {
            a.error((Object)("Impossible de calculer l'effet " + this.getClass().getSimpleName() + " : Context \u00e9gal \u00e0 null."));
        } else {
            aii_0 aii_02 = this.bdv.gT();
            if (aii_02 == null) {
                a.error((Object)("Impossible de calculer l'effet " + this.getClass().getSimpleName() + " : EffectUserTargetInformationProvider \u00e9gal \u00e0 null."));
            } else {
                int n2 = Math.min(this.bWl.gn(), this.bWm.gn());
                int n3 = Math.max(this.bWl.gn(), this.bWm.gn());
                int n4 = Math.min(this.bWl.go(), this.bWm.go());
                int n5 = Math.max(this.bWl.go(), this.bWm.go());
                Iterator iterator = aii_02.agn();
                while (iterator.hasNext()) {
                    kc_2 kc_22 = (kc_2)iterator.next();
                    if (kc_22 == null || kc_22 == this.bWl || kc_22 == this.bWm || ((gn_0)kc_22).rD() || n2 > kc_22.gn() || kc_22.gn() > n3 || n4 > kc_22.go() || kc_22.go() > n5) continue;
                    this.byd.add(kc_22);
                }
            }
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

