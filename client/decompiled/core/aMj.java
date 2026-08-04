/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class aMj
extends uc_1 {
    final /* synthetic */ fp yb;

    public aMj(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "isVisible";
    }

    public LX[] Q() {
        return new LX[]{new LX("elementId", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("visible", aos_1.elV, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        tp_1 tp_12 = GY.Ss().bF(l2);
        if (tp_12 == null) {
            this.a(a, "l'element interactif d'id " + l2 + " n'existe pas..");
            this.agB();
            return;
        }
        this.cp(tp_12.isVisible());
    }
}

