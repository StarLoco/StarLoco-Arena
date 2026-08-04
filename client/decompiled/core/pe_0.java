/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Pe
 */
public class pe_0
extends hs_2 {
    private final no bDk;

    public pe_0(no no2) {
        super(no2);
        this.bDk = no2;
    }

    public void fK() {
        this.ays();
    }

    public int kR() {
        return this.bDk.dYH[this._index];
    }

    public short qD() {
        return this.bDk.Ol[this._index];
    }

    public short E(short s) {
        short s2 = this.qD();
        this.bDk.Ol[this._index] = s;
        return s2;
    }
}

