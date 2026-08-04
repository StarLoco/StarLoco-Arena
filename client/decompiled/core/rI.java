/*
 * Decompiled with CFR 0.152.
 */
public class rI
extends yw_2 {
    public rI(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, boolean bl2) {
        super(by, by2, by3, by4, by5, by6, by7, by8, bl2);
    }

    public boolean b(byte by, byte by2, byte by3, byte by4) {
        return by == this.aCw && by2 == this.aCx && by3 == this.aCy && (!this.aCE || by4 == this.aCz);
    }

    public void d(byte[] byArray, int n2) {
        byArray[n2] = this.aCA;
        byArray[n2 + 1] = this.aCB;
        byArray[n2 + 2] = this.aCC;
        if (this.aCE) {
            byArray[n2 + 3] = this.aCD;
        }
    }
}

