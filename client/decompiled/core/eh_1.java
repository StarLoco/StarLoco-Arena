/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from eH
 */
class eh_1
extends uc_1 {
    final /* synthetic */ aja pd;

    public eh_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "interrupt";
    }

    public final LX[] Q() {
        return new LX[]{new LX("taskId", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        JX jX = this.agC();
        if (jX != null) {
            if (n2 == 1) {
                jX.gx(this.hW(0));
            } else {
                jX.interrupt();
            }
        }
    }
}

