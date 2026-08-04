/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GB
 */
public class gb_1
extends DV {
    public final String[] rb;

    public gb_1(lc_0 lc_02, String[] stringArray) {
        super(lc_02);
        this.rb = stringArray;
    }

    public final void a(afw_0 afw_02) {
        afw_02.c(this);
    }

    public String toString() {
        return "import " + jf_1.a(this.rb, ".") + ".*;";
    }
}

