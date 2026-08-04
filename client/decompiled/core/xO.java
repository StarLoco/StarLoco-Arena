/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class xO
extends uc_1 {
    final /* synthetic */ apM oR;

    public xO(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "removeColorTween";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("widgetId", aos_1.elS, false)};
    }

    public final LX[] R() {
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
            this.a(a, "EventDispatcher inconnu " + string2 + " dans le dialog " + string);
            return;
        }
        if (!(na_12 instanceof adg_2)) {
            this.a(a, "l'EventDispatcher n'est pas du type Widget");
            return;
        }
        ((adg_2)na_12).getAppearance().q(kD.class);
    }
}

