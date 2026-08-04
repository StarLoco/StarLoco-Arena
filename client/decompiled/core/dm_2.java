/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dM
 */
enum dm_2 {
    my(false, false),
    mz(false, true),
    mA(true, true),
    mB(true, true),
    mC(false, true),
    mD(true, true),
    mE(true, true),
    mF(true, false),
    mG(true, false);

    private boolean ba;
    private boolean mH;

    /*
     * WARNING - void declaration
     */
    private dm_2() {
        void var4_2;
        void var3_1;
        this.ba = var3_1;
        this.mH = var4_2;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public boolean isVertical() {
        return this.mH;
    }

    private static dm_2 a(boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        if (bl2) {
            if (bl4) {
                return mA;
            }
            if (bl5) {
                return mB;
            }
            return mz;
        }
        if (bl3) {
            if (bl4) {
                return mD;
            }
            if (bl5) {
                return mE;
            }
            return mC;
        }
        if (bl4) {
            return mF;
        }
        if (bl5) {
            return mG;
        }
        return my;
    }

    public static dm_2 a(int n2, int n3, aab_2 aab_22, aht_1 aht_12) {
        return dm_2.a(dm_2.a(0, 0, n3), dm_2.b(aht_12.getHeight(), n3, aab_22.getHeight()), dm_2.d(aht_12.getWidth(), n2, aab_22.getWidth()), dm_2.c(0, 0, n2));
    }

    public static dm_2 a(int n2, int n3, aab_2 aab_22, aab_2 aab_23) {
        return dm_2.a(n2, n3, aab_22.getWidth(), aab_22.getHeight(), aab_23.getX(), aab_23.getY(), aab_23.getWidth(), aab_23.getHeight());
    }

    public static dm_2 a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        return dm_2.a(dm_2.a(n7, n9, n3) && dm_2.d(n2, n4, n6, n8), dm_2.b(n7, n3, n5) && dm_2.d(n2, n4, n6, n8), dm_2.d(n6, n2, n4) && dm_2.e(n3, n5, n7, n9), dm_2.c(n6, n8, n2) && dm_2.e(n3, n5, n7, n9));
    }

    public int a(int n2, aab_2 aab_22) {
        switch (this) {
            case mB: 
            case mG: 
            case mE: {
                return 0;
            }
            case mA: 
            case mF: 
            case mD: {
                return aab_22.getContainer().getWidth() - aab_22.getWidth();
            }
        }
        return n2;
    }

    public int b(int n2, aab_2 aab_22) {
        switch (this) {
            case mB: 
            case mA: 
            case mz: {
                return 0;
            }
            case mE: 
            case mD: 
            case mC: {
                return aab_22.getContainer().getHeight() - aab_22.getHeight();
            }
        }
        return n2;
    }

    public int a(int n2, aab_2 aab_22, aab_2 aab_23) {
        switch (this) {
            case mA: 
            case mF: 
            case mD: {
                return aab_22.getX() - aab_23.getWidth();
            }
            case mB: 
            case mG: 
            case mE: {
                return aab_22.getX() + aab_22.getWidth();
            }
        }
        return n2;
    }

    public int b(int n2, aab_2 aab_22, aab_2 aab_23) {
        switch (this) {
            case mE: 
            case mD: 
            case mC: {
                return aab_22.getY() - aab_23.getHeight();
            }
            case mB: 
            case mA: 
            case mz: {
                return aab_22.getY() + aab_22.getHeight();
            }
        }
        return n2;
    }

    private static boolean a(int n2, int n3, int n4) {
        return Math.abs(n2 + n3 - n4) < 20;
    }

    private static boolean b(int n2, int n3, int n4) {
        return Math.abs(n3 + n4 - n2) < 20;
    }

    private static boolean c(int n2, int n3, int n4) {
        return Math.abs(n2 + n3 - n4) < 20;
    }

    private static boolean d(int n2, int n3, int n4) {
        return Math.abs(n3 + n4 - n2) < 20;
    }

    private static boolean d(int n2, int n3, int n4, int n5) {
        return n2 <= n4 && n2 + n3 > n4 || n4 <= n2 && n4 + n5 > n2;
    }

    private static boolean e(int n2, int n3, int n4, int n5) {
        return n2 <= n4 && n2 + n3 > n4 || n4 <= n2 && n4 + n5 > n2;
    }
}

