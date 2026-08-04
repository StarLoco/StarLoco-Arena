/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class axR
extends uc_1 {
    final /* synthetic */ aja pd;

    private axR(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public String getName() {
        return "toString";
    }

    public LX[] Q() {
        return new LX[]{new LX("param1", aos_1.elX, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("result", aos_1.elS, false)};
    }

    protected void c(int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < n2; ++j) {
            String string = this.ib(j);
            stringBuilder.append(string != null ? string : null);
        }
        this.fY(stringBuilder.toString());
    }

    /* synthetic */ axR(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

