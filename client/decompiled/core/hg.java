/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class hg
extends uc_1 {
    final /* synthetic */ aja pd;

    private hg(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public String getName() {
        return "toLong";
    }

    public LX[] Q() {
        return new LX[]{new LX("param", aos_1.elQ, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elR, false)};
    }

    protected void c(int n2) {
        this.da(this.hY(0));
    }

    /* synthetic */ hg(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

