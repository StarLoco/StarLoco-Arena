/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aDV
 */
class adv_1
extends uc_1 {
    final /* synthetic */ uc_2 aBX;

    public adv_1(uc_2 uc_22, LuaState luaState) {
        this.aBX = uc_22;
        super(luaState);
    }

    public String getName() {
        return "getPlayer";
    }

    public LX[] Q() {
        return null;
    }

    public final LX[] R() {
        return new LX[]{new LX("localPlayerId", aos_1.elR, false)};
    }

    protected void c(int n2) {
        this.da(apN.aDK().Ln().getId());
    }
}

