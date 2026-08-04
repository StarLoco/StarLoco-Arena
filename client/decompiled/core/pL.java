/*
 * Decompiled with CFR 0.152.
 */
public class pL
implements Comparable {
    private String m_name;
    private String aca;
    private boolean act = false;
    private long nD;

    public pL(String string, String string2, boolean bl2, long l2) {
        this.aca = string;
        this.m_name = string2;
        this.act = bl2;
        this.nD = l2;
    }

    public pL(String string) {
        this(string, string, false, -1L);
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public boolean uq() {
        return this.act;
    }

    public void ai(boolean bl2) {
        this.act = bl2;
    }

    public long getId() {
        return this.nD;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public String uj() {
        return this.aca;
    }

    public void bx(String string) {
        this.aca = string;
    }

    public int a(pL pL2) {
        return this.getName().toLowerCase().compareTo(pL2.getName().toLowerCase());
    }
}

