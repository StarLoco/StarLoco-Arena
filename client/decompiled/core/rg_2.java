/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Rg
 */
class rg_2
extends uc_1 {
    final /* synthetic */ apM oR;

    public rg_2(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "manageFrame";
    }

    public LX[] Q() {
        return new LX[]{new LX("frame", aos_1.elS, false), new LX("push", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        String string = this.hZ(0);
        atG atG2 = string.equalsIgnoreCase("Chat") ? aap_2.aMJ() : (string.equalsIgnoreCase("World") ? wp_2.Dl() : (string.equalsIgnoreCase("TeamManagement") ? hu_2.li() : (string.equalsIgnoreCase("MenuBar") ? po_0.abV() : (string.equalsIgnoreCase("EvolutionTeamManagement") ? nb_0.aaI() : (string.equalsIgnoreCase("SphereBoard") ? afb_1.auN() : (string.equalsIgnoreCase("Ladder") ? ahg_1.aTk() : null))))));
        if (atG2 != null) {
            if (this.ic(1)) {
                if (!apN.aDK().c(atG2)) {
                    apN.aDK().a(atG2);
                } else {
                    this.a(a, " On essaie de pousser une frame qui est d\u00e9j\u00e0 l\u00e0. C'est soit un bug, soit une erreur dans un sc\u00e9nario.");
                }
            } else {
                apN.aDK().b(atG2);
            }
        } else {
            this.a(a, "Cette frame n'est pas support\u00e9e par manageFrame " + string);
        }
    }
}

