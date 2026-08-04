/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class fY
extends uc_1 {
    final /* synthetic */ aja pd;

    public fY(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "isLower";
    }

    public final LX[] Q() {
        return new LX[]{new LX("param1", aos_1.elR, false), new LX("param2", aos_1.elU, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elV, false)};
    }

    public final void c(int n2) {
        long l2;
        long l3 = this.hY(0);
        boolean bl2 = l3 < (l2 = this.hY(1));
        this.cp(bl2);
    }
}

