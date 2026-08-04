/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

class Lz
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public Lz(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "getEvolutionModeInfo";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        cp_2 cp_22 = adY.atu().amq();
        cp_22.a(new ob_2(this));
        lb_0 lb_02 = akp_1.aVO().lB();
        ll_0 ll_02 = lb_02.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((Ei)ll_02.value()).MQ();
        }
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }

    static /* synthetic */ Logger dT() {
        return a;
    }

    static /* synthetic */ Logger kF() {
        return a;
    }

    static /* synthetic */ Logger sP() {
        return a;
    }

    static /* synthetic */ Logger XE() {
        return a;
    }

    static /* synthetic */ Logger XF() {
        return a;
    }

    static /* synthetic */ Logger XG() {
        return a;
    }

    static /* synthetic */ Logger XH() {
        return a;
    }

    static /* synthetic */ Logger XI() {
        return a;
    }
}

