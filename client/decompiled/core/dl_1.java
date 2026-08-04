/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;

/*
 * Renamed from DL
 */
public class dl_1
extends cb_0 {
    public static final String TAG = "RadioButton";
    private String aOh = "";
    private String ayx;
    public static final int aOi = "groupId".hashCode();
    public static final int dL = "value".hashCode();

    public String getTag() {
        return TAG;
    }

    public act_2 getAppearance() {
        return (act_2)this.cLZ;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof act_2;
    }

    public String getGroupId() {
        return this.aOh;
    }

    public void setGroupId(String string) {
        this.aOh = string;
        this.Mi();
    }

    public String getValue() {
        return this.ayx;
    }

    public void setValue(String string) {
        this.ayx = string;
        this.Mi();
    }

    public void a(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        super.a(k_02, na_12, stack, afq_12);
    }

    private void Mi() {
        if (this.blb.is(this.aOh)) {
            this.blb.ir(this.aOh).a(this);
            if (this.ayx != null && this.ayx.equalsIgnoreCase(this.blb.ir(this.aOh).getValue()) && !this.getAppearance().isChecked()) {
                this.getAppearance().abR();
            }
        }
    }

    public void Mj() {
        super.Mj();
        this.Mi();
    }

    public void b() {
        super.b();
        act_2 act_22 = new act_2();
        act_22.b();
        act_22.setWidget(this);
        this.a(act_22);
    }

    public void j() {
        if (this.blb.is(this.aOh)) {
            this.blb.ir(this.aOh).b(this);
        }
        super.j();
        this.aOh = null;
        this.ayx = null;
    }

    public void a(air_1 air_12) {
        dl_1 dl_12 = (dl_1)air_12;
        super.a((air_1)dl_12);
        dl_12.aOh = this.aOh;
        dl_12.ayx = this.ayx;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aOi) {
            this.setGroupId(if_12.eM(string));
        } else if (n2 == dL) {
            this.setValue(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == aOi) {
            this.setGroupId(String.valueOf(object));
        } else if (n2 == dL) {
            this.setValue(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

