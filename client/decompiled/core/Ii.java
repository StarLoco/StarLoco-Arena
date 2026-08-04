/*
 * Decompiled with CFR 0.152.
 */
public class Ii
extends hs_2 {
    private final vy_1 bgr;

    public Ii(vy_1 vy_12) {
        super(vy_12);
        this.bgr = vy_12;
    }

    public void fK() {
        this.ays();
    }

    public short fL() {
        return this.bgr.aqv[this._index];
    }

    public byte kH() {
        return this.bgr.aFu[this._index];
    }

    public byte n(byte by) {
        byte by2 = this.kH();
        this.bgr.aFu[this._index] = by;
        return by2;
    }
}

