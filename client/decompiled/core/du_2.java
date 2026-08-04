/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from dU
 */
public class du_2
extends uc_1 {
    public du_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setCarriedMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("carrierId", aos_1.elR, false), new LX("carriedId", aos_1.elR, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        boolean bl2 = n2 > 1;
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "Pas de carrier trouv\u00e9 avec l'id " + l2);
            return;
        }
        if (bl2) {
            if (mT2.rE()) {
                this.a(a, "Le mobile " + l2 + " porte deja qq ");
                return;
            }
            long l3 = this.hY(1);
            mT mT3 = bd_1.Is().bb(l3);
            if (mT3 != null && mT3.rD()) {
                this.a(a, "Le mobile " + l2 + " est deja port\u00e9 ou est null ");
                return;
            }
            mT2.d(mT3);
        } else {
            if (!mT2.rE()) {
                this.a(a, "Le mobile " + l2 + " ne porte personne ");
                return;
            }
            mT2.rF();
        }
    }
}

