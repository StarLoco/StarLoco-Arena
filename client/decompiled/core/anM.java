/*
 * Decompiled with CFR 0.152.
 */
public final class anM
extends anw {
    public final String[] rb;
    public final int n;
    private atu_0 HD = null;
    alb_0 cJX = null;

    public anM(lc_0 lc_02, String[] stringArray) {
        this(lc_02, stringArray, stringArray.length);
    }

    public anM(lc_0 lc_02, String[] stringArray, int n2) {
        super(lc_02);
        this.rb = stringArray;
        this.n = n2;
    }

    public atu_0 aAo() {
        if (this.HD == null) {
            String[] stringArray = new String[this.n];
            System.arraycopy(this.rb, 0, stringArray, 0, this.n);
            this.HD = new ft(this.aP(), stringArray);
            this.HD.a(this.oi());
        }
        return this.HD;
    }

    public String toString() {
        return jf_1.a(this.rb, ".", 0, this.n);
    }

    public anw aAp() {
        if (this.cJX != null) {
            return this.cJX.aAp();
        }
        return this;
    }

    public jy_2 oj() {
        if (this.cJX != null) {
            return this.cJX.oj();
        }
        return this;
    }

    public void a(Ax ax) {
        ax.i(this);
    }

    public void a(EO eO) {
        eO.i(this);
    }

    public void a(ale_0 ale_02) {
        ale_02.i(this);
    }
}

