/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from ZG
 */
public enum zg_1 implements rk_0,
eU
{
    cdv(1, rc_2.class, rc_2.kv, 1),
    cdw(2, nw_0.class, nw_0.kv, 8),
    cdx(3, qv.class, qv.kv, 4),
    cdy(4, sp_2.class, sp_2.kv, 2),
    cdz(5, cX.class, cX.kv, 7),
    cdA(6, qx_2.class, qx_2.kv, 5),
    cdB(7, nd_1.class, nd_1.kv, 6),
    cdC(8, acg_0.class, acg_0.kv, 3),
    cdD(9, arG.class, arG.kv, 2),
    cdE(10, aJF.class, (xX)aJF.dRP, 5),
    cdF(Short.MAX_VALUE, ams.class, ams.kv, 0);

    private static final Logger a;
    private short cdG;
    private Class cdH;
    private xX cdI;
    private short cdJ;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private zg_1(xX xX2, short s3) {
        void var6_4;
        void var5_3;
        this.cdG = (short)xX2;
        this.cdH = (Class)s3;
        this.cdI = var5_3;
        this.cdJ = var6_4;
    }

    public short aoa() {
        return this.cdG;
    }

    public agf_2 a(int[] nArray, short s) {
        try {
            agf_2 agf_22 = (agf_2)this.cdH.newInstance();
            agf_22.a(nArray);
            agf_22.bG(s);
            return agf_22;
        }
        catch (InstantiationException instantiationException) {
            a.error((Object)bl_0.b(instantiationException));
        }
        catch (IllegalAccessException illegalAccessException) {
            a.error((Object)bl_0.b(illegalAccessException));
        }
        return null;
    }

    public static agf_2 a(int n2, int[] nArray, short s) {
        for (zg_1 zg_12 : zg_1.values()) {
            if (zg_12.aoa() != n2) continue;
            return zg_12.a(nArray, s);
        }
        return null;
    }

    public static agf_2 r(ArrayList arrayList) {
        agf_2 agf_22 = null;
        for (agf_2 agf_23 : arrayList) {
            if (agf_22 == null) {
                agf_22 = agf_23;
                continue;
            }
            if (agf_22.fj().aob() >= agf_23.fj().aob()) continue;
            agf_22 = agf_23;
        }
        return agf_22;
    }

    public short aob() {
        return this.cdJ;
    }

    public void bv(short s) {
        this.cdJ = s;
    }

    public String cC() {
        return Short.valueOf(this.cdG).toString();
    }

    public String cD() {
        return this.toString();
    }

    public xX ff() {
        return this.cdI;
    }

    public String cE() {
        return null;
    }

    static {
        a = Logger.getLogger(zg_1.class);
    }
}

