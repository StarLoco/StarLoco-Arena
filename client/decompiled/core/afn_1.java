/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from afn
 */
class afn_1
extends uc_1 {
    private afn_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "removeDialogProperty";
    }

    public LX[] Q() {
        return new LX[]{new LX("property", aos_1.elS, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        azs_0.aLV().kb(string);
    }

    /* synthetic */ afn_1(LuaState luaState, apd_1 apd_12) {
        this(luaState);
    }
}

