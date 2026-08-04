/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aDI
extends uc_1 {
    public aDI(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addDialogUnloadedListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        String string2 = this.hZ(1);
        jJ[] jJArray = this.aX(2, n2);
        JX jX = this.agC();
        rw_1 rw_12 = new rw_1(this, jX, string2, jJArray, string);
        aMi.aWT().a(string, rw_12);
    }
}

