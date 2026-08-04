/*
 * Decompiled with CFR 0.152.
 */
public class aoN
extends ui_2 {
    float acB = 0.0f;
    float acC = 0.0f;

    public void b(acf acf2) {
        super.b(acf2);
        this.acB = acf2.readFloat();
        this.acC = acf2.readFloat();
    }

    public final float lt() {
        return this.acB;
    }

    public final float lu() {
        return this.acC;
    }

    public final boolean lv() {
        return true;
    }

    public void a(pq_0 pq_02, pq_0 pq_03) {
        pq_03.acD = false;
        if (pq_02.acD) {
            pq_03.acx = (float)this.aqM / 256.0f;
            pq_03.acy = (float)this.aqN / 256.0f;
            pq_03.acz = (float)this.aqO / 256.0f;
            pq_03.acA = (float)this.aqP / 256.0f;
        } else {
            pq_03.acx = (float)this.aqM / 256.0f * pq_02.acx + (float)this.aqN / 256.0f * pq_02.acz;
            pq_03.acy = (float)this.aqM / 256.0f * pq_02.acy + (float)this.aqN / 256.0f * pq_02.acA;
            pq_03.acz = (float)this.aqO / 256.0f * pq_02.acx + (float)this.aqP / 256.0f * pq_02.acz;
            pq_03.acA = (float)this.aqO / 256.0f * pq_02.acy + (float)this.aqP / 256.0f * pq_02.acA;
        }
        pq_03.acE = false;
        if (pq_02.acD) {
            pq_03.acB = this.acB + pq_02.acB;
            pq_03.acC = this.acC + pq_02.acC;
        } else {
            pq_03.acB = this.acB * pq_02.acx + this.acC * pq_02.acz + pq_02.acB;
            pq_03.acC = this.acB * pq_02.acy + this.acC * pq_02.acA + pq_02.acC;
        }
        pq_03.IQ = pq_02.IQ;
        pq_03.IR = pq_02.IR;
        pq_03.IS = pq_02.IS;
        pq_03.IT = pq_02.IT;
    }
}

