/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Ot
 */
class ot_1
extends uc_1 {
    final /* synthetic */ akn yt;

    private ot_1(akn akn2, LuaState luaState) {
        this.yt = akn2;
        super(luaState);
    }

    public String getName() {
        return "deleteActor";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        bd_1.Is().ba(l2);
    }

    /* synthetic */ ot_1(akn akn2, LuaState luaState, KX kX) {
        this(akn2, luaState);
    }
}

