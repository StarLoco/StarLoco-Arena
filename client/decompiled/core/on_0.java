/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from oN
 */
public class on_0
extends Zb
implements ajb_0 {
    public static final String TAG = "ListAppearance";
    public static final String aaQ = "selection";
    private vP aaR;

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof aab_0) {
            this.setColor(((aab_0)na_12).getColor(), null);
        }
    }

    public String getTag() {
        return TAG;
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equalsIgnoreCase(aaQ)) {
            this.aaR = vP2;
            if (this.DD instanceof rf_0) {
                ((rf_0)this.DD).setMouseOverColor(this.aaR);
                ((rf_0)this.DD).setSelectedColor(this.aaR);
            }
        } else {
            super.setColor(vP2, string);
        }
    }

    public void j() {
        super.j();
        this.aaR = null;
    }
}

