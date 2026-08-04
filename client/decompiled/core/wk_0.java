/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from wk
 */
class wk_0
extends uc_1 {
    final /* synthetic */ xj_2 acv;

    public wk_0(xj_2 xj_22, LuaState luaState) {
        this.acv = xj_22;
        super(luaState);
    }

    public final String getName() {
        return "getActions";
    }

    public final LX[] Q() {
        return new LX[]{new LX("actionType", aos_1.elT, false), new LX("actionId", aos_1.elT, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("actions", aos_1.elX, false)};
    }

    public final void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        this.L.newTable();
        int n5 = 1;
        for (Eq eq : xj_2.a(this.acv).aVH()) {
            if (eq.M() != n4 || eq.Nk() != n3) continue;
            this.L.pushNumber((double)n5++);
            this.L.newTable();
            this.L.pushString("uid");
            this.L.pushNumber((double)eq.Ao());
            this.L.setTable(-3);
            this.L.pushString("from");
            this.L.pushNumber((double)eq.Nl());
            this.L.setTable(-3);
            this.L.pushString("target");
            this.L.pushNumber((double)eq.mS());
            this.L.setTable(-3);
            this.L.setTable(-3);
        }
        ++this.bPs;
    }
}

