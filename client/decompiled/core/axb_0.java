/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from axb
 */
public class axb_0
implements aho_0 {
    public static final String nJ = "position";
    public static final String NAME = "name";
    public static final String diW = "bossName";
    public static final String STRENGTH = "strength";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "name", "bossName", "strength", "style"};
    private short nO;
    private String m_name;
    private String diX;
    private short bMF;

    public String[] getFields() {
        return ce;
    }

    public short afA() {
        return this.bMF;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void jX(String string) {
        this.diX = string;
    }

    public void bj(short s) {
        this.bMF = s;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(diW)) {
            return this.diX;
        }
        if (string.equals(STRENGTH)) {
            return this.bMF;
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
        this.bMF = 0;
    }
}

