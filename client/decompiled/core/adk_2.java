/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adK
 */
public class adk_2
extends xs_1 {
    private adg_2 DD;

    public adk_2(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public int getValue() {
        if (this.DD != null) {
            return this.DD.getHeight();
        }
        return 0;
    }

    public void setValue(int n2) {
        this.DD.setSize(this.DD.aLd.width, n2);
    }

    public void j() {
        super.j();
        this.DD = null;
    }
}

