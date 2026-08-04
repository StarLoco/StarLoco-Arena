/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from hk
 */
class hk_2
extends uc_1 {
    public hk_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setWindowMovable";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false), new LX("movable", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        boolean bl2 = this.ic(1);
        na_1 na_12 = add_1.aOG().kM(string);
        if (na_12 != null && na_12 instanceof aab_2) {
            aab_2 aab_22 = (aab_2)na_12;
            aab_22.setMovable(bl2);
        } else {
            a.error((Object)("Impossible de trouver la fen\u00eatre '" + string + "' pour la rendre movable=" + bl2));
        }
    }
}

