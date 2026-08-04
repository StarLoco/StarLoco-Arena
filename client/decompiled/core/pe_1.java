/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from pe
 */
class pe_1
extends uc_1 {
    final /* synthetic */ agk abf;

    public pe_1(agk agk2, LuaState luaState) {
        this.abf = agk2;
        super(luaState);
    }

    public String getName() {
        return "gotoAnimation";
    }

    public LX[] Q() {
        return new LX[]{new LX("animationName", aos_1.elS, false), new LX("percent", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        if (n2 == 2) {
            int n3 = this.hW(1);
            if (ej_0.am(100) > n3) {
                return;
            }
        }
        String string = this.hZ(0);
        this.abf.he.aY(string);
    }
}

