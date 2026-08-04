/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Oh
 */
class oh_1
extends uc_1 {
    final /* synthetic */ aja pd;

    private oh_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "import";
    }

    public LX[] Q() {
        return new LX[]{new LX("libName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        try {
            mp_0 mp_02 = Ky.WG().fa(this.hZ(0));
            if (mp_02 != null) {
                mp_02.d(this.L);
            }
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors du chargement d'un script " + exception));
        }
    }

    /* synthetic */ oh_1(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

