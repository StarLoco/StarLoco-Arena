/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class MM
extends ZT {
    private static final acl_0 aU = new ym_0(new ar_2());
    protected ArrayList byd = new ArrayList();

    public MM() {
        this.aG();
    }

    public MM Zu() {
        MM mM;
        try {
            mM = (MM)aU.adr();
            mM.uG = aU;
        }
        catch (Exception exception) {
            mM = new MM();
            mM.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + MM.class.getSimpleName() + " : " + exception.getMessage()));
        }
        mM.g(this);
        return mM;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (bl2 && this.bWl != null && 0 < this.byd.size()) {
            for (int j = this.byd.size() - 1; 0 <= j; --j) {
                kc_2 kc_22 = (kc_2)this.byd.get(j);
                if (kc_22 == null) {
                    a.error((Object)("Impossible de lancer l'effet " + MM.class.getSimpleName() + " : EffectUser \u00e9gal \u00e0 null."));
                    continue;
                }
                df_2 df_22 = df_2.c(this.bdv, this.r, this.bWl);
                df_22.akd();
                df_22.g(this.bWl);
                df_22.i(kc_22);
                df_22.release();
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + MM.class.getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length));
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

