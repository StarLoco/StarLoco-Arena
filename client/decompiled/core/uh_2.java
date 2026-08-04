/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uH
 */
public class uh_2
extends abd_0 {
    float acB = 0.0f;
    float acC = 0.0f;

    public void b(acf acf2) {
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
        pq_03.acD = pq_02.acD;
        pq_03.acx = pq_02.acx;
        pq_03.acz = pq_02.acz;
        pq_03.acy = pq_02.acy;
        pq_03.acA = pq_02.acA;
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

