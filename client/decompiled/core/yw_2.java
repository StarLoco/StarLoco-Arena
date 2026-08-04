/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yw
 */
public abstract class yw_2
implements zi_1 {
    protected byte aCw;
    protected byte aCx;
    protected byte aCy;
    protected byte aCz;
    protected byte aCA;
    protected byte aCB;
    protected byte aCC;
    protected byte aCD;
    protected boolean aCE = false;

    protected yw_2(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, boolean bl2) {
        this.aCw = by;
        this.aCx = by2;
        this.aCy = by3;
        this.aCz = by4;
        this.aCA = by5;
        this.aCB = by6;
        this.aCC = by7;
        this.aCD = by8;
        this.aCE = bl2;
    }

    public void e(byte by, byte by2, byte by3, byte by4) {
        this.aCw = by;
        this.aCx = by2;
        this.aCy = by3;
        this.aCz = by4;
    }

    public void f(byte by, byte by2, byte by3, byte by4) {
        this.aCA = by;
        this.aCB = by2;
        this.aCC = by3;
        this.aCD = by4;
    }

    public void ew(int n2) {
        if (n2 == 32) {
            this.aCE = true;
        }
    }
}

