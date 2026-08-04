/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from apn
 */
public abstract class apn_1
extends aNZ
implements agx_1 {
    protected adg_2 DD;
    protected Zb cLZ;
    protected boolean OD = false;
    protected boolean cMa = true;
    public static final int cMb = "enabled".hashCode();
    public static final int cMc = "removable".hashCode();

    public boolean isEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
        if (this.cLZ != null) {
            this.cLZ.setNeedsToResetMeshes();
        }
    }

    public void setWidget(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public adg_2 getWidget() {
        return this.DD;
    }

    public void setDecoratorAppearance(Zb zb) {
        this.cLZ = zb;
    }

    public Zb getDecoratorAppearance() {
        return this.cLZ;
    }

    public boolean isRemovable() {
        return this.cMa;
    }

    public void setRemovable(boolean bl2) {
        this.cMa = bl2;
    }

    public void awt() {
    }

    public void j() {
        super.j();
        this.DD = null;
        this.cLZ = null;
    }

    public void b() {
        super.b();
        this.OD = false;
        this.cMa = true;
    }

    public void a(air_1 air_12) {
        apn_1 apn_12 = (apn_1)air_12;
        super.a((air_1)apn_12);
        apn_12.OD = this.OD;
        apn_12.cMa = this.cMa;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(string));
        } else if (n2 == cMc) {
            this.setRemovable(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(object));
        } else if (n2 == cMc) {
            this.setRemovable(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

