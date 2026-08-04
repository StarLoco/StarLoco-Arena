/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ave
 */
class ave_0
extends uc_1 {
    final /* synthetic */ vp_2 am;

    public ave_0(vp_2 vp_22, LuaState luaState) {
        this.am = vp_22;
        super(luaState);
    }

    public String getName() {
        return "addGlobalLight";
    }

    public LX[] Q() {
        return new LX[]{new LX("red", aos_1.elU, false), new LX("green", aos_1.elU, false), new LX("blue", aos_1.elU, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("lightId", aos_1.elT, false)};
    }

    protected void c(int n2) {
        ty_2 ty_22 = new ty_2();
        ty_22.c((float)this.hX(0), (float)this.hX(1), (float)this.hX(2));
        ahn_0.dNL.a(ty_22);
        int n3 = vp_2.BH();
        vp_2.b(this.am).c(n3, ty_22);
        this.id(n3);
    }
}

