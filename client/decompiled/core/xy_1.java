/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from XY
 */
class xy_1
extends uc_1 {
    public xy_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setWidgetVisibility";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("widgetId", aos_1.elS, false), new LX("visible", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        String string2 = this.hZ(1);
        boolean bl2 = this.ic(2);
        aji_1 aji_12 = add_1.aOG().azj().lh(string);
        if (aji_12 == null) {
            this.a(a, "Dialogue inconnu " + string);
            return;
        }
        na_1 na_12 = aji_12.R(string2);
        if (na_12 == null) {
            this.a(a, "ElementDispatcher inconnu " + string2 + " dans le dialog " + string);
            return;
        }
        if (!(na_12 instanceof adg_2)) {
            this.a(a, "le widget n'est pas du type Widget");
            return;
        }
        adg_2 adg_22 = (adg_2)na_12;
        adg_22.setVisible(bl2);
    }
}

