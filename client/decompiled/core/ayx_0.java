/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayX
 */
public class ayx_0
extends adg_2 {
    public static final String TAG = "graph";
    private ux_2 dmS;
    private agj_1 bfP = null;
    private boolean dmT = false;
    private boolean dmU = false;
    private abs_1 dmV = null;
    public static final int ei = "content".hashCode();
    public static final int bfZ = "cellSize".hashCode();

    protected void pX() {
        super.pX();
        this.arC.i(this.dmS.getEntity());
    }

    public void setContent(abs_1 abs_12) {
        this.dmV = abs_12;
        this.dmS.a(abs_12);
        this.dmU = true;
        this.dmT = true;
        this.setNeedsToPreProcess();
        this.setNeedsToPostProcess();
    }

    public void setCellSize(agj_1 agj_12) {
        this.bfP = agj_12;
        this.dmS.setCellWidth(this.bfP.width);
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public String getTag() {
        return TAG;
    }

    public void clear() {
        this.dmS.clear();
    }

    private boolean zs() {
        int n2 = this.dmV != null ? this.dmV.aqb().size() : 0;
        int n3 = this.bfP.width * n2;
        int n4 = this.bfP.height;
        agj_1 agj_12 = this.getContentMinSize();
        if (agj_12.width == n3 || agj_12.height == n4) {
            return false;
        }
        this.setMinSize(new agj_1(n3, n4));
        this.dmT = false;
        return true;
    }

    public void validate() {
        super.validate();
        if (this.dmS != null) {
            this.dmS.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
        }
    }

    public boolean cc(int n2) {
        boolean bl2;
        boolean bl3 = super.cc(n2);
        if (this.dmT && (bl2 = this.zs()) && this.dxR != null) {
            this.dxR.Am();
            this.dmU = true;
            this.setNeedsToPostProcess();
        }
        return bl3;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.dmU) {
            this.dmS.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
            this.dmU = false;
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.dmV = null;
        this.bfP = null;
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        this.dmS = new ux_2();
        this.dmS.b();
        this.dmT = false;
        this.dmU = false;
        this.bfP = new agj_1(0, 0);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != bfZ) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setCellSize(if_12.eL(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != ei) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setContent((abs_1)object);
        return true;
    }
}

