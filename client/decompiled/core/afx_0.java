/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from afx
 */
class afx_0
extends uc_1 {
    private afx_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setDialogProperty";
    }

    public LX[] Q() {
        return new LX[]{new LX("property", aos_1.elS, false), new LX("value", aos_1.elV, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        boolean bl2 = this.ic(1);
        azs_0.aLV().g(string, bl2);
    }

    /* synthetic */ afx_0(LuaState luaState, apd_1 apd_12) {
        this(luaState);
    }
}

