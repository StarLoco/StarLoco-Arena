/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from LQ
 */
class lq_1
extends uc_1 {
    final /* synthetic */ apM oR;

    public lq_1(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "removeSphereSelectionListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("funcName", aos_1.elS, false)};
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
        JX jX = this.agC();
        ov_1 ov_12 = (ov_1)aMi.aWT().b(jX, "sphereBoardDialog", "sphereBoard", qe_12.name(), string3);
        na_12.b(qe_12, ov_12, false);
    }
}

