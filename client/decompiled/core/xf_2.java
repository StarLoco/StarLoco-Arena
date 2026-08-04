/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Xf
 */
public abstract class xf_2
extends apn_1
implements acf_1 {
    private qe_1 bWY;
    public static final int bWZ = "triggerAction".hashCode();

    public qe_1 getTriggerAction() {
        return this.bWY;
    }

    public void setTriggerAction(qe_1 qe_12) {
        this.bWY = qe_12;
    }

    public void j() {
        super.j();
        this.bWY = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != bWZ) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setTriggerAction(qe_1.fG(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != bWZ) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setTriggerAction((qe_1)((Object)object));
        return true;
    }
}

