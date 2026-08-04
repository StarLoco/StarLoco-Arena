/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aKz
 */
public class akz_0
extends hs_2 {
    private final cp_2 dTF;

    public akz_0(cp_2 cp_22) {
        super(cp_22);
        this.dTF = cp_22;
    }

    public void fK() {
        this.ays();
    }

    public long TO() {
        return this.dTF.aty[this._index];
    }

    public Object value() {
        return this.dTF.iN[this._index];
    }

    public Object setValue(Object object) {
        Object object2 = this.value();
        this.dTF.iN[this._index] = object;
        return object2;
    }
}

