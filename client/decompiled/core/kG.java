/*
 * Decompiled with CFR 0.152.
 */
public enum kG {
    Fg,
    Fh,
    Fi;


    public final byte lV() {
        return (byte)this.ordinal();
    }

    public static kG r(byte by) {
        if (by < 0) {
            return Fg;
        }
        kG[] kGArray = kG.values();
        if (by >= kGArray.length) {
            return Fg;
        }
        return kGArray[by];
    }

    public static kG ae(long l2) {
        byte by = (byte)(l2 >>> 56);
        return kG.r(by);
    }

    public static kG aO(String string) {
        if (string == null) {
            return Fg;
        }
        return kG.valueOf(string.toUpperCase());
    }
}

