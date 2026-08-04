/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Vi
 */
public class vi_0
extends afs_1 {
    private long gY;
    private short gZ;

    public vi_0(long l2, short s) {
        this.gY = l2;
        this.gZ = s;
    }

    public short tI() {
        return 0;
    }

    public amd_0 aih() {
        return null;
    }

    public int hashCode() {
        return new Long(this.gY * (long)this.gZ).hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof vi_0) {
            vi_0 vi_02 = (vi_0)object;
            return this.gY == vi_02.gY && this.gZ == vi_02.gZ;
        }
        return false;
    }
}

