/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Ha
 */
class ha_1
extends uc_1 {
    final /* synthetic */ apM oR;

    private ha_1(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "characterDisplayerClose";
    }

    public LX[] Q() {
        return new LX[]{new LX("id", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        add_1.aOG().kO(apM.a(this.oR, n3));
    }

    /* synthetic */ ha_1(apM apM2, LuaState luaState, apd_1 apd_12) {
        this(apM2, luaState);
    }
}

