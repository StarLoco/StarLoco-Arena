/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aCE
 */
class ace_1
extends uc_1 {
    public ace_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "removePropertyClient";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("widgetId", aos_1.elS, false), new LX("propertyName", aos_1.elS, false), new LX("local", aos_1.elV, true)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        aji_1 aji_12;
        String string = this.hZ(0);
        String string2 = this.hZ(1);
        String string3 = this.hZ(2);
        boolean bl2 = false;
        if (n2 > 3) {
            bl2 = this.ic(3);
        }
        if ((aji_12 = add_1.aOG().azj().lh(string)) == null) {
            this.a(a, "ElementMap inconnue " + string);
            return;
        }
        afl_0 afl_02 = bl2 ? azs_0.aLV().ak(string3, string) : azs_0.aLV().getProperty(string3);
        if (afl_02 == null) {
            this.a(a, "Propri\u00e9t\u00e9e inconnue " + string3);
            return;
        }
        na_1 na_12 = aji_12.R(string2);
        if (na_12 == null) {
            this.a(a, "ElementDispatcher inconnu " + string2 + " dans le dialog " + string);
            return;
        }
        afl_02.i(na_12);
    }
}

