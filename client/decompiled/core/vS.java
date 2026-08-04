/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class vS
extends uc_1 {
    final /* synthetic */ aja pd;

    private vS(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public String getName() {
        return "longToPosition";
    }

    public LX[] Q() {
        return new LX[]{new LX("x", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false), new LX("z", aos_1.elU, false)};
    }

    protected void c(int n2) {
        ry ry2 = wi_2.dc(this.hY(0));
        this.id(ry2.getX());
        this.id(ry2.getY());
        this.id(ry2.wk());
    }

    /* synthetic */ vS(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

