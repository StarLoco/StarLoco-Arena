/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anJ
 */
public class anj_1
extends abd_0 {
    short cJV;
    short cJW;

    public void b(acf acf2) {
        this.cJV = acf2.readShort();
        this.cJW = acf2.readShort();
    }

    public final float lt() {
        return (float)this.cJV / 256.0f;
    }

    public final float lu() {
        return (float)this.cJW / 256.0f;
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
            pq_03.acB = (float)this.cJV / 256.0f + pq_02.acB;
            pq_03.acC = (float)this.cJW / 256.0f + pq_02.acC;
        } else {
            pq_03.acB = (float)this.cJV / 256.0f * pq_02.acx + (float)this.cJW / 256.0f * pq_02.acz + pq_02.acB;
            pq_03.acC = (float)this.cJV / 256.0f * pq_02.acy + (float)this.cJW / 256.0f * pq_02.acA + pq_02.acC;
        }
        pq_03.IQ = pq_02.IQ;
        pq_03.IR = pq_02.IR;
        pq_03.IS = pq_02.IS;
        pq_03.IT = pq_02.IT;
    }
}

