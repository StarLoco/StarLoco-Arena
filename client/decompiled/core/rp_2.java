/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from rp
 */
public class rp_2
extends dz_2 {
    public static final String TAG = "gld";
    private BT cG = BT.aJX;
    private BT agh = BT.aJX;
    private boolean agi = false;
    private boolean agj = true;
    public static final int cJ = "align".hashCode();
    public static final int agk = "initAlign".hashCode();
    public static final int agl = "initValue".hashCode();

    public String getTag() {
        return TAG;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public BT getInitAlign() {
        return this.agh;
    }

    public void setInitAlign(BT bT) {
        this.agh = bT;
    }

    public boolean isInitValue() {
        return this.agi;
    }

    public void setInitValue(boolean bl2) {
        this.agi = bl2;
    }

    public boolean isUsable() {
        return !this.agi || this.agj || ago_2.getInstance().isResized();
    }

    public void setUsable(boolean bl2) {
        this.agj = bl2;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ((rp_2)air_12).setAlign(this.cG);
        ((rp_2)air_12).setAlign(this.agh);
        ((rp_2)air_12).setInitValue(this.agi);
    }

    public void j() {
        super.j();
        this.cG = null;
        this.agh = null;
    }

    public void b() {
        super.b();
        this.agi = false;
        this.agj = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == agk) {
            this.setInitAlign(BT.dv(string));
        } else if (n2 == agl) {
            this.setInitValue(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }
}

