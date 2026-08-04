/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aao
 */
class aao_1
extends uc_1 {
    final /* synthetic */ aja pd;

    private aao_1(aja aja2, LuaState luaState) {
        this.pd = aja2;
        super(luaState);
    }

    public final String getName() {
        return "require";
    }

    public LX[] Q() {
        return new LX[]{new LX("fileName", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        this.L.LdoFile(Ky.WG().getPath() + n3 + Ky.WG().getExtension());
    }

    /* synthetic */ aao_1(aja aja2, LuaState luaState, amr_0 amr_02) {
        this(aja2, luaState);
    }
}

