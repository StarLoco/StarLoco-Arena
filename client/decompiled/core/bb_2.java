/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bb
 */
public abstract class bb_2 {
    public static final bb_2 ep = null;
    public static final byte eq = -128;
    public static final byte er = 0;
    public static final String es = null;
    public static final String et = null;
    public static final byte[] eu = null;
    private static final byte ev = -128;
    private static final String ew = "";
    private static final String ex = "";
    private byte ey = (byte)-128;
    private String ez = "";
    private String eA = "";

    protected bb_2(byte by, String string, String string2) {
        this.ey = by;
        this.ez = string;
        this.eA = string2;
    }

    protected bb_2(String string, String string2) {
        this(-128, string, string2);
    }

    public byte cc() {
        return this.ey;
    }

    public String getDirectoryName() {
        return this.ez;
    }

    public String getFileName() {
        return this.eA;
    }

    public void b(byte by) {
        this.ey = by;
    }

    public abstract byte[] cd();

    public abstract void b(byte[] var1);
}

