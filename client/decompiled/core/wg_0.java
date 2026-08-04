/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from wG
 */
class wg_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public wg_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "changeCursor";
    }

    public LX[] Q() {
        return new LX[]{new LX("cursorState", aos_1.elS, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("cursorState", aos_1.elS, false)};
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        String string2 = "DEFAULT";
        xy_0 xy_02 = xy_0.valueOf(string);
        if (xy_02 == null) {
            if (string.equals("SPELL")) {
                xy_02 = xy_0.bYv;
            } else {
                this.a(a, "Type de curseur inconnu " + string);
            }
        }
        apw_1.aDr().unlock();
        apw_1.aDr().a(xy_02, true);
        this.fY("DEFAULT");
    }
}

