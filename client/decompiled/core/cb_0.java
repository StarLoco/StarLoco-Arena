/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from cB
 */
public class cb_0
extends aqq_0 {
    public static final String TAG = "CheckBox";
    public static final String jj = "ToggleButton";
    protected ov_1 jk;
    protected boolean jl = true;
    public static final int ek = "selected".hashCode();

    public String getTag() {
        return TAG;
    }

    public void setSelected(boolean bl2) {
        pk_1 pk_12 = this.getAppearance();
        if (pk_12 != null && bl2 != pk_12.isChecked()) {
            pk_12.abR();
        }
    }

    public boolean getSelected() {
        return this.getAppearance().isChecked();
    }

    public void setOverrideSoundClick(boolean bl2) {
        this.jl = bl2;
    }

    public pk_1 getAppearance() {
        return (pk_1)this.cLZ;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof pk_1;
    }

    public void eT() {
        super.eT();
        this.jk = new sb_1(this);
        this.a(qe_1.bFB, this.jk, true);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ((cb_0)air_12).jl = this.jl;
        ((cb_0)air_12).b(qe_1.bFB, this.jk, true);
    }

    protected void a(ke ke2, boolean bl2) {
        if (this.jl) {
            switch (ke2.aV()) {
                case bFB: 
                case bFC: 
                case bFi: 
                case bFj: {
                    return;
                }
            }
        }
        super.a(ke2, bl2);
    }

    public void j() {
        super.j();
        this.jk = null;
    }

    public void b() {
        super.b();
        this.jl = true;
        pk_1 pk_12 = new pk_1();
        pk_12.b();
        pk_12.setWidget(this);
        this.a(pk_12);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != ek) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setSelected(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != ek) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setSelected(Gr.getBoolean(object));
        return true;
    }
}

