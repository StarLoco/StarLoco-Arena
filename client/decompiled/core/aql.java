/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class aql
extends mp_0 {
    private mv_0 cNS;

    public aql(mv_0 mv_02) {
        super("SpellEffect");
        this.cNS = mv_02;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new aHe(this, luaState), new dc(this, luaState), new aot_0(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    static /* synthetic */ mv_0 a(aql aql2) {
        return aql2.cNS;
    }
}

