/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from zC
 */
public class zc_0
implements Comparable {
    private int aFG;
    private long aFH = -1L;
    private String aFI;
    private String sI;
    private String aFJ = null;
    private final long azN;
    private long aFK;
    private static long aFL = Long.MIN_VALUE;

    public zc_0(String string, String string2) {
        this.aFI = string;
        this.sI = string2;
        this.azN = System.currentTimeMillis();
        if (aFL == Long.MAX_VALUE) {
            aFL = Long.MIN_VALUE;
        }
        this.aFK = aFL++;
    }

    public zc_0(String string) {
        this(null, string);
    }

    public zc_0(long l2, String string) {
        this(string);
        this.aFH = l2;
    }

    public zc_0(String string, long l2, String string2) {
        this(string, string2);
        this.aFH = l2;
    }

    public int GH() {
        return this.aFG;
    }

    public void eD(int n2) {
        this.aFG = n2;
    }

    public long GI() {
        return this.aFH;
    }

    public String getSourceName() {
        return this.aFI;
    }

    public void setSourceName(String string) {
        this.aFI = string;
    }

    public String getMessage() {
        return this.sI;
    }

    public void setMessage(String string) {
        this.sI = string;
    }

    public long getTime() {
        return this.azN;
    }

    public void setColor(String string) {
        this.aFJ = string;
    }

    public String getColor() {
        return this.aFJ;
    }

    public int b(zc_0 zc_02) {
        int n2 = (int)(this.getTime() - zc_02.getTime());
        if (n2 == 0) {
            return (int)(this.aFK - zc_02.aFK);
        }
        return n2;
    }
}

