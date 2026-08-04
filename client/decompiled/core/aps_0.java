/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aPs
 */
class aps_0
extends uc_1 {
    final /* synthetic */ fp yb;

    public aps_0(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "isUsable";
    }

    public LX[] Q() {
        return new LX[]{new LX("elementId", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("usable", aos_1.elV, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        tp_1 tp_12 = GY.Ss().bF(l2);
        if (tp_12 == null) {
            this.a(a, "l'element interactif d'id " + l2 + " n'existe pas.....");
            this.agB();
            return;
        }
        do_1 do_12 = (do_1)tp_12.zp();
        this.cp(do_12.isUsable());
    }
}

