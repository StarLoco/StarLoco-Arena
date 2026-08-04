/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VI
 */
public enum vi_1 implements rk_0
{
    bSW(1, "weapon", 0),
    bSX(2, "pet", 1),
    bSY(3, "cloak", 2),
    bSZ(4, "hat", 3),
    bTa(5, "dofus", 4);

    private byte bTb;
    private String fM;
    private short bTc;
    private static final afj_0 ayv;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private vi_1(short s) {
        void var5_3;
        void var4_2;
        this.bTb = (byte)s;
        this.fM = var4_2;
        this.bTc = var5_3;
    }

    public short aiJ() {
        return this.bTc;
    }

    public byte aiK() {
        return this.bTb;
    }

    public static vi_1 ap(byte by) {
        return (vi_1)ayv.bk(by);
    }

    public String cC() {
        return Byte.valueOf(this.aiK()).toString();
    }

    public String cD() {
        return this.fM;
    }

    public String cE() {
        return null;
    }

    static {
        ayv = new afj_0();
        for (vi_1 vi_12 : vi_1.values()) {
            ayv.b(vi_12.aiK(), vi_12);
        }
    }
}

