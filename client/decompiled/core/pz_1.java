/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Pz
 */
class pz_1
extends uc_1 {
    final /* synthetic */ vp_2 am;

    public pz_1(vp_2 vp_22, LuaState luaState) {
        this.am = vp_22;
        super(luaState);
    }

    public String getName() {
        return "removeGlobalLight";
    }

    public LX[] Q() {
        return new LX[]{new LX("lightId", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        ahn_0.dNL.b((afr_1)vp_2.b(this.am).get(n3));
        vp_2.b(this.am).remove(n3);
    }
}

