/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alM
 */
class alm_1 {
    private final dr_2 cFz;
    private float cth;
    private long ctf;

    public alm_1(int n2) {
        this.cFz = dr_2.ly;
        this.ctf = n2;
    }

    public alm_1(long l2) {
        this.cFz = dr_2.lz;
        this.ctf = l2;
    }

    public alm_1(float f) {
        this.cFz = dr_2.lA;
        this.cth = f;
    }

    public float getFloatValue() {
        return this.cth;
    }

    public void c(float f) {
        this.cth = f;
    }

    public long getLongValue() {
        return this.ctf;
    }

    public void e(long l2) {
        this.ctf = l2;
    }

    public int getIntValue() {
        return (int)this.ctf;
    }

    public void g(int n2) {
        this.ctf = n2;
    }

    public dr_2 aBa() {
        return this.cFz;
    }

    public String toString() {
        String string = "";
        if (this.cFz == dr_2.ly) {
            string = string + this.ctf + " (as int)";
        } else if (this.cFz == dr_2.lz) {
            string = string + this.ctf + " (as long)";
        } else if (this.cFz == dr_2.lA) {
            string = string + this.cth + " (as float)";
        }
        return string;
    }
}

