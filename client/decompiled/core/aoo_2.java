/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aoO
 */
class aoo_2
extends uc_1 {
    final /* synthetic */ fp yb;

    public aoo_2(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "getPosition";
    }

    public LX[] Q() {
        return new LX[]{new LX("elementId", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("x", aos_1.elT, false), new LX("y", aos_1.elT, false), new LX("z", aos_1.elT, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        tp_1 tp_12 = GY.Ss().bF(l2);
        if (tp_12 == null) {
            this.a(a, this.getName() + " : l'element interactif d'id " + l2 + " n'existe pas...");
            this.agB();
            this.agB();
            this.agB();
            return;
        }
        ry ry2 = tp_12.aTI();
        this.id(ry2.getX());
        this.id(ry2.getY());
        this.id(ry2.wk());
    }
}

