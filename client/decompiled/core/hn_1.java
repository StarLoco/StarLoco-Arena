/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hn
 */
public class hn_1
extends hs_2 {
    private final zy_0 vn;

    public hn_1(zy_0 zy_02) {
        super(zy_02);
        this.vn = zy_02;
    }

    public void fK() {
        this.ays();
    }

    public byte kG() {
        return this.vn.auE[this._index];
    }

    public byte kH() {
        return this.vn.aFu[this._index];
    }

    public byte n(byte by) {
        byte by2 = this.kH();
        this.vn.aFu[this._index] = by;
        return by2;
    }
}

