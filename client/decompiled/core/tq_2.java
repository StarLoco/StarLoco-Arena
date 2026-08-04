/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Tq
 */
public class tq_2
extends hs_2 {
    private final aba_0 bOn;

    public tq_2(aba_0 aba_02) {
        super(aba_02);
        this.bOn = aba_02;
    }

    public void fK() {
        this.ays();
    }

    public long TO() {
        return this.bOn.aty[this._index];
    }

    public long afW() {
        return this.bOn.cis[this._index];
    }

    public long cJ(long l2) {
        long l3 = this.afW();
        this.bOn.cis[this._index] = l2;
        return l3;
    }
}

