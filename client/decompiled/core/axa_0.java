/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from axa
 */
public class axa_0
extends rV
implements aho_0 {
    public static short abC = 0;
    public static short diQ = 1;
    public static short diR = (short)2;
    public static short diS = (short)4;
    public static short czR = (short)8;
    private short Gp = 0;
    private boolean diT = false;
    public static final String diU = "notify";
    public static final String diV = "type";
    public static final String[] ce = new String[]{"notify", "type"};
    public static final String[] oT = new String[ce.length + rV.ce.length];

    public axa_0(String string, String string2, boolean bl2, long l2, boolean bl3) {
        super(string, string2, bl2, l2);
        this.diT = bl3;
    }

    public axa_0(String string) {
        super(string);
    }

    public void cg(short s) {
        this.Gp = (short)(s | this.Gp);
    }

    public void ch(short s) {
        this.Gp = (short)(~s & this.Gp);
    }

    public boolean ci(short s) {
        return (this.Gp & s) != 0;
    }

    public boolean aJL() {
        return this.Gp == abC;
    }

    public Object getFieldValue(String string) {
        if (string.equals(diU)) {
            return this.aJM();
        }
        if (string.equals(diV)) {
            return this.Gp;
        }
        return super.getFieldValue(string);
    }

    public boolean aJM() {
        return this.diT;
    }

    public void setNotify(boolean bl2) {
        this.diT = bl2;
    }

    public String[] getFields() {
        return oT;
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(rV.ce, 0, oT, ce.length, rV.ce.length);
    }
}

