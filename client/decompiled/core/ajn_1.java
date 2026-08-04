/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJN
 */
public enum ajn_1 {
    dSm,
    dSn,
    dSo,
    dSp,
    dSq,
    dSr,
    dSs,
    dSt,
    dSu,
    dSv,
    dSw,
    dSx,
    dSy,
    dSz,
    dSA,
    dSB,
    dSC;


    public int eL(int n2) {
        switch (this) {
            case dSm: 
            case dSr: 
            case dSt: 
            case dSw: 
            case dSy: {
                return 0;
            }
            case dSn: 
            case dSz: {
                return (int)Math.max(0.0, (double)n2 * 0.25);
            }
            case dSo: 
            case dSu: 
            case dSA: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case dSp: 
            case dSB: {
                return (int)Math.max(0.0, (double)n2 * 0.75);
            }
            case dSq: 
            case dSs: 
            case dSv: 
            case dSx: 
            case dSC: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case dSm: 
            case dSn: 
            case dSo: 
            case dSp: 
            case dSq: {
                return Math.max(0, n2);
            }
            case dSr: 
            case dSs: {
                return (int)Math.max(0.0, (double)n2 * 0.75);
            }
            case dSt: 
            case dSu: 
            case dSv: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case dSw: 
            case dSx: {
                return (int)Math.max(0.0, (double)n2 * 0.25);
            }
            case dSy: 
            case dSz: 
            case dSA: 
            case dSB: 
            case dSC: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case dSm: 
            case dSr: 
            case dSt: 
            case dSw: 
            case dSy: {
                return 0;
            }
            case dSn: 
            case dSz: {
                return (int)Math.round((double)(n3 - n2) * 0.25);
            }
            case dSo: 
            case dSu: 
            case dSA: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case dSp: 
            case dSB: {
                return (int)Math.round((double)(n3 - n2) * 0.75);
            }
            case dSq: 
            case dSs: 
            case dSv: 
            case dSx: 
            case dSC: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case dSm: 
            case dSn: 
            case dSo: 
            case dSp: 
            case dSq: {
                return n3 - n2;
            }
            case dSr: 
            case dSs: {
                return (int)Math.round((double)(n3 - n2) * 0.75);
            }
            case dSt: 
            case dSu: 
            case dSv: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case dSw: 
            case dSx: {
                return (int)Math.round((double)(n3 - n2) * 0.25);
            }
            case dSy: 
            case dSz: 
            case dSA: 
            case dSB: 
            case dSC: {
                return 0;
            }
        }
        return 0;
    }

    public boolean IB() {
        return this == dSr || this == dSm || this == dSn || this == dSo || this == dSp || this == dSq || this == dSs;
    }

    public boolean IC() {
        return this == dSw || this == dSy || this == dSz || this == dSA || this == dSz || this == dSC || this == dSw;
    }

    public boolean ID() {
        return this == dSr || this == dSm || this == dSn || this == dSt || this == dSz || this == dSy || this == dSw;
    }

    public boolean IE() {
        return this == dSs || this == dSq || this == dSp || this == dSv || this == dSB || this == dSC || this == dSx;
    }

    public static ajn_1 lz(String string) {
        ajn_1[] ajn_1Array = ajn_1.values();
        String string2 = string.toUpperCase();
        for (ajn_1 ajn_12 : ajn_1Array) {
            if (!ajn_12.name().equals(string2)) continue;
            return ajn_12;
        }
        return ajn_1Array[0];
    }
}

