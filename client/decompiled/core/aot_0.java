/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.HashSet;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aot
 */
class aot_0
extends uc_1 {
    private int cKT;
    private int cKU;
    final /* synthetic */ aql kE;

    public aot_0(aql aql2, LuaState luaState) {
        this.kE = aql2;
        super(luaState);
        this.cKT = 4000;
        this.cKU = 300;
    }

    public String getName() {
        return "displayFlyingValue";
    }

    public LX[] Q() {
        return new LX[]{new LX("R", aos_1.elU, false), new LX("G", aos_1.elU, false), new LX("B", aos_1.elU, false), new LX("negatesValue", aos_1.elV, true)};
    }

    public void c(int n2) {
        ma_1 ma_12;
        QG qG;
        boolean bl2;
        mT mT2;
        float f = (float)this.hX(0);
        float f2 = (float)this.hX(1);
        float f3 = (float)this.hX(2);
        int n3 = aql.a(this.kE).rS();
        if (n3 == 0) {
            return;
        }
        if (n2 >= 4 && this.ic(3)) {
            n3 *= -1;
        }
        if ((mT2 = bd_1.Is().bb(aql.a(this.kE).mS())) == null || !mT2.isVisible()) {
            return;
        }
        boolean bl3 = bl2 = aql.a(this.kE).rR() instanceof ig_1 || aql.a(this.kE).rR() instanceof ib_1;
        if (bl2) {
            qG = new QB(ej_0.n(-50, 50), 50, 2);
            ma_12 = abw_1.e("wci", 5, 30);
        } else {
            qG = new ew_1();
            ma_12 = abw_1.e("wci", 5, 20);
        }
        bd_0 bd_02 = new bd_0(ma_12, String.valueOf(n3), qG, this.cKT);
        bd_02.setColor(f, f2, f3, 1.0f);
        bd_02.c(mT2);
        HashSet hashSet = wj_2.Df().a(mT2);
        if (!bl2 && hashSet != null) {
            bd_02.nr(hashSet.size() * this.cKU);
        }
        wj_2.Df().a(bd_02);
    }

    public LX[] R() {
        return null;
    }
}

