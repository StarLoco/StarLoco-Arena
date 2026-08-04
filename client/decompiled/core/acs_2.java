/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCs
 */
public class acs_2
extends xs_1 {
    private adg_2 DD;

    public acs_2(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public int getValue() {
        if (this.DD != null) {
            return this.DD.getX();
        }
        return 0;
    }

    public void setValue(int n2) {
        this.DD.setX(n2);
    }

    public void j() {
        super.j();
        this.DD = null;
    }
}

