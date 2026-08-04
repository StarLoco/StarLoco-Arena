/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Hc
 */
class hc_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public hc_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "selectFirstSphere";
    }

    public LX[] Q() {
        return new LX[]{new LX("funcName", aos_1.elS, true), new LX("params", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        String string = "sphereBoardDialog";
        aji_1 aji_12 = add_1.aOG().azj().lh("sphereBoardDialog");
        if (aji_12 == null) {
            this.a(a, "Dialogue inconnu sphereBoardDialog");
            return;
        }
        String string2 = "sphereBoard";
        ahr_2 ahr_22 = (ahr_2)aji_12.R("sphereBoard");
        if (ahr_22 == null) {
            this.a(a, "ElementDispatcher inconnu sphereBoard dans le dialog sphereBoardDialog");
            return;
        }
        Ei ei = afb_1.auN().auO();
        ayr_0 ayr_02 = (ayr_0)ei.X(ei.MU(), ei.MV());
        ac_2 ac_22 = new ac_2();
        ac_22.a(this.c(ayr_02, null));
        ac_22.f(16921);
        acu_1.ara().c(ac_22);
        if (n2 > 0) {
            String string3 = this.hZ(0);
            jJ[] jJArray = this.aX(1, n2);
            JX jX = this.agC();
            iN iN2 = new iN(jX, string3, jJArray);
            iN2.lG();
        }
    }

    private ayr_0 c(ayr_0 ayr_02, ayr_0 ayr_03) {
        ayr_0 ayr_04 = null;
        if (ayr_02.aLr() != null && ayr_02.aLr() != ayr_03) {
            ayr_04 = ayr_02.aLr();
        } else if (ayr_02.aLs() != null && ayr_02.aLs() != ayr_03) {
            ayr_04 = ayr_02.aLs();
        } else if (ayr_02.aLp() != null && ayr_02.aLp() != ayr_03) {
            ayr_04 = ayr_02.aLp();
        } else if (ayr_02.aLq() != null && ayr_02.aLq() != ayr_03) {
            ayr_04 = ayr_02.aLq();
        }
        if (ayr_04 != null && ayr_04.azm()) {
            return ayr_04;
        }
        return this.c(ayr_04, ayr_02);
    }
}

