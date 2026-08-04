/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class akn
extends mp_0 {
    private static final akn cDn = new akn();

    protected akn() {
        super("Actor");
    }

    public static akn azL() {
        return cDn;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new aac_0(this, luaState, null), new ot_1(this, luaState, null), new ahc_0(this, luaState), new ix_0(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }
}

