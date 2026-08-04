/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from alG
 */
class alg_0
extends uc_1 {
    final /* synthetic */ rt_0 nt;

    public alg_0(rt_0 rt_02, LuaState luaState) {
        this.nt = rt_02;
        super(luaState);
    }

    public String getName() {
        return "setScreenPosition";
    }

    public LX[] Q() {
        return new LX[]{new LX("bubbleId", aos_1.elT, false), new LX("align", aos_1.elS, false), new LX("screenXoffset", aos_1.elT, false), new LX("screenYoffset", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        aod_2 aod_22 = (aod_2)rt_0.b(this.nt).get(n3);
        if (aod_22 != null) {
            ajn_1 ajn_12 = ajn_1.valueOf(this.hZ(1));
            int n4 = this.hW(2);
            int n5 = this.hW(3);
            auW auW2 = new auW();
            auW2.b();
            auW2.setAlign(ajn_12);
            auW2.setXOffset(n4);
            auW2.setYOffset(n5);
            aod_22.a(auW2);
        }
        a.info((Object)("SetScreenPosition " + aod_22 + " "));
    }
}

