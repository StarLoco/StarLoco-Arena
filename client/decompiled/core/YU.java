/*
 * Decompiled with CFR 0.152.
 */
import java.io.OutputStream;

public final class YU {
    private static final int cbP = 8;
    private static final int cbQ = 3;
    private byte[] cbR;

    private YU() {
    }

    public YU(YU yU) {
        this.cbR = new byte[yU.cbR.length];
        System.arraycopy(yU.cbR, 0, this.cbR, 0, this.cbR.length);
    }

    public YU(int n2) {
        this.cbR = new byte[n2 + 7 >> 3];
    }

    public YU(int n2, boolean bl2) {
        this.cbR = new byte[n2 + 7 >> 3];
        this.cG(bl2);
    }

    public final boolean get(int n2) {
        assert (n2 >> 3 < this.cbR.length) : "trying to get a bit index=" + n2 + " but only " + this.cbR.length * 8 + " available.";
        int n3 = n2 >> 3;
        int n4 = 7 - (n2 - (n3 << 3));
        return (this.cbR[n3] & YU.jh(n4)) != 0;
    }

    public final void set(int n2, boolean bl2) {
        assert (n2 >> 3 < this.cbR.length) : "trying to set a bit index=" + n2 + " but only " + this.cbR.length * 8 + " available.";
        int n3 = n2 >> 3;
        int n4 = 7 - (n2 - (n3 << 3));
        if (bl2) {
            int n5 = n3;
            this.cbR[n5] = (byte)(this.cbR[n5] | YU.jh(n4));
        } else {
            int n6 = n3;
            this.cbR[n6] = (byte)(this.cbR[n6] & ~YU.jh(n4));
        }
    }

    public final void cG(boolean bl2) {
        if (bl2) {
            for (int j = 0; j < this.cbR.length; ++j) {
                this.cbR[j] = -1;
            }
        } else {
            for (int j = 0; j < this.cbR.length; ++j) {
                this.cbR[j] = 0;
            }
        }
    }

    private void resize(int n2) {
        assert (n2 >= this.cbR.length * 8) : "loosing data in BitSet (oldSize=" + this.cbR.length + " newSize=" + n2 + ")";
        byte[] byArray = new byte[(n2 + 7) / 8];
        System.arraycopy(this.cbR, 0, byArray, 0, this.cbR.length);
        this.cbR = byArray;
    }

    public final int capacity() {
        return this.cbR.length * 8;
    }

    private static byte jh(int n2) {
        assert (n2 < 8) : "bit index should be < 8 , found : " + n2;
        return (byte)(1 << n2);
    }

    public final byte[] ana() {
        return this.cbR;
    }

    public final void write(OutputStream outputStream) {
        outputStream.write(this.cbR);
    }

    public static YU f(byte[] byArray, int n2, int n3) {
        YU yU = new YU();
        yU.cbR = new byte[n3];
        System.arraycopy(byArray, n2, yU.cbR, 0, n3);
        return yU;
    }
}

