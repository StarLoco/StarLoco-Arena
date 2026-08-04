/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Mr
 */
public class mr_2
extends hs_2 {
    private final axu btR;

    public mr_2(axu axu2) {
        super(axu2);
        this.btR = axu2;
    }

    public void fK() {
        this.ays();
    }

    public byte kG() {
        return this.btR.auE[this._index];
    }

    public short qD() {
        return this.btR.Ol[this._index];
    }

    public short E(short s) {
        short s2 = this.qD();
        this.btR.Ol[this._index] = s;
        return s2;
    }
}

