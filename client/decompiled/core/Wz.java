/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class Wz
extends mp_0 {
    private static final Wz bUy = new Wz();

    protected Wz() {
        super("Particle");
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new agj_0(luaState), new ajm_1(luaState), new ec_1(luaState), new eT(luaState), new su_2(luaState), new tk_0(luaState), new vg_0(luaState), new adj_1(luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    public static Wz ajg() {
        return bUy;
    }
}

