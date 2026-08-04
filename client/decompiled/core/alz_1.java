/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alZ
 */
public class alz_1
extends hs_2 {
    private final pk_0 cFZ;

    public alz_1(pk_0 pk_02) {
        super(pk_02);
        this.cFZ = pk_02;
    }

    public void fK() {
        this.ays();
    }

    public long TO() {
        return this.cFZ.aty[this._index];
    }

    public byte kH() {
        return this.cFZ.aFu[this._index];
    }

    public byte n(byte by) {
        byte by2 = this.kH();
        this.cFZ.aFu[this._index] = by;
        return by2;
    }
}

