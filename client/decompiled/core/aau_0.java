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
 * Renamed from aaU
 */
public class aau_0
extends mp_0 {
    protected static Logger a = Logger.getLogger(aau_0.class);
    private static final aau_0 cgQ = new aau_0();
    private sb_2 cgR;
    private arV cgS;
    private ajq_0 cgT = new rj();

    private aau_0() {
        super("Sound");
    }

    public boolean apx() {
        return this.cgR != null;
    }

    public void a(sb_2 sb_22) {
        this.cgR = sb_22;
    }

    public sb_2 apy() {
        return this.cgR;
    }

    public arV apz() {
        return this.cgS;
    }

    public void a(arV arV2) {
        this.cgS = arV2;
    }

    public ajq_0 apA() {
        return this.cgT;
    }

    public void a(ajq_0 ajq_02) {
        this.cgT = ajq_02;
    }

    public uc_1[] a(LuaState luaState) {
        return new uc_1[]{new aAF(luaState), new bt_1(luaState), new zj_0(luaState), new ads_1(luaState), new tm_1(luaState), new aud_0(luaState), new aer_2(luaState), new agg_1(luaState), new kt_1(luaState), new ael_1(luaState), new ako_2(luaState)};
    }

    public uc_1[] b(LuaState luaState) {
        return null;
    }

    public static aau_0 apB() {
        return cgQ;
    }

    public xt_0 a(long l2, float f, int n2, long l3, long l4, int n3) {
        if (this.cgR != null) {
            return this.cgR.a(l2, f, n2, l3, l4, n3);
        }
        a.debug((Object)"On essaie de jouer un son alors que le son n'est pas initialis\u00e9");
        return null;
    }

    public xt_0 a(long l2, float f, int n2, long l3, long l4, int n3, qq_1 qq_12, int n4) {
        return this.a(l2, f, n2, l3, l4, n3, qq_12, n4, true);
    }

    public xt_0 a(long l2, float f, int n2, long l3, long l4, int n3, qq_1 qq_12, int n4, boolean bl2) {
        if (this.cgR != null) {
            return this.cgR.a(l2, f, n2, l3, l4, n3, qq_12, n4, bl2);
        }
        a.debug((Object)"On essaie de jouer un son alors que le son n'est pas initialis\u00e9");
        return null;
    }

    public avg_0 h(byte by, byte by2) {
        if (this.cgR != null && this.cgT != null) {
            return this.cgT.c(by, by2);
        }
        return null;
    }

    public ns_0 a(int n2, qq_1 qq_12, int n3) {
        if (this.cgR != null && this.cgS != null) {
            return this.cgS.a(qq_12, n2, n3);
        }
        return null;
    }

    public xt_0 a(int n2, int n3, float f, qq_1 qq_12, int n4) {
        if (this.cgR != null && this.cgS != null) {
            ns_0 ns_02 = this.cgS.a(qq_12, n2, n4);
            if (ns_02 == null) {
                a.debug((Object)"Impossible de trouver de BarkData ad\u00e9quat");
                return null;
            }
            return this.cgR.a(ns_0.a(ns_02), ns_0.b(ns_02) * f, 1, -1L, -1L, n3, qq_12, ns_02.sr(), true);
        }
        a.debug((Object)"On essaie de jouer un son alors que le son n'est pas initialis\u00e9");
        return null;
    }

    public void aeG() {
        if (this.cgR != null) {
            this.cgR.aeG();
        } else {
            a.debug((Object)"appel \u00e0 resetLinkerMix alors que le son n'est pas initialis\u00e9");
        }
    }

    public void B(float f, float f2) {
        if (this.cgR != null) {
            this.cgR.B(f, f2);
        } else {
            a.debug((Object)"appel \u00e0 resetLinkerMix alors que le son n'est pas initialis\u00e9");
        }
    }

    public void a(long l2, avE avE2) {
        this.cgR.a(l2, avE2);
    }
}

