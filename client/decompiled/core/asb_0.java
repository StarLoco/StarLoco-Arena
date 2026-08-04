/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from asb
 */
public class asb_0
extends hs_2 {
    private final asc cQZ;

    public asb_0(asc asc2) {
        super(asc2);
        this.cQZ = asc2;
    }

    public void fK() {
        this.ays();
    }

    public int kR() {
        return this.cQZ.dYH[this._index];
    }

    public byte kH() {
        return this.cQZ.aFu[this._index];
    }

    public byte n(byte by) {
        byte by2 = this.kH();
        this.cQZ.aFu[this._index] = by;
        return by2;
    }
}

