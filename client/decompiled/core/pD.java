/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.Insets;

public abstract class pD
extends apf_0 {
    protected Insets CB;
    protected boolean acj;
    public static final int ack = "spacing".hashCode();
    public static final int acl = "insets".hashCode();

    public Insets getInsets() {
        return this.CB;
    }

    public void setInsets(Insets insets) {
        this.CB.set(insets.top, insets.left, insets.bottom, insets.right);
    }

    public void setSpacing(Insets insets) {
        this.setInsets(insets);
    }

    public abstract Entity getEntity();

    public void j() {
        super.j();
        this.CB = null;
    }

    public void b() {
        super.b();
        this.CB = new Insets(0, 0, 0, 0);
    }

    public void a(air_1 air_12) {
        pD pD2 = (pD)air_12;
        super.a((air_1)pD2);
        pD2.setInsets(this.CB);
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

