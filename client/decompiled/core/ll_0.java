/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from LL
 */
public class ll_0
extends hs_2 {
    private final lb_0 bsh;

    public ll_0(lb_0 lb_02) {
        super(lb_02);
        this.bsh = lb_02;
    }

    public void fK() {
        this.ays();
    }

    public int kR() {
        return this.bsh.dYH[this._index];
    }

    public Object value() {
        return this.bsh.iN[this._index];
    }

    public Object setValue(Object object) {
        Object object2 = this.value();
        this.bsh.iN[this._index] = object;
        return object2;
    }
}

