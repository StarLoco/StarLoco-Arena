/*
 * Decompiled with CFR 0.152.
 */
public class adr {
    private int cmy;
    private int cmz;
    private int cmA;
    final /* synthetic */ zm_0 cmB;

    public adr(zm_0 zm_02, int n2, int n3, int n4) {
        this.cmB = zm_02;
        this.cmy = n2;
        this.cmz = n3;
        this.cmA = n4;
    }

    public adr(zm_0 zm_02, String string) {
        this.cmB = zm_02;
        this.cmy = 0;
        this.cmz = 0;
        this.cmA = 0;
        if (string != null && string.length() > 0) {
            String[] stringArray = string.split("(\\s|\\.)");
            try {
                this.cmy = Integer.valueOf(stringArray[0]);
            }
            catch (NumberFormatException numberFormatException) {
                this.cmy = 0;
            }
            if (stringArray.length >= 2) {
                try {
                    this.cmz = Integer.valueOf(stringArray[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    this.cmz = 0;
                }
            }
            if (stringArray.length >= 3) {
                try {
                    this.cmA = Integer.valueOf(stringArray[2]);
                }
                catch (NumberFormatException numberFormatException) {
                    this.cmA = 0;
                }
            }
        }
    }

    public int asp() {
        return this.cmA;
    }

    public int getMajor() {
        return this.cmy;
    }

    public int getMinor() {
        return this.cmz;
    }

    public int a(adr adr2, boolean bl2) {
        int n2 = this.cmy - adr2.getMajor();
        if (n2 == 0 && (n2 = this.cmz - adr2.getMinor()) == 0 && bl2) {
            n2 = this.cmA - adr2.asp();
        }
        return n2;
    }

    public int a(adr adr2) {
        return this.a(adr2, false);
    }
}

