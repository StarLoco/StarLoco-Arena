/*
 * Decompiled with CFR 0.152.
 */
public class Pm
extends dz_2 {
    public static final String TAG = "rld";
    private BT cG = BT.aJX;
    public static final int cJ = "align".hashCode();
    public static final int aTN = "vgap".hashCode();
    public static final int ej = "horizontal".hashCode();

    public String getTag() {
        return TAG;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ((Pm)air_12).setAlign(this.cG);
    }

    public void j() {
        super.j();
        this.cG = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cJ) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setAlign(BT.dv(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

