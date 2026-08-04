/*
 * Decompiled with CFR 0.152.
 */
public class xM {
    public static final xM azv = new xM();
    private final aBp azw = new aBp();
    private final aBp azx = new aBp();
    private final aBp azy = new aBp();
    private boolean tY = true;

    public void em(int n2) {
        this.tY |= this.azx.nk(n2);
    }

    public void en(int n2) {
        this.tY |= this.azx.remove(n2);
    }

    public void eo(int n2) {
        this.tY |= this.azy.nk(n2);
    }

    public void ep(int n2) {
        this.tY |= this.azy.remove(n2);
    }

    public void update() {
        if (!this.tY) {
            return;
        }
        this.azw.clear();
        this.azw.F(this.azx.aNn());
        this.azw.F(this.azy.aNn());
        this.tY = false;
    }

    public void clear() {
        this.azw.clear();
        this.azy.clear();
        this.azx.clear();
    }

    boolean contains(int n2) {
        this.update();
        return this.azw.contains(n2);
    }

    void Ev() {
        this.azx.clear();
        this.tY = true;
    }

    public static xM b(xM xM2) {
        if (xM2 == azv) {
            return azv;
        }
        return xM2.Ew();
    }

    public xM Ew() {
        xM xM2 = new xM();
        xM2.azw.F(this.azw.aNn());
        xM2.azx.F(this.azx.aNn());
        xM2.azy.F(this.azy.aNn());
        xM2.tY = this.tY;
        return xM2;
    }
}

