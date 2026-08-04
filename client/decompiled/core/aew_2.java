/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aew
 */
class aew_2
extends uc_1 {
    public aew_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getWindowSize";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("x", aos_1.elU, false), new LX("y", aos_1.elU, false)};
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        na_1 na_12 = add_1.aOG().kM(string);
        if (na_12 != null && na_12 instanceof aab_2) {
            aab_2 aab_22 = (aab_2)na_12;
            agj_1 agj_12 = aab_22.getPrefSize();
            this.id(agj_12.width);
            this.id(agj_12.height);
        } else {
            this.id(0);
            this.id(0);
            a.error((Object)("Impossible de r\u00e9cup\u00e9rer la taille de la fen\u00eatre '" + string + "' qui n'existe pas"));
        }
    }
}

