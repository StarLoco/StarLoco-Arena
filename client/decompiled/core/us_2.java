/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Us
 */
public class us_2
extends mp_0 {
    private re_0 bPR;

    public us_2(re_0 re_02) {
        super("Cast");
        this.bPR = re_02;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new su_0(this, luaState), new Uo(this, luaState), new avc_0(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    static /* synthetic */ re_0 a(us_2 us_22) {
        return us_22.bPR;
    }
}

