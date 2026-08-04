/*
 * Decompiled with CFR 0.152.
 */
public enum BT {
    aJT,
    aJU,
    aJV,
    aJW,
    aJX,
    aJY,
    aJZ,
    aKa,
    aKb;


    public int eL(int n2) {
        switch (this) {
            case aJT: 
            case aJW: 
            case aJZ: {
                return 0;
            }
            case aJU: 
            case aJX: 
            case aKa: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJV: 
            case aJY: 
            case aKb: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case aJT: 
            case aJU: 
            case aJV: {
                return Math.max(0, n2);
            }
            case aJW: 
            case aJX: 
            case aJY: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJZ: 
            case aKa: 
            case aKb: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case aJT: 
            case aJW: 
            case aJZ: {
                return 0;
            }
            case aJU: 
            case aJX: 
            case aKa: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJV: 
            case aJY: 
            case aKb: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int q(int n2, int n3, int n4) {
        switch (this) {
            case aJT: 
            case aJW: 
            case aJZ: {
                return n2 - n4;
            }
            case aJU: 
            case aJX: 
            case aKa: {
                return (int)Math.round((double)(n3 - n4) * 0.5) + n2;
            }
            case aJV: 
            case aJY: 
            case aKb: {
                return n2 + n3;
            }
        }
        return 0;
    }

    public int r(int n2, int n3, int n4) {
        switch (this) {
            case aJT: 
            case aJU: 
            case aJV: {
                return n2 + n3;
            }
            case aJW: 
            case aJX: 
            case aJY: {
                return (int)Math.round((double)(n3 - n4) * 0.5) + n2;
            }
            case aJZ: 
            case aKa: 
            case aKb: {
                return n2 - n4;
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case aJT: 
            case aJU: 
            case aJV: {
                return n3 - n2;
            }
            case aJW: 
            case aJX: 
            case aJY: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJZ: 
            case aKa: 
            case aKb: {
                return 0;
            }
        }
        return 0;
    }

    public boolean IB() {
        return this == aJT || this == aJU || this == aJV;
    }

    public boolean IC() {
        return this == aJZ || this == aKa || this == aKb;
    }

    public boolean ID() {
        return this == aJT || this == aJW || this == aJZ;
    }

    public boolean IE() {
        return this == aJV || this == aJY || this == aKb;
    }

    public BT IL() {
        switch (this) {
            case aJT: {
                return aJV;
            }
            case aJV: {
                return aJT;
            }
            case aJW: {
                return aJY;
            }
            case aJY: {
                return aJW;
            }
            case aJZ: {
                return aKb;
            }
            case aKb: {
                return aJZ;
            }
        }
        return this;
    }

    public BT IM() {
        switch (this) {
            case aJT: {
                return aJZ;
            }
            case aJU: {
                return aKa;
            }
            case aJV: {
                return aKb;
            }
            case aJZ: {
                return aJT;
            }
            case aKa: {
                return aJU;
            }
            case aKb: {
                return aJT;
            }
        }
        return this;
    }

    public BT IN() {
        BT[] bTArray = BT.values();
        return bTArray[bTArray.length - 1 - this.ordinal()];
    }

    public static BT dv(String string) {
        BT[] bTArray = BT.values();
        String string2 = string.toUpperCase();
        for (BT bT : bTArray) {
            if (!bT.name().equals(string2)) continue;
            return bT;
        }
        return bTArray[0];
    }
}

