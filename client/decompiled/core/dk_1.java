/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dk
 */
public class dk_1
extends hs_2 {
    private final zm_1 lt;

    public dk_1(zm_1 zm_12) {
        super(zm_12);
        this.lt = zm_12;
    }

    public void fK() {
        this.ays();
    }

    public short fL() {
        return this.lt.aqv[this._index];
    }

    public Object value() {
        return this.lt.iN[this._index];
    }

    public Object setValue(Object object) {
        Object object2 = this.value();
        this.lt.iN[this._index] = object;
        return object2;
    }
}

