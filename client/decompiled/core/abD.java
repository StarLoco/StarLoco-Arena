/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class abD
extends uc_1 {
    final /* synthetic */ aAZ ciu;

    public abD(aAZ aAZ2, LuaState luaState) {
        this.ciu = aAZ2;
        super(luaState);
    }

    public String getName() {
        return "getTarget";
    }

    public LX[] Q() {
        return null;
    }

    public void c(int n2) {
        this.da(aAZ.a(this.ciu).mS());
    }

    public LX[] R() {
        return new LX[]{new LX("targetId", aos_1.elR, false)};
    }
}

