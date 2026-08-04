/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from YG
 */
class yg_2
extends uc_1 {
    final /* synthetic */ fp yb;

    private yg_2(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "setAnimation";
    }

    public LX[] Q() {
        return new LX[]{new LX("id", aos_1.elR, false), new LX("animationName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        tp_1 tp_12 = GY.Ss().bF(l2);
        if (tp_12 == null) {
            this.a(a, "l'eleemnt d'id " + l2 + " n'existe pas");
            return;
        }
        tp_12.aY(string);
    }

    /* synthetic */ yg_2(fp fp2, LuaState luaState, ay_1 ay_12) {
        this(fp2, luaState);
    }
}

