/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aAn
 */
public abstract class aan_1
implements aho_0 {
    private static final Logger a = Logger.getLogger(aan_1.class);
    public static final String NAME = "name";
    public static final String bhD = "iconUrl";
    public static final String[] ce = new String[]{"name", "iconUrl"};
    private String m_name;

    public aan_1(String string) {
        this.m_name = string;
    }

    public String[] getFields() {
        return ce;
    }

    public String getName() {
        return this.m_name;
    }

    public abstract byte getType();

    public abstract boolean jt();

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(bhD)) {
            try {
                return String.format(mu_1.rM().getString("infosTypeIconsPath"), this.getType());
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public boolean bQ(long l2) {
        return this instanceof td_0 && ((td_0)this).fx() == l2;
    }
}

