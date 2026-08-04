/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Pc
 */
public class pc_0 {
    private int bDf = 0;
    private int bDg = -1;
    private byte bDh = (byte)-1;
    public final int[] bDi = new int[32];

    public pc_0() {
    }

    public pc_0(int n2, byte by, int n3) {
        this.bDh = by;
        this.bDf = n2;
        this.bDg = n3;
    }

    public final int abN() {
        return this.bDf;
    }

    public final void a(int n2, byte by, int n3) {
        this.bDf = n2;
        this.bDg = n3;
        this.bDh = by;
    }

    public final int abO() {
        return this.bDg;
    }

    public byte abP() {
        return this.bDh;
    }

    public final int a(int n2, int n3, int n4, akd_0[] akd_0Array, int n5, int n6, akd_0[] akd_0Array2) {
        assert (this.bDf > 0) : "no moverHeight defined";
        assert (this.bDg >= 0) : "no jump capacity defined";
        assert (akd_0Array != null && akd_0Array2 != null) : "startCellData and nextCellData can't be null";
        assert (n3 >= 0 && n4 > 0 && n3 + n4 < akd_0Array.length) : "startCell indexes out of bounds";
        assert (n5 >= 0 && n6 > 0 && n5 + n6 < akd_0Array2.length) : "nextCell indexes out of bounds";
        assert (n2 >= n3 && n2 < n3 + n4) : "startCellIndex out of bounds";
        assert (mo_1.a(n2, n3, n4, akd_0Array, this.bDf)) : "incoming position is not valid : " + akd_0Array[n2].aG + "," + akd_0Array[n2].aH + ", " + akd_0Array[n2].wp;
        akd_0 akd_02 = akd_0Array[n2];
        if (n6 == 1) {
            akd_0 akd_03 = akd_0Array2[n5];
            assert (!akd_03.cCI) : "only one element on the cell, and can move through. We'll fall forever. Cell : " + akd_03.aG + ", " + akd_03.aH;
            if (akd_03.cCJ == -1) {
                return 0;
            }
            int n7 = akd_03.wp - akd_02.wp;
            if ((n7 < 0 ? -n7 : n7) > this.bDg) {
                return 0;
            }
            if (!pc_0.a(akd_02, akd_03)) {
                return 0;
            }
            if (n7 <= 0) {
                this.bDi[0] = n5;
                return 1;
            }
            if (n2 == n3 + n4 - 1) {
                this.bDi[0] = n5;
                return 1;
            }
            int n8 = akd_03.wp + this.bDf;
            for (int j = n2 + 1; j < n3 + n4; ++j) {
                akd_0 akd_04 = akd_0Array[j];
                if (akd_04.wp - akd_04.aba >= n8) {
                    this.bDi[0] = n5;
                    return 1;
                }
                if (akd_04.cCI) continue;
                return 0;
            }
            this.bDi[0] = n5;
            return 1;
        }
        int n9 = 0;
        block1: for (int j = n5; j < n5 + n6; ++j) {
            int n10;
            akd_0 akd_05 = akd_0Array2[j];
            if (akd_05.cCJ == -1 || akd_05.cCI || ((n10 = akd_05.wp - akd_02.wp) < 0 ? -n10 : n10) > this.bDg || !pc_0.a(akd_02, akd_05)) continue;
            int n11 = akd_05.wp + this.bDf;
            int n12 = Math.max(n11, akd_02.wp + this.bDf);
            block2: for (int i2 = j + 1; i2 < n5 + n6; ++i2) {
                akd_0 akd_06 = akd_0Array2[i2];
                int n13 = akd_06.wp - akd_06.aba;
                if (n13 >= n12) {
                    if (n10 <= 0 || n2 == n3 + n4 - 1) break;
                    for (int i3 = n2 + 1; i3 < n3 + n4; ++i3) {
                        akd_0 akd_07 = akd_0Array[i3];
                        if (akd_07.wp - akd_07.aba >= n11) break block2;
                        if (!akd_05.cCI) continue block1;
                    }
                }
                if (!akd_06.cCI) continue block1;
            }
            this.bDi[n9++] = j;
        }
        return n9;
    }

    public final boolean a(int n2, int n3, int n4, akd_0[] akd_0Array, int n5, int n6, int n7, akd_0[] akd_0Array2) {
        int n8 = this.a(n2, n3, n4, akd_0Array, n6, n7, akd_0Array2);
        if (n8 == 0) {
            return false;
        }
        for (int j = 0; j < n8; ++j) {
            if (this.bDi[j] != n5) continue;
            return true;
        }
        return false;
    }

    public static boolean a(akd_0 akd_02, akd_0 akd_03) {
        int n2;
        int n3;
        assert (akd_02 != null) : "Can't check murfin movement validity for null cell";
        assert (akd_03 != null) : "Can't check murfin movement validity for null cell";
        int n4 = akd_02.azH();
        if (n4 == (n3 = akd_03.azH())) {
            return true;
        }
        if (!pc_0.aF(n4 & 0x30, n3 & 0x30)) {
            return false;
        }
        if ((n4 & 0xFFFFFFCF) == (n3 & 0xFFFFFFCF)) {
            return true;
        }
        int n5 = akd_02.azG();
        if (n5 == (n2 = akd_03.azG())) {
            int n6 = n4 & 0xFFFFFF0F;
            int n7 = n3 & 0xFFFFFF0F;
            return n6 == n7 || n5 == 64;
        }
        switch (n5) {
            case 0: {
                return n2 == 64;
            }
            case 128: {
                return n2 == 192;
            }
            case 64: {
                return n2 == 0 || n2 == 192;
            }
            case 192: {
                return n2 == 64 || n2 == 128;
            }
        }
        assert (false) : "Type de Cellule non connu poru valider un murfin : " + n5;
        return false;
    }

    private static boolean aF(int n2, int n3) {
        if (n2 == n3) {
            return true;
        }
        return (n2 | n3) == 48;
    }
}

