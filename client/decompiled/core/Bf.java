/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;

public class Bf
extends el_0 {
    private int aIH = 0;
    private int aII = 0;
    private final lu_0[] aIJ = new lu_0[5];
    private float aIK = 0.2f;
    private static final int aIL = 5;

    public final void a(float f) {
        lu_0 lu_02;
        int n2;
        assert (!this.isDead());
        this.fG += f;
        this.aIK -= f;
        if (this.auU < 5 * this.aII && this.fG > 0.15f && this.aIK < 0.0f) {
            this.Hk += 0.5f * (2.0f * ej_0.hL() - 1.0f);
            this.Hl += 0.5f * (2.0f * ej_0.hL() - 1.0f);
            this.Hm += 2.0f * (2.0f * ej_0.hL() - 1.0f);
            this.Ih();
        }
        float f2 = 10.5f * f;
        float f3 = f * -2.3f;
        for (n2 = 0; n2 < this.auT; ++n2) {
            float f4;
            lu_02 = this.aQk[n2];
            if (lu_02.KQ < 0.0f) continue;
            lu_02.KQ -= f;
            if (lu_02.KQ >= lu_02.KR * 0.5f) {
                lu_02.bsE += f2;
                lu_02.bsF += f2;
            } else {
                lu_02.bsE -= 6.0f * f2;
                lu_02.bsF -= 6.0f * f2;
                if (lu_02.bsE <= 0.0f || lu_02.bsF <= 0.0f) {
                    lu_02.bsE = 0.0f;
                    lu_02.bsF = 0.0f;
                    lu_02.KQ = -0.1f;
                }
            }
            lu_02.KO += f3;
            lu_02.Hk += f * lu_02.KM;
            lu_02.Hl += f * lu_02.KN;
            lu_02.Hm += f * lu_02.KO;
            if (!(lu_02.KQ < 0.0f) || !(lu_02.bsG < 5.0f)) continue;
            lu_02.bsG += 1.0f;
            if (lu_02.IT <= 0.3f) {
                f4 = 100.0f + ej_0.hL() * 6.5f;
                lu_02.KQ = 0.4f;
                lu_02.KR = 0.4f;
            } else {
                f4 = 2.0f + ej_0.hL() * 6.5f;
                lu_02.IT = 1.0f;
                lu_02.KR = lu_02.KQ = 0.15f + ej_0.hL() * 0.15f;
            }
            lu_02.bsE = f4;
            lu_02.bsF = f4;
            lu_02.Hk += ej_0.hL() * 0.2f;
            lu_02.Hl += ej_0.hL() * 0.2f;
            lu_02.Hm = this.Hm + ej_0.hL() * 0.5f;
            lu_02.KO = ej_0.hL() * 0.5f;
        }
        for (n2 = 0; n2 < this.aIJ.length; ++n2) {
            lu_02 = this.aIJ[n2];
            if (lu_02 == null || lu_02.KQ < 0.0f) continue;
            this.aIJ[n2].IT = this.aIJ[n2].KQ;
        }
        float f5 = this.fG < 0.15f ? this.fG / 0.15f * 0.25f : (this.fG < 1.0f ? 0.25f : (1.0f - (this.fG - 1.0f) / 0.5f) * 0.25f);
        this.aQj.s(this.IQ * f5, this.IR * f5, this.IS * f5);
    }

    public final void reset() {
        this.aIH = 0;
        ahn_0.dNL.b(this.aQj);
    }

    public final void a(float f, float f2, float f3, float f4, float f5, float f6) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
        this.IQ = f4;
        this.IR = f5;
        this.IS = f6;
        this.fG = 0.0f;
        this.aIH = 0;
        int n2 = ej_0.n(2 * this.aQl / 3, this.aQl);
        this.aII = n2 / 5;
        this.auU = 0;
        this.Ih();
        auU.a((short)8, (byte)0, (short)4);
        DisplayedScreenElement displayedScreenElement = aga_0.aSG().d((int)f, (int)f2, (short)f3, pq_2.abV);
        float f7 = f3;
        if (displayedScreenElement != null) {
            f7 = displayedScreenElement.atV().avU();
        }
        this.aQj.qG().d(f, f2, f7);
        float f8 = 0.25f;
        if ((float)this.auW.size() < 30.0f) {
            f8 *= (float)this.auW.size() / 30.0f;
        }
        this.aQj.u(3.0f);
        this.aQj.r(0.0f, 0.0f, 0.0f);
        this.aQj.s(this.IQ * f8, this.IR * f8, this.IS * f8);
        ahn_0.dNL.a(this.aQj);
    }

    public final boolean isDead() {
        return this.fG > 1.5f;
    }

    private void Ih() {
        float f;
        this.aIK = 0.1f + ej_0.hL() * 0.1f;
        int n2 = this.auU + this.aII - 1;
        for (int j = this.auU; j < n2; ++j) {
            float f2;
            lu_0 lu_02 = this.aQk[j];
            float[] fArray = ej_0.hO();
            lu_02.bsE = f2 = 2.0f + ej_0.hL() * 6.5f;
            lu_02.bsF = f2;
            lu_02.bsB = 0.65234375f;
            lu_02.bsC = 0.6875f;
            lu_02.bsA = 0.890625f;
            lu_02.bsD = 0.953125f;
            lu_02.Hk = this.Hk + fArray[0] * 0.15f;
            lu_02.Hl = this.Hl + fArray[1] * 0.15f;
            lu_02.Hm = this.Hm + fArray[2] * 0.15f;
            lu_02.KM = 0.0f;
            lu_02.KN = 0.0f;
            lu_02.KO = 0.0f;
            lu_02.IT = 1.0f;
            lu_02.IQ = this.IQ / 2.0f;
            lu_02.IR = this.IR / 2.0f;
            lu_02.IS = this.IS / 2.0f;
            lu_02.bsG = 0.0f;
            lu_02.KQ = lu_02.KR = 0.15f + ej_0.hL() * 0.15f;
            aQn = aQn + 1 & 0xFF;
        }
        lu_0 lu_03 = this.aQk[n2];
        lu_03.bsE = f = 100.0f + ej_0.hL() * 6.5f;
        lu_03.bsF = f;
        lu_03.bsB = 0.52734375f;
        lu_03.bsC = 0.62109375f;
        lu_03.bsA = 0.78125f;
        lu_03.bsD = 0.9453125f;
        lu_03.Hk = this.Hk;
        lu_03.Hl = this.Hl;
        lu_03.Hm = this.Hm;
        lu_03.KM = 0.0f;
        lu_03.KN = 0.0f;
        lu_03.KO = 0.0f;
        lu_03.IT = 0.3f;
        lu_03.IQ = this.IQ;
        lu_03.IR = this.IR;
        lu_03.IS = this.IS;
        lu_03.KR = 0.4f;
        lu_03.KQ = 0.4f;
        lu_03.bsG = 0.0f;
        this.aIJ[this.aIH++] = lu_03;
        this.auU += this.aII;
        this.auT = this.auU;
    }
}

