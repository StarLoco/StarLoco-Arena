/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from AN
 */
public class an_1
extends xs_1 {
    private adg_2 DD;

    public an_1(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public int getValue() {
        if (this.DD != null) {
            return this.DD.getWidth();
        }
        return 0;
    }

    public void setValue(int n2) {
        this.DD.setSize(n2, this.DD.aLd.height);
    }

    public void j() {
        super.j();
        this.DD = null;
    }
}

