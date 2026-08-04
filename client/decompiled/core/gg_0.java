/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from gG
 */
public abstract class gg_0
extends UC {
    private static final Logger a = Logger.getLogger(gg_0.class);
    protected static final byte ul = 1;
    protected static final byte um = 2;
    protected static final byte un = 3;
    protected static final byte uo = 4;
    protected static final byte up = 5;
    protected static final byte uq = 6;
    protected static final byte ur = 7;
    protected static final byte us = 8;
    protected static final byte ut = 9;
    protected static final byte uu = 10;
    protected static final byte uv = 11;

    public static ua_0 b(acf acf2, float f) {
        byte by = acf2.readByte();
        switch (by) {
            case 1: {
                return ku_1.FQ.e(acf2, f);
            }
            case 2: {
                return apu_0.cMh.s(acf2, f);
            }
            case 3: {
                return On.bBO.k(acf2, f);
            }
            case 4: {
                return aop_2.emQ.w(acf2, f);
            }
            case 5: {
                return wW.avP.h(acf2, f);
            }
            case 6: {
                return lo_0.bqv.j(acf2, f);
            }
            case 7: {
                return pr_2.bEu.l(acf2, f);
            }
            case 8: {
                return abo_1.chV.o(acf2, f);
            }
            case 9: {
                return aan_0.cgH.n(acf2, f);
            }
            case 10: {
                return ayw_0.dmv.u(acf2, f);
            }
            case 11: {
                return jb_1.zo.c(acf2, f);
            }
        }
        a.error((Object)("type de condition inconnu " + by));
        return null;
    }
}

