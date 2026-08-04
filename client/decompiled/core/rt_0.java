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
 * Renamed from RT
 */
public class rt_0
extends mp_0
implements ajY,
axq_0 {
    private static final Logger a = Logger.getLogger(rt_0.class);
    private static final rt_0 bKN = new rt_0();
    private final lb_0 bKO = new lb_0();
    private final lb_0 bKP = new lb_0();
    private final lb_0 bKQ = new lb_0();

    private rt_0() {
        super("BubbleText");
        add_1.aOG().a(this);
        wj_2.Df().a(this);
    }

    public static rt_0 aeA() {
        return bKN;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new hv_0(this, luaState), new ft_2(this, luaState, null), new ajx_2(this, luaState, null), new aqF(this, luaState, null), new aa_0(this, luaState, null), new tc_1(this, luaState, null), new mz_2(this, luaState, null), new jo_0(this, luaState), new aqV(this, luaState), new qt_1(this, luaState, null), new aju_2(this, luaState, null), new alg_0(this, luaState), new ic_1(this, luaState, null)};
    }

    public uc_1[] b(LuaState luaState) {
        return new uc_1[0];
    }

    private static String fM(String string) {
        String string2 = aon_0.aYc().getString(string);
        if (string2 == null) {
            return string;
        }
        return sx_0.replace(string2);
    }

    public void a(Tw tw) {
        assert (tw != null) : "ClientEvent null at BubbleText.onAdviserEvent";
        if (tw.agd() != iw_2.biJ) {
            return;
        }
        int n2 = tw.agc().getId();
        dr_0 dr_02 = (dr_0)this.bKO.get(n2);
        if (dr_02 == null) {
            return;
        }
        dr_02.gF();
        this.bKO.remove(n2);
    }

    public void aL(String string) {
        int n2 = string.hashCode();
        dr_0 dr_02 = (dr_0)this.bKP.get(n2);
        if (dr_02 == null) {
            return;
        }
        dr_02.gF();
        this.bKO.remove(n2);
    }

    public void clear() {
        this.bKQ.clear();
        this.bKO.clear();
        this.bKP.clear();
    }

    static /* synthetic */ String fN(String string) {
        return rt_0.fM(string);
    }

    static /* synthetic */ lb_0 a(rt_0 rt_02) {
        return rt_02.bKP;
    }

    static /* synthetic */ lb_0 b(rt_0 rt_02) {
        return rt_02.bKQ;
    }
}

