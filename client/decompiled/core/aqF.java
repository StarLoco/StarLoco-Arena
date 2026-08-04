/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aqF
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private aqF(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "show";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        aod_2 aod_22 = (aod_2)rt_0.b(this.nt).get(this.hW(0));
        if (aod_22 != null) {
            aod_22.show();
        }
    }

    /* synthetic */ aqF(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

