/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from FC
 */
class fc_1
extends uc_1 {
    final /* synthetic */ aja pd;

    public fc_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "randomFloat";
    }

    public final LX[] Q() {
        return new LX[]{new LX("param1", aos_1.elU, false), new LX("param2", aos_1.elU, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elU, false)};
    }

    public final void c(int n2) {
        double d = this.hX(0);
        double d2 = this.hX(1);
        float f = ej_0.e((float)d, (float)d2);
        this.as(f);
    }
}

