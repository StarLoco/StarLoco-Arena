/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aAE
 */
class aae_1
extends uc_1 {
    final /* synthetic */ aja pd;

    public aae_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "abs";
    }

    public final LX[] Q() {
        return new LX[]{new LX("param", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elR, false)};
    }

    public final void c(int n2) {
        long l2 = this.hY(0);
        this.da(Math.abs(l2));
    }
}

