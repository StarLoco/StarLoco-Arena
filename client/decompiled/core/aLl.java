/*
 * Decompiled with CFR 0.152.
 */
public class aLl
extends aoN {
    byte lf = (byte)127;
    byte lg = (byte)127;
    byte lh = (byte)127;
    byte li = (byte)127;

    public void b(acf acf2) {
        super.b(acf2);
        this.lf = acf2.readByte();
        this.lg = acf2.readByte();
        this.lh = acf2.readByte();
        this.li = acf2.readByte();
    }

    public final float fz() {
        return (float)this.lf / 127.0f;
    }

    public final float fA() {
        return (float)this.lg / 127.0f;
    }

    public final float fB() {
        return (float)this.lh / 127.0f;
    }

    public final float fC() {
        return (float)this.li / 127.0f;
    }

    public final boolean fD() {
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
        pq_03.IQ = pq_02.IQ * ((float)this.lf / 127.0f);
        pq_03.IR = pq_02.IR * ((float)this.lg / 127.0f);
        pq_03.IS = pq_02.IS * ((float)this.lh / 127.0f);
        pq_03.IT = pq_02.IT * ((float)this.li / 127.0f);
    }
}

