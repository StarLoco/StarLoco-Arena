/*
 * Decompiled with CFR 0.152.
 */
public class tt
extends azc_0 {
    public static final String TAG = "Separator";
    private boolean ba = true;
    private boolean amR = false;
    public static final int ej = "horizontal".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
        this.amR = true;
        this.setNeedsToPreProcess();
    }

    public boolean zs() {
        boolean bl2 = super.zs();
        if (this.dnd.getPixmap() != null) {
            if (this.ba) {
                this.setMinSize(new agj_1(0, this.dnd.getPixmap().getHeight()));
            } else {
                this.setMinSize(new agj_1(this.dnd.getPixmap().getWidth(), 0));
            }
            bl2 = true;
        }
        return bl2;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.amR) {
            if (this.zs()) {
                this.dxR.Am();
            }
            this.amR = false;
        }
        return bl2;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != ej) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setHorizontal(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

