/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Qb
 */
class qb_1
extends uc_1 {
    final /* synthetic */ xj_2 acv;

    public qb_1(xj_2 xj_22, LuaState luaState) {
        this.acv = xj_22;
        super(luaState);
    }

    public final String getName() {
        return "executeAllAction";
    }

    public final LX[] Q() {
        return new LX[]{new LX("actionType", aos_1.elT, false), new LX("actionId", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    public final void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        Eq eq = xj_2.a(this.acv).ci(n3, n4);
        while (eq != null) {
            xj_2.a(this.acv).a(eq, false);
            eq = xj_2.a(this.acv).ci(n3, n4);
        }
    }
}

