/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aaq
 */
class aaq_1
extends uc_1 {
    final /* synthetic */ fp yb;

    public aaq_1(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "fireAction";
    }

    public LX[] Q() {
        return new LX[]{new LX("interactiveElementId", aos_1.elR, false), new LX("action", aos_1.elS, false), new LX("user", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("executed", aos_1.elV, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        long l3 = this.hY(2);
        tp_1 tp_12 = GY.Ss().bF(l2);
        try {
            aez_0 aez_02 = (aez_0)bd_1.Is().bb(l3);
            avr_0 avr_02 = avr_0.valueOf(string);
            boolean bl2 = tp_12.zp().b(avr_02, aez_02);
            this.cp(bl2);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.a(a, "type d'action inconnue " + string);
            this.agB();
            return;
        }
    }
}

