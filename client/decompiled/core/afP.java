/*
 * Decompiled with CFR 0.152.
 */
public class afP {
    private int csc;
    private int bBF;
    private String sI;
    private String wV;
    private String bMN;
    private long adg;

    public afP(int n2, int n3, String string) {
        this(n2, n3, string, 2L);
    }

    public afP(int n2, int n3, String string, long l2) {
        this(n2, n3, string, " ", null, l2);
    }

    public afP(int n2, int n3, String string, String string2, String string3, long l2) {
        this.csc = n2;
        this.bBF = n3;
        this.sI = string;
        this.wV = string2;
        this.adg = l2;
        this.bMN = string3;
    }

    public int A() {
        return this.csc;
    }

    public void ko(int n2) {
        this.csc = n2;
    }

    public int getLevel() {
        return this.bBF;
    }

    public void setLevel(int n2) {
        this.bBF = n2;
    }

    public String getMessage() {
        return this.sI;
    }

    public void setMessage(String string) {
        this.sI = string;
    }

    public long VI() {
        return this.adg;
    }

    public void kp(int n2) {
        this.adg = n2;
    }

    public String getTitle() {
        return this.wV;
    }

    public void setTitle(String string) {
        this.wV = string;
    }

    public String Sk() {
        return this.bMN;
    }

    public boolean B() {
        if ((this.adg & 0x400L) == 1024L) {
            return false;
        }
        int n2 = 0;
        if ((this.adg & 2L) == 2L) {
            ++n2;
        }
        if ((this.adg & 4L) == 4L) {
            ++n2;
        }
        if ((this.adg & 8L) == 8L) {
            ++n2;
        }
        if ((this.adg & 0x10L) == 16L) {
            ++n2;
        }
        return n2 < 2;
    }

    public int avB() {
        if ((this.adg & 2L) == 2L) {
            return 2;
        }
        if ((this.adg & 4L) == 4L) {
            return 4;
        }
        if ((this.adg & 8L) == 8L) {
            return 8;
        }
        if ((this.adg & 0x10L) == 16L) {
            return 16;
        }
        return 0;
    }
}

