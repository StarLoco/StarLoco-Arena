/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from uC
 */
public class uc_2
extends mp_0 {
    private static final uc_2 aqF = new uc_2();

    public static uc_2 AH() {
        return aqF;
    }

    private uc_2() {
        super("Context");
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new adv_1(this, luaState), new ab_1(this, luaState), new yl_0(this, luaState), new sr_1(this, luaState), new yl(this, luaState), new bv_0(this, luaState), new aii_1(this, luaState), new aov_2(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }
}

