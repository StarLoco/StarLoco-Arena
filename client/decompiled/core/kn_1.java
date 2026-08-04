/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Kn
 */
public class kn_1
extends aht_1 {
    public static final String TAG = "dndc";
    private awl_0 bnr = null;
    private boolean bns = true;
    private boolean bnt = true;
    private boolean bnu = false;
    public static final int bnv = "validateDrop".hashCode();
    public static final int bnw = "dragEnabled".hashCode();
    public static final int bnx = "dropEnabled".hashCode();

    public void setDragEnabled(boolean bl2) {
        this.bns = bl2;
    }

    public void setDropEnabled(boolean bl2) {
        this.bnt = bl2;
    }

    public boolean isDragEnabled() {
        return this.bns;
    }

    public boolean isDropEnabled() {
        return this.bnt;
    }

    public void setValidateDrop(awl_0 awl_02) {
        this.bnr = awl_02;
    }

    public String getTag() {
        return TAG;
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.isCopy()) {
            return null;
        }
        return super.getWidget(n2, n3);
    }

    public boolean isCopy() {
        return this.bnu;
    }

    public void setCopy(boolean bl2) {
        this.bnu = bl2;
    }

    public void W(Object object) {
        if (!this.bns) {
            return;
        }
        aly_2 aly_22 = aly_2.a(ago_2.getInstance().getCurrentMouseEvent(), this, object);
        this.f(aly_22);
    }

    public void a(kn_1 kn_12, Object object) {
        if (!this.bnt) {
            return;
        }
        aiU aiU2 = aiU.c(ago_2.getInstance().getCurrentMouseEvent(), this, kn_12, object);
        this.f(aiU2);
    }

    public void X(Object object) {
        if (!this.bnt) {
            return;
        }
        lt_0 lt_02 = lt_0.a(ago_2.getInstance().getCurrentMouseEvent(), this, object);
        this.f(lt_02);
    }

    public void b(kn_1 kn_12, Object object) {
        if (!this.bns) {
            return;
        }
        vc_1 vc_12 = vc_1.b(ago_2.getInstance().getCurrentMouseEvent(), this, kn_12, object);
        this.f(vc_12);
    }

    public void c(kn_1 kn_12, Object object) {
        if (!this.bns) {
            return;
        }
        aek.atD().rollOver();
        qw qw2 = qw.a(ago_2.getInstance().getCurrentMouseEvent(), this, kn_12, object);
        this.f(qw2);
    }

    public boolean isDropValid(kn_1 kn_12, Object object) {
        Object object2;
        if (!this.bnt) {
            return false;
        }
        if (this.bnr != null && (object2 = this.bnr.b(kn_12, this, object)) != null) {
            return (Boolean)object2;
        }
        return true;
    }

    public void EO() {
        super.EO();
    }

    public void a(air_1 air_12) {
        kn_1 kn_12 = (kn_1)air_12;
        super.a(air_12);
        kn_12.bnr = this.bnr;
        kn_12.bns = this.bns;
        kn_12.bnt = this.bnt;
    }

    public void j() {
        super.j();
        this.bnr = null;
    }

    public void b() {
        super.b();
        this.bnu = false;
        this.a(qe_1.bFv, new aut_0(this), false);
        this.a(qe_1.bFv, new aus(this), true);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bnv) {
            this.setValidateDrop((awl_0)if_12.c(awl_0.class, string));
        } else if (n2 == bnw) {
            this.setDragEnabled(Gr.getBoolean(string));
        } else if (n2 == bnx) {
            this.setDropEnabled(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bnw) {
            this.setDragEnabled(Gr.getBoolean(object));
        } else if (n2 == bnx) {
            this.setDropEnabled(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ boolean c(kn_1 kn_12) {
        return kn_12.bns;
    }
}

