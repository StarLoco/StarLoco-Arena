/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anG
 */
public class ang_2
extends abd_0 {
    byte cJP = (byte)127;
    byte cJQ = 0;
    byte cJR = 0;
    byte cJS = (byte)127;

    public void b(acf acf2) {
        this.cJP = acf2.readByte();
        this.cJQ = acf2.readByte();
        this.cJR = acf2.readByte();
        this.cJS = acf2.readByte();
    }

    public final float AM() {
        return (float)this.cJP / 127.0f;
    }

    public final float AN() {
        return (float)this.cJQ / 127.0f;
    }

    public final float AO() {
        return (float)this.cJR / 127.0f;
    }

    public final float AP() {
        return (float)this.cJS / 127.0f;
    }

    public final boolean AQ() {
        return true;
    }

    public void a(pq_0 pq_02, pq_0 pq_03) {
        pq_03.acD = false;
        if (pq_02.acD) {
            pq_03.acx = (float)this.cJP / 127.0f;
            pq_03.acy = (float)this.cJQ / 127.0f;
            pq_03.acz = (float)this.cJR / 127.0f;
            pq_03.acA = (float)this.cJS / 127.0f;
        } else {
            pq_03.acx = (float)this.cJP / 127.0f * pq_02.acx + (float)this.cJQ / 127.0f * pq_02.acz;
            pq_03.acy = (float)this.cJP / 127.0f * pq_02.acy + (float)this.cJQ / 127.0f * pq_02.acA;
            pq_03.acz = (float)this.cJR / 127.0f * pq_02.acx + (float)this.cJS / 127.0f * pq_02.acz;
            pq_03.acA = (float)this.cJR / 127.0f * pq_02.acy + (float)this.cJS / 127.0f * pq_02.acA;
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

