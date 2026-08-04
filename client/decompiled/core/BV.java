/*
 * Decompiled with CFR 0.152.
 */
public enum BV {
    aKd,
    aKe,
    aKf,
    aKg,
    aKh,
    aKi,
    aKj,
    aKk;


    public int eL(int n2) {
        switch (this) {
            case aKd: 
            case aKg: 
            case aKi: {
                return 0;
            }
            case aKe: 
            case aKj: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aKf: 
            case aKh: 
            case aKk: {
                return Math.max(0, n2);
            }
        }
        return 0;
    }

    public int eM(int n2) {
        switch (this) {
            case aKd: 
            case aKe: 
            case aKf: {
                return Math.max(0, n2);
            }
            case aKg: 
            case aKh: {
                return (int)Math.max(0.0, (double)n2 * 0.5);
            }
            case aKi: 
            case aKj: 
            case aKk: {
                return 0;
            }
        }
        return 0;
    }

    public int ag(int n2, int n3) {
        switch (this) {
            case aKd: 
            case aKg: 
            case aKi: {
                return 0;
            }
            case aKe: 
            case aKj: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aKf: 
            case aKh: 
            case aKk: {
                return Math.max(0, n3 - n2);
            }
        }
        return 0;
    }

    public int ah(int n2, int n3) {
        switch (this) {
            case aKd: 
            case aKe: 
            case aKf: {
                return n3 - n2;
            }
            case aKg: 
            case aKh: {
                return (int)Math.round((double)(n3 - n2) * 0.5);
            }
            case aKi: 
            case aKj: 
            case aKk: {
                return 0;
            }
        }
        return 0;
    }

    public BV IO() {
        switch (this) {
            case aKd: {
                return aKk;
            }
            case aKe: {
                return aKj;
            }
            case aKf: {
                return aKi;
            }
            case aKg: {
                return aKh;
            }
            case aKh: {
                return aKg;
            }
            case aKi: {
                return aKf;
            }
            case aKj: {
                return aKe;
            }
            case aKk: {
                return aKd;
            }
        }
        return null;
    }

    public boolean IB() {
        return this == aKd || this == aKe || this == aKf;
    }

    public boolean IC() {
        return this == aKi || this == aKj || this == aKk;
    }

    public boolean ID() {
        return this == aKd || this == aKg || this == aKi;
    }

    public boolean IE() {
        return this == aKf || this == aKh || this == aKk;
    }

    public static BV dw(String string) {
        BV[] bVArray = BV.values();
        String string2 = string.toUpperCase();
        for (BV bV : bVArray) {
            if (!bV.name().equals(string2)) continue;
            return bV;
        }
        return bVArray[0];
    }
}

