/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from eR
 */
class er_2
extends uc_1 {
    public er_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "removeDialogLoadedListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        aMi.aWT().lG(string);
    }
}

