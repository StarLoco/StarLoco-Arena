/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from xJ
 */
public class xj_2
extends mp_0 {
    private final akb_2 azs;

    public xj_2(akb_2 akb_22) {
        super("ScriptedAction");
        this.azs = akb_22;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new wk_0(this, luaState), new akt_1(this, luaState), new pN(this, luaState), new qb_1(this, luaState), new vx(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    public final akb_2 Er() {
        return this.azs;
    }

    static /* synthetic */ akb_2 a(xj_2 xj_22) {
        return xj_22.azs;
    }
}

