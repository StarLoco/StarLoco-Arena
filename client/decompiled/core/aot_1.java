/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aoT
 */
class aot_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public aot_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "removeMail";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        ads_0 ads_02 = new ads_0();
        aLb aLb2 = (aLb)ayg_0.aKP().aKR().get(ayg_0.aKP().aKR().size() - 1);
        if (aLb2 != null) {
            ads_02.l(new long[]{aLb2.getId()});
            apN.aDK().vJ().b(ads_02);
            ayg_0.aKP().cQ(aLb2.getId());
        }
    }
}

