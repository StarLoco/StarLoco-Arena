/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aJU
 */
class aju_2
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private aju_2(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "close";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        if (n3 < 0) {
            return;
        }
        if (rt_0.b(this.nt).remove(n3) != null) {
            add_1.aOG().kO("interactiveBubbleDialog" + n3);
        } else {
            wj_2.Df().ei(n3);
        }
    }

    /* synthetic */ aju_2(rt_0 rt_02, LuaState luaState, aKf aKf2) {
        this(rt_02, luaState);
    }
}

