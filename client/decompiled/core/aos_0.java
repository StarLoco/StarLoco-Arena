/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aoS
 */
class aos_0
extends uc_1 {
    final /* synthetic */ vp_2 am;

    public aos_0(vp_2 vp_22, LuaState luaState) {
        this.am = vp_22;
        super(luaState);
    }

    public String getName() {
        return "setPointLightColor";
    }

    public LX[] Q() {
        return new LX[]{new LX("lightId", aos_1.elT, false), new LX("red", aos_1.elU, false), new LX("green", aos_1.elU, false), new LX("blue", aos_1.elU, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        lP lP2 = (lP)vp_2.a(this.am).get(this.hW(0));
        if (lP2 != null) {
            lP2.t((float)this.hX(1), (float)this.hX(2), (float)this.hX(3));
        } else {
            this.a(a, "La lumi\u00e8re n'existe pas");
        }
    }
}

