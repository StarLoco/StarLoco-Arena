/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Rj
 */
public abstract class rj_2
extends apf_0 {
    private boolean aVo = false;
    public static final int aVq = "scaled".hashCode();

    public boolean isScaled() {
        return this.aVo;
    }

    public void setScaled(boolean bl2) {
        this.aVo = bl2;
    }

    public abstract aqn_0 getMesh();

    public void a(air_1 air_12) {
        rj_2 rj_22 = (rj_2)air_12;
        super.a((air_1)rj_22);
        rj_22.setScaled(this.aVo);
    }

    public void b() {
        super.b();
        this.aVo = false;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != aVq) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setScaled(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != aVq) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setScaled(Gr.getBoolean(object));
        return true;
    }
}

