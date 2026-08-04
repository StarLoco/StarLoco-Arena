/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class Ct
extends uc_1 {
    private Ct(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "writeInChat";
    }

    public LX[] Q() {
        return new LX[]{new LX("message", aos_1.elS, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = aon_0.aYc().getString(this.hZ(0));
        int n3 = 5;
        ql_1.acX().p(string, 5);
    }

    /* synthetic */ Ct(LuaState luaState, apd_1 apd_12) {
        this(luaState);
    }
}

