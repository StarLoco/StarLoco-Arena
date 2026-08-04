/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pK
 */
public class pk_2
extends hs_2 {
    private final aim_1 acs;

    public pk_2(aim_1 aim_12) {
        super(aim_12);
        this.acs = aim_12;
    }

    public void fK() {
        this.ays();
    }

    public byte kG() {
        return this.acs.auE[this._index];
    }

    public int value() {
        return this.acs.aiN[this._index];
    }

    public int aR(int n2) {
        int n3 = this.value();
        this.acs.aiN[this._index] = n2;
        return n3;
    }
}

