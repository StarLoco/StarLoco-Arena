/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Insets;

/*
 * Renamed from awH
 */
public abstract class awh_0
extends aNZ {
    private Insets dii = new Insets(0, 0, 0, 0);
    public static final int ack = "spacing".hashCode();
    public static final int acl = "insets".hashCode();

    public void setSpacing(Insets insets) {
        this.setInsets(insets);
    }

    public Insets getSpacing() {
        return this.getInsets();
    }

    public void setInsets(Insets insets) {
        this.dii.top = insets.top;
        this.dii.bottom = insets.bottom;
        this.dii.left = insets.left;
        this.dii.right = insets.right;
    }

    public Insets getInsets() {
        return this.dii;
    }

    public void a(air_1 air_12) {
        awh_0 awh_02 = (awh_0)air_12;
        super.a((air_1)awh_02);
        awh_02.setInsets(this.dii);
    }

    public void b() {
        super.b();
        this.dii.set(0, 0, 0, 0);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != ack && n2 != acl) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setInsets(if_12.eN(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != ack && n2 != acl) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setInsets((Insets)object);
        return true;
    }
}

