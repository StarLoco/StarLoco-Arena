/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ik
 */
class ik_1
extends uc_1 {
    final /* synthetic */ fp yb;

    public ik_1(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "setVisible";
    }

    public LX[] Q() {
        return new LX[]{new LX("id", aos_1.elR, false), new LX("visible", aos_1.elV, false)};
    }

    public LX[] R() {
        return new LX[0];
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        boolean bl2 = this.ic(1);
        tp_1 tp_12 = GY.Ss().bF(l2);
        if (tp_12 == null) {
            this.a(a, "l'element interactif d'id " + l2 + " n'existe pas.");
            return;
        }
        tp_12.setVisible(bl2);
    }
}

