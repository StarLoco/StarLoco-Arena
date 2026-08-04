/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GK
 */
public class gk_1
extends hs_2 {
    private final afj_0 bcn;

    public gk_1(afj_0 afj_02) {
        super(afj_02);
        this.bcn = afj_02;
    }

    public void fK() {
        this.ays();
    }

    public byte kG() {
        return this.bcn.auE[this._index];
    }

    public Object value() {
        return this.bcn.iN[this._index];
    }

    public Object setValue(Object object) {
        Object object2 = this.value();
        this.bcn.iN[this._index] = object;
        return object2;
    }
}

