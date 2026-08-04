/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZN
 */
public class zn_0
extends xs_1 {
    private adg_2 DD;

    public zn_0(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public int getValue() {
        if (this.DD != null) {
            return this.DD.getY();
        }
        return 0;
    }

    public void setValue(int n2) {
        this.DD.setY(n2);
    }

    public void j() {
        super.j();
        this.DD = null;
    }
}

