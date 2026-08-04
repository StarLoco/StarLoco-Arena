/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qc
 */
public final class qc_0
extends Enum
implements ye_0 {
    public static final /* enum */ qc_0 bEJ = new qc_0("EAST", 0, 0, new int[]{1, -1});
    public static final /* enum */ qc_0 bEK = new qc_0("SOUTH_EAST", 1, 1, new int[]{1, 0});
    public static final /* enum */ qc_0 bEL = new qc_0("SOUTH", 2, 2, new int[]{1, 1});
    public static final /* enum */ qc_0 bEM = new qc_0("SOUTH_WEST", 3, 3, new int[]{0, 1});
    public static final /* enum */ qc_0 bEN = new qc_0("WEST", 4, 4, new int[]{-1, 1});
    public static final /* enum */ qc_0 bEO = new qc_0("NORTH_WEST", 5, 5, new int[]{-1, 0});
    public static final /* enum */ qc_0 bEP = new qc_0("NORTH", 6, 6, new int[]{-1, -1});
    public static final /* enum */ qc_0 bEQ = new qc_0("NORTH_EAST", 7, 7, new int[]{0, -1});
    public static final /* enum */ qc_0 bER = new qc_0("TOP", 8, 8, new int[]{0, 0});
    public static final /* enum */ qc_0 bES = new qc_0("BOTTOM", 9, 9, new int[]{0, 0});
    public static final /* enum */ qc_0 bET = new qc_0("NONE", 10, -1, new int[]{0, 0});
    private static final qc_0[] bEU;
    private static final qc_0[] bEV;
    private static final qc_0[] bEW;
    private final int m_index;
    private final int[] bEX;
    private static final /* synthetic */ qc_0[] bEY;

    public static final qc_0[] values() {
        return (qc_0[])bEY.clone();
    }

    public static qc_0 valueOf(String string) {
        return Enum.valueOf(qc_0.class, string);
    }

    /*
     * WARNING - void declaration
     */
    private qc_0() {
        void var4_2;
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.m_index = var3_1;
        this.bEX = var4_2;
    }

    public int getIndex() {
        return this.m_index;
    }

    public final int[] acJ() {
        return this.bEX;
    }

    public ye_0 acK() {
        switch (this) {
            case bEJ: {
                return bEN;
            }
            case bEQ: {
                return bEM;
            }
            case bEP: {
                return bEL;
            }
            case bEO: {
                return bEK;
            }
            case bEN: {
                return bEJ;
            }
            case bEM: {
                return bEQ;
            }
            case bEL: {
                return bEP;
            }
            case bEK: {
                return bEO;
            }
        }
        return bET;
    }

    public static qc_0 hf(int n2) {
        for (qc_0 qc_02 : bEW) {
            if (qc_02.getIndex() != n2) continue;
            return qc_02;
        }
        return bET;
    }

    public static qc_0 aG(int n2, int n3) {
        assert (-1 <= n2 && n2 <= 1);
        assert (-1 <= n3 && n3 <= 1);
        if (n2 == 0 && n3 == 0) {
            return null;
        }
        for (qc_0 qc_02 : bEU) {
            if (qc_02.acJ()[0] != n2 || qc_02.acJ()[1] != n3) continue;
            return qc_02;
        }
        return null;
    }

    public boolean acL() {
        return this.bEX[0] == 0 || this.bEX[1] == 0;
    }

    public boolean acM() {
        return this.bEX[0] != 0 && this.bEX[1] != 0;
    }

    public qc_0 acN() {
        switch (this) {
            case bEJ: 
            case bEL: 
            case bEK: {
                return bEK;
            }
            case bEP: 
            case bEO: 
            case bEN: {
                return bEO;
            }
        }
        return bET;
    }

    public qc_0 acO() {
        switch (this) {
            case bEN: 
            case bEM: 
            case bEL: {
                return bEM;
            }
            case bEJ: 
            case bEQ: 
            case bEP: {
                return bEQ;
            }
        }
        return bET;
    }

    public static qc_0[] acP() {
        return bEU;
    }

    public static qc_0[] acQ() {
        return bEV;
    }

    public static boolean a(qc_0 qc_02, qc_0 qc_03) {
        return Math.abs(qc_02.getIndex() - qc_03.getIndex()) == 4;
    }

    public qc_0 acR() {
        switch (this) {
            case bEJ: {
                return bEN;
            }
            case bEK: {
                return bEO;
            }
            case bEL: {
                return bEP;
            }
            case bEM: {
                return bEQ;
            }
            case bEN: {
                return bEJ;
            }
            case bEO: {
                return bEK;
            }
            case bEP: {
                return bEL;
            }
            case bEQ: {
                return bEM;
            }
            case bER: {
                return bES;
            }
            case bES: {
                return bER;
            }
        }
        return bET;
    }

    public qc_0 hg(int n2) {
        int n3 = this.getIndex();
        n3 += n2;
        if ((n3 %= bEU.length) < 0) {
            n3 += bEU.length;
        }
        return qc_0.hf(n3);
    }

    public static qc_0 acS() {
        return qc_0.hf(ej_0.am(8));
    }

    public qc_0 hh(int n2) {
        int n3 = this.getIndex();
        if (n3 % 2 == 0) {
            --n3;
        }
        n3 += 2 * n2;
        if ((n3 %= bEU.length) < 0) {
            n3 += bEU.length;
        }
        return qc_0.hf(n3);
    }

    public static boolean d(qc_0 qc_02) {
        for (qc_0 qc_03 : bEV) {
            if (!qc_03.equals(qc_02)) continue;
            return true;
        }
        return false;
    }

    public static qc_0[] acT() {
        return bEW;
    }

    static {
        bEY = new qc_0[]{bEJ, bEK, bEL, bEM, bEN, bEO, bEP, bEQ, bER, bES, bET};
        bEU = new qc_0[]{bEK, bEM, bEO, bEQ, bEJ, bEN, bEP, bEL};
        bEV = new qc_0[]{bEK, bEM, bEO, bEQ};
        bEW = qc_0.values();
    }
}

