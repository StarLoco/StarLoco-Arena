/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aIu
 */
class aiu_2
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    private aiu_2(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "setFont";
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
            aod_22.setBubbleFontName(this.hZ(1));
            aod_22.setBubbleFontSize(this.hW(2));
            aod_22.setBubbleFontStyle(this.hW(3));
        }
    }
}

