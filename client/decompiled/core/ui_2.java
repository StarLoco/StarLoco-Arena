/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uI
 */
public class ui_2
extends abd_0 {
    short aqM = (short)256;
    short aqN = 0;
    short aqO = 0;
    short aqP = (short)256;

    public void b(acf acf2) {
        this.aqM = acf2.readShort();
        this.aqN = acf2.readShort();
        this.aqO = acf2.readShort();
        this.aqP = acf2.readShort();
    }

    public final float AM() {
        return (float)this.aqM / 256.0f;
    }

    public final float AN() {
        return (float)this.aqN / 256.0f;
    }

    public final float AO() {
        return (float)this.aqO / 256.0f;
    }

    public final float AP() {
        return (float)this.aqP / 256.0f;
    }

    public final boolean AQ() {
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
        pq_03.acE = pq_02.acE;
        pq_03.acB = pq_02.acB;
        pq_03.acC = pq_02.acC;
        pq_03.IQ = pq_02.IQ;
        pq_03.IR = pq_02.IR;
        pq_03.IS = pq_02.IS;
        pq_03.IT = pq_02.IT;
    }
}

