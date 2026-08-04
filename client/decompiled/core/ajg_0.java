/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ajg
 */
class ajg_0
extends uc_1 {
    final /* synthetic */ aja pd;

    private ajg_0(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public String getName() {
        return "positionToLong";
    }

    public LX[] Q() {
        return new LX[]{new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false), new LX("z", aos_1.elU, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elR, false)};
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        int n5 = this.hW(2);
        this.da(wi_2.u(n3, n4, (short)n5));
    }

    /* synthetic */ ajg_0(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

