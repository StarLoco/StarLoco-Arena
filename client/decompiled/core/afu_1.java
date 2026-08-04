/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;

/*
 * Renamed from afU
 */
public class afu_1
extends el_0 {
    int csR = -1;
    lu_0 csS;
    float csT;

    public final void a(float f) {
        assert (!this.isDead());
        this.fG += f;
        this.csT += 100.0f * f;
        if (this.csT > 1.0f && this.fG < 3.0f) {
            this.kq((int)this.csT);
        }
        float f2 = 1.0f - 2.8f * f;
        float f3 = 1.0f - 2.8f * f;
        float f4 = 1.0f - 1.4f * f;
        float f5 = f * -2.3f;
        int n2 = this.auW.size();
        int n3 = 0;
        while (n3 < n2) {
            float f6;
            int n4 = this.auW.bu(n3);
            lu_0 lu_02 = this.aQk[n4 - 1];
            if (lu_02.KQ < 0.0f) {
                this.auS.mm(n4 - 1);
                this.auW.bv(n3);
                --n2;
                continue;
            }
            ++n3;
            lu_02.KQ -= f;
            if (lu_02.KR - lu_02.KQ < 0.2f) {
                f6 = lu_02.KO * f;
                lu_02.bsE += f6;
                lu_02.bsF += f6;
            } else {
                f6 = 40.5f * f;
                lu_02.bsE -= f6;
                lu_02.bsF -= f6;
                if (lu_02.bsE < 0.0f || lu_02.bsF < 0.0f) {
                    lu_02.bsE = 0.0f;
                    lu_02.bsF = 0.0f;
                    lu_02.KQ = -0.1f;
                }
            }
            if (lu_02.KQ < 1.0f) {
                f6 = 1.0f - 0.5f * f;
                lu_02.bsE *= f6;
                lu_02.bsF *= f6;
            }
            lu_02.KM *= f2;
            lu_02.KN *= f3;
            lu_02.KO *= f4;
            lu_02.Hk += f * lu_02.KM;
            lu_02.Hl += f * lu_02.KN;
            lu_02.Hm += f * lu_02.KO;
            lu_02.Hm += f5;
        }
        this.csS.IT = (float)this.auW.size() / 30.0f;
        float f7 = 0.25f;
        if ((float)this.auW.size() < 30.0f) {
            f7 *= (float)this.auW.size() / 30.0f;
        }
        this.aQj.s(this.IQ * f7, this.IR * f7, this.IS * f7);
        this.aQj.r(0.0f, 0.0f, 0.0f);
    }

    public final void reset() {
        if (this.csR >= 0) {
            this.auS.mm(this.csR);
        }
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
        int n2 = 3;
        this.kq(3);
        auU.a((short)8, (byte)0, (short)4);
        DisplayedScreenElement displayedScreenElement = aga_0.aSG().d((int)f, (int)f2, (short)f3, pq_2.abV);
        float f7 = f3;
        if (displayedScreenElement != null) {
            f7 = displayedScreenElement.atV().avU();
        }
        this.aQj.qG().d(f, f2, f7);
        this.csR = this.auS.aHc();
        this.csS = this.aQk[this.csR];
        this.csS.bsE = 35.0f;
        this.csS.bsF = 35.0f;
        this.csS.bsB = 0.52734375f;
        this.csS.bsC = 0.62109375f;
        this.csS.bsA = 0.78125f;
        this.csS.bsD = 0.9453125f;
        this.csS.Hk = f;
        this.csS.Hl = f2;
        this.csS.Hm = f3;
        this.csS.IT = 0.5f;
        this.csS.IQ = f4;
        this.csS.IR = f5;
        this.csS.IS = f6;
        this.csS.KQ = 3.0f;
        this.aQj.u(2.0f);
        this.aQj.s(0.0f, 0.0f, 0.0f);
        this.aQj.r(0.0f, 0.0f, 0.0f);
        ahn_0.dNL.a(this.aQj);
    }

    public final boolean isDead() {
        return this.fG > 3.0f && this.auW.size() == 0;
    }

    private void kq(int n2) {
        for (int j = 0; j < n2; ++j) {
            if (this.auS.pz() == 0) {
                return;
            }
            int n3 = this.auS.aHc();
            this.auW.add(n3 + 1);
            lu_0 lu_02 = this.aQk[n3];
            float[] fArray = ej_0.hP();
            lu_02.bsE = 4.0f;
            lu_02.bsF = 4.0f;
            lu_02.bsB = 0.65234375f;
            lu_02.bsC = 0.6875f;
            lu_02.bsA = 0.890625f;
            lu_02.bsD = 0.953125f;
            lu_02.Hk = this.Hk;
            lu_02.Hl = this.Hl;
            lu_02.Hm = this.Hm;
            lu_02.KM = fArray[0] * 6.0f;
            lu_02.KN = fArray[1] * 6.0f;
            lu_02.KO = fArray[2] * 24.0f;
            lu_02.IT = 1.0f;
            lu_02.IQ = this.IQ;
            lu_02.IR = this.IR;
            lu_02.IS = this.IS;
            lu_02.KQ = lu_02.KR = 0.3f + ej_0.hL() * 0.3f;
            aQn = aQn + 1 & 0xFF;
            this.csT -= 1.0f;
        }
    }

    private void kr(int n2) {
        for (int j = 0; j < n2; ++j) {
            if (this.auS.pz() == 0) {
                return;
            }
            int n3 = this.auS.aHc();
            this.auW.add(n3 + 1);
            lu_0 lu_02 = this.aQk[n3];
            float[] fArray = ej_0.hP();
            lu_02.bsE = 16.0f + ej_0.hL() * 16.0f;
            lu_02.bsF = 16.0f + ej_0.hL() * 16.0f;
            lu_02.bsB = 0.703125f;
            lu_02.bsC = 1.0f;
            lu_02.bsA = 0.0f;
            lu_02.bsD = 0.65625f;
            lu_02.Hk = this.Hk;
            lu_02.Hl = this.Hl;
            lu_02.Hm = this.Hm;
            lu_02.KM = fArray[0] * 3.0f;
            lu_02.KN = fArray[1] * 3.0f;
            lu_02.KO = fArray[2] * 15.0f;
            lu_02.IT = 0.15f;
            lu_02.IQ = 0.1f;
            lu_02.IR = 0.2f;
            lu_02.IS = 0.5f;
            lu_02.KQ = lu_02.KR = ej_0.e(0.5f, 1.0f);
            aQn = aQn + 1 & 0xFF;
            this.csT -= 1.0f;
        }
    }
}

