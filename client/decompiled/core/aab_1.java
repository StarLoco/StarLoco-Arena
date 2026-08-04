/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aab
 */
class aab_1
extends uc_1 {
    final /* synthetic */ apM oR;

    public aab_1(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "addSphereSelectionListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
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
        na_1 na_12 = aji_12.R("sphereBoard");
        if (na_12 == null) {
            this.a(a, "ElementDispatcher inconnu sphereBoard dans le dialog sphereBoardDialog");
            return;
        }
        qe_1 qe_12 = qe_1.bFB;
        String string3 = this.hZ(0);
        jJ[] jJArray = this.aX(1, n2);
        JX jX = this.agC();
        iN iN2 = new iN(jX, string3, jJArray);
        lv_0 lv_02 = new lv_0(this, iN2, jX, qe_12, string3);
        aMi.aWT().a(jX, "sphereBoardDialog", "sphereBoard", qe_12.name(), string3, lv_02);
        na_12.a(qe_12, lv_02, false);
    }
}

