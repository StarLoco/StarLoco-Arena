/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aJE
 */
class aje_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public aje_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "serializeTeam";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3;
        mk_2 mk_22 = new mk_2("team");
        zK zK2 = bs_0.IF().at((short)0);
        long[] lArray = zK2.afE().eJ();
        et_2[] et_2Array = new et_2[lArray.length];
        for (n3 = 0; n3 < lArray.length; ++n3) {
            et_2Array[n3] = adY.atu().dz(lArray[n3]).Om();
        }
        mk_22.a(et_2Array);
        if (!br.b(mk_22)) {
            add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
        }
        mk_22 = new mk_2("team");
        br.a(mk_22);
        for (n3 = 0; n3 < mk_22.rt().length; ++n3) {
            aNb aNb2 = new aNb();
            aNb2.fn(false);
            aNb2.h(mk_22.rt()[n3]);
            apN.aDK().vJ().b(aNb2);
        }
    }
}

