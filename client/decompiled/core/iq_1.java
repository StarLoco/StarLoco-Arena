/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from IQ
 */
public enum iq_1 {
    bio(0),
    bip(1),
    biq(2),
    bir(3),
    bis(4),
    bit(5),
    biu(6),
    biv(8),
    biw(9);

    private final byte aIm;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private iq_1() {
        void var3_1;
        this.aIm = var3_1;
    }

    public byte getType() {
        return this.aIm;
    }
}

