/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import javax.media.opengl.GL;

public class aAU
extends af_1 {
    private ti_1 dqR;
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

    public aAU() {
        aAU.aE[3] = 1.0f;
        aAU.aE[7] = 1.0f;
        aAU.aE[11] = 1.0f;
        aAU.aE[15] = 1.0f;
        aAU.aat[3] = 1.0f;
        aAU.aat[7] = 1.0f;
        aAU.aat[11] = 1.0f;
        aAU.aat[15] = 1.0f;
    }

    public void a(ti_1 ti_12) {
        this.dqR = ti_12;
    }

    public String getFontName() {
        if (this.dqR == null) {
            return null;
        }
        return this.dqR.getName();
    }

    public int aA() {
        if (this.dqR == null) {
            return 0;
        }
        return this.dqR.getStyle();
    }

    public ma_1 getFont() {
        return this.dqR;
    }

    public ma_1 a(int n2, float f) {
        return null;
    }

    public int a(char c) {
        qj_0 qj_02 = this.dqR.bl((short)c);
        if (qj_02 == null) {
            return 4;
        }
        if (c == ' ') {
            return qj_02.adI;
        }
        return qj_02.adI;
    }

    public int aB() {
        if (this.dqR == null) {
            return 8;
        }
        return this.dqR.GT() + this.dqR.qL() * 2;
    }

    public int aC() {
        if (this.dqR == null) {
            return 12;
        }
        return this.dqR.GU() + this.dqR.qL() * 2;
    }

    public int a(String string, int n2, int n3) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        if (this.dqR == null) {
            return string.length();
        }
        float f = this.dqR.qL();
        char[] cArray = string.toCharArray();
        for (int j = 0; j < cArray.length; ++j) {
            if (j == n2) {
                return n2;
            }
            char c = cArray[j];
            qj_0 qj_02 = this.dqR.bl((short)c);
            if (qj_02 == null) continue;
            f += (float)qj_02.adI;
            if (qj_02.adJ != null && j < cArray.length - 1) {
                f += (float)qj_02.adJ.cp((short)cArray[j + 1]);
            }
            if (!(f > (float)n3)) continue;
            return j;
        }
        return string.length();
    }

    public int g(String string) {
        if (this.dqR == null) {
            return 4 * string.length();
        }
        float f = 0.0f;
        char[] cArray = string.toCharArray();
        for (int j = 0; j < cArray.length; ++j) {
            char c = cArray[j];
            qj_0 qj_02 = this.dqR.bl((short)c);
            if (qj_02 == null) continue;
            if (qj_02.adJ != null && j < cArray.length - 1) {
                f += (float)qj_02.adJ.cp((short)cArray[j + 1]);
            }
            f += (float)qj_02.adI;
        }
        return (int)(f += (float)this.dqR.qL());
    }

    public int h(String string) {
        if (this.dqR == null) {
            return 12;
        }
        return this.dqR.getCellHeight();
    }

    public int i(String string) {
        return this.dqR.afT();
    }

    public boolean aD() {
        return false;
    }

    public void setColor(float f, float f2, float f3, float f4) {
        aAU.aE[0] = f * f4;
        aAU.aE[1] = f2 * f4;
        aAU.aE[2] = f3 * f4;
        aAU.aE[3] = f4;
        aAU.aE[4] = f * f4;
        aAU.aE[5] = f2 * f4;
        aAU.aE[6] = f3 * f4;
        aAU.aE[7] = f4;
        aAU.aE[8] = f * f4;
        aAU.aE[9] = f2 * f4;
        aAU.aE[10] = f3 * f4;
        aAU.aE[11] = f4;
        aAU.aE[12] = f * f4;
        aAU.aE[13] = f2 * f4;
        aAU.aE[14] = f3 * f4;
        aAU.aE[15] = f4;
        aAU.aat[3] = f4;
        aAU.aat[7] = f4;
        aAU.aat[11] = f4;
        aAU.aat[15] = f4;
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
        this.a(cArray, n2, n3, n4, f, 0.0f);
    }

    public void a(char[] cArray, int n2, int n3, int n4, float f, float f2) {
        if (this.dqR == null) {
            return;
        }
        float f3 = n2;
        this.aaw = f;
        ef_1 ef_12 = this.dqR.jI();
        short s = this.dqR.qL();
        f3 += this.Gv - (float)s;
        n4 = (int)((float)n4 + (this.Gw - (float)s + (float)this.dqR.afS()));
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        aA.clear();
        az.clear();
        ef_12.f(qp_22);
        adz_1 adz_12 = ef_12.lC(0);
        float f4 = adz_12.getX();
        float f5 = adz_12.getY();
        boolean bl2 = true;
        for (int j = 0; j < Math.min(cArray.length, n3); ++j) {
            char c = cArray[j];
            qj_0 qj_02 = this.dqR.bl((short)c);
            if (qj_02 == null) continue;
            if (c == ' ') {
                f3 += ((float)qj_02.adI + f2) * this.aaw;
                continue;
            }
            this.a(Math.round(f3), n4, f4, f5, qj_02, aE);
            if (qj_02.adJ != null && j < Math.min(cArray.length, n3) - 1) {
                f3 += (float)qj_02.adJ.cp((short)cArray[j + 1]) * this.aaw;
            }
            f3 += (float)(qj_02.adI + 0) * this.aaw;
        }
        az.pn(aA.fq());
        GL gL = (GL)qp_22.LV();
        gL.glVertexPointer(2, 5126, 0, aA.ys());
        gL.glColorPointer(4, 5126, 0, aA.yt());
        gL.glTexCoordPointer(2, 5126, 0, aA.yu());
        gL.glDrawElements(7, az.aWY(), 5123, az.aWZ());
    }

    private void a(int n2, int n3, float f, float f2, qj_0 qj_02, float[] fArray) {
        aAU.aas[0] = n2 + qj_02.adG;
        aAU.aas[1] = n3 - qj_02.adH - qj_02.adF;
        aAU.aau[0] = (float)qj_02.EL / f;
        aAU.aau[1] = (float)(qj_02.EM + qj_02.adF) / f2;
        aAU.aas[2] = n2 + qj_02.adG;
        aAU.aas[3] = (float)(n3 - qj_02.adH - qj_02.adF) + (float)qj_02.adF * this.aaw;
        aAU.aau[2] = (float)qj_02.EL / f;
        aAU.aau[3] = (float)qj_02.EM / f2;
        aAU.aas[4] = (float)(n2 + qj_02.adG) + (float)qj_02.adE * this.aaw;
        aAU.aas[5] = (float)(n3 - qj_02.adH - qj_02.adF) + (float)qj_02.adF * this.aaw;
        aAU.aau[4] = (float)(qj_02.EL + qj_02.adE) / f;
        aAU.aau[5] = (float)qj_02.EM / f2;
        aAU.aas[6] = (float)(n2 + qj_02.adG) + (float)qj_02.adE * this.aaw;
        aAU.aas[7] = n3 - qj_02.adH - qj_02.adF;
        aAU.aau[6] = (float)(qj_02.EL + qj_02.adE) / f;
        aAU.aau[7] = (float)(qj_02.EM + qj_02.adF) / f2;
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
        vo_12.a(air.cyd, air.cye);
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
        vo_12.a(air.cyd, air.cye);
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

