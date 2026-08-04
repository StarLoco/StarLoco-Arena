/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from rQ
 */
class rq_1
extends uc_1 {
    final /* synthetic */ vp_2 am;

    public rq_1(vp_2 vp_22, LuaState luaState) {
        this.am = vp_22;
        super(luaState);
    }

    public String getName() {
        return "setPointLightRange";
    }

    public LX[] Q() {
        return new LX[]{new LX("lightId", aos_1.elT, false), new LX("range", aos_1.elU, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        lP lP2 = (lP)vp_2.a(this.am).get(this.hW(0));
        if (lP2 != null) {
            lP2.u((float)this.hX(1));
        } else {
            this.a(a, "La lumi\u00e8re n'existe pas");
        }
    }
}

