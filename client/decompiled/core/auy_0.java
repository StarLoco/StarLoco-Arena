/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from auy
 */
class auy_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public auy_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "unloadDialog";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialogName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        add_1.aOG().kO(string);
    }
}

