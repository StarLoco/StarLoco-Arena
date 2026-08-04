/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from vp
 */
public class vp_2
extends mp_0 {
    private static final Logger a = Logger.getLogger(vp_2.class);
    private static final boolean DEBUG = true;
    private static final vp_2 asE = new vp_2();
    private static int ID = 0;
    private final lb_0 asF = new lb_0();
    private final lb_0 asG = new lb_0();

    public static vp_2 BG() {
        return asE;
    }

    private vp_2() {
        super("Light");
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new ld_0(this, luaState), new au_0(this, luaState), new x(this, luaState), new aos_0(this, luaState), new rq_1(this, luaState), new ave_0(this, luaState), new pz_1(this, luaState), new in_1(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    static /* synthetic */ int BH() {
        return ++ID;
    }

    static /* synthetic */ lb_0 a(vp_2 vp_22) {
        return vp_22.asF;
    }

    static /* synthetic */ lb_0 b(vp_2 vp_22) {
        return vp_22.asG;
    }
}

