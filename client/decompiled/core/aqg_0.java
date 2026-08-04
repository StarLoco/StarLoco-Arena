/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.ArrayList;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aqg
 */
class aqg_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public aqg_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "addEventListener";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false), new LX("id", aos_1.elS, false), new LX("eventType", aos_1.elS, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        qe_1 qe_12;
        String string = this.hZ(0);
        aji_1 aji_12 = add_1.aOG().azj().lh(string);
        if (aji_12 == null) {
            this.a(a, "Dialogue inconnu " + string);
            return;
        }
        String string2 = this.hZ(1);
        na_1 na_12 = aji_12.R(string2);
        if (na_12 == null) {
            this.a(a, "ElementDispatcher inconnu " + string2 + " dans le dialog " + string);
            return;
        }
        String string3 = this.hZ(2);
        boolean bl2 = string3.equals("MOUSE_CLICKED_AND_DOUBLE_CLICKED");
        qe_1 qe_13 = qe_12 = bl2 ? qe_1.bFB : qe_1.valueOf(string3);
        if (qe_12 == null) {
            this.a(a, "Type d'evnement inconnu " + string3 + " pour le dialog " + string);
            return;
        }
        String string4 = this.hZ(3);
        jJ[] jJArray = this.aX(4, n2);
        JX jX = this.agC();
        ov_1 ov_12 = (ov_1)aMi.aWT().a(jX, string, string2, string3, string4);
        if (ov_12 == null) {
            ov_12 = new iN(jX, string4, jJArray);
            aMi.aWT().a(jX, string, string2, string3, string4, ov_12);
            na_12.a(qe_12, ov_12, false);
            if (bl2) {
                na_12.a(qe_1.bFC, ov_12, false);
            }
        } else {
            ((iN)ov_12).a(jJArray);
            ArrayList arrayList = na_12.getListeners(qe_12, false);
            if (arrayList == null || !arrayList.contains(ov_12)) {
                na_12.a(qe_12, ov_12, false);
                if (bl2) {
                    na_12.a(qe_1.bFC, ov_12, false);
                }
            }
        }
    }
}

