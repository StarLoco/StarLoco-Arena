/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from axd
 */
class axd_0
extends uc_1 {
    public axd_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "removeDialogUnloadedListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        aMi.aWT().lF(string);
    }
}

