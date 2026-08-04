/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;

/*
 * Renamed from aqT
 */
public class aqt_0
extends el_0 {
    lu_0 csS;
    lu_0 cOW;

    public final void a(float f) {
        assert (!this.isDead());
        this.fG += f;
        if (this.auU < this.auT - 3 && this.fG > 0.15f) {
            this.lP(this.auT - this.auU - 3);
        }
        float f2 = 1.0f - 2.8f * f;
        float f3 = f * -2.3f;
        for (int j = 0; j < this.auT; ++j) {
            lu_0 lu_02 = this.aQk[j];
            if (lu_02.KQ < 0.0f || lu_02 == this.csS || lu_02 == this.cOW) continue;
            lu_02.KQ -= f;
            if (lu_02.KQ < 0.5f) {
                lu_02.IT = lu_02.KQ / 0.5f;
            }
            if (lu_02.KQ < 1.0f) {
                float f4 = 1.0f - 0.5f * f;
                lu_02.bsE *= f4;
                lu_02.bsF *= f4;
            }
            lu_02.KM *= f2;
            lu_02.KN *= f2;
            lu_02.KO *= f2;
            lu_02.KO += f3;
            lu_02.Hk += f * lu_02.KM;
            lu_02.Hl += f * lu_02.KN;
            lu_02.Hm += f * lu_02.KO;
        }
        this.csS.KQ -= f;
        if (this.csS.KQ < 0.2f) {
            this.csS.IT = this.csS.KQ / 0.2f;
        }
        if (this.fG > 0.15f) {
            this.cOW.KQ -= f;
            this.cOW.IT = this.cOW.KQ / 0.45f;
            this.cOW.bsE += 170.0f * f;
            this.cOW.bsF += 170.0f * f;
        }
        if (this.fG < 0.15f) {
            float f5 = this.fG / 0.15f * 0.25f;
            this.aQj.s(this.IQ * f5, this.IR * f5, this.IS * f5);
        } else if (this.fG < 2.0f) {
            float f6 = 0.25f;
            this.aQj.s(this.IQ * f6, this.IR * f6, this.IS * f6);
        } else {
            float f7 = (1.0f - (this.fG - 2.0f) / 0.5f) * 0.25f;
            this.aQj.s(this.IQ * f7, this.IR * f7, this.IS * f7);
        }
    }

    public final void reset() {
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
        this.auT = ej_0.n(2 * this.aQl / 3, this.aQl);
        this.auU = 0;
        int n2 = this.auT * 2 / 3 - 2;
        this.lP(n2);
        this.cOW = this.aQk[this.auT - 2];
        this.cOW.bsE = 0.0f;
        this.cOW.bsF = 0.0f;
        this.cOW.bsB = 0.015625f;
        this.cOW.bsC = 0.25f;
        this.cOW.bsA = 0.515625f;
        this.cOW.bsD = 0.984375f;
        this.cOW.Hk = f;
        this.cOW.Hl = f2;
        this.cOW.Hm = f3;
        this.cOW.IT = 0.0f;
        this.cOW.IQ = f4;
        this.cOW.IR = f5;
        this.cOW.IS = f6;
        this.cOW.KQ = 0.45f;
        this.csS = this.aQk[this.auT - 1];
        this.csS.bsE = 200.0f;
        this.csS.bsF = 200.0f;
        this.csS.bsB = 0.52734375f;
        this.csS.bsC = 0.62109375f;
        this.csS.bsA = 0.78125f;
        this.csS.bsD = 0.9453125f;
        this.csS.Hk = f;
        this.csS.Hl = f2;
        this.csS.Hm = f3;
        this.csS.KM = 0.0f;
        this.csS.KN = 0.0f;
        this.csS.KO = 0.0f;
        this.csS.IT = 1.0f;
        this.csS.IQ = f4;
        this.csS.IR = f5;
        this.csS.IS = f6;
        this.csS.KQ = 0.35f;
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
        this.aQj.s(this.IQ * f8, this.IR * f8, this.IS * f8);
        this.aQj.r(0.0f, 0.0f, 0.0f);
        ahn_0.dNL.a(this.aQj);
    }

    public final boolean isDead() {
        return this.fG > 2.5f;
    }

    private void lP(int n2) {
        for (int j = this.auU; j < this.auU + n2; ++j) {
            lu_0 lu_02 = this.aQk[j];
            float[] fArray = ej_0.hO();
            lu_02.bsE = 6.5f;
            lu_02.bsF = 6.0f;
            lu_02.bsB = 0.65234375f;
            lu_02.bsC = 0.6875f;
            lu_02.bsA = 0.890625f;
            lu_02.bsD = 0.953125f;
            lu_02.Hk = this.Hk;
            lu_02.Hl = this.Hl;
            lu_02.Hm = this.Hm;
            lu_02.KM = fArray[0] * 4.0f;
            lu_02.KN = fArray[1] * 4.0f;
            lu_02.KO = fArray[2] * 18.0f;
            lu_02.IT = 1.0f;
            lu_02.IQ = this.IQ;
            lu_02.IR = this.IR;
            lu_02.IS = this.IS;
            lu_02.KQ = lu_02.KR = ej_0.e(1.5f, 2.5f);
            aQn = aQn + 1 & 0xFF;
        }
        this.auU += n2;
    }
}

