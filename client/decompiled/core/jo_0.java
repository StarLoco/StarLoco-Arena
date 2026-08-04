/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Jo
 */
class jo_0
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    public jo_0(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "setOffset";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false), new LX("x", aos_1.elT, false), new LX("y", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        aod_2 aod_22 = (aod_2)rt_0.b(this.nt).get(this.hW(0));
        if (aod_22 != null) {
            aod_22.setOffset(this.hW(1), this.hW(2));
        }
    }
}

