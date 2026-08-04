/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from OX
 */
public final class ox_1 {
    private static final ox_1 bDa = new ox_1();
    private volatile byte[] bDb;
    private final int bDc = 10000;

    public static ox_1 abJ() {
        return bDa;
    }

    private ox_1() {
        this.abK();
    }

    public boolean abK() {
        if (this.bDb != null) {
            return true;
        }
        ox_1 ox_12 = this;
        synchronized (ox_12) {
            if (this.bDb != null) {
                return true;
            }
            this.bDb = new byte[10000];
            this.bDb[0] = 80;
            this.bDb[1] = 65;
            this.bDb[2] = 82;
            this.bDb[3] = 65;
            this.bDb[4] = 67;
            this.bDb[5] = 72;
            this.bDb[6] = 85;
            this.bDb[7] = 84;
            this.bDb[8] = 69;
            this.bDb[9] = 33;
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void abL() {
        if (this.bDb == null) {
            return;
        }
        ox_1 ox_12 = this;
        synchronized (ox_12) {
            if (this.bDb == null) {
                return;
            }
            this.bDb = null;
            try {
                byte[] byArray = new byte[4];
                byArray[1] = 2;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                // empty catch block
            }
        }
    }
}

