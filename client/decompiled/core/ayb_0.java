/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ayB
 */
public class ayb_0
implements aho_0 {
    private static final Logger a = Logger.getLogger(ayb_0.class);
    public static final String nJ = "position";
    public static final String NAME = "name";
    public static final String POINTS = "points";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "name", "points", "style"};
    private short nO;
    private String m_name;
    private int dmx;
    private int bdE;

    public short ha() {
        return this.nO;
    }

    public String getName() {
        return this.m_name;
    }

    public int aLC() {
        return this.dmx;
    }

    public int aLD() {
        return this.bdE;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void mR(int n2) {
        this.dmx = n2;
    }

    public void gd(int n2) {
        this.bdE = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(POINTS)) {
            return this.dmx;
        }
        if (string.equals(nN)) {
            if (this.nO == 1) {
                return "LadderFirst";
            }
            if (this.nO == 2) {
                return "LadderSecond";
            }
            if (this.nO == 3) {
                return "LadderThird";
            }
            if (this.nO - 1 == this.bdE) {
                return "LadderLocalCoach";
            }
            if (this.nO % 2 == 1) {
                return "LadderOdd";
            }
            return "";
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

    public void clear() {
        this.nO = 0;
        this.m_name = "";
        this.dmx = 0;
        this.bdE = 0;
    }
}

