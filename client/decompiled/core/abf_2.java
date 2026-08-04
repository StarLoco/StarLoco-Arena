/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBF
 */
public class abf_2
extends xs_1 {
    private xs_1 dsr;

    public abf_2(xs_1 xs_12) {
        if (xs_12 != null) {
            this.dsr = xs_12;
        } else {
            xs_1.Dm().error((Object)"Le Spring pass\u00e9 en param\u00e8tre est null");
            this.dsr = abf_2.iU(0);
        }
    }

    public int getValue() {
        return -this.dsr.getValue();
    }

    public void setValue(int n2) {
        this.dsr.setValue(n2);
    }

    public void j() {
        super.j();
        this.dsr = null;
    }
}

