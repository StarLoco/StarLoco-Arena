/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Fj
 */
class fj_2
extends uc_1 {
    final /* synthetic */ apM oR;

    private fj_2(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "characterDisplayerChangeLinkage";
    }

    public LX[] Q() {
        return new LX[]{new LX("id", aos_1.elT, false), new LX("animName", aos_1.elS, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        String string = this.hZ(1);
        String string2 = apM.a(this.oR, n3);
        String string3 = string.substring(2);
        int n4 = Integer.parseInt(string.substring(0, 1));
        azs_0.aLV().a("animName", (Object)string3, string2);
        azs_0.aLV().a("direction", (Object)n4, string2);
    }

    /* synthetic */ fj_2(apM apM2, LuaState luaState, apd_1 apd_12) {
        this(apM2, luaState);
    }
}

