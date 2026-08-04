/*
 * Decompiled with CFR 0.152.
 */
public class asV
implements aho_0 {
    public static final byte cSG = 0;
    public static final byte cSH = 1;
    public static final String ID = "id";
    public static final String cSI = "textureUrl";
    public static final String cSJ = "color";
    public static final String[] ce = new String[]{"id", "textureUrl", "color"};
    private byte aIm;
    private byte axW;
    private byte cSK;

    public asV(byte by, byte by2, byte by3) {
        this.aIm = by;
        this.axW = by2;
        this.cSK = by3;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(ID)) {
            return this.axW;
        }
        if (string.equals(cSI)) {
            try {
                if (this.aIm == 0) {
                    return String.format(mu_1.rM().getString("teamsIconsPath"), this.axW);
                }
                if (this.aIm == 1) {
                    return String.format(mu_1.rM().getString("teamsBackgroundsPath"), this.axW);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(cSJ)) {
            float[] fArray = tn_0.hT(this.cSK).Aa();
            return fArray[0] + "," + fArray[1] + "," + fArray[2] + ",1";
        }
        return null;
    }

    public void as(byte by) {
        this.axW = by;
    }

    public void aT(byte by) {
        this.cSK = by;
    }

    public byte lV() {
        return this.axW;
    }

    public byte aFS() {
        return this.cSK;
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
}

