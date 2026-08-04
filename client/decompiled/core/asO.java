/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

public class asO
extends mp_0 {
    private static final asO cSx = new asO();
    private qs_2 bxL = null;

    public qs_2 YP() {
        return this.bxL;
    }

    public void e(qs_2 qs_22) {
        this.bxL = qs_22;
    }

    protected asO() {
        super("Camera");
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new adG(luaState), new uj_1(luaState), new acu_2(luaState), new ma_2(luaState), new kv_0(luaState), new dg_1(luaState), new aln_2(luaState), new afd_1(luaState), new ahI(luaState), new amw_1(luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    public static asO aFM() {
        return cSx;
    }
}

