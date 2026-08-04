/*
 * Decompiled with CFR 0.152.
 */
public class Ib
extends hs_2 {
    private final aLO bgh;

    public Ib(aLO aLO2) {
        super(aLO2);
        this.bgh = aLO2;
    }

    public void fK() {
        this.ays();
    }

    public long TO() {
        return this.bgh.aty[this._index];
    }

    public int value() {
        return this.bgh.aiN[this._index];
    }

    public int aR(int n2) {
        int n3 = this.value();
        this.bgh.aiN[this._index] = n2;
        return n3;
    }
}

