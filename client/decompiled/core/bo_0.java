/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from BO
 */
public enum bo_0 {
    aJs,
    aJt,
    aJu,
    aJv;


    public int eL(int n2) {
        switch (this) {
            case aJv: {
                return 0;
            }
            case aJs: 
            case aJt: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJu: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case aJs: {
                return Math.max(0, n2);
            }
            case aJv: 
            case aJu: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aJt: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case aJv: {
                return 0;
            }
            case aJs: 
            case aJt: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJu: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case aJs: {
                return n3 - n2;
            }
            case aJv: 
            case aJu: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aJt: {
                return 0;
            }
        }
        return 0;
    }

    public boolean IB() {
        return this == aJs;
    }

    public boolean IC() {
        return this == aJt;
    }

    public boolean ID() {
        return this == aJv;
    }

    public boolean IE() {
        return this == aJu;
    }

    public static bo_0 ds(String string) {
        bo_0[] bo_0Array = bo_0.values();
        String string2 = string.toUpperCase();
        for (bo_0 bo_02 : bo_0Array) {
            if (!bo_02.name().equals(string2)) continue;
            return bo_02;
        }
        return bo_0Array[0];
    }
}

