/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class apM
extends mp_0 {
    private static final Logger a = Logger.getLogger(apM.class);
    private static final apM cNq = new apM();
    private static int cNr = 0;
    private static int cNs = 0;
    private final lb_0 cNt = new lb_0();

    public static apM aDH() {
        return cNq;
    }

    private apM() {
        super("UI");
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new aqg_0(this, luaState), new be_1(this, luaState), new ul_2(this, luaState), new is_2(this, luaState), new xO(this, luaState), new aDl(this, luaState), new abx_0(this, luaState), new afm_0(this, luaState), new lm_0(this, luaState), new auy_0(this, luaState), new wg_0(this, luaState), new rg_2(this, luaState), new ew_0(this, luaState), new SO(this, luaState), new tg_0(this, luaState, null), new ha_1(this, luaState, null), new fj_2(this, luaState, null), new atZ(this, luaState), new hk_2(luaState), new aaL(luaState), new er_2(luaState), new aDI(luaState), new axd_0(luaState), new aew_2(luaState), new azv_0(luaState), new xy_1(luaState), new ace_1(luaState), new ahn_1(luaState), new Ct(luaState, null), new aab_1(this, luaState), new lq_1(this, luaState), new afx_0(luaState, null), new wg_1(luaState, null), new afn_1(luaState, null), new hc_0(this, luaState), new cd_1(this, luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    private String lN(int n2) {
        return "characterDialog" + n2;
    }

    public void clear() {
        ll_0 ll_02 = this.cNt.pK();
        for (int j = this.cNt.size(); j > 0; --j) {
            ll_02.fK();
            ((ob_1)ll_02.value()).setTimeToLive(0);
        }
        this.cNt.clear();
        cNr = 0;
        cNs = 0;
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }

    static /* synthetic */ String a(apM apM2, int n2) {
        return apM2.lN(n2);
    }

    static /* synthetic */ int aDI() {
        return ++cNr;
    }

    static /* synthetic */ int aDJ() {
        return ++cNs;
    }

    static /* synthetic */ lb_0 a(apM apM2) {
        return apM2.cNt;
    }
}

