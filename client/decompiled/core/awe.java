/*
 * Decompiled with CFR 0.152.
 */
public class awe
extends alt_0
implements ov_0 {
    public static final String TAG = "propertyExists";
    private boolean dhu;
    private String m_name;
    private boolean crh;
    public static final int crj = "local".hashCode();
    public static final int aru = "name".hashCode();

    public String getTag() {
        return TAG;
    }

    public String getName() {
        return this.m_name;
    }

    public boolean getLocal() {
        return this.crh;
    }

    public void setName(String string) {
        if (this.m_name != null && !this.m_name.equalsIgnoreCase(string) || string != null && !string.equalsIgnoreCase(this.m_name)) {
            this.m_name = string;
            this.setNeedsToPreProcess();
        }
    }

    public void setLocal(boolean bl2) {
        if (this.crh != bl2) {
            this.crh = bl2;
            this.setNeedsToPreProcess();
        }
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        return this.dhu;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        boolean bl3 = this.dhu;
        afl_0 afl_02 = azs_0.aLV().l(this.m_name, this.crh ? this.getElementMap() : null);
        boolean bl4 = this.dhu = afl_02 != null && afl_02.avn();
        if (bl3 != this.dhu) {
            this.g(true);
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        awe awe2 = (awe)air_12;
        awe2.m_name = this.m_name;
        awe2.crh = this.crh;
    }

    public void a(aes_1 aes_12) {
        afl_0 afl_02 = aes_12.getProperty();
        if (afl_02 != null && afl_02.getName().equalsIgnoreCase(this.m_name) && (!this.crh || afl_02.getElementMap() == this.blb)) {
            switch (aes_12.auJ()) {
                case ddK: {
                    this.dhu = true;
                    this.g(true);
                    break;
                }
                case ddJ: {
                    this.dhu = false;
                    this.g(true);
                    break;
                }
            }
        }
    }

    public void b() {
        super.b();
        this.dhu = false;
        this.crh = false;
        azs_0.aLV().a(this);
    }

    public void j() {
        super.j();
        azs_0.aLV().b(this);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == crj) {
            this.setLocal(Gr.getBoolean(string));
        } else if (n2 == aru) {
            this.setName(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == crj) {
            this.setLocal(Gr.getBoolean(object));
        } else if (n2 == aru) {
            this.setName(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

