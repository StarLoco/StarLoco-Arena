/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from UL
 */
class ul_2
extends uc_1 {
    final /* synthetic */ apM oR;

    public ul_2(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "enableEvent";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false), new LX("id", aos_1.elS, false), new LX("eventType", aos_1.elS, false), new LX("enable", aos_1.elV, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
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
        qe_1 qe_12 = qe_1.valueOf(string3);
        if (qe_12 == null) {
            this.a(a, "Type d'evenement inconnu " + string3 + " pour le dialog " + string);
            return;
        }
        na_12.a(qe_12, this.ic(3));
    }
}

