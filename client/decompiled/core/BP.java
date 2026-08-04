/*
 * Decompiled with CFR 0.152.
 */
public enum BP {
    aJx,
    aJy,
    aJz,
    aJA,
    aJB;


    public int eL(int n2) {
        switch (this) {
            case aJA: {
                return 0;
            }
            case aJx: 
            case aJB: 
            case aJy: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJz: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case aJx: {
                return Math.max(0, n2);
            }
            case aJA: 
            case aJB: 
            case aJz: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJy: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case aJA: {
                return 0;
            }
            case aJx: 
            case aJB: 
            case aJy: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJz: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case aJx: {
                return n3 - n2;
            }
            case aJA: 
            case aJB: 
            case aJz: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJy: {
                return 0;
            }
        }
        return 0;
    }

    public boolean IB() {
        return this == aJx;
    }

    public boolean IC() {
        return this == aJy;
    }

    public boolean ID() {
        return this == aJA;
    }

    public boolean IE() {
        return this == aJz;
    }

    public static BP dt(String string) {
        BP[] bPArray = BP.values();
        String string2 = string.toUpperCase();
        for (BP bP : bPArray) {
            if (!bP.name().equals(string2)) continue;
            return bP;
        }
        return bPArray[0];
    }
}

