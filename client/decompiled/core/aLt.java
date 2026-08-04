/*
 * Decompiled with CFR 0.152.
 */
public class aLt
extends aoN {
    short aoB = 0;
    short aoC = 0;
    short aoD = 0;
    short aoE = 0;

    public void b(acf acf2) {
        super.b(acf2);
        this.aoB = acf2.readShort();
        this.aoC = acf2.readShort();
        this.aoD = acf2.readShort();
        this.aoE = acf2.readShort();
    }

    public final float Ad() {
        return (float)this.aoB / 256.0f;
    }

    public final float Ae() {
        return (float)this.aoC / 256.0f;
    }

    public final float Af() {
        return (float)this.aoD / 256.0f;
    }

    public final float Ag() {
        return (float)this.aoE / 256.0f;
    }

    public final boolean Ah() {
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
        pq_03.IQ = pq_02.IQ + (float)this.aoB / 256.0f;
        pq_03.IR = pq_02.IR + (float)this.aoC / 256.0f;
        pq_03.IS = pq_02.IS + (float)this.aoD / 256.0f;
        pq_03.IT = pq_02.IT + (float)this.aoE / 256.0f;
    }
}

