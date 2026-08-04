/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from akT
 */
class akt_1
extends uc_1 {
    final /* synthetic */ xj_2 acv;

    public akt_1(xj_2 xj_22, LuaState luaState) {
        this.acv = xj_22;
        super(luaState);
    }

    public final String getName() {
        return "executeAction";
    }

    public final LX[] Q() {
        return new LX[]{new LX("actionUID", aos_1.elT, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("actionExists", aos_1.elV, false)};
    }

    public final void c(int n2) {
        int n3 = this.hW(0);
        Eq eq = xj_2.a(this.acv).pd(n3);
        if (eq != null) {
            xj_2.a(this.acv).a(eq, false);
            this.cp(true);
        } else {
            this.cp(false);
        }
    }
}

