/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNA
 */
public class ana_1 {
    public short fL;
    short dZA;
    float bsA;
    float bsB;
    float bsD;
    float bsC;
    short adE;
    short adF;
    float Gv;
    float Gw;

    public final void b(acf acf2) {
        this.fL = acf2.readShort();
        this.dZA = acf2.readShort();
        this.bsA = (float)(acf2.readShort() & 0xFFFF) / 65535.0f;
        this.bsB = (float)(acf2.readShort() & 0xFFFF) / 65535.0f;
        this.bsD = (float)(acf2.readShort() & 0xFFFF) / 65535.0f;
        this.bsC = (float)(acf2.readShort() & 0xFFFF) / 65535.0f;
        this.adE = acf2.readShort();
        this.adF = acf2.readShort();
        this.Gv = acf2.readFloat();
        this.Gw = acf2.readFloat();
    }

    public final int getSize() {
        return 31;
    }

    public void bk(short s) {
        this.fL = s;
    }

    public void bv(byte by) {
        this.dZA = by;
    }

    public void k(float f, float f2, float f3, float f4) {
        this.bsA = f;
        this.bsB = f2;
        this.bsD = f3;
        this.bsC = f4;
    }

    public void C(short s, short s2) {
        this.adE = s;
        this.adF = s2;
    }

    public final void i(float f, float f2) {
        this.Gv = f;
        this.Gw = f2;
    }
}

