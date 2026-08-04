/*
 * Decompiled with CFR 0.152.
 */
public final class pw {
    public static final pw acb = new pw("RAW");
    public static final pw acc = new pw("TGA");
    public static final pw acd = new pw("TGAM");
    public static final pw ace = new pw("PNG");
    public static final pw acf = new pw("BMP");
    public static final pw acg = new pw("JPG");
    private int aW;

    public pw() {
        this.aW = 0;
    }

    public pw(int n2) {
        this.set(n2);
    }

    public pw(String string) {
        this.set(string);
    }

    public static int bu(String string) {
        byte[] byArray = string.getBytes();
        int n2 = 0;
        if (byArray.length == 0) {
            return n2;
        }
        if (byArray.length >= 1) {
            n2 |= afy_0.aA(byArray[0]);
        }
        if (byArray.length >= 2) {
            n2 |= afy_0.aA(byArray[1]) << 8;
        }
        if (byArray.length >= 3) {
            n2 |= afy_0.aA(byArray[2]) << 16;
        }
        if (byArray.length >= 4) {
            n2 |= afy_0.aA(byArray[3]) << 24;
        }
        return n2;
    }

    public static String cF(int n2) {
        byte[] byArray = new byte[4];
        byArray[3] = (byte)(n2 >> 24);
        byArray[2] = (byte)(n2 >> 16 & 0xFF);
        byArray[1] = (byte)(n2 >> 8 & 0xFF);
        byArray[0] = (byte)(n2 & 0xFF);
        return new String(byArray);
    }

    public final String getString() {
        return pw.cF(this.aW);
    }

    public final int getID() {
        return this.aW;
    }

    public final void set(String string) {
        this.aW = pw.bu(string);
    }

    public final void set(int n2) {
        this.aW = n2;
    }
}

