/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import javax.media.opengl.GL;

/*
 * Renamed from oA
 */
public class oa_0
extends af_1 {
    private zq_0 aar;
    private static VertexBufferPCT aA;
    private static ams_1 az;
    private static float[] aas;
    private static float[] aE;
    private static float[] aat;
    private static float[] aau;
    private float Gv = 0.0f;
    private float Gw = 0.0f;
    private int aav = 1;
    private float aaw = 1.0f;

    public oa_0() {
        oa_0.aE[3] = 1.0f;
        oa_0.aE[7] = 1.0f;
        oa_0.aE[11] = 1.0f;
        oa_0.aE[15] = 1.0f;
        oa_0.aat[3] = 1.0f;
        oa_0.aat[7] = 1.0f;
        oa_0.aat[11] = 1.0f;
        oa_0.aat[15] = 1.0f;
    }

    public void a(zq_0 zq_02) {
        this.aar = zq_02;
    }

    public String getFontName() {
        if (this.aar == null) {
            return null;
        }
        return this.aar.getName();
    }

    public int aA() {
        if (this.aar == null) {
            return 0;
        }
        return this.aar.getStyle();
    }

    public ma_1 getFont() {
        return this.aar;
    }

    public ma_1 a(int n2, float f) {
        return null;
    }

    public int a(char c) {
        tx_2 tx_22 = this.aar.ar((short)c);
        if (tx_22 == null) {
            return 4;
        }
        if (c == ' ') {
            return tx_22.aon;
        }
        return tx_22.aon + tx_22.aom;
    }

    public int aB() {
        if (this.aar == null) {
            return 8;
        }
        return this.aar.GT() + this.aar.qL() * 2;
    }

    public int aC() {
        if (this.aar == null) {
            return 12;
        }
        return this.aar.GU() + this.aar.qL() * 2;
    }

    public int a(String string, int n2, int n3) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        if (this.aar == null) {
            return string.length();
        }
        int n4 = this.aar.qL();
        char[] cArray = string.toCharArray();
        boolean bl2 = true;
        for (int j = 0; j < cArray.length; ++j) {
            if (j == n2) {
                return n2;
            }
            char c = cArray[j];
            tx_2 tx_22 = this.aar.ar((short)c);
            if (tx_22 == null) continue;
            if (bl2) {
                n4 += tx_22.aom;
                bl2 = false;
            }
            n4 = c == ' ' ? (n4 += tx_22.aon) : (n4 += tx_22.aon);
            if (n4 <= n3) continue;
            return j;
        }
        return string.length();
    }

    public int g(String string) {
        if (this.aar == null) {
            return 4 * string.length();
        }
        int n2 = 0;
        char[] cArray = string.toCharArray();
        boolean bl2 = true;
        for (int j = 0; j < cArray.length; ++j) {
            char c = cArray[j];
            tx_2 tx_22 = this.aar.ar((short)c);
            if (tx_22 == null) continue;
            if (bl2) {
                n2 += tx_22.aom;
                bl2 = false;
            }
            if (c == ' ') {
                n2 += tx_22.aon;
                continue;
            }
            n2 += tx_22.aon;
        }
        return n2 += this.aar.qL();
    }

    public int h(String string) {
        if (this.aar == null) {
            return 12;
        }
        return this.aar.getCellHeight();
    }

    public int i(String string) {
        return 0;
    }

    public boolean aD() {
        return false;
    }

    public void setColor(float f, float f2, float f3, float f4) {
        oa_0.aE[0] = f * f4;
        oa_0.aE[1] = f2 * f4;
        oa_0.aE[2] = f3 * f4;
        oa_0.aE[3] = f4;
        oa_0.aE[4] = f * f4;
        oa_0.aE[5] = f2 * f4;
        oa_0.aE[6] = f3 * f4;
        oa_0.aE[7] = f4;
        oa_0.aE[8] = f * f4;
        oa_0.aE[9] = f2 * f4;
        oa_0.aE[10] = f3 * f4;
        oa_0.aE[11] = f4;
        oa_0.aE[12] = f * f4;
        oa_0.aE[13] = f2 * f4;
        oa_0.aE[14] = f3 * f4;
        oa_0.aE[15] = f4;
        oa_0.aat[3] = f4;
        oa_0.aat[7] = f4;
        oa_0.aat[11] = f4;
        oa_0.aat[15] = f4;
    }

    public void a(char[] cArray, int n2, int n3, int n4) {
        this.a(cArray, n2, n3, n4, 1.0f);
    }

    public void a(char[] cArray, int n2, int n3) {
        this.a(cArray, n2, cArray.length, n3, 1.0f);
    }

    public void a(char[] cArray, int n2, int n3, float f) {
        this.a(cArray, n2, cArray.length, n3, f);
    }

    public void a(char[] cArray, int n2, int n3, int n4, float f) {
        if (this.aar == null) {
            return;
        }
        this.aaw = f;
        ef_1 ef_12 = this.aar.jI();
        short s = this.aar.qL();
        n2 = (int)((float)n2 + (this.Gv - (float)s));
        n4 = (int)((float)n4 + (this.Gw - (float)s));
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        aA.clear();
        az.clear();
        ef_12.f(qp_22);
        adz_1 adz_12 = ef_12.lC(0);
        float f2 = adz_12.getX();
        float f3 = adz_12.getY();
        boolean bl2 = true;
        for (int j = 0; j < Math.min(cArray.length, n3); ++j) {
            char c = cArray[j];
            tx_2 tx_22 = this.aar.ar((short)c);
            if (tx_22 == null) continue;
            if (bl2) {
                n2 = (int)((float)n2 + (float)tx_22.aom * this.aaw);
                bl2 = false;
            }
            n2 = (int)((float)n2 - (float)tx_22.aom * this.aaw);
            if (c == ' ') {
                n2 = (int)((float)n2 + (float)tx_22.aon * this.aaw);
                continue;
            }
            this.a(n2, n4, f2, f3, tx_22, aE);
            n2 = (int)((float)n2 + (float)(tx_22.aon + tx_22.aom) * this.aaw);
        }
        az.pn(aA.fq());
        GL gL = (GL)qp_22.LV();
        gL.glVertexPointer(2, 5126, 0, aA.ys());
        gL.glColorPointer(4, 5126, 0, aA.yt());
        gL.glTexCoordPointer(2, 5126, 0, aA.yu());
        gL.glDrawElements(7, az.aWY(), 5123, az.aWZ());
    }

    private void b(String string, int n2, int n3) {
        ef_1 ef_12 = this.aar.jI();
        adz_1 adz_12 = ef_12.lC(0);
        float f = adz_12.getX();
        float f2 = adz_12.getY();
        char[] cArray = string.toCharArray();
        for (int j = 0; j < cArray.length; ++j) {
            char c = cArray[j];
            tx_2 tx_22 = this.aar.ar((short)c);
            if (tx_22 == null) continue;
            if (c == ' ') {
                n2 += tx_22.aon;
                continue;
            }
            int n4 = this.aav;
            for (int i2 = -n4; i2 <= n4; ++i2) {
                for (int i3 = -n4; i3 <= n4; ++i3) {
                    if (i2 == 0 && i3 == 0) continue;
                    this.a(n2 + i2, n3 + i3, f, f2, tx_22, aat);
                }
            }
            n2 += tx_22.aon;
        }
    }

    private void a(int n2, int n3, float f, float f2, tx_2 tx_22, float[] fArray) {
        oa_0.aas[0] = n2;
        oa_0.aas[1] = n3;
        oa_0.aau[0] = (float)tx_22.EL / f;
        oa_0.aau[1] = (float)(tx_22.EM + tx_22.adF) / f2;
        oa_0.aas[2] = n2;
        oa_0.aas[3] = (float)n3 + (float)tx_22.adF * this.aaw;
        oa_0.aau[2] = (float)tx_22.EL / f;
        oa_0.aau[3] = (float)tx_22.EM / f2;
        oa_0.aas[4] = (float)n2 + (float)tx_22.adE * this.aaw;
        oa_0.aas[5] = (float)n3 + (float)tx_22.adF * this.aaw;
        oa_0.aau[4] = (float)(tx_22.EL + tx_22.adE) / f;
        oa_0.aau[5] = (float)tx_22.EM / f2;
        oa_0.aas[6] = (float)n2 + (float)tx_22.adE * this.aaw;
        oa_0.aas[7] = n3;
        oa_0.aau[6] = (float)(tx_22.EL + tx_22.adE) / f;
        oa_0.aau[7] = (float)(tx_22.EM + tx_22.adF) / f2;
        aA.g(aas);
        aA.j(aau);
        aA.h(fArray);
        aA.dz(aA.fq() + 4);
    }

    public void beginRendering(int n2, int n3) {
        this.Gv = -n2 / 2;
        this.Gw = -n3 / 2;
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        qp_22.adV.nO(13);
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cya, air.cye);
        vo_12.n(qp_22);
    }

    public void endRendering() {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cyd, air.cye);
        vo_12.n(qp_22);
    }

    public void begin3DRendering() {
        this.Gv = 0.0f;
        this.Gw = 0.0f;
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        qp_22.adV.nO(13);
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cya, air.cye);
        vo_12.n(qp_22);
    }

    public void end3DRendering() {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cyd, air.cye);
        vo_12.n(qp_22);
    }

    static {
        aas = new float[8];
        aE = new float[16];
        aat = new float[16];
        aau = new float[8];
        int n2 = 4096;
        aA = new VertexBufferPCT(4096);
        az = new ams_1(4096);
        for (int j = 0; j < 4096; ++j) {
            az.add(j);
        }
    }
}

