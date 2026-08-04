/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yn
 */
public enum yn_0 implements aeo_2
{
    aCa(0, new String[]{"Arme"}),
    aCb(2, new String[]{"Cape"}),
    aCc(3, new String[]{"Chapeau"});

    private short nO;
    private String[] aCd;
    private static final zm_1 arf;

    /*
     * WARNING - void declaration
     */
    private yn_0() {
        void var4_2;
        void var3_1;
        this.nO = var3_1;
        this.aCd = var4_2;
    }

    public short ha() {
        return this.nO;
    }

    public String[] ES() {
        return this.aCd;
    }

    public static yn_0 ae(short s) {
        return (yn_0)arf.an(s);
    }

    static {
        arf = new zm_1();
        for (yn_0 yn_02 : yn_0.values()) {
            arf.b(yn_02.ha(), yn_02);
        }
    }
}

