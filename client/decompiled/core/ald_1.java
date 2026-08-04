/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aLd
 */
class ald_1
extends uc_1 {
    final /* synthetic */ aja pd;

    public ald_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "trace";
    }

    public final LX[] Q() {
        return new LX[]{new LX("message", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        StringBuilder stringBuilder = new StringBuilder("[ligne: " + this.getLineNumber() + "]");
        for (int j = 0; j < n2; ++j) {
            String string = this.ib(j);
            stringBuilder.append(", ").append(string != null ? string : null);
        }
        a.info((Object)stringBuilder.toString());
    }
}

