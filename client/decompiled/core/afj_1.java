/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afj
 */
public class afj_1
extends hz_2 {
    protected afj_1() {
    }

    public afj_1(xb_2 xb_22, long l2) {
        this();
        this.c(xb_22);
        this.bC(l2);
    }

    protected void c(OZ oZ) {
        oZ.a(this);
    }

    public void auQ() {
        this.bge = null;
    }

    public void clean() {
        if (this.bge != null) {
            this.bge.release();
        }
        this.bge = null;
    }
}

