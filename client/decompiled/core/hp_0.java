/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hP
 */
public class hp_0
extends hs_2 {
    private final ano_2 wF;

    public hp_0(ano_2 ano_22) {
        super(ano_22);
        this.wF = ano_22;
    }

    public void fK() {
        this.ays();
    }

    public int kR() {
        return this.wF.dYH[this._index];
    }

    public int value() {
        return this.wF.aiN[this._index];
    }

    public int aR(int n2) {
        int n3 = this.value();
        this.wF.aiN[this._index] = n2;
        return n3;
    }
}

