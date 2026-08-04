/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJT
 */
public enum ajt_0 {
    dSK,
    dSL,
    dSM,
    dSN,
    dSO,
    dSP,
    dSQ,
    dSR,
    dSS,
    dST,
    dSU,
    dSV,
    dSW,
    dSX,
    dSY,
    dSZ;


    public int eL(int n2) {
        switch (this) {
            case dSK: 
            case dSP: 
            case dSR: 
            case dST: 
            case dSV: {
                return 0;
            }
            case dSL: 
            case dSW: {
                return (int)Math.max(0.0, (double)n2 * 0.25);
            }
            case dSM: 
            case dSX: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case dSN: 
            case dSY: {
                return (int)Math.max(0.0, (double)n2 * 0.75);
            }
            case dSO: 
            case dSQ: 
            case dSS: 
            case dSU: 
            case dSZ: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case dSK: 
            case dSL: 
            case dSM: 
            case dSN: 
            case dSO: {
                return Math.max(0, n2);
            }
            case dSP: 
            case dSQ: {
                return (int)Math.max(0.0, (double)n2 * 0.75);
            }
            case dSR: 
            case dSS: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case dST: 
            case dSU: {
                return (int)Math.max(0.0, (double)n2 * 0.25);
            }
            case dSV: 
            case dSW: 
            case dSX: 
            case dSY: 
            case dSZ: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case dSK: 
            case dSP: 
            case dSR: 
            case dST: 
            case dSV: {
                return 0;
            }
            case dSL: 
            case dSW: {
                return (int)Math.round((double)(n3 - n2) * 0.25);
            }
            case dSM: 
            case dSX: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case dSN: 
            case dSY: {
                return (int)Math.round((double)(n3 - n2) * 0.75);
            }
            case dSO: 
            case dSQ: 
            case dSS: 
            case dSU: 
            case dSZ: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case dSK: 
            case dSL: 
            case dSM: 
            case dSN: 
            case dSO: {
                return n3 - n2;
            }
            case dSP: 
            case dSQ: {
                return (int)Math.round((double)(n3 - n2) * 0.75);
            }
            case dSR: 
            case dSS: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case dST: 
            case dSU: {
                return (int)Math.round((double)(n3 - n2) * 0.25);
            }
            case dSV: 
            case dSW: 
            case dSX: 
            case dSY: 
            case dSZ: {
                return 0;
            }
        }
        return 0;
    }

    public boolean IB() {
        return this == dSP || this == dSK || this == dSL || this == dSM || this == dSN || this == dSO || this == dSQ;
    }

    public boolean IC() {
        return this == dST || this == dSV || this == dSW || this == dSX || this == dSW || this == dSZ || this == dST;
    }

    public boolean ID() {
        return this == dSP || this == dSK || this == dSL || this == dSR || this == dSW || this == dSV || this == dST;
    }

    public boolean IE() {
        return this == dSQ || this == dSO || this == dSN || this == dSS || this == dSY || this == dSZ || this == dSU;
    }

    public static ajt_0 lA(String string) {
        ajt_0[] ajt_0Array = ajt_0.values();
        String string2 = string.toUpperCase();
        for (ajt_0 ajt_02 : ajt_0Array) {
            if (!ajt_02.name().equals(string2)) continue;
            return ajt_02;
        }
        return ajt_0Array[0];
    }
}

