/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaException
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Zn
 */
class zn_1
extends uc_1 {
    final /* synthetic */ apM oR;

    public zn_1(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "closeWindow";
    }

    public LX[] Q() {
        return new LX[]{new LX("windowName", aos_1.elS, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        if (string.equals("CHAT")) {
            return;
        }
        throw new LuaException("Fen\u00eatre inconnue ou non prise en charge " + string);
    }
}

