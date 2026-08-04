/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aHe
extends uc_1 {
    final /* synthetic */ aql kE;

    public aHe(aql aql2, LuaState luaState) {
        this.kE = aql2;
        super(luaState);
    }

    public String getName() {
        return "getTarget";
    }

    public LX[] Q() {
        return null;
    }

    public void c(int n2) {
        this.da(aql.a(this.kE).mS());
    }

    public LX[] R() {
        return new LX[]{new LX("targetId", aos_1.elR, false)};
    }
}

